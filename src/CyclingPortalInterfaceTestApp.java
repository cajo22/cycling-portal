import cycling.SegmentType;
import cycling.StageType;
import src.cycling.CyclingPortal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

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
		int teamId;
		int riderId;
		System.out.println("The system compiled and started the execution...");

		CyclingPortal portal = new CyclingPortal();

		assert (portal.getRaceIds().length == 0)
				: "Innitial SocialMediaPlatform not empty as required or not returning an empty array.";

		try {
			raceId = portal.createRace("Test race", "Test description");
			stageId = portal.addStageToRace(raceId, "eeeeeee", "Test description",
					5.0, LocalDateTime.now(), StageType.FLAT);
			segmentId = portal.addCategorizedClimbToStage(stageId, 2.0, SegmentType.C1, 4.0, 2.0);
			segmentId = portal.addIntermediateSprintToStage(stageId, 5.0);
			//portal.removeStageById(stageId);
			portal.concludeStagePreparation(stageId);
			portal.getStageSegments(stageId);
			portal.loadCyclingPortal("portal.txt");
			System.out.println(portal.stages.get(0).toString());
			stageId = portal.stages.get(0).getId();
			teamId = portal.createTeam("Test team", "Test description");
			riderId = portal.createRider(teamId, "Test rider", 1900);
			portal.registerRiderResultsInStage(stageId, riderId, new LocalTime[]{
					LocalTime.of(12, 00, 00, 00),
					LocalTime.of(13, 30, 00, 00),
					LocalTime.of(13, 40, 00, 00)});
			portal.saveCyclingPortal("portal.txt");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
