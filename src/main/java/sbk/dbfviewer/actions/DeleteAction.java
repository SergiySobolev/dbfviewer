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

public class DeleteAction extends Action {
	private final String INDEX  =   "index";
	private final String ROWID	=	"row_id";
	private final String FILE	=	"file";
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
			DbfTable 		table 		= 	(DbfTable)form;
			DbfViewer		dbfViewer   = 	new DbfViewer();
			int	 			row_id  	=  	Integer.valueOf(request.getParameter(ROWID));
			String 			file  		= 	request.getParameter(FILE);
			if(file==null){
				file = DbfViewerUtils.findCookie(request.getCookies(), FILE);
			}
			if(file != null){
				table = dbfViewer.delete(file, row_id);
				request.setAttribute(FILE, file);
			}
			return mapping.findForward(INDEX);	
	}
}
