package gizmo.userinterface;

import gizmo.environmentmanager.Environnement;

import java.awt.Dimension;
import java.awt.Point;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.JEditorPane;
import javax.swing.JFrame;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.element.IDataSource;

import com.hraccess.openhr.topology.IJdbcConnector;
import com.hraccess.openhr.topology.ISystemTopology;

public class RunReport extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4255436363495737794L;
	JEditorPane myEditorPane = null;
	IReportEngine engine = null;
	EngineConfig config = null;
	private IReportRunnable design;
	private JFXPanel fxPanel;

	RunReport(String reportName, Environnement source, Environnement target,
			String process) {
		super("Comparaison processus " + source.getFileName() + "<>"
				+ target.getFileName());
		getContentPane().setLayout(null);
		fxPanel = new JFXPanel();
		/*
		 * myEditorPane = new JEditorPane(); myEditorPane.setEditable(false);
		 * JScrollPane scrollPane = new JScrollPane(myEditorPane);
		 */
		add(fxPanel);
		setVisible(true);
		fxPanel.setSize(new Dimension(900, 900));
		fxPanel.setLocation(new Point(0, 27));
		getContentPane().setPreferredSize(new Dimension(900, 927));
		pack();
		setResizable(true);
		startPlatform();
		final String report = runReport(reportName, source, target, process);
		javafx.application.Platform.runLater(new Runnable() { // this will run
																// initFX as
																// JavaFX-Thread
					public void run() {
						initFX(fxPanel, report);
					}
				});

		// getContentPane().add(scrollPane);
		/*
		 * setSize(1200, 800); setVisible(true);
		 */

		// stopPlatform();
	}

	/* Creates a WebView and fires up google.com */
	private static void initFX(final JFXPanel fxPanel, String report) {
		Group group = new Group();
		Scene scene = new Scene(group);
		fxPanel.setScene(scene);

		WebView webView = new WebView();

		group.getChildren().add(webView);
		webView.setMinSize(900, 900);
		webView.setMaxSize(900, 900);

		// Obtain the webEngine to navigate
		WebEngine webEngine = webView.getEngine();
		webEngine.loadContent(report, "text/html");

	}

	@SuppressWarnings("unchecked")
	public String runReport(String reportName, Environnement envsource,
			Environnement envtarget, String process) {
		try {

			// Open the report design
			InputStream stream = this.getClass().getResourceAsStream(
					"/gizmo/resources/" + reportName);
			design = engine.openReportDesign(stream);
			updateDataSourceFromTopology("Source", envsource);
			updateDataSourceFromTopology("Target", envtarget);

			// Create task to run and render the report,
			IRunAndRenderTask task = engine.createRunAndRenderTask(design);

			HTMLRenderOption options = new HTMLRenderOption();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			options.setOutputStream(bos);
			options.setOutputFormat(IRenderOption.OUTPUT_FORMAT_HTML);
			options.setEmbeddable(true);

			task.getAppContext().put(
					EngineConstants.APPCONTEXT_CLASSLOADER_KEY,
					RunReport.class.getClassLoader());

			task.setParameterValue("Process", process);

			task.setRenderOption(options);
			task.run();
			task.close();

			System.out.println("Finished Gen");
			return bos.toString();

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "probleme de chargement";
	}

	private void updateDataSourceFromTopology(String datasourceName,
			Environnement env) throws ScriptException {
		PropertiesConfiguration configuration = env
				.getConfiguration();
		ISystemTopology topology = env.getTopology();
		IDataSource ds = design.getDesignInstance().getDataSource(
				datasourceName);
		if (topology != null) {
			IJdbcConnector jdbc = topology.getEnvironment()
					.getDesignServerFunction().getJdbcConnector();

			ds.setPrivateDriverProperty("odaDriverClass", jdbc.getDriverClass());
			ds.setPrivateDriverProperty("odaURL", jdbc.getURL());
			ds.setPrivateDriverProperty("odaUser", jdbc.getUser());
			ds.setPrivateDriverProperty("odaPassword", jdbc.getPassword());
		} else {
			ds.setPrivateDriverProperty("odaDriverClass", "bla");
			ds.setPrivateDriverProperty("odaURL", "");
			ds.setPrivateDriverProperty("odaUser", "");
			ds.setPrivateDriverProperty("odaPassword", "");
		}
		overrideJdbcParameter(configuration, ds,"odaDriverClass");
		overrideJdbcParameter(configuration, ds,"odaURL");
		overrideJdbcParameter(configuration, ds,"odaUser");
		overrideJdbcParameter(configuration, ds,"odaPassword");
	}

	private void overrideJdbcParameter(PropertiesConfiguration configuration,
			IDataSource ds, String parameter) throws ScriptException {
		if (configuration.containsKey(parameter)) {
			ds.setPrivateDriverProperty(parameter, configuration.getString(parameter));
		}
	}

	public void startPlatform() {
		try {
			config = new EngineConfig();
			config.setResourceLocator(new GizmoRessourceLocator());

			Platform.startup(config);
			IReportEngineFactory factory = (IReportEngineFactory) Platform
					.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			engine = factory.createReportEngine(config);

		}

		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopPlatform() {
		engine.destroy();
		Platform.shutdown();
	}

}
