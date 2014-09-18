package gizmo.environmentmanager.connectors;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import gizmo.environmentmanager.Environnement;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.pool.BaseObjectPool;

import com.hraccess.openhr.topology.IFtpLink;
import com.hraccess.openhr.topology.ISystemTopology;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

public class SSHClient extends BaseObjectPool {

	private Session sess;
	private Connection conn;

	private OutputStreamWriter writer;

	public SSHClient(Environnement environnement, OutputStream ostrm)
			throws IOException {
		PropertiesConfiguration configuration = environnement
				.getConfiguration();
		IFtpLink ftpLink;
		
			ISystemTopology topology = environnement.getTopology();

			ftpLink = topology.getEnvironment().getQueryExploitations()
					.get(topology.getEnvironment().getQueryExploitationCount() - 1)
					.getArchiveVolumeToClientLink();
		
		String hostname;
		if (configuration.containsKey("sshhostname")) {
			hostname = configuration.getString("sshhostname");
		} else {
			hostname = ftpLink.getHostName();
		}

		String username = "";
		if (configuration.containsKey("sshusername")) {
			username = configuration.getString("sshusername");
		} else {
			username = ftpLink.getUser();
		}

		String password = "";

		if (configuration.containsKey("sshpassword")) {
			password = configuration.getString("sshpassword");
		} else {
			password = ftpLink.getPassword();
		}

		conn = new Connection(hostname);

		/* Now connect */

		conn.connect();

		/* Authenticate */

		boolean isAuthenticated = conn.authenticateWithPassword(username,
				password);

		if (isAuthenticated == false)
			throw new IOException("Authentication failed.");

		/* Create a session */

		sess = conn.openSession();
		sess.requestPTY("bash");
		sess.startShell();
		new Thread(new SyncPipe(sess.getStderr(), ostrm)).start();
		new Thread(new SyncPipe(sess.getStdout(), ostrm)).start();

		writer = new OutputStreamWriter(sess.getStdin(), "utf-8");
		command("nupro=$$");
		command("export nupro");
	}

	public void command(String command) throws IOException {

		writer.write(command + "\n");
		writer.flush();

	}

	
	public void param(String carte, String valeur) throws IOException {

		command("echo \"" + valeur + "\" >> $TMP/" + carte + ".\"$nupro\"");

	}

	@Override
	public Object borrowObject() throws Exception {
		sess.startShell();
		return this;
		

	}

	@Override
	public void invalidateObject(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void returnObject(Object arg0) throws Exception {
		
		command("exit 0");
	}

	
}
