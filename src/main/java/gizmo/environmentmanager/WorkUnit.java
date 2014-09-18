// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   WorkUnit.java

package gizmo.environmentmanager;

import java.io.Serializable;

// Referenced classes of package gizmo.environmentmanager:
//            WorkUnitStatus

public class WorkUnit implements Serializable {
	public WorkUnit() {
	}

	public String getWorkLabel() {
		return workLabel;
	}

	public void setWorkLabel(String workLabel) {
		this.workLabel = workLabel;
	}

	public WorkUnitStatus getStatus() {
		return status;
	}

	public void setStatus(WorkUnitStatus status) {
		this.status = status;
	}

	public String getWorkUnitID() {
		return workUnitID;
	}

	public void setWorkUnitID(String workUnitID) {
		this.workUnitID = workUnitID;
	}

	public WorkUnit(Object object) {
		setWorkUnitID(object.toString());
	}

	private String workUnitID;
	private String workLabel;
	private WorkUnitStatus status;
}
