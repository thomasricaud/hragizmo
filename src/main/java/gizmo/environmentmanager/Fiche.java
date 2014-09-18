// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Fiche.java

package gizmo.environmentmanager;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

// Referenced classes of package gizmo.environmentmanager:
//            EntiteeTableModel, DonneeTableModel, WorkUnit
@XmlRootElement
public class Fiche implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4656073642252918151L;
	
	public Fiche(WorkUnit workUnit) {
		this.workUnit = workUnit;
	}

	public Fiche() {
	}

	public EntiteeTableModel getEntitees() {
		return entitees;
	}
	
	public void setEntitees(EntiteeTableModel entitees) {
		this.entitees = entitees;
		
	}

	public DonneeTableModel getDonnees() {
		return donnees;
	}
	
	public void setDonnees(DonneeTableModel donnees) {
		this.donnees = donnees;
	}

	public WorkUnit getWorkUnit() {
		return workUnit;
	}
	
	public void setWorkUnit(WorkUnit workUnit) {
		this.workUnit = workUnit;
	}
	
	private EntiteeTableModel entitees;
	
	private DonneeTableModel donnees;
	
	private WorkUnit workUnit;
}
