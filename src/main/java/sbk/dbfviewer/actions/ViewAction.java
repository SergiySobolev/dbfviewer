package sbk.dbfviewer.actions;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sbk.dbfviewer.beans.DbfTable;
import sbk.dbfviewer.utils.DbfViewerUtils;
import sbk.dbfviewer.viewers.DbfViewer;
public class ViewAction extends Action {
	final private String INDEX  		= 	"index";
	final private String FILE   		= 	"file";
	final private String TABLE  		= 	"table";
	final private String CURRENTPAGE	=	"currentPage";
	final private String ENCODING		=	"encoding";
	final private String PAGENUM		=	"pgNum";
	final private String FILTERMAP		=	"filtermap";
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {	
		DbfTable  resultTable;
		DbfViewer dbfViewer;	
		response.setCharacterEncoding("utf-8");
		String file	= request.getParameter("file");		
		String encoding;
		int recordsOnPage = 0;
		try{
			recordsOnPage = Integer.parseInt(request.getParameter("recordsOnPage"));
		}catch(NumberFormatException ex){
			recordsOnPage = 50;
		}		
		int pgNum = 1;
		String pageNum = request.getParameter(PAGENUM);
		if(pageNum == null){
			try{
				pageNum =  request.getSession().getAttribute(CURRENTPAGE).toString();
			}catch(NullPointerException ex){
				pageNum = "1";
			}
		}		
		try{			
			pgNum = Integer.parseInt(pageNum);			
			request.getSession().setAttribute(CURRENTPAGE, pgNum);			
		}catch(NumberFormatException ex){
		}		
		encoding = (String) request.getParameter(ENCODING);
		if(encoding == null){
			encoding = "cp866";
		}
		request.getSession().setAttribute(ENCODING, encoding);
		//view full table, clear all filters
		request.getSession().setAttribute(FILTERMAP, null);
		if(file == null){
			file = DbfViewerUtils.findCookie(request.getCookies(), FILE);
		}
		if(file != null){
			dbfViewer 	= new DbfViewer();
			//resultTable = new DbfTable();
			resultTable = dbfViewer.getTable(file, pgNum, (pgNum-1)*recordsOnPage, (pgNum)*recordsOnPage, encoding);
			if(resultTable != null){
				request.setAttribute(TABLE, resultTable);
				request.setAttribute(FILE, file);
				request.getSession().setAttribute(FILE, file);
				request.getSession().setAttribute(TABLE, resultTable);
				DbfViewerUtils.addCookie(FILE, file, response);		
			}
		}
		return mapping.findForward(INDEX);
	}	
}
