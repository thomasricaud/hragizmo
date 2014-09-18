package gizmo.environmentmanager.connectors;

import java.io.OutputStream;

import gizmo.environmentmanager.Environnement;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.PoolableObjectFactory;

public class SSHClientFactory extends BasePoolableObjectFactory implements
		PoolableObjectFactory {

	private Environnement environment;
	private OutputStream ostrm;

	public SSHClientFactory(Environnement environment,OutputStream ostrm) {
		this.environment = environment;
		this.ostrm = ostrm;
	}

	@Override
	public Object makeObject() throws Exception {
		return new SSHClient(environment,ostrm);
	}

}
