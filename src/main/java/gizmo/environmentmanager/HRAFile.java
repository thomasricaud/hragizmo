/**
 * 
 */
package gizmo.environmentmanager;

import gizmo.environmentmanager.connectors.FTPPool;
import gizmo.environmentmanager.connectors.GizmoFTPClient;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;

/**
 * @author Thomas
 * 
 */
public class HRAFile extends File {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2797812836483326693L;

	private String serverFile;

	private String localfilePath;

	private String remoteFilePath;

	/**
	 * @param Name
	 *            ! Name of the file
	 */
	public HRAFile(String pathname) {
		super(pathname);
		try {
			createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (this.isFile()) {
			serverFile = this.getName();
			
		}
		localfilePath= serverFile;
		remoteFilePath = serverFile;

		// Generate a local file at the root directory
	}

	public void upload(Environnement env, OutputStream ostrm) throws Exception {
		 
		ObjectPool pool = new StackObjectPool(new FTPPool(env, ostrm));
		GizmoFTPClient ftpClient = (GizmoFTPClient) pool.borrowObject();
		new File(localfilePath);
		ftpClient.upload(localfilePath, remoteFilePath);
	}

	public void download(Environnement env,OutputStream ostrm) throws Exception {
		
		ObjectPool pool = new StackObjectPool(new FTPPool(env, ostrm));
		GizmoFTPClient ftpClient = (GizmoFTPClient) pool.borrowObject();
		new File(localfilePath);
		ftpClient.download(localfilePath, remoteFilePath);
	}

}
