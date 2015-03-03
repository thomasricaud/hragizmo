package gizmo.userinterface;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Surcharge extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -635541127842673415L;
	private PropertiesConfiguration configuration;

	public Surcharge(final PropertiesConfiguration configuration) {
		super();
		this.configuration = configuration;

		setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

		createParameterFields(configuration, "sshusername");
		createParameterFields(configuration, "sshpassword");
		createParameterFields(configuration, "sshhostname");
		createParameterFields(configuration, "odaDriverClass");
		createParameterFields(configuration, "odaURL");
		createParameterFields(configuration, "odaUser");
		createParameterFields(configuration, "odaPassword");
		createParameterFields(configuration, "loginCommand");
		setSize(400, 400);
		setVisible(true);

	}

	private void createParameterFields(
			final PropertiesConfiguration configuration, String parametre) {
		Dimension dimensionCellule = new Dimension(150, 20);
		String value = configuration.getProperty(parametre) != null ? configuration
				.getProperty(parametre).toString() : "";
		JFormattedTextField valeur = new JFormattedTextField(value);
		valeur.setName(parametre);
		valeur.setMinimumSize(dimensionCellule);

		JLabel labelPropertie = new JLabel(parametre);
		labelPropertie.setMinimumSize(dimensionCellule);
		PropertyChangeListener listener = new PropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent evt) {

				if (evt.getNewValue() != null) {

					configuration.setProperty(
							((JFormattedTextField) evt.getSource()).getName(),
							evt.getNewValue().toString());
				}

				try {
					configuration.save();
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					// e.printStackTrace(outConfig);
				}

			};
		};

		valeur.addPropertyChangeListener("value", listener);
		getContentPane().add(labelPropertie);
		getContentPane().add(valeur);
	}

}
