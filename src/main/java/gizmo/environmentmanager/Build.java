package gizmo.environmentmanager;

public class Build extends Environnement {

	public Build() {
		setFileName("build");
	}
	public static synchronized Build getInstance()
    {
        return instance;
    }

    private static Build instance = new Build();
}
