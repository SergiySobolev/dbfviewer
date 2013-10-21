package sbk.dbfviewer.actions;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import sbk.dbfviewer.beans.FileUploadForm;
import sbk.dbfviewer.utils.DbfViewerUtils;

public class FileUploadAction extends Action {
	private final String INDEX  = "index";
	private final String FILE 	= "file";
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {			
		FileUploadForm fileUploadForm = (FileUploadForm) form;
		FormFile file = fileUploadForm.getFile();		
		String filePath = getServlet().getServletContext().getRealPath("/") + "db";	
		File folder = new File(filePath);
		if (!folder.exists()) {
			folder.mkdir();
		}
		String fileName = file.getFileName();		 
	    if(!("").equals(fileName)){ 	       
	        File newFile = new File(filePath, fileName); 
	        if(!newFile.exists()){
	          FileOutputStream fos = new FileOutputStream(newFile);
	          fos.write(file.getFileData());
	          fos.flush();
	          fos.close();
	        }   
	        request.setAttribute("uploadedFilePath",newFile.getAbsoluteFile());
	        request.setAttribute("uploadedFileName",newFile.getName());	   
	        DbfViewerUtils.addCookie(FILE, newFile.getAbsoluteFile().toString(), response);	 
	        request.setAttribute(FILE, newFile.getAbsoluteFile().toString());
	    }
		return mapping.findForward(INDEX);
	}
}
