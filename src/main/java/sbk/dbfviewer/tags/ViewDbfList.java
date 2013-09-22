package sbk.dbfviewer.tags;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ViewDbfList extends SimpleTagSupport {
	public void doTag() throws JspException, IOException {
		JspContext jspContext = this.getJspContext();
		ServletContext servletContext = ((PageContext) jspContext).getServletContext();
		String dbfFilesPath = servletContext.getRealPath("/") + "db\\";
		JspWriter out = getJspContext().getOut();
		File f = new File(dbfFilesPath);
		if (f.exists()) {
			ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));					
			out.println("<ul>");
			for (String s : names) {
				out.print("<li>");
				out.print(String.format(
						"<a href='/dbfviewer/index.do?file=%s%s'>%s</a>",
						dbfFilesPath, s, s));
				out.println("</li>");
			}
			out.println("</ul>");
		}
	}
}
