package gizmo.environmentmanager;

import gizmo.environmentmanager.connectors.SSHClient;
import gizmo.environmentmanager.connectors.SSHClientFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Timestamp;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;

import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

public class Processus extends Entitee {

	public Processus( String groupe, String name, String libellE9,
			Timestamp timestamp) {
		super("PS", groupe, name, libellE9, timestamp);
		
	}

	public Processus(String groupe, String name, String libellE9) {
		super("PS", groupe, name, libellE9);
		
	}
	
	public void compil(Environnement environnement, OutputStream ostrm) throws Exception{
		
		ObjectPool pool  = new StackObjectPool(new SSHClientFactory(environnement, ostrm));
		SSHClient sshClient = (SSHClient) pool.borrowObject();
		sshClient.param("T120PA","PA20  "+getName()+environnement.getTopology().getEnvironment().getLogicalPlatform());
		sshClient.param("T160PA","PA30 "+environnement.getTopology().getEnvironment().getName());
		sshClient.param("T160PA","PA31  "+getName());
		sshClient.param("T200PA","PA40"+environnement.getTopology().getEnvironment().getName()+"  "+getName());
		sshClient.command("$SIGACS/prod/shl/TYBXRON;");
		sshClient.command("ps -ef | grep "+getName());
		pool.returnObject(sshClient);
	}

}
