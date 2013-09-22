package sbk.dbfviewer.actions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sbk.dbfviewer.beans.DbfTable;
import sbk.dbfviewer.viewers.DbfViewer;
public class ViewAction extends Action {
	final private String INDEX = "index";
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) {
		DbfTable  resultTable = (DbfTable)form;
		DbfViewer dbfViewer;
		String file	= request.getParameter("file");
		if(file == null){
			file = this.findCookie(request.getCookies(), "file");
		}
		if(file != null){
			dbfViewer 	= new DbfViewer();
			resultTable = dbfViewer.getTable(file);	
			request.setAttribute("table", resultTable);
			try {
				response.addCookie(new Cookie("file", URLEncoder.encode(file,"UTF-8")));
			} catch (UnsupportedEncodingException e) {				
				e.printStackTrace();
			}			
		}
		return mapping.findForward(INDEX);
	}
	public  String findCookie(Cookie[] cookies, String key){
		if(cookies != null){
			for(int i=0;i<cookies.length;i++){
				String name = cookies[i].getName();
				if(name.compareTo(key) == 0){
					try {
						return URLDecoder.decode(cookies[i].getValue(), "UTF-8");
					} catch (UnsupportedEncodingException e) {
						return null;
					}
				}
			}
		}
		return null;
	}
}
