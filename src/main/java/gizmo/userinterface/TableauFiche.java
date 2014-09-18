package gizmo.userinterface;

import gizmo.environmentmanager.Development;
import gizmo.environmentmanager.Build;
import gizmo.environmentmanager.Entitee;
import gizmo.environmentmanager.Fiche;
import gizmo.environmentmanager.FicheTableModel;
import gizmo.environmentmanager.HRAFile;
import gizmo.environmentmanager.Livraison;
import gizmo.environmentmanager.connectors.SSHClient;
import gizmo.environmentmanager.connectors.SSHClientFactory;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;

public class TableauFiche extends JTable {
	public class TableauListener implements KeyListener {

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 127) {
				for (int i = getSelectedRows().length - 1; i >= 0; i--) {
					dm.deleteRow(convertRowIndexToModel(getSelectedRows()[i]));
				}
			}
		}

		public void keyTyped(KeyEvent keyevent) {
		}

		public void keyReleased(KeyEvent keyevent) {
		}

		public TableauListener() {

			super();
		}
	}

	private AbstractAction build;
	private FicheTableModel dm;

	public TableauFiche(Livraison livraison, final PrintStream outBuild) {
		super(new FicheTableModel(livraison));
		dm = (FicheTableModel) this.getModel();
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		build = new LivreEntitees(dm, outBuild,Development.getInstance(),Build.getInstance()) ;
		ButtonColumn buttonColumn = new ButtonColumn(this, build, 3);
		addKeyListener(new TableauListener());
	}

}
