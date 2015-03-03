package gizmo.environmentmanager;

import com.hraccess.datasource.TDataNode;
import com.hraccess.openhr.*;
import com.hraccess.openhr.beans.HRTechnicalExtractionSource;
import com.hraccess.openhr.event.SessionChangeEvent;
import com.hraccess.openhr.event.SessionChangeListener;
import com.hraccess.openhr.topology.ISystemTopology;

import java.io.*;
import java.util.*;

import org.apache.commons.configuration.PropertiesConfiguration;

// Referenced classes of package gizmo.environmentmanager:
//            Entitee

public abstract class Environnement implements Serializable{

	private ISystemTopology topology;
	private Set<SessionChangeListener> listeners = new HashSet<SessionChangeListener>();
	private String fileName;

	public ISystemTopology getTopology() {
		return topology;
	}

	protected Environnement() {
		typeEntitee = new Hashtable<String, String>();

	}
	public void init() throws Exception{
		
		init(getFileName());
	}
	public void init(String fileName) throws Exception {
		File openhr = new File((new StringBuilder(String.valueOf(fileName)))
				.append(".properties").toString());
		openhr.createNewFile();
		configuration = new PropertiesConfiguration((new StringBuilder(
				String.valueOf(fileName))).append(".properties").toString());
		HRApplication.configureLogs("log.properties");
		configuration.reload();

		if (session == null) {
			session = HRSessionFactory.getFactory()
					.createSession(configuration);
		} else if (session.isConnected()) {
			session.disconnect();
			session = HRSessionFactory.getFactory()
					.createSession(configuration);
		} else if (!session.isConnected()) {
			session = HRSessionFactory.getFactory()
					.createSession(configuration);
		}
		

		isConnected = session.isConnected();
		superuser = session.getSessionUser();
		topology = session.getSystemTopology();
		extractionSource = new HRTechnicalExtractionSource(session);
		int i = 0;
		for (Iterator<SessionChangeListener> iterator = listeners.iterator(); iterator
				.hasNext();) {
			SessionChangeListener value = iterator.next();
			session.addSessionChangeListener(value);
			SessionChangeEvent event = new SessionChangeEvent(session) ;
			value.sessionStateChanged(event);
			i++;
		}
		typeEntitee.put("TR", "Traitement");
		typeEntitee.put("RO", "Role");
		typeEntitee.put("WF", "Page Web");
	}

	public PropertiesConfiguration getConfiguration() {
		return configuration;
	}

	public Hashtable<String, String> getTypeEntitee() {
		return typeEntitee;
	}

	public Hashtable<String, String> getUserID() {
		Hashtable<String, String> userIds = new Hashtable<String, String>();
		if (!isConnected) {
			userIds.put("HRF", "HR");
		} else {
			extractionSource.setMaxRowCount(500);
			extractionSource
					.setSQLExtraction("select CDUTIL,LIUTIL from UC10 where TECONC=1");
			extractionSource.setActive(true);

			TDataNode node = extractionSource.getDataNode();
			if (node.first()) {
				do
					userIds.put(node.getAsString(0), node.getAsString(1));
				while (node.next());
				System.out.println((new StringBuilder(
						"Are there any more records available ? ")).append(
						extractionSource.isMoreRowsAvailable()).toString());
			}

			if (extractionSource != null && extractionSource.isActive())
				extractionSource.setActive(false);
		}
		return userIds;
	}

	public Hashtable<String, String> getProcessList() {
		Hashtable<String, String> processIds = new Hashtable<String, String>();
		if (!isConnected) {
			processIds.put("AS001", "Pas de connection");
		} else {
			extractionSource.setMaxRowCount(1000);
			extractionSource
					.setSQLExtraction("select CDPROS,LILONG from AP21 where CDLANG='F'");
			extractionSource.setActive(true);

			TDataNode node = extractionSource.getDataNode();
			if (node.first()) {
				do
					processIds.put(node.getAsString(0), node.getAsString(1));
				while (node.next());
				System.out.println((new StringBuilder(
						"Are there any more records available ? ")).append(
						extractionSource.isMoreRowsAvailable()).toString());
			}

			if (extractionSource != null && extractionSource.isActive())
				extractionSource.setActive(false);
		}
		return processIds;
	}

	public Collection<Entitee> getEntiteeInCollection(String collectionID) {
		Set<Entitee> collection = new HashSet<Entitee>();

		if (!isConnected) {
			collection.add(new Entitee(collectionID, collectionID,
					collectionID, collectionID));
			collection.add(new Entitee(collectionID + "2", collectionID + "2",
					collectionID + "2", collectionID + "2"));
		} else {

			extractionSource.setMaxRowCount(500);
			extractionSource
					.setSQLExtraction((new StringBuilder(
							"SELECT distinct a.TYENTI, a.CDENTI, a.CDENTI,b.LILONG FROM KL50 a, LB12 b where a.GPCOLL='"))
							.append(collectionID.substring(0, 2))
							.append("' and a.CDCOLL='")
							.append(collectionID.substring(2))
							.append("' and a.CDENTI=b.CDENTI and B.CDLANG='F'")
							.toString());
			extractionSource.setActive(true);

			TDataNode node = extractionSource.getDataNode();
			if (node.first()) {
				int row = 0;
				do
					collection.add(new Entitee(node.getAsString(0), node
							.getAsString(1), node.getAsString(2), node
							.getAsString(3)));
				while (node.next());
				System.out.println((new StringBuilder(
						"Are there any more records available ? ")).append(
						extractionSource.isMoreRowsAvailable()).toString());
			}

			if (extractionSource != null && extractionSource.isActive())
				extractionSource.setActive(false);

			if (extractionSource != null && extractionSource.isActive())
				extractionSource.setActive(false);
		}
		return collection;
	}

	public Collection<Entitee> getEntiteeModifyBy(String userid) {
		Set<Entitee> collection;
		extractionSource.setMaxRowCount(500);
		String jour = "2012-01-01";
		String sql = (new StringBuilder(
				"select 'TR' as TYPE,a.GPTRAI as GROUPE ,b.CDTRAI as NOM , b.LILONG as LABEL, a.TIMODI as TIMODI from TR20 a, TR21 b where a.CDUTMO='"))
				.append(userid)
				.append("'  and a.timodi > to_date('")
				.append(jour)
				.append("','YYYY-MM-DD') and a.GPTRAI=b.GPTRAI and A.CDTRAI=b.CDTRAI and B.CDLANG='F' UNION ALL select 'WP' as TYPE,a.GPECRA as GROUPE ,b.CDECRA as NOM , b.LILONG as LABEL, a.TIMODI as TIMODI from WF20 a, WF21 b where a.CDUTMO='")
				.append(userid)
				.append("'  and a.timodi > to_date('")
				.append(jour)
				.append("','YYYY-MM-DD') and a.GPECRA=b.GPECRA and A.CDECRA=b.CDECRA and B.CDLANG='F' UNION ALL select 'IN' as TYPE ,a.CDSTDO as GROUPE,b.CDINFO as NOM , b.LILONG as LABEL, a.TIMODI as TIMODI from DI40 a, DI41 b where a.CDUTMO='")
				.append(userid)
				.append("'  and a.timodi > to_date('")
				.append(jour)
				.append("','YYYY-MM-DD') and a.CDSTDO=b.CDSTDO and A.cdinfo=b.cdinfo and B.CDLANG='F' UNION ALL select a.TYENTI as TYPE, a.CDENTI as GROUPE ,a.CDENTI as NOM ,b.LILONG as LABEL, a.TIMODI as TIMODI from EN10 a,LB12 b where a.CDUTMO='")
				.append(userid)
				.append("' and a.timodi > to_date('")
				.append(jour)
				.append("','YYYY-MM-DD') and a.CDENTI=B.CDENTI and B.CDLANG='F'  UNION ALL select 'PS' as TYPE, ' ' as GROUPE ,a.CDPROS as NOM ,b.LILONG as LABEL, a.TIMODI as TIMODI from AP20 a,AP21 b where a.CDUTMO='")
				.append(userid)
				.append("' and a.timodi > to_date('")
				.append(jour)
				.append("','YYYY-MM-DD') and a.CDPROS=B.CDPROS and B.CDLANG='F'")
				.toString();
		extractionSource.setSQLExtraction(sql);
		extractionSource.setActive(true);
		collection = new HashSet<Entitee>();
		TDataNode node = extractionSource.getDataNode();
		if (node.first()) {
			int row = 0;
			do
				collection.add(new Entitee(node.getAsString(0), node
						.getAsString(1), node.getAsString(2), node
						.getAsString(3), node.getDatetime(4)));
			while (node.next());
			System.out.println((new StringBuilder(
					"Are there any more records available ? ")).append(
					extractionSource.isMoreRowsAvailable()).toString());
		}

		if (extractionSource != null && extractionSource.isActive())
			extractionSource.setActive(false);

		if (extractionSource != null && extractionSource.isActive())
			extractionSource.setActive(false);
		return collection;
	}

	public boolean isConnected() {
		return session.isConnected();
	}

	public void addSessionListener(SessionChangeListener listener) {
		// TODO Auto-generated method stub
		if (session != null)
			session.addSessionChangeListener(listener);
		listeners.add(listener);
	}

	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private IHRSession session;
	private IHRSessionUser superuser;
	private HRTechnicalExtractionSource extractionSource;
	private Hashtable<String, String> typeEntitee;
	private PropertiesConfiguration configuration;
	private boolean isConnected;
}
