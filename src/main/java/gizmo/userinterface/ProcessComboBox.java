package gizmo.userinterface;

import gizmo.environmentmanager.Development;

import java.util.Hashtable;
import java.util.Iterator;
import javax.swing.JComboBox;

import com.hraccess.openhr.event.SessionChangeEvent;
import com.hraccess.openhr.event.SessionChangeListener;

public class ProcessComboBox extends JComboBox<Object> implements SessionChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3987211365985157771L;

	public ProcessComboBox(Hashtable<String, String> comboHash) {
		super();

		int i = 0;
		for (Iterator<String> iterator = comboHash.keySet().iterator(); iterator
				.hasNext();) {
			String value = (String) iterator.next();
			super.addItem(value);
			i++;
		}

		// TODO Auto-generated constructor stub
	}

	public void sessionStateChanged(SessionChangeEvent event) {
		// TODO Auto-generated method stub
		Development development = Development.getInstance();
		super.removeAllItems();
		
		int i = 0;
		for (Iterator<String> iterator = development.getProcessList().keySet().iterator(); iterator
				.hasNext();) {
			String value = (String) iterator.next();
			super.addItem(value);
			i++;
		}
		
	}

}
