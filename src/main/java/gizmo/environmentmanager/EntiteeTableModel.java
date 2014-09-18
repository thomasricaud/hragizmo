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

// Referenced classes of package gizmo.environmentmanager:
//            Development, Entitee
@XmlRootElement
public class EntiteeTableModel extends AbstractTableModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -668975015089658219L;

	public boolean isCellEditable(int row, int col) {
		return true;
	}

	public EntiteeTableModel() {
		entiteeList = new ArrayList<Entitee>();
		development = Development.getInstance();
		Hashtable<String, String> typeEntitee = development.getTypeEntitee();
		Entitee firstEntitee = new Entitee("TR", "AS", "UT01ER",
				"libelle de traitement", new Timestamp(0xbc8b84L));
		entiteeList.clear();
		entiteeList.add(firstEntitee);
		entiteeList.add(new Entitee("TR", "AS", "UT02ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT03ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT04ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT05ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT06ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT07ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT08ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT09ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT10ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT11ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT12ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT13ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT14ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT15ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT16ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT17ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT18ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));
		entiteeList.add(new Entitee("TR", "AS", "UT19ER",
				"libelle de traitement", new Timestamp(0xbc8b84L)));

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
		Entitee entitee = entiteeList.get(rowIndex);
		if (entitee != null) {
			switch (columnIndex) {
			case 0: // '\0'
				entitee.setType(aValue.toString());
				break;

			case 1: // '\001'
				entitee.setGroupe(aValue.toString());
				break;

			case 2: // '\002'
				entitee.setName(aValue.toString());
				break;

			case 3: // '\003'
				entitee.setLibellee(aValue.toString());
				break;

			case 4: // '\004'
				entitee.setTimestamp((Timestamp) aValue);
				break;
			}
			fireTableCellUpdated(rowIndex, columnIndex);
		} else {
			entiteeList.add(new Entitee(aValue.toString(), aValue.toString(),
					aValue.toString(), aValue.toString()));
			fireTableRowsInserted(rowIndex, rowIndex);
		}
	}

	public int getRowCount() {
		return entiteeList.size();
	}

	public int getColumnCount() {
		return columnNames.length;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		Entitee entitee = entiteeList.get(rowIndex);
		if (entitee != null) {
			switch (columnIndex) {
			case 0: // '\0'
				return entitee.getType();

			case 1: // '\001'
				return entitee.getGroupe();

			case 2: // '\002'
				return entitee.getName();

			case 3: // '\003'
				return entitee.getLibelle();

			case 4: // '\004'
				return entitee.getTimestamp();
			}
			return "N/A";
		} else {
			return "";
		}
	}

	public void addRow() {
		entiteeList.add(new Entitee("", "", "",
				""));
		fireTableRowsInserted(getRowCount()-1, getRowCount()-1);
	}

	public void deleteRow(int i) {
		entiteeList.remove(i);
		fireTableRowsDeleted(i, i);

	}

	public void deleteRow(int ai[]) {

		Object[] listToBeRemoved = new Object[ai.length];
		int i = 0;
		for (int j : ai) {
			listToBeRemoved[i] = entiteeList.toArray()[j];
			i++;

		}

		for (Object o : listToBeRemoved) {

			entiteeList.remove(o);
		}

		fireTableRowsDeleted(ai[0], ai[ai.length - 1]);

	}

	public void addCollection(String collectionID) {
		entiteeList.clear();
		int firstRow = getRowCount();
		entiteeList.addAll(development.getEntiteeInCollection(collectionID));
		System.out.println((new StringBuilder("firstRow : ")).append(firstRow)
				.append(" getRowCount() : ").append(getRowCount()).toString());
		fireTableRowsInserted(firstRow - 1, getRowCount() - 1);
	}

	public void addUser(String userid) {
		entiteeList.clear();
		int firstRow = getRowCount();
		entiteeList.addAll(development.getEntiteeModifyBy(userid));
		fireTableRowsInserted(firstRow - 1, getRowCount() - 1);
	}

	public Hashtable<String, String> getUserID() {
		return development.getUserID();
	}
	
	private String columnNames[] = { "Type d'entitée", "Groupe", "Nom",
			"Libéllé", "Timestamp", "Compare" };
	@XmlElement(name="entitee")
	private List<Entitee> entiteeList;
	/**
	 * @return the entiteeList
	 */
	public List<Entitee> getEntiteeList() {
		return entiteeList;
	}

	private Development development;
	
}
