import cycling.SegmentType;
import cycling.StageType;
import src.cycling.CyclingPortal;
import java.time.LocalDateTime;
import cycling.Stage;

/**
 * A short program to illustrate an app testing some minimal functionality of a
 * concrete implementation of the CyclingPortalInterface interface -- note you
 * will want to increase these checks, and run it on your CyclingPortal class
 * (not the BadCyclingPortal class).
 *
 * 
 * @author Diogo Pacheco
 * @version 1.0
 */
public class CyclingPortalInterfaceTestApp {

	/**
	 * Test method.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		int raceId;
		int stageId;
		int segmentId;
		System.out.println("The system compiled and started the execution...");

		CyclingPortal portal = new CyclingPortal();

		assert (portal.getRaceIds().length == 0)
				: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";

		try {
			raceId = portal.createRace("Test race", "Test description");
			stageId = portal.addStageToRace(raceId, "Test stage", "Test description",
					5.0, LocalDateTime.now(), StageType.FLAT);
			//segmentId = portal.addCategorizedClimbToStage(stageId, 5.0, SegmentType.C1, 4.0, 2.0);
			//portal.removeStageById(stageId);
			portal.concludeStagePreparation(stageId);
			portal.getStageSegments(stageId);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
