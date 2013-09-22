package sbk.dbfviewer.viewers;
import sbk.dbfviewer.beans.DbfTable;
public interface IDbfViewer {
	DbfTable	getTable(String path); 
}
