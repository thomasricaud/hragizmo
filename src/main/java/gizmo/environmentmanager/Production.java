package gizmo.environmentmanager;

public class Production extends Environnement  {

	public Production() {
		setFileName("prod");
		// TODO Auto-generated constructor stub
	}

	public static synchronized Production getInstance() {
		return instance;
	}

	private static Production instance = new Production();

}
