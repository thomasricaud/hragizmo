package gizmo.environmentmanager;

import com.hraccess.openhr.IHRSession;
import com.hraccess.openhr.event.SessionChangeEvent;
import com.hraccess.openhr.event.SessionChangeListener;

public class EnvironnementListener implements SessionChangeListener {

	public EnvironnementListener() {
		// TODO Auto-generated constructor stub
	}

	
		public void sessionStateChanged(SessionChangeEvent event) {
			IHRSession session = (IHRSession) event.getSource();
			
		}
	}


