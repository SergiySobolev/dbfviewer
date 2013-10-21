package sbk.dbfviewer.viewers;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.chain.web.MapEntry;

import sbk.dbfviewer.beans.DbfTable;

public class JdbcDbfViewer implements IDbfViewer {
	public final String orderField = "RecNum";
	DbfTable dbfTable;
	public DbfTable getTable(String path, int recordsOnPage, int recordsFrom, int recordsTo, String encoding, Map<String, String> filterMap) {
		if(filterMap.isEmpty() || filterMap == null){
			return  this.getTable(path, recordsOnPage, recordsFrom, recordsTo, encoding);			
		}
		try{
			this.getTableStructure(path);
			Class.forName("com.hxtt.sql.dbf.DBFDriver");
	        String connString = String.format("%s%s", "jdbc:dbf:/", dbfTable.getPath());
	        java.util.Properties prop = new java.util.Properties();
	        prop.put("charSet", encoding);
	        if(encoding == "cp866"){
	        	prop.put("CODEPAGEID", "66");
	        }
	        if(encoding == "cp1251"){
	        	prop.put("CODEPAGEID", "C9");
	        }
            Connection connection=DriverManager.getConnection(connString, prop);  	    
	        String conditions = this.makeConditionsString(filterMap, encoding);
	    //    String query = String.format("select  recNo() as %s, * from %s  where (recno()>=%s and recno()<%s) and %s order by %s", orderField, dbfTable.getTableName(), recordsFrom, recordsTo, conditions, orderField);	
	        String query = String.format("select  recNo() as %s, * from (select * from %s  where  %s )", orderField, dbfTable.getTableName(), conditions);	
	        Statement stmt=connection.createStatement();
            ResultSet resultSet=stmt.executeQuery(query);
            ArrayList<ArrayList<String>> records =  new ArrayList<ArrayList<String>>() ;
            ArrayList<String> row = new ArrayList<String>();
            dbfTable.setRecords(records);
            Integer rowCount = 0;
            while(resultSet.next()){
            	dbfTable.getRecords().add(new ArrayList<String>());
				row = dbfTable.getRecords().get(rowCount++);				
				for (int i = 1; i <= dbfTable.getFieldsCaptions().size(); i++) {	
							String value = resultSet.getString(i);
							if(value != null){
								byte[] bytes = resultSet.getBytes(i);
								value = new String(bytes, encoding);								
							}
							if(value == null) value = "";
							row.add(String.format("%s", value));
				}				
            }
            resultSet.close();
            stmt.close();
            connection.close();
		}catch(Exception ex){
			return null;
		}
		
		return dbfTable;
	}
	public DbfTable getTable(String path, int recordsOnPage, int recordsFrom, int recordsTo, String encoding) {
	//	DbfTable dbfTable = new DbfTable();	
		try{			
			this.getTableStructure(path);
			Class.forName("com.hxtt.sql.dbf.DBFDriver");
            String connString = String.format("%s%s", "jdbc:dbf:/", dbfTable.getPath());
            java.util.Properties prop  =  new java.util.Properties();
            prop.put("charSet", "Cp866");
            prop.put("CODEPAGEID", "66");
            Connection connection=DriverManager.getConnection(connString, prop);            
            String query = String.format("select  recNo() as %s, * from %s  where recno()>=%s and recno()<%s  order by %s", orderField, dbfTable.getTableName(), recordsFrom, recordsTo, orderField);
        //    query = String.format("select recNo() as %s,* from (SELECT * from student)", orderField);
            Statement stmt=connection.createStatement();            
            ResultSet resultSet=stmt.executeQuery(query);             
    //        ArrayList<String> fields = dbfTable.getFieldsCaptions();
            ArrayList<ArrayList<String>> records =  new ArrayList<ArrayList<String>>() ;
            ArrayList<String> row = new ArrayList<String>();
            dbfTable.setRecords(records);
            Integer rowCount = 0;          
            while(resultSet.next()){
            	dbfTable.getRecords().add(new ArrayList<String>());
				row = dbfTable.getRecords().get(rowCount++);				
				for (int i = 1; i <= dbfTable.getFieldsCaptions().size(); i++) {	
							String value = resultSet.getString(i);
							if(value != null){
								byte[] bytes = resultSet.getBytes(i);
								value = new String(bytes, encoding);								
							}
							if(value == null) value = "";
							row.add(String.format("%s", value));
				}				
            }
            resultSet.close();
            stmt.close();
            connection.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return dbfTable;
	}
	public DbfTable delete(String path, int rowId) {		
		return null;
	}
	public DbfTable select(DbfTable table, Map<String, String> parameters) {		
		return null;
	}
	public boolean makeDbfFileFromDbfTable(DbfTable table, String path,
			boolean isSameStructure) {		
		return false;
	}
	public DbfTable edit(DbfTable table, int row,
			Map<String, String> parameters, boolean overwrite) {		
		return null;
	}
	public void getTableStructure(String path){			
		try{
			Properties connInfo = new java.util.Properties();
			connInfo.put("charSet", "ibm866");
			Class.forName("com.hxtt.sql.dbf.DBFDriver");
			String pathToTable	=	path.substring(0,path.lastIndexOf("\\"));
	        String connString = String.format("%s%s", "jdbc:dbf:/", pathToTable);
	        Connection connection=DriverManager.getConnection(connString);		        
	        String tableName = path.substring(path.lastIndexOf("\\") + 1,path.length() - 4);	
	        dbfTable = new DbfTable();
			dbfTable.setPath(pathToTable);
			dbfTable.setTableName(tableName);
			dbfTable.setFullPath(path);
	        String strctQuery = String.format("select * from %s", dbfTable.getTableName());
	        PreparedStatement stmt = connection.prepareStatement(strctQuery);
	        ResultSet resultSet = stmt.executeQuery();
	        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
	        int numberOfFields = resultSetMetaData.getColumnCount();
	        dbfTable.setNumFields(numberOfFields);
	        ArrayList<String> fieldCaptions = dbfTable.getFieldsCaptions();
			fieldCaptions.add(orderField);
			String columnLabel;
	        for(int i=1; i <= numberOfFields;i++){	
	        	columnLabel = resultSetMetaData.getColumnLabel(i);
	        	columnLabel = columnLabel.substring(columnLabel.lastIndexOf(".") + 1, columnLabel.length());
	        	fieldCaptions.add(columnLabel);
	        }	       
		}catch(ClassNotFoundException ex){
			dbfTable = null;
		}catch (SQLException e) {
			dbfTable = null;
		}
	}
	public static void main(String[] args) {
		JdbcDbfViewer viewer = new JdbcDbfViewer();
		System.out.println(viewer.getTable("d:\\db\\Table5.dbf", 50,0,50, "cp866"));		
	}
	public int getRowCount(String path, Map<String, String> filterMap) {
		try {
			Class.forName("com.hxtt.sql.dbf.DBFDriver");		
			String connString = String.format("%s%s", "jdbc:dbf:/", path.substring(0,path.lastIndexOf("\\")));
			Connection connection=DriverManager.getConnection(connString);
			String query = String.format("select count(*) from  %s", path.substring(path.lastIndexOf("\\") + 1,path.length() - 4));
			Statement stmt=connection.createStatement();
			ResultSet resultSet=stmt.executeQuery(query);  
			resultSet.next();
			return resultSet.getInt(1);
		} catch (ClassNotFoundException e) {
			return 0;
		} catch (SQLException e){
			return 0;
		}
	}	
	String makeConditionsString(Map<String, String> filterMap, String encoding){
		StringBuilder mcsBuilder = new StringBuilder(" ");
		int condCount = 0;
		String condition = null;
		for(Map.Entry<String, String> me: filterMap.entrySet()){
		//	if(condCount == 0)  mcsBuilder.append(" where ");
			if(condCount >  0) 	mcsBuilder.append(" and ");
			String field = me.getKey();
			String value = me.getValue();
			//byte[] valueBytes = me.getValue().getBytes();
			//String value="";
			//try {
			//	value = new String(valueBytes, encoding);
			//} catch (UnsupportedEncodingException e) {				
			//}
			condition = String.format("(%s = '%s')", field, value);
			mcsBuilder.append(condition);
			condCount++;			
		}
		return mcsBuilder.toString();
	}
}
