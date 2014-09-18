package gizmo.environmentmanager;

public class Qa extends Environnement {

	public Qa() {
		setFileName("qa");
	}

	public static synchronized Qa getInstance() {
		return instance;
	}

	private static Qa instance = new Qa();

}
