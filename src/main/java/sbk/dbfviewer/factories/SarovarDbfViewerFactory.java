package sbk.dbfviewer.factories;

import sbk.dbfviewer.viewers.IDbfViewer;
import sbk.dbfviewer.viewers.SarovarDbfViewer;

public class SarovarDbfViewerFactory implements IDbfViewerFactory {
	public IDbfViewer getDbfViewer() {
		return (IDbfViewer)new SarovarDbfViewer();
	}
}
