package gizmo.environmentmanager.connectors;

import gizmo.environmentmanager.Environnement;

import java.io.OutputStream;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.PoolableObjectFactory;

public class FTPPool extends BasePoolableObjectFactory implements
		PoolableObjectFactory {

	private Environnement environnement;
	private OutputStream ostrm;

	public FTPPool(Environnement environnement, OutputStream ostrm) {
		this.environnement= environnement;
		this.ostrm= ostrm;
	}

	@Override
	public Object makeObject() throws Exception {
		// TODO Auto-generated method stub
		return new GizmoFTPClient(environnement, ostrm);
	}

}
