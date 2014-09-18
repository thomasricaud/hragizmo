package gizmo.userinterface;

import gizmo.environmentmanager.EntiteeTableModel;
import gizmo.environmentmanager.Fiche;
import gizmo.environmentmanager.Livraison;
import gizmo.environmentmanager.WorkUnit;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

// Referenced classes of package gizmo.userinterface:
//            TableauEntitee

public class Create extends JPanel {
	public class CreateListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Fiche fiche = new Fiche(new WorkUnit(uniteDevBox.getSelectedItem()));
			fiche.setEntitees((EntiteeTableModel) tableauEntite.getModel());
			currentLivraison.add(fiche);
			try {

				File file = new File("Livraison.xml");
				JAXBContext jaxbContext = JAXBContext.newInstance(Livraison.class);
				Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

				// output pretty printed
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
						true);

				jaxbMarshaller.marshal(currentLivraison, file);
				jaxbMarshaller.marshal(currentLivraison, System.out);

			} catch (JAXBException error) {
				error.printStackTrace();
			}
		}

	}

	public class addListener implements ActionListener {

		public void actionPerformed(ActionEvent actionevent) {
		}

		public addListener() {

			super();
		}
	}

	private JComboBox uniteDevBox;
	private Livraison currentLivraison;

	public Create() {
		setLayout(new BorderLayout());
		JPanel northPanel = new JPanel();
		JPanel southPanel = new JPanel();
		JPanel eastPanel = new JPanel();
		JLabel titre = new JLabel(
				"Association d'une unitée de developpement ou d'un correctif");
		String uniteDev[] = { "Story256", "Jira 2599", "tes 23", "789556",
				"azsxri" };
		uniteDevBox = new JComboBox(uniteDev);
		uniteDevBox.addActionListener(new addListener());
		AutoCompleteDecorator.decorate(uniteDevBox);
		JButton creation = new JButton("Créer la fiche");
		creation.addActionListener(new CreateListener());
		northPanel.add(titre);
		northPanel.add(uniteDevBox);
		northPanel.add(creation);

		add(northPanel, "North");

		tableauEntite = new TableauEntitee();
		add(tableauEntite, "Center");

	}

	public Create(LayoutManager layout) {
		super(layout);
	}

	public Create(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public Create(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public Create(Livraison currentLivraison) {
		this();
		this.currentLivraison = currentLivraison;
	}

	private static final long serialVersionUID = 0xf2bd6da2e6005a45L;
	private TableauEntitee tableauEntite;
	private JComboBox collectionBox;
}
