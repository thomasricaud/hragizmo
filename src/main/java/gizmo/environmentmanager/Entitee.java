// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Entitee.java

package gizmo.environmentmanager;

import java.sql.Timestamp;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

public class Entitee implements Comparable<Entitee> {

	public Entitee(){}
	
	public Entitee(String type, String groupe, String name, String libellE9,
			Timestamp timestamp) {
		this.type = type;
		this.groupe = groupe;
		this.name = name;
		this.libellee = libellE9;
		this.timestamp = timestamp;
	}
	@XmlJavaTypeAdapter( TimestampAdapter.class)
	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Entitee(String type, String groupe, String name, String libellE9) {
		setName(name);
		setGroupe(groupe);
		setType(type);
		setLibellee(libellE9);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String groupe) {
		if (groupe.length() > 2)
			this.groupe = groupe.substring(0, 2);
		else
			this.groupe = groupe;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		if (type.length() > 2)
			this.type = type.substring(0, 2);
		else
			this.type = type;
	}

	public String getLibelle() {
		return libellee;
	}

	public void setLibellee(String libellee) {
		this.libellee = libellee;
	}

	public boolean equals(Object obj) {
		return (obj instanceof Entitee)
				&& name.equalsIgnoreCase(((Entitee) obj).getName())
				&& type.equalsIgnoreCase(((Entitee) obj).getType())
				&& groupe.equalsIgnoreCase(((Entitee) obj).getGroupe())
				&& libellee.equalsIgnoreCase(((Entitee) obj).getLibelle());
	}

	private String type;
	private String groupe;
	private String name;
	private String libellee;
	private Timestamp timestamp;
	private String sorting;

	public String getSorting() {
		return getType() + getGroupe() + getName();
	}

	public int compareTo(Entitee o) {
		// TODO Auto-generated method stub
		return getSorting().compareTo(o.getSorting());
	}

	public String getPA63() {
		return "PA63"+getType()+getGroupe()+getName();
		
	}
}
