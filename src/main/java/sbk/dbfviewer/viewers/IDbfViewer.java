package sbk.dbfviewer.viewers;
import java.util.Map;
import sbk.dbfviewer.beans.DbfTable;
public interface IDbfViewer {
	DbfTable	getTable(String path, int recordsOnPage, int recordsFrom, int recordsTo, String encoding); 
	DbfTable	getTable(String path, int recordsOnPage, int recordsFrom, int recordsTo, String encoding, Map<String, String> filterMap);
	DbfTable	delete(String path, int rowId);
	DbfTable	select(DbfTable table, Map<String, String> parameters);
	boolean 	makeDbfFileFromDbfTable(DbfTable table, String path, boolean isSameStructure);
	DbfTable 	edit(DbfTable table,int row, Map<String, String> parameters, boolean overwrite );
	int			getRowCount(String path, Map<String, String> filterMap);		  		
}
