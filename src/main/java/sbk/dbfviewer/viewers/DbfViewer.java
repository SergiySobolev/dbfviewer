package sbk.dbfviewer.viewers;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import sbk.dbfviewer.beans.DbfTable;
import sbk.dbfviewer.factories.IDbfViewerFactory;
public class DbfViewer {
	IDbfViewer dbfViewer;
	static String factoryName;
	static Class  factoryClass;
	static{
		try{
			InitialContext ctx = new InitialContext();
			factoryName = (String) ctx.lookup("java:comp/env/dbfmanager.ObjectFactory");
			factoryClass = Class.forName(factoryName);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		} catch (NamingException e) {			
			e.printStackTrace();
		}
	}
	public DbfViewer(){
		try{
			IDbfViewerFactory dbfViewerFactory = (IDbfViewerFactory) factoryClass.newInstance();
			dbfViewer	=	dbfViewerFactory.getDbfViewer();
		}catch(Exception ex){
			ex.printStackTrace();			
		}
	}	
	public DbfTable getTable(String path, int recordsOnPage, int recordsFrom, int recordsTo, String encoding, Map<String, String> filterMap){
		return dbfViewer.getTable(path, recordsOnPage, recordsFrom, recordsTo, encoding, filterMap);
	}
	public DbfTable getTable(String path, int recordsOnPage, int recordsFrom, int recordsTo, String encoding){
		return dbfViewer.getTable(path, recordsOnPage, recordsFrom, recordsTo, encoding);
	}
	public DbfTable delete(String path, int rowId){
		return dbfViewer.delete(path, rowId);
	}	
	public DbfTable select(DbfTable table, Map<String, String> parameters){
		return dbfViewer.select(table, parameters);
	}
	public boolean makeDbfFileFromDbfTable(DbfTable table, String path, boolean isSameStructure){
		return dbfViewer.makeDbfFileFromDbfTable(table, path, isSameStructure);
	}
	public DbfTable edit(DbfTable table,int row, Map<String, String> parameters, boolean overwrite ){
		return dbfViewer.edit(table, row, parameters, overwrite);
	}
	public int getRowCount(String path, Map<String, String> filterMap){
		return dbfViewer.getRowCount(path, filterMap);
	}
}
