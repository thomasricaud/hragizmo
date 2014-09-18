package gizmo.userinterface;


import gizmo.environmentmanager.Development;

import java.util.Hashtable;
import java.util.Iterator;
import javax.swing.JComboBox;
import com.hraccess.openhr.event.SessionChangeEvent;
import com.hraccess.openhr.event.SessionChangeListener;

public class UserComboBox extends JComboBox<Object> implements SessionChangeListener {

	public UserComboBox(Hashtable<String, String> comboHash) {
		super();

		int i = 0;
		for (Iterator<String> iterator = comboHash.keySet().iterator(); iterator
				.hasNext();) {
			String value = (String) iterator.next();
			super.addItem(value);
			i++;
		}

		
	}
	
	public void sessionStateChanged(SessionChangeEvent event) {
		// TODO Auto-generated method stub
		Development development = Development.getInstance();
		super.removeAllItems();
		
		int i = 0;
		for (Iterator<String> iterator = development.getUserID().keySet().iterator(); iterator
				.hasNext();) {
			String value = (String) iterator.next();
			super.addItem(value);
			i++;
		}
	}

}
