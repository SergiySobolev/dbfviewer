package sbk.dbfviewer.viewers;
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
	public DbfTable getTable(String path){
		return dbfViewer.getTable(path);
	}
}
