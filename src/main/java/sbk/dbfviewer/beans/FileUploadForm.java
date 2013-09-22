package sbk.dbfviewer.beans;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
public class FileUploadForm extends ActionForm {
	private FormFile file;
	public FormFile getFile() {
		return file;
	} 
	public void setFile(FormFile file) {
		this.file = file;
	}
	private String getExt(){
		String extension = "";
		String fileName = file.getFileName();
		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
		if (i > p) {
		    extension = fileName.substring(i+1);
		}
		return extension;
	}
	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request) {
	    ActionErrors errors = new ActionErrors(); 
	    if( getFile().getFileSize()== 0){
	       errors.add("common.file.err",
	    	new ActionMessage("error.common.file.required"));
	       return errors;
	    } 
	    //only allow textfile to upload
//	    String fileContentType = this.getFile().getContentType();
//	    System.out.println(fileContentType);
//	    if(!"text/plain".equals(fileContentType)){
//	        errors.add("common.file.err.ext",
//	    	 new ActionMessage("error.common.file.dbf.only"));
//	        return errors;
//	    } 
	    //only allow dbf file to upload
	    String ext = this.getExt();
	    if(!"dbf".equals(ext)){
	    	errors.add("common.file.err.ext",  new ActionMessage("error.common.file.dbf.only"));
	    	return errors;
	    }
	    
        //file size cant larger than 10kb	    
	    if(getFile().getFileSize() > 10240){ //10kb
	       errors.add("common.file.err.size",
		    new ActionMessage("error.common.file.size.limit", 10240));
	       return errors;
	    } 
	    return errors;
	}
}
