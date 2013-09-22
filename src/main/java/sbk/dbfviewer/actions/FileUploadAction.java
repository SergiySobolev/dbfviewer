package sbk.dbfviewer.actions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import sbk.dbfviewer.beans.FileUploadForm;

public class FileUploadAction extends Action {
	private final String INDEX = "index";

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
	        try {
				response.addCookie(new Cookie("file", URLEncoder.encode(newFile.getAbsolutePath(),"UTF-8")));
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
