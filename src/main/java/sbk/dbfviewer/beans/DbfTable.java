package sbk.dbfviewer.beans;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

public class DbfTable extends ActionForm {
	String tableName;
	int NumFields;
	int NumRecords;
	ArrayList<String> fieldsCaptions;
	ArrayList<ArrayList<String>> records;
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
	public DbfTable(){
		fieldsCaptions = new ArrayList<String>();
		records = new ArrayList<ArrayList<String>>();
	}
}
