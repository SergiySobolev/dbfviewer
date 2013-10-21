package sbk.dbfviewer.viewers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;
import com.linuxense.javadbf.DBFWriter;

import sbk.dbfviewer.beans.DbfTable;

public class SarovarDbfViewer implements IDbfViewer {
	public final String orderField = "¹";
	DbfTable dbfTable;
	public DbfTable getTable(String path, int recordsOnPage, int recordsFrom, int recordsTo, String encoding, Map<String, String> filterMap) {	
		return null;
	}
	public DbfTable getTable(String path, int recordsOnPage, int recordsFrom, int recordsTo, String encoding) {
		try {
			InputStream inputStream = new FileInputStream(path);
			DBFReader reader = new DBFReader(inputStream);
			reader.setCharactersetName("ibm866");
			int numberOfFields = reader.getFieldCount();
			int numberOfRecords = reader.getRecordCount();
			String tableName = path.substring(path.lastIndexOf("\\") + 1,
					path.length() - 4);
			dbfTable.setPath(path);
			dbfTable.setTableName(tableName);
			dbfTable.setNumFields(numberOfFields);
			dbfTable.setNumRecords(numberOfRecords);
			// order column for row definition
			ArrayList<String> fieldCaptions = dbfTable.getFieldsCaptions();
			fieldCaptions.add(orderField);
			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				fieldCaptions.add(field.getName());
			}
			Object[] rowObjects;
			Integer rowCount = 0;
			ArrayList<String> row = null;
			while ((rowObjects = reader.nextRecord()) != null) {
				dbfTable.getRecords().add(new ArrayList<String>());
				row = dbfTable.getRecords().get(rowCount);
				row.add(rowCount.toString());
				for (int i = 0; i < rowObjects.length; i++) {
					dbfTable.getRecords().get(rowCount)
							.add(rowObjects[i].toString().trim());
				}
				rowCount++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DBFException e) {
			e.printStackTrace();
		}
		return dbfTable;
	}
	public DbfTable delete(String path, int rowId) {
		InputStream ios;
		try {
			ios = new FileInputStream(path);
			this.getTable(path, 0, 0, 0, "cp866");
			DBFReader reader = new DBFReader(ios);
			int numberOfFields = reader.getFieldCount();
			DBFWriter writer = new DBFWriter();
			DBFField[] fields = new DBFField[numberOfFields];
			for (int i = 0; i < numberOfFields; i++) {
				fields[i] = reader.getField(i);
			}
			writer.setFields(fields);
			Object[] rowData = new Object[numberOfFields];
			while ((rowData = reader.nextRecord()) != null) {
				if (dbfTable != null
						&& dbfTable.isRecordWithNumber(rowId, rowData)) {
					continue;
				}
				writer.addRecord(rowData);
			}
			File f = new File(path);
			FileOutputStream fos = new FileOutputStream(f);
			writer.write(fos);
			fos.close();
			ios.close();
		} catch (Exception e) {
			return null;
		}
		return null;
	}
	public DbfTable select(DbfTable table, Map<String, String> parameters) {
		DbfTable newTable = new DbfTable();
		ArrayList<ArrayList<String>> rowsToRemove = new ArrayList<ArrayList<String>>();
		newTable.setTableName(table.getTableName());
		newTable.setPath(table.getPath());
		newTable.setNumFields(table.getNumFields());
		newTable.setFieldsCaptions(table.getFieldsCaptions());
		newTable.setRecords(table.getRecords());
		ArrayList<ArrayList<String>> records = newTable.getRecords();
		for (ArrayList<String> row : records) {
			for (int i = 1; i < row.size(); i++) {
				String fieldName = table.getFieldsCaptions().get(i);
				String parameterValue = parameters.get(fieldName);
				if (parameterValue == null) {
					continue;
				}
				String cellValue = row.get(i);
				if (!cellValue.equals(parameterValue)) {
					rowsToRemove.add(row);
					break;
				}
			}
		}
		records.removeAll(rowsToRemove);
		newTable.setNumRecords(newTable.getRecords().size());
		return newTable;
	}
	public boolean makeDbfFileFromDbfTable(DbfTable table, String path, boolean isSameStructure) {
		DBFWriter writer = new DBFWriter();
		InputStream ios;
		int numberOfFields;
		try {
			ios = new FileInputStream(path);
			this.getTable(path, 0, 0, 0, "cp866");
			DBFReader reader = new DBFReader(ios);
			if(isSameStructure){
				numberOfFields = reader.getFieldCount();
			}else{
				numberOfFields = table.getNumFields();
			}
			DBFField[] fields = new DBFField[numberOfFields];
			for (int i = 0; i < numberOfFields; i++) {
				if (isSameStructure) {
					fields[i] = reader.getField(i);
				} else {
					if(i == 0) {continue;}
					fields[i] = new DBFField();
					fields[i].setName(table.getFieldsCaptions().get(i));
					fields[i].setDataType(DBFField.FIELD_TYPE_C);
					fields[i].setFieldLength(30);
				}
			}
			writer.setFields(fields);
			ArrayList<ArrayList<String>> records = table.getRecords();			
			Object[] rowData = new Object[table.getNumFields()];
			for(ArrayList<String> row: records){
				for(int i=1;i<row.size();i++){
					rowData[i-1] = row.get(i);					
				}	
				writer.addRecord(rowData);
				rowData = new Object[table.getNumFields()];
			}
			File f = new File(path);
			FileOutputStream fos = new FileOutputStream(f);
			writer.write(fos);
			fos.close();
			ios.close();
		} catch (Exception ex) {
			return false;
		}

		return true;
	}
	public DbfTable edit(DbfTable table,int row, Map<String, String> parameters, boolean overwrite ){
		DbfTable newTable = new DbfTable();
		newTable = table;
		ArrayList<String> fields = newTable.getFieldsCaptions();
		ArrayList<String> rowToEdit = newTable.getRecords().get(row);
		int count=0;
		for(String field: fields){
			if(count == 0){
				count++;
				continue;
			}
			String newValue = parameters.get(field);
			if(newValue != null){
				rowToEdit.set(count, newValue);
			}			
			newValue = null;
			count++;
		}
		if(overwrite){
			this.makeDbfFileFromDbfTable(newTable, newTable.getPath(), true);
		}
		return newTable;
	}
	public void getTableStructure(DbfTable dbfTable, String path){
		try{
			InputStream inputStream = new FileInputStream(path);
			DBFReader reader = new DBFReader(inputStream);
			reader.setCharactersetName("ibm866");
			int numberOfFields = reader.getFieldCount();
			int numberOfRecords = reader.getRecordCount();
			String tableName = path.substring(path.lastIndexOf("\\") + 1,
					path.length() - 4);
			dbfTable.setPath(path.substring(0,path.lastIndexOf("\\")));
			dbfTable.setTableName(tableName);
			dbfTable.setNumFields(numberOfFields);
			dbfTable.setNumRecords(numberOfRecords);
			// order column for row definition
			ArrayList<String> fieldCaptions = dbfTable.getFieldsCaptions();
			fieldCaptions.add(orderField);
			for (int i = 0; i < numberOfFields; i++) {
				DBFField field = reader.getField(i);
				fieldCaptions.add(field.getName());
			}
			dbfTable.setNumFields(dbfTable.getFieldsCaptions().size());
		} catch (DBFException ex){			
		} catch (FileNotFoundException ex){			
		}
	}
	public SarovarDbfViewer() {
		dbfTable = new DbfTable();
	}
	public int getRowCount(String path) {		
		return 0;
	}
	public int getRowCount(String path, Map<String, String> filterMap) {
		// TODO Auto-generated method stub
		return 0;
	}
}
