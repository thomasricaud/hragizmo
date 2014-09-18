package gizmo.environmentmanager;

/**
 * @author Thomas Ricaud
 *
 */
public enum WorkUnitStatus {
	/**
	 * Started : when the workunit starts keep track of the modified entities 
	 * Developed: when the first released is ready to be exported
	 * Build: After import in the build environment
	 * Tested: after delivery in Qualification an approbation from tester
	 * Delivered: after delivery in production
	 * Accepted: when the work unit is closed.
	 */
	STARTED, DEVELOPPED, BUILD, TESTED, DELIVERED, ACCEPTED
}
