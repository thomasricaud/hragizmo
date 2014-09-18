package gizmo.userinterface;

import gizmo.environmentmanager.EnvironnementListener;
import gizmo.environmentmanager.Livraison;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.configuration.ConfigurationException;

// Referenced classes of package gizmo.userinterface:
//            Configuration, Create, Build

public class Pagedacceuil {
	public class BuildListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Pagedacceuil.cl.show(centerPanel, BUILD);

		}

		public BuildListener() {
			super();
		}
	}

	public class ConfigurationListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Pagedacceuil.cl.show(centerPanel, CONFIGURATION);
		}

		public ConfigurationListener() {

			super();
		}
	}

	public class AideDevelopmentListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Pagedacceuil.cl.show(centerPanel, AIDEDEVELOPMENT);

		}

		public AideDevelopmentListener() {

			super();
		}
	}

	public class CreateListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Pagedacceuil.cl.show(centerPanel, CREATE);

		}

		public CreateListener() {

			super();
		}
	}

	public class CurrentWindowListener implements WindowListener {

		public void windowOpened(WindowEvent windowevent) {
		}

		public void windowClosing(WindowEvent windowevent) {
		}

		public void windowClosed(WindowEvent windowevent) {
		}

		public void windowIconified(WindowEvent windowevent) {
		}

		public void windowDeiconified(WindowEvent windowevent) {
		}

		public void windowActivated(WindowEvent windowevent) {
		}

		public void windowDeactivated(WindowEvent windowevent) {
		}

		public CurrentWindowListener() {

			super();
		}
	}

	public class ToProdListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Pagedacceuil.cl.show(centerPanel, TOPROD);
		}

		public ToProdListener() {

			super();
		}
	}

	public class ToQaListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Pagedacceuil.cl.show(centerPanel, TOQA);
		}

		public ToQaListener() {

			super();
		}
	}

	private static EnvironnementListener devListener;
	private String AIDEDEVELOPMENT;
	private AideDevelopment aideDevelopmentPanel;
	private Livraison currentLivraison;

	public Pagedacceuil() {
		CONFIGURATION = "Configuration";
		AIDEDEVELOPMENT = "Aide au developement";
		CREATE = "Création d'une Fiche";
		BUILD = "Build d'une fiche";
		TOQA = "Envoi en Recette";
		TOPROD = "Envoi en Prod";
	}

	public static void main(String args[]) throws Exception {
		final Pagedacceuil pagedacceuil = new Pagedacceuil();
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
					pagedacceuil.go();
				} catch (ConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
		
	}

	private void go() throws IOException, ConfigurationException {
		currentLivraison = new Livraison();
		System.setSecurityManager(null);
		try {

			File file = new File("Livraison.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Livraison.class);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			currentLivraison = (Livraison) jaxbUnmarshaller.unmarshal(file);
			System.out.println(currentLivraison);

		} catch (JAXBException e) {
			e.printStackTrace();
		}

		try {

			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		frame = new JFrame();
		frame.setDefaultCloseOperation(3);
		frame.setLayout(new BorderLayout());
		menuPanel = new JPanel();
		frame.setExtendedState(6);
		cl = new CardLayout();
		centerPanel = new JPanel();
		centerPanel.setLayout(cl);
		centerPanel.setSize(frame.getSize());
		JButton configuration = new JButton(CONFIGURATION);
		configuration.addActionListener(new ConfigurationListener());
		JButton aideDevelopment = new JButton(AIDEDEVELOPMENT);
		aideDevelopment.addActionListener(new AideDevelopmentListener());
		JButton createFiche = new JButton(CREATE);
		createFiche.addActionListener(new CreateListener());
		JButton buildFiche = new JButton(BUILD);
		buildFiche.addActionListener(new BuildListener());
		JButton toQaFiche = new JButton(TOQA);
		toQaFiche.addActionListener(new ToQaListener());
		JButton toProdFiche = new JButton(TOPROD);
		toProdFiche.addActionListener(new ToProdListener());
		menuPanel.add(configuration);
		menuPanel.add(aideDevelopment);
		menuPanel.add(createFiche);
		menuPanel.add(buildFiche);
		menuPanel.add(toQaFiche);
		menuPanel.add(toProdFiche);
		configurationPanel = new Configuration();
		aideDevelopmentPanel = new AideDevelopment();

		createPanel = new Create(currentLivraison);
		buildPanel = new Build(currentLivraison);
		toqaPanel = new JPanel();
		toprodPanel = new JPanel();

		centerPanel.add(configurationPanel, CONFIGURATION);
		centerPanel.add(aideDevelopmentPanel, AIDEDEVELOPMENT);
		centerPanel.add(createPanel, CREATE);
		centerPanel.add(buildPanel, BUILD);
		centerPanel.add(toqaPanel, TOQA);
		centerPanel.add(toprodPanel, TOPROD);
		progressbar = new JProgressBar(0, 100);
		frame.getContentPane().add(menuPanel, "North");
		frame.getContentPane().add(centerPanel, "Center");
		frame.getContentPane().add(progressbar, "South");
		frame.setVisible(true);
	}

	private JFrame frame;
	private JTextArea text;
	private Font font;
	private JProgressBar progressbar;
	private JPanel menuPanel;
	private JPanel configurationPanel;
	private JPanel buildPanel;
	private JPanel toqaPanel;
	private JPanel toprodPanel;
	private Create createPanel;
	private JPanel centerPanel;
	private static CardLayout cl;
	private String CONFIGURATION;
	private String CREATE;
	private String BUILD;
	private String TOQA;
	private String TOPROD;

}
