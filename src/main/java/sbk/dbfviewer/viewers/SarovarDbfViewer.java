package sbk.dbfviewer.viewers;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import com.linuxense.javadbf.DBFException;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFReader;

import sbk.dbfviewer.beans.DbfTable;
public class SarovarDbfViewer implements IDbfViewer {
	DbfTable dbfTable ;
	public DbfTable getTable(String path) {	
		try {
			InputStream inputStream = new FileInputStream(path);
			DBFReader reader = new DBFReader(inputStream);			
			int numberOfFields = reader.getFieldCount();
			int numberOfRecords = reader.getRecordCount();
			String tableName = path.substring(path.lastIndexOf("\\")+1, path.length()-4);	 		
			dbfTable.setTableName(tableName);
			dbfTable.setNumFields(numberOfFields);
			dbfTable.setNumRecords(numberOfRecords);
			for(int i=0;i<numberOfFields;i++){
				DBFField field = reader.getField(i);
				dbfTable.getFieldsCaptions().add(field.getName());
			}
			Object[] 	rowObjects;
			int 	 	rowCount = 0;
			while( (rowObjects = reader.nextRecord()) != null){							
				dbfTable.getRecords().add(new ArrayList<String>());				
				for(int i=0;i<rowObjects.length;i++){
					dbfTable.getRecords().get(rowCount).add(rowObjects[i].toString().trim());
				}
				rowCount++;	
			}			
		} catch (FileNotFoundException e) {			
			e.printStackTrace();
		}
		catch (DBFException e) {			
			e.printStackTrace();
		}
		return dbfTable;
	}
	public SarovarDbfViewer(){
		dbfTable = new DbfTable();
	}
}
