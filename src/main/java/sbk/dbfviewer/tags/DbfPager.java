package sbk.dbfviewer.tags;
import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import sbk.dbfviewer.viewers.DbfViewer;
public class DbfPager extends SimpleTagSupport{
	String tableName;
	int recOnPage;
	int currentPage;
	int groupMaxLength = 4;
	int genPageCount;
	int maxNonInterruptedPager = 12;
	@SuppressWarnings("unchecked")
	public void doTag() throws JspException, IOException {
		//JspContext jspContext = this.getJspContext();
		//ServletContext servletContext = ((PageContext) jspContext).getServletContext();
		Map<String, String> filterMap = (Map<String, String>) getJspContext().getAttribute("filtermap", PageContext.SESSION_SCOPE);
		JspWriter out = getJspContext().getOut();
		out.println("<div style=style=\"float: left\">");
		int count = 0;
		try{
			count = new DbfViewer().getRowCount(tableName, filterMap);
			if(count <= 0) return;
		}catch(Exception ex){
			return;
		}	
		genPageCount = count/recOnPage + 1;
		if(genPageCount > maxNonInterruptedPager){
			for(int i=1;i<=groupMaxLength;i++){
				out.print(String.format(" <a href='/dbfviewer/Filter.do?file=%s&pgNum=%s'>%s</a> ", tableName, i, i));
			}
			if(currentPage > groupMaxLength+2){
				out.print("....");
			}			
			if(currentPage > groupMaxLength+1 && currentPage <= genPageCount-groupMaxLength+1){
				out.print(String.format(" <a href='/dbfviewer/Filter.do?file=%s&pgNum=%s'>%s</a> ", tableName, currentPage-1, currentPage-1));
			}
			if(currentPage > groupMaxLength && currentPage <= genPageCount-groupMaxLength){
				out.print(String.format(" <a href='/dbfviewer/Filter.do?file=%s&pgNum=%s'>%s</a> ", tableName, currentPage, currentPage));
			}
			if(currentPage < genPageCount - groupMaxLength && currentPage >= groupMaxLength){
				out.print(String.format(" <a href='/dbfviewer/Filter.do?file=%s&pgNum=%s'>%s</a> ", tableName, currentPage+1, currentPage+1));
			}
			if(currentPage < genPageCount - groupMaxLength-1){
				out.print("....");
			}			
			for(int i=genPageCount-3;i<=genPageCount;i++){
				out.print(String.format(" <a href='/dbfviewer/Filter.do?file=%s&pgNum=%s'>%s</a>", tableName, i, i));
			}			
		}else{			
			for(int j=1;j<=genPageCount;j++){		
				out.print(String.format(" <a href='/dbfviewer/Filter.do?file=%s&pgNum=%s'>%s</a>", tableName, j, j));
			}
		}
		out.println("</div>");
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public int getRecOnPage() {
		return recOnPage;
	}
	public void setRecOnPage(int recOnPage) {
		this.recOnPage = recOnPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
}

