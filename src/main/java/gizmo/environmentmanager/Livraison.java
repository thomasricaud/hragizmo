// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Livraison.java

package gizmo.environmentmanager;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Livraison extends ArrayList<Fiche> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9079841007676741619L;

	public Livraison() {
		super();

	}

	@XmlElement(name = "fiche")
	public ArrayList<Fiche> getFiches() {
		return this;
	}

}
