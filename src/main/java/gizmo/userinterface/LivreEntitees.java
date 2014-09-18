package gizmo.userinterface;

import gizmo.environmentmanager.Build;
import gizmo.environmentmanager.Development;
import gizmo.environmentmanager.Entitee;
import gizmo.environmentmanager.Environnement;
import gizmo.environmentmanager.Fiche;
import gizmo.environmentmanager.FicheTableModel;
import gizmo.environmentmanager.HRAFile;
import gizmo.environmentmanager.connectors.SSHClient;
import gizmo.environmentmanager.connectors.SSHClientFactory;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.AbstractAction;
import javax.swing.JTable;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;
/**
 * @param fiche
 * @param sshClient
 * @throws IOException
 * 
 *             CDSTRC PA61 1 à 4 (4) TYEXPT Type d'exportation 5 E
 *             Valeur obligatoire</BR>
 * 
 *             CDPLPH Plate-forme source 6 à 13 (8)</BR>
 * 
 *             CDPLPC Plate-forme cible 14 à 21 (8)</BR>
 * 
 *             TEEXPS Simulation/Transfert réel 22 (1)</BR> S Simulation
 *             (pas de mise à jour)</BR> blanc Transfert réel</BR>
 * 
 *             TETENT Objets modifiés/Transfert total 23 (1) blanc
 *             Objets modifiés</BR> T Tous les objets</BR>
 * 
 *             TYRECM Format de fichier de transfert V 24 (1)
 *             fichier variable, seule valeur possible</BR>
 * 
 *             TEMMAC 25 (1) Témoin d'export des résultats de
 *             génération et de déploiement vers une plate-forme
 *             permettant génération et déploiement. Permet de
 *             demander, en plus de l'exportation des objets,
 *             l'exportation des résultats de génération et de
 *             déploiement.</BR>
 * 
 *             1 Export des résultats de génération : macros
 *             processus / information</BR>
 * 
 *             2 Export des résultats de déploiement (Rôles, Query,
 *             Processus Guidés, …)</BR>
 * 
 *             3 Export des résultats de génération et des résultats
 *             de déploiement</BR>
 * 
 *             blanc Pas d'export des résultats de génération et de
 *             déploiement</BR>
 */
public class LivreEntitees extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8458640944455976608L;
	private FicheTableModel dm;
	private PrintStream outBuild;
	private Environnement source;
	private Environnement cible;

	public LivreEntitees(FicheTableModel dm, final PrintStream outBuild, Environnement source, Environnement cible) {
		super();
		this.outBuild = outBuild;
		this.dm = dm;
		this.source = source;
		this.cible = cible;
		

	}

	public void actionPerformed(ActionEvent e) {
		JTable table = (JTable) e.getSource();
		int modelRow = Integer.valueOf(e.getActionCommand()).intValue();
		Fiche fiche = dm.getFiche(modelRow);

		ObjectPool pool = new StackObjectPool(new SSHClientFactory(
				source, outBuild));
		ObjectPool poolBuild = new StackObjectPool(new SSHClientFactory(
				cible, outBuild));
		try {
			SSHClient sshClient = (SSHClient) pool.borrowObject();
			
			sshClient.param("T120PA", "PA6AF"+fiche.getWorkUnit().getWorkLabel());
			sshClient.param("T120PA", "PA61E"+source.getTopology().getEnvironment().getName()+cible.getTopology().getEnvironment().getName()+"  V3 " );
			
			for (Entitee entitee : fiche.getEntitees().getEntiteeList()) {
				sshClient.param("T120PA", entitee.getPA63());
				System.out.println(entitee.getPA63());
			}
			sshClient.command("$SIGACS/prod/shl/TYBXRB3;");

			pool.returnObject(sshClient);

			HRAFile exportFile = new HRAFile("PSBBCG01");
			
			exportFile.download(source,outBuild);
			exportFile.upload(cible,outBuild);

			SSHClient sshClientBuild = (SSHClient) poolBuild.borrowObject();
			sshClient.param("T120PA", "PSBBCG01");
			sshClientBuild.command("$SIGACS/prod/shl/TYBXRB4;");

			poolBuild.returnObject(sshClientBuild);

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace(outBuild);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace(outBuild);
		}

	}
}
