package gizmo.userinterface;

import gizmo.environmentmanager.Livraison;
import gizmo.environmentmanager.connectors.SyncPipe;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.InputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

public class Build extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6905322803965793854L;
	private JTextArea BuildLog;
	private Livraison currentLivraison;
	private PrintStream outBuild;

	/**
	 * Create the panel.
	 */
	public Build() {
		setLayout(new BorderLayout(10, 10));
		

	}

	public Build(Livraison currentLivraison) {
		this();

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
		outBuild = new PrintStream(new TextAreaOutputStream(txtConsole));
		
		add(scroller, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane(new TableauFiche(currentLivraison,outBuild));
		add(scrollPane, BorderLayout.NORTH);
		
	}

	public JTextArea getTextArea() {
		return BuildLog;
	}
}
