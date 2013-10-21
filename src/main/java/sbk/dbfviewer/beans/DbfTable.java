package sbk.dbfviewer.beans;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

public class DbfTable extends ActionForm {
	String tableName;
	String fullPath;
	int NumFields;
	int NumRecords;
	ArrayList<String> fieldsCaptions;
	ArrayList<ArrayList<String>> records;
	String path;
	public int getNumFields() {
		return NumFields;
	}
	public void setNumFields(int numFields) {
		NumFields = numFields;
	}
	public int getNumRecords() {
		return NumRecords;
	}
	public void setNumRecords(int numRecords) {
		NumRecords = numRecords;
	}
	public ArrayList<String> getFieldsCaptions() {
		return fieldsCaptions;
	}
	public void setFieldsCaptions(ArrayList<String> fieldsCaptions) {
		this.fieldsCaptions = fieldsCaptions;
	}
	public ArrayList<ArrayList<String>> getRecords() {
		return records;
	}
	public void setRecords(ArrayList<ArrayList<String>> records) {
		this.records = records;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getFullPath() {
		return fullPath;
	}
	public void setFullPath(String fullPath) {
		this.fullPath = fullPath;
	}
	public DbfTable(){
		fieldsCaptions = new ArrayList<String>();
		records = new ArrayList<ArrayList<String>>();
	}
	public boolean isRecordWithNumber(int rowId, Object[] row){
		final int orderColumnNum = 0;
		final int startColumnNum = 1;		
		try{
			if(records != null){
				for(ArrayList<String> l : records){
					if(Integer.parseInt(l.get(orderColumnNum)) == rowId){					
						for(int i=0;i<l.size()-1;i++){
							String rowValue = row[i].toString().trim();
							String tableValue = l.get(i+1).trim();
							if(!rowValue.equals(tableValue)){
								return false;
							}							
						}
						return true;
					}
				}
			} 
		}catch(NullPointerException ex){
			return false;
		}
		return false;
	}
}
