package gizmo.environmentmanager;

public class Reference extends Environnement {

	public Reference() {
		setFileName("ref");
	}
	public static synchronized Reference getInstance()
    {
        return instance;
    }

    private static Reference instance = new Reference();
}
