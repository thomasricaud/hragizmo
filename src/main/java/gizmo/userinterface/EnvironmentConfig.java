package gizmo.userinterface;

import gizmo.environmentmanager.Environnement;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.Transient;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class EnvironmentConfig extends JPanel {

	private class configListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if (e.getSource().equals(refresh)) {
				try {

					environnment.init();

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace(outConfig);
					return;
				}
				// development.addSessionListener(((JPanel)((JPanel)((JButton)e.getSource()).getParent()).getParent()).getParent());
				outConfig.println("Connexion R�ussi");
			}
			
			if (e.getSource().equals(surcharge)) {
				Surcharge surcharge = new Surcharge(configuration);
			}
		}

	}

	PropertyChangeListener l = new PropertyChangeListener() {

		public void propertyChange(PropertyChangeEvent evt) {

			if (evt.getSource().equals(server)) {
				String value = evt.getNewValue() != null ? evt.getNewValue()
						.toString() : "sheldon";
				configuration.setProperty("openhr_server.server", value);

			}
			if (evt.getSource().equals(port)) {
				String value = evt.getNewValue() != null ? evt.getNewValue()
						.toString() : "165";
				configuration.setProperty("normal_message_sender.port", value);
				configuration.setProperty("sensitive_message_sender.port",
						value);
				configuration.setProperty("privilegied_message_sender.port",
						value);
			}

			try {
				configuration.save();
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(outConfig);
			}

		}
	};

	private PrintStream outConfig;
	private Environnement environnment;

	private PropertiesConfiguration configuration;
	private JFormattedTextField server;
	private JFormattedTextField port;
	private JButton refresh;

	private JButton surcharge;

	public EnvironmentConfig(PrintStream outConfig, Environnement environnment,
			String Name) {
		super();
		
		
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		Dimension dimensionCellule = new Dimension(150,20);
		this.outConfig = outConfig;
		this.environnment = environnment;
		File openhr = new File(environnment.getFileName()+".properties");
		try {
			openhr.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(outConfig);
		}

		try {
			configuration = new PropertiesConfiguration(openhr.getPath());
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(outConfig);
		}
		iniProperty("session.language", "f");
		iniProperty("session.process_list", "AS001");
		iniProperty("session.work_directory", "work");
		iniProperty("session.create_work_directory_if_needed", "true");
		iniProperty("normal_message_sender.security", "disabled");
		iniProperty("sensitive_message_sender.security", "disabled");
		// iniProperty("sensitive_message_sender.port", "8130");
		iniProperty("privilegied_message_sender.security", "disabled");
		// iniProperty("privilegied_message_sender.port", "8130");

		try {
			configuration.save();
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(outConfig);
		}

		JLabel labelServer = new JLabel(" Serveur de " + Name);
		labelServer.setMaximumSize(dimensionCellule);
		labelServer.setMinimumSize(dimensionCellule);
		String value = configuration.getProperty("openhr_server.server") != null ? configuration
				.getProperty("openhr_server.server").toString() : "sheldon";
		server = new JFormattedTextField(value);
		server.addPropertyChangeListener("value", l);
		server.setMaximumSize(dimensionCellule);
		server.setMinimumSize(dimensionCellule);
		JLabel labelPort = new JLabel(" Port openhr de " + Name);
		labelPort.setMaximumSize(dimensionCellule);
		labelPort.setMinimumSize(dimensionCellule);
		value = configuration.getProperty("normal_message_sender.port") != null ? configuration
				.getProperty("normal_message_sender.port").toString() : "165";
		port = new JFormattedTextField(value);
		port.addPropertyChangeListener("value", l);
		port.setMaximumSize(dimensionCellule);
		port.setMinimumSize(dimensionCellule);

		refresh = new JButton("Rafraichir " + Name);
		refresh.addActionListener(new configListener());
		surcharge = new JButton("surcharge " + Name);
		surcharge.addActionListener(new configListener());
		add(labelServer);
		add(server);
		add(labelPort);
		add(port);
		add(refresh);
		add(surcharge);

	}

	private void iniProperty(String key, String defaultValue) {
		if (!configuration.containsKey(key)) {
			configuration.setProperty(key, defaultValue);
		}
	}

}
