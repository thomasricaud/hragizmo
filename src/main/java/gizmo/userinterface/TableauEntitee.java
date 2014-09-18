// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TableauEntitee.java

package gizmo.userinterface;

import gizmo.environmentmanager.Development;
import gizmo.environmentmanager.EntiteeTableModel;
import gizmo.userinterface.Create.addListener;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.hraccess.openhr.event.SessionChangeListener;

// Referenced classes of package gizmo.userinterface:
//            ButtonColumn

public class TableauEntitee extends JPanel {
	public class TableauListener implements KeyListener {

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 127) {
				for (int i = jt.getSelectedRows().length - 1; i >= 0; i--) {
					TableauEntitee.dm.deleteRow(TableauEntitee.jt
							.convertRowIndexToModel(jt.getSelectedRows()[i]));
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

	private class addListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource().equals(collectionBox)) {
				TableauEntitee.dm.addCollection((String) collectionBox
						.getSelectedItem());
				ButtonColumn buttoncolumn = new ButtonColumn(TableauEntitee.jt,
						delete, 5);
			}
			if (e.getSource().equals(userBox)) {
				TableauEntitee.dm.addUser((String) userBox.getSelectedItem());
				ButtonColumn buttoncolumn1 = new ButtonColumn(
						TableauEntitee.jt, delete, 5);
			}
			if (e.getSource().equals(addRow)) {
				TableauEntitee.dm.addRow();
			}
		}

	}

	public TableauEntitee() {
		super(new BorderLayout());
		delete = new AbstractAction() {

			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand()).intValue();
			}

		};
		add(sc, "Center");
		jt.addKeyListener(new TableauListener());
		JLabel collectionTitre = new JLabel(
				"Alimentation a partir d'un collection");
		String collectionList[] = { "WAFORMAT", "Jira 2599", "tes 23",
				"789556", "azsxri" };
		collectionBox = new JComboBox(collectionList);
		AutoCompleteDecorator.decorate(collectionBox);
		northPanel.add(collectionTitre);
		northPanel.add(collectionBox);
		collectionBox.addActionListener(new addListener());
		JLabel userTitre = new JLabel(
				"Alimentation a partir des modifs d'un utilisateur");

		userBox = new UserComboBox(dm.getUserID());
		Development.getInstance().addSessionListener(
				(SessionChangeListener) userBox);
		AutoCompleteDecorator.decorate(userBox);
		northPanel.add(userTitre);
		northPanel.add(userBox);
		userBox.addActionListener(new addListener());
		add(northPanel, "North");
		jt.setAutoCreateRowSorter(true);
		jt.setAutoscrolls(true);
		jt.setIntercellSpacing(new Dimension(3, 3));
		ButtonColumn buttonColumn = new ButtonColumn(jt, delete, 5);
		JPanel southPanel = new JPanel();
		addRow = new JButton("Ajouter une ligne");
		JLabel aide = new JLabel(
				"Pour supprimer une ligne selectionnez la et appuyez sur Suppr");
		addRow.addActionListener(new addListener());
		southPanel.add(addRow);
		southPanel.add(aide);
		add(southPanel, "South");

	}

	public TableModel getModel() {
		return jt.getModel();
	}

	AbstractAction delete;
	private static EntiteeTableModel dm;
	private static JTable jt;
	private static JScrollPane sc;

	private static JPanel northPanel = new JPanel();
	private static final long serialVersionUID = 0xb76aae2f0390a862L;
	private JComboBox collectionBox;
	private JComboBox userBox;
	private JButton addRow;

	static {
		dm = new EntiteeTableModel();
		jt = new JTable(dm);
		jt.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		sc = new JScrollPane(jt);
	}

}
