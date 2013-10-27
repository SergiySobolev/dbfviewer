package sbk.dbfviewer.actions;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sbk.dbfviewer.beans.DbfTable;
import sbk.dbfviewer.utils.DbfViewerUtils;
import sbk.dbfviewer.viewers.DbfViewer;

public class FilterAction extends Action {
	final private String INDEX  		= 	"index";
	final private String FILE   		= 	"file";
	final private String TABLE  		= 	"table";
	final private String CURRENTPAGE	=	"currentPage";
	final private String ENCODING		=	"encoding";
	final private String PAGENUM		=	"pgNum";
	final private String FILTERMAP		=	"filtermap";
	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {		
		String file = (String) request.getSession().getAttribute(FILE);
		if(file == null){
			file = DbfViewerUtils.findCookie(request.getCookies(), FILE);
		}
		DbfTable  		resultTable;
		DbfViewer		dbfViewer;
		if(file != null){			
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
			if(file != null){
				dbfViewer 	= new DbfViewer();
				Map<String, String> filterMap = new HashMap<String, String>();
				String[] fields = request.getParameterValues("filterField");
				String[] values = request.getParameterValues("filterValue");
				if(fields == null && values == null) {
					 filterMap = (Map<String, String>) request.getSession().getAttribute(FILTERMAP);					 
				}
				else{
					String field, value;
					//suppose fields.length = values.length
					for(int i=0;i<fields.length;i++){
						field = fields[i];
						value = values[i];
						try {
							byte[] valueByte = value.getBytes("iso8859-1");
							value = new String(valueByte, "utf-8");
						} catch (UnsupportedEncodingException e) {						
						}					
						if(value != null && !("").equals(value)){ //empty text field
							filterMap.put(field, value);
						}
					}
					request.getSession().setAttribute(FILTERMAP, filterMap);
				}				
				resultTable = dbfViewer.getTable(file, pgNum, (pgNum-1)*recordsOnPage, (pgNum)*recordsOnPage, encoding, filterMap);
				if(resultTable != null){
					request.setAttribute(TABLE, resultTable);
					request.setAttribute(FILE, file);
					request.getSession().setAttribute(FILE, file);
					request.getSession().setAttribute(TABLE, resultTable);
					DbfViewerUtils.addCookie(FILE, file, response);		
				}
			}
		}
		return mapping.findForward(INDEX);
	}
}
