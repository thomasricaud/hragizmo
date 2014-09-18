package gizmo.userinterface;

import gizmo.environmentmanager.Development;
import gizmo.environmentmanager.Production;
import gizmo.environmentmanager.Build;
import gizmo.environmentmanager.Qa;
import gizmo.environmentmanager.Reference;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.DefaultCaret;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.hraccess.openhr.event.SessionChangeListener;

public class Configuration extends JPanel {

	private class configListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

		}

	}

	PropertyChangeListener l = new PropertyChangeListener() {

		public void propertyChange(PropertyChangeEvent evt) {

			if (evt.getSource().equals(birt)) {
				String value = evt.getNewValue() != null ? evt.getNewValue()
						.toString() : "D:\birt-runtime-4_2_2";
				configuration.setProperty("birt.engine", value);
			}
			try {
				configuration.save();
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace(outConfig);
			}

		}
	};

	private JFormattedTextField serverDev;
	private JFormattedTextField portDev;
	private JFormattedTextField birt;

	private Development development;

	private PropertiesConfiguration configuration;

	private JButton refreshDev;

	private PrintStream outConfig;

	private JFileChooser chooseBirt;

	private Container parentcontainer;

	private Build build;

	private Qa qa;

	private Production production;

	private Reference reference;

	public Configuration() throws IOException, ConfigurationException {

		setLayout(new BorderLayout());

		JTextArea txtConsole = new JTextArea();
		DefaultCaret caret = (DefaultCaret) txtConsole.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		txtConsole.setLineWrap(true);
		txtConsole.setFont(getFont());
		txtConsole.setMargin(new Insets(10, 10, 10, 10));

		JScrollPane scroller = new JScrollPane(txtConsole);
		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroller.setPreferredSize(new Dimension(1000, 600));

		outConfig = new PrintStream(new TextAreaOutputStream(txtConsole));
		// redirect standard output stream to the TextAreaOutputStream
		System.setOut(outConfig);

		// redirect standard error stream to the TextAreaOutputStream
		System.setErr(outConfig);

		development = Development.getInstance();
		EnvironmentConfig devConfig = new EnvironmentConfig(outConfig,
				development, "Dev");

		build = Build.getInstance();
		EnvironmentConfig buildConfig = new EnvironmentConfig(outConfig, build,
				"Int");

		qa = Qa.getInstance();
		EnvironmentConfig qaConfig = new EnvironmentConfig(outConfig, qa, "Rec");

		reference = Reference.getInstance();
		EnvironmentConfig refConfig = new EnvironmentConfig(outConfig,
				reference, "Ref");

		production = Production.getInstance();
		EnvironmentConfig prodConfig = new EnvironmentConfig(outConfig,
				production, "Prd");

		configuration = new PropertiesConfiguration(new File("birt.properties"));
		JLabel labelBirt = new JLabel("le chemin d'acc�s au BIRT engine");
		String value = configuration.getProperty("birt.engine") != null ? configuration
				.getProperty("birt.engine").toString()
				: "D:\\birt-runtime-4_2_2";
		birt = new JFormattedTextField(value);

		chooseBirt = new JFileChooser("choisir un r�pertoire");
		chooseBirt.addActionListener(new configListener());

		birt.addPropertyChangeListener("value", l);

		JPanel birtPanel = new JPanel();
		birtPanel.setLayout(new BoxLayout(birtPanel, BoxLayout.LINE_AXIS));
		birtPanel.add(labelBirt);
		birtPanel.add(birt);
		// birtPanel.add(chooseBirt);

		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
		north.add(refConfig);
		north.add(devConfig);

		north.add(buildConfig);

		north.add(qaConfig);

		north.add(prodConfig);
		//north.add(birtPanel);

		add(north, BorderLayout.NORTH);
		add(scroller, BorderLayout.CENTER);

	}

	public Configuration(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public Configuration(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	private static final long serialVersionUID = 0x646c4eaa8da05c8cL;
}
