// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EntiteeTableModel.java

package gizmo.environmentmanager;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

import javax.swing.table.AbstractTableModel;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.birt.report.engine.emitter.excel.DataCache.RowIndexAdjuster;

// Referenced classes of package gizmo.environmentmanager:
//            Development, Entitee
@XmlRootElement
public class FicheTableModel extends AbstractTableModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -668975015089658219L;

	public boolean isCellEditable(int row, int col) {
		return true;
	}

	public FicheTableModel(Livraison currentLivraison) {
		ficheList = currentLivraison.getFiches();
	}

	public Class getColumnClass(int c) {
		switch (c) {
		case 0: // '\0'
			return String.class;

		case 1: // '\001'
			return String.class;

		case 2: // '\002'
			return String.class;

		case 3: // '\003'
			return String.class;

		case 4: // '\004'
			return Timestamp.class;
		default:
			return String.class;
		}

	}

	public String getColumnName(int column) {
		return columnNames[column];
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Fiche fiche = ficheList.get(rowIndex);
		if (fiche != null) {
			switch (columnIndex) {
			case 0: // '\0'
				fiche.getWorkUnit().setWorkUnitID(aValue.toString());
				break;

			case 1: // '\001'
				fiche.getWorkUnit().setWorkLabel(aValue.toString());
				break;

						}
			fireTableCellUpdated(rowIndex, columnIndex);
		} else {
			ficheList.add(new Fiche());
			fireTableRowsInserted(rowIndex, rowIndex);
		}
	}

	public int getRowCount() {
		return ficheList.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Fiche fiche = ficheList.get(rowIndex);
		if (fiche != null) {
			switch (columnIndex) {
			case 0: // '\0'
				return fiche.getWorkUnit().getWorkUnitID();

			case 1: // '\001'
				return fiche.getWorkUnit().getWorkUnitID();

			case 2: // '\002'
				return fiche.getEntitees().getRowCount();
			case 3: // '\002'
				return "lancer le build";
		}
			return "N/A";
		} else {
			return "";
		}
	}

	public void addRow() {
		ficheList.add(new Fiche());
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}

	public void deleteRow(int i) {
		ficheList.remove(i);
		fireTableRowsDeleted(i, i);

	}

	public void deleteRow(int ai[]) {

		Object[] listToBeRemoved = new Object[ai.length];
		int i = 0;
		for (int j : ai) {
			listToBeRemoved[i] = ficheList.toArray()[j];
			i++;

		}

		for (Object o : listToBeRemoved) {

			ficheList.remove(o);
		}

		fireTableRowsDeleted(ai[0], ai[ai.length - 1]);

	}

	

	public Fiche getFiche(int rowIndex){
		return ficheList.get(rowIndex);
	}

	
	
	private String columnNames[] = { "Identifiant", "Description", "Nombre objet", "Build" };
	
	private List<Fiche> ficheList;
	
	private Development development;
	
}
