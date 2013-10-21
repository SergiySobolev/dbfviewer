package sbk.dbfviewer.actions;

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

public class EditAction extends Action {
	private final String INDEX = "index";	
	private final String ROWNUMBER  = "row_number";
	private final String TABLE = "table";
	private final String EDITPREFIX = "edit";
	private final String VIEW	=	"view";
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String 			rowNumber 	= 	request.getParameter(ROWNUMBER);
		DbfViewer		dbfViewer   = 	new DbfViewer();
		DbfTable table = (DbfTable) request.getSession().getAttribute(TABLE);
		String   editName, editValue;		
		Map<String, String> parametersMap = new HashMap<String, String>();
		for(int i=1;i<table.getFieldsCaptions().size();i++){
			String field = table.getFieldsCaptions().get(i);
			editName = String.format("%s%s_%s", EDITPREFIX,field,rowNumber);
			editValue = request.getParameter(editName);
			parametersMap.put(field, editValue);
		}
		table = dbfViewer.edit(table, Integer.parseInt(rowNumber),parametersMap, true);
		request.getSession().setAttribute(TABLE, table);
		request.setAttribute(TABLE, table);
		return mapping.findForward(VIEW);	
	}
}
