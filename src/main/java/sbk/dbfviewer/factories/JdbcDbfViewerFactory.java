package sbk.dbfviewer.factories;

import sbk.dbfviewer.viewers.IDbfViewer;
import sbk.dbfviewer.viewers.JdbcDbfViewer;
import sbk.dbfviewer.viewers.SarovarDbfViewer;

public class JdbcDbfViewerFactory implements IDbfViewerFactory {
	public IDbfViewer getDbfViewer() {
		return (IDbfViewer)new JdbcDbfViewer();
	}
}
