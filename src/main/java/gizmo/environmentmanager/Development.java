package gizmo.environmentmanager;


// Referenced classes of package gizmo.environmentmanager:
//            Environnement

public class Development extends Environnement 
{

    public Development()
    {
    	setFileName("dev");
    }

    public static synchronized Development getInstance()
    {
        return instance;
    }

    private static Development instance = new Development();

	
	

	

}
