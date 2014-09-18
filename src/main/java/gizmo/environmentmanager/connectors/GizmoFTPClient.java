package gizmo.environmentmanager.connectors;

import gizmo.environmentmanager.Environnement;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool.BaseObjectPool;
import org.apache.commons.pool.ObjectPool;

import com.hraccess.openhr.topology.IFtpLink;
import com.hraccess.openhr.topology.ISystemTopology;

public class GizmoFTPClient extends BaseObjectPool {

	private FTPClient f;

	public GizmoFTPClient(Environnement environnement, OutputStream ostrm)
			throws SocketException, IOException {
		f = new FTPClient();
		PropertiesConfiguration configuration = environnement
				.getConfiguration();
		ISystemTopology topology = environnement.getTopology();

		IFtpLink ftpLink = topology.getEnvironment().getQueryExploitations()
				.get(topology.getEnvironment().getQueryExploitationCount() - 1)
				.getArchiveVolumeToClientLink();
		String hostname = "";
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
		f.connect(hostname);
		f.login(username, password);
		f.enterLocalPassiveMode();
		f.setFileType(FTPClient.BINARY_FILE_TYPE);

	}

	public boolean upload(String localfilePath, String remoteFilePath)
			throws Exception {
		return f.storeFile(remoteFilePath, new FileInputStream(localfilePath));
		
	}

	public boolean download(String localfilePath, String remoteFilePath)
			throws Exception {

		
		return f.retrieveFile(remoteFilePath, new FileOutputStream(localfilePath ));
		
	}

	@Override
	public void invalidateObject(Object arg0) throws Exception {
		// TODO Auto-generated method stub
		f.abor();
	}

	@Override
	public void returnObject(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Object borrowObject() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
