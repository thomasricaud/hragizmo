package gizmo.userinterface;

import gizmo.environmentmanager.Development;
import gizmo.environmentmanager.Processus;
import gizmo.environmentmanager.Production;
import gizmo.environmentmanager.Qa;
import gizmo.environmentmanager.Reference;
import gizmo.environmentmanager.Build;
import gizmo.environmentmanager.connectors.SSHClient;
import gizmo.environmentmanager.connectors.SSHClientFactory;
import gizmo.userinterface.Create.addListener;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import javax.swing.JPanel;
import javax.swing.text.DefaultCaret;

import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import com.hraccess.openhr.event.SessionChangeEvent;
import com.hraccess.openhr.event.SessionChangeListener;

public class AideDevelopment extends javax.swing.JPanel implements
		SessionChangeListener {

	public class Listener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			

			if (arg0.getSource().equals(compil)) {

				Processus process = new Processus("AS",
						(String) processus.getSelectedItem(),
						(String) processus.getSelectedItem());
				try {
					process.compil(Development.getInstance(), out);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace(out);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(out);
				}
			}
			if (arg0.getSource().equals(kill)) {

				try {
					ObjectPool pool = new StackObjectPool(new SSHClientFactory(
							development, out));
					SSHClient sshClient = (SSHClient) pool.borrowObject();
					sshClient.command("pkill -f "
							+ (String) processus.getSelectedItem()
							);
					pool.returnObject(sshClient);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace(out);
				}
			}
			if (arg0.getSource().equals(compareREF)) {
				
				RunReport html = new RunReport ("CompareProcessInTwoEnvironment.rptdesign",Reference.getInstance(),Development.getInstance(),(String) processus.getSelectedItem());
				

			}
			if (arg0.getSource().equals(compareINT)) {
				RunReport html = new RunReport ("CompareProcessInTwoEnvironment.rptdesign",Development.getInstance(), Build.getInstance(),(String) processus.getSelectedItem());
				

			}
			if (arg0.getSource().equals(compareQA)) {
				RunReport html = new RunReport ("CompareProcessInTwoEnvironment.rptdesign",Development.getInstance(),Qa.getInstance(),(String) processus.getSelectedItem());
				

			}

		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4579082359069303439L;
	private ProcessComboBox processus;
	private JButton compil;
	private JButton kill;
	private Development development;
	private PrintStream out;
	private JButton compareREF;
	private JButton compareINT;
	private JButton compareQA;

	public AideDevelopment() {

		development = Development.getInstance();
		setLayout(new BorderLayout());
		JPanel northPanel = new JPanel();
		JPanel southPanel = new JPanel();
		JPanel eastPanel = new JPanel();

		compil = new JButton("Compilation du processus");
		compil.addActionListener(new Listener());
		compareREF = new JButton("Comparaison du processus avec la r�f�rence");
		compareREF.addActionListener(new Listener());
		compareINT = new JButton("Comparaison avec build");
		compareINT.addActionListener(new Listener());
		compareQA = new JButton("Comparaison avec recette");
		compareQA.addActionListener(new Listener());

		kill = new JButton("kill BHR associé");
		kill.addActionListener(new Listener());
		processus = new ProcessComboBox(development.getProcessList());
		development.addSessionListener((SessionChangeListener) processus);
		AutoCompleteDecorator.decorate(processus);
		northPanel.add(processus);
		northPanel.add(compil);
		northPanel.add(kill);
		northPanel.add(compareREF);
		northPanel.add(compareINT);
		northPanel.add(compareQA);
		add(northPanel, "North");

		JTextArea txtConsole = new JTextArea();
		DefaultCaret caret = (DefaultCaret) txtConsole.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		txtConsole.setLineWrap(true);
		txtConsole.setFont(getFont());
		txtConsole.setMargin(new Insets(10, 10, 10, 10));
		JScrollPane scroller = new JScrollPane(txtConsole);

		scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		// Now create a new TextAreaOutputStream to write to our JTextArea
		// control and wrap a
		// PrintStream around it to support the println/printf methods.
		out = new PrintStream(new TextAreaOutputStream(txtConsole));

		add(scroller, "Center");
		// now test the mechanism
		// System.out.println("Hello World");

	}

	public AideDevelopment(boolean isDoubleBuffered) {
		super(isDoubleBuffered);

	}

	public AideDevelopment(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);

	}

	public AideDevelopment(LayoutManager layout) {
		super(layout);

	}

	public void sessionStateChanged(SessionChangeEvent event) {
		// TODO Auto-generated method stub

	}

}
