package src.cycling;

import cycling.CyclingPortalInterface;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;
import cycling.Race;
import cycling.Stage;
import cycling.StageType;
import cycling.StageState;
import cycling.Segment;
import cycling.SegmentType;
import cycling.Team;
import cycling.Rider;
import cycling.IllegalNameException;
import cycling.InvalidNameException;
import cycling.IDNotRecognisedException;
import cycling.InvalidLocationException;
import cycling.InvalidStageStateException;
import cycling.InvalidStageTypeException;
import cycling.DuplicatedResultException;
import cycling.InvalidCheckpointsException;
import cycling.NameNotRecognisedException;
import cycling.InvalidLengthException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

/**
 * BadCyclingPortal is a minimally compiling, but non-functioning implementor
 * of the CyclingPortalInterface interface.
 * 
 * @author Diogo Pacheco
 * @version 1.0
 *
 */
public class CyclingPortal implements CyclingPortalInterface {
	public ArrayList<Race> races = new ArrayList<Race>();
	public ArrayList<Stage> stages = new ArrayList<Stage>();
	public ArrayList<Segment> segments = new ArrayList<Segment>();
	public ArrayList<Team> teams = new ArrayList<Team>();
	public ArrayList<Rider> riders = new ArrayList<Rider>();

	@Override
	public int[] getRaceIds() {
		int[] retRaceIds = new int[races.size()];
		for (int i = 0; i < races.size(); i++) {
			retRaceIds[i] = (races.get(i)).getId();
		}
		return retRaceIds;
	}

	private Integer generateId(int[] id) {
		Random rand = new Random();
		Integer newId;
		do {
			newId = rand.nextInt(10000);
		}
		while (Arrays.asList(id).contains(newId));
		return (newId);
	}

	@Override
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		Integer raceId = generateId(getRaceIds());

		if (name == null || name == "")
			throw new InvalidNameException("Name invalid.");
		for (Integer i = 0; i < races.size(); i++) {
			if ((races.get(i)).getName() == name) {
				throw new IllegalNameException("Name already in use.");
			}
		}
		Race newRace = new Race(name, description, raceId);
		races.add(newRace);
		return raceId;
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		for (int i = 0; i < races.size(); i++) {
			if ((races.get(i)).getId() == raceId) {
				return (races.get(i)).toString();
			}
		}
		throw new IDNotRecognisedException("Race ID of "+raceId+" not found");
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		for (int i = 0; i < races.size(); i++) {
			if ((races.get(i)).getId() == raceId) {
				races.remove(i);
				return;
			}
		}
		throw new IDNotRecognisedException("Race ID of "+raceId+" not found");
	}

	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		for (int i = 0; i < races.size(); i++) {
			if ((races.get(i)).getId() == raceId) {
				return ((races.get(i)).getStageIds()).size();
			}
		}
		throw new IDNotRecognisedException("Race ID of "+raceId+" not found");
	}

	public int[] getStageIds() {
		int[] retStageIds = new int[stages.size()];
		for (int i = 0; i < stages.size(); i++) {
			retStageIds[i] = (stages.get(i)).getId();
		}
		return retStageIds;
	}

	@Override
	public int addStageToRace(int raceId, String stageName, String description, double length, LocalDateTime startTime,
			StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {
		Integer stageId;
		Stage newStage;

		if (length < 5.0)
			throw new InvalidLengthException("Length invalid.");
		if (stageName == null || stageName == "")
			throw new InvalidNameException("Name invalid.");
		for (Integer i = 0; i < stages.size(); i++) {
			if ((stages.get(i)).getName() == stageName) {
				throw new IllegalNameException("Name already in use.");
			}
		}
		for (Integer i = 0; i < races.size(); i++) {
			if ((races.get(i)).getId() == raceId) {
				stageId = generateId(getStageIds());
				newStage = new Stage(stageName, description, length, startTime, type, stageId);
				stages.add(newStage);
				(races.get(i)).addStageId(stageId);
				return stageId;
			}
		}
		throw new IDNotRecognisedException("Race ID of "+raceId+" not found");
	}

	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		for (int i = 0; i < races.size(); i++) {
			if ((races.get(i)).getId() == raceId) {
				return (races.get(i)).getStageIdsIntArray();
			}
		}
		throw new IDNotRecognisedException("Race ID of "+raceId+" not found");
	}

	@Override
	public double getStageLength(int stageId) throws IDNotRecognisedException {
		for (int i = 0; i < stages.size(); i++) {
			if ((stages.get(i)).getId() == stageId) {
				return ((stages.get(i)).getLength());
			}
		}
		throw new IDNotRecognisedException("Id not found");
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		int stageIndex;

		for (int i = 0; i < stages.size(); i++) {
			if ((stages.get(i)).getId() == stageId) {
				stages.remove(i);
				for (int j = 0; j < races.size(); j++) {
					stageIndex = ((races.get(i)).getStageIds()).indexOf(stageId);
					if (stageIndex >= 0) {
						(races.get(i)).removeStageId(stageIndex);
						return;
					}
				}
			}
		}
		throw new IDNotRecognisedException("Stage ID of "+stageId+" not found");
	}

	public int[] getSegmentIds() {
		int[] retSegmentIds = new int[segments.size()];
		for (int i = 0; i < segments.size(); i++) {
			retSegmentIds[i] = (segments.get(i)).getId();
		}
		return retSegmentIds;
	}

	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, SegmentType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		Integer segmentId;
		Segment newSegment;

		for (int i = 0; i < stages.size(); i++) {
			if ((stages.get(i)).getId() == stageId) {
				if (location > (stages.get(i)).getLength() || location < 0.0)
					throw new InvalidLocationException("Location (finish point) of "+location+" is invalid");
				else if (stages.get(i).getStageType() == StageType.TT)
					throw new InvalidStageTypeException("Cannot add categorized climbs to a time trial stage.");
				else if (stages.get(i).getState() == StageState.WAITING)
					throw new InvalidStageStateException("Cannot add to a stage that is waiting for results.");
				segmentId = generateId(getSegmentIds());
				newSegment = new Segment(type, location, averageGradient, length, segmentId);
				segments.add(newSegment);
				(stages.get(i)).addSegmentId(segmentId);
				return segmentId;
			}
		}
		throw new IDNotRecognisedException("Stage ID of "+stageId+" not found");
	}

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		Integer segmentId;
		Segment newSegment;

		for (int i = 0; i < stages.size(); i++) {
			if ((stages.get(i)).getId() == stageId) {
				if (location > (stages.get(i)).getLength() || location < 0.0)
					throw new InvalidLocationException("Location (finish point) of "+location+" is invalid");
				else if (stages.get(i).getStageType() == StageType.TT)
					throw new InvalidStageTypeException("Cannot add sprints to a time trial stage.");
				else if (stages.get(i).getState() == StageState.WAITING)
					throw new InvalidStageStateException("Cannot add to a stage that is waiting for results.");
				segmentId = generateId(getSegmentIds());
				newSegment = new Segment(SegmentType.SPRINT, location, 0.0, 0.0, segmentId);
				segments.add(newSegment);
				(stages.get(i)).addSegmentId(segmentId);
				return segmentId;
			}
		}
		throw new IDNotRecognisedException("Stage ID of "+stageId+" not found");
	}

	@Override
	public void removeSegment(int segmentId) throws IDNotRecognisedException, InvalidStageStateException {
		int segmentIndex;

		for (int i = 0; i < segments.size(); i++) {
			if ((segments.get(i)).getId() == segmentId) {
				segments.remove(i);
				for (int j = 0; j < stages.size(); j++) {
					segmentIndex = ((stages.get(i)).getSegmentIds()).indexOf(segmentId);
					if (segmentIndex >= 0) {
						(stages.get(i)).removeSegmentId(segmentIndex);
						return;
					}
				}
			}
		}
		throw new IDNotRecognisedException("Segment ID of "+segmentId+" not found");
	}

	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		for (int i = 0; i < stages.size(); i++) {
			if ((stages.get(i)).getId() == stageId) {
				if (stages.get(i).getState() == StageState.WAITING)
					throw new InvalidStageStateException("Cannot conclude "+stages.get(i).getName()+" (id:"+
							stageId+") because it is already waiting for results");
				stages.get(i).setState(StageState.WAITING);
				return;
			}
		}
		throw new IDNotRecognisedException("Stage ID of "+stageId+" not found");
	}

	@Override
	public int[] getStageSegments(int stageId) throws IDNotRecognisedException {
		for (int i = 0; i < stages.size(); i++) {
			if ((stages.get(i)).getId() == stageId) {
				return (stages.get(i)).getSegmentsIdsIntArray();
			}
		}
		throw new IDNotRecognisedException("stage ID of "+stageId+" not found");
	}

	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		Integer teamId = generateId(getTeams());

		if (name == null || name == "")
			throw new InvalidNameException("Name invalid.");
		for (Integer i = 0; i < teams.size(); i++) {
			if ((teams.get(i)).getName() == name) {
				throw new IllegalNameException("Name already in use.");
			}
		}
		Team newTeam = new Team(name, description, teamId);
		teams.add(newTeam);
		return teamId;
	}

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		for (int i = 0; i < teams.size(); i++) {
			if ((teams.get(i)).getId() == teamId) {
				teams.remove(i);
				return;
			}
		}
		throw new IDNotRecognisedException("Team ID of "+teamId+" not found");

	}

	@Override
	public int[] getTeams() {
		int[] retTeamIds = new int[teams.size()];
		for (int i = 0; i < teams.size(); i++) {
			retTeamIds[i] = (teams.get(i)).getId();
		}
		return retTeamIds;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		for (int i = 0; i < teams.size(); i++) {
			if ((teams.get(i)).getId() == teamId) {
				return (teams.get(i)).getRiderIdsIntArray();
			}
		}
		throw new IDNotRecognisedException("Team ID of "+teamId+" not found");
	}

	public int[] getRiderIds() {
		int[] retRiderIds = new int[riders.size()];
		for (int i = 0; i < riders.size(); i++) {
			retRiderIds[i] = (riders.get(i)).getId();
		}
		return retRiderIds;
	}

	public boolean isRiderIdPresent(int riderId) {
		for (int i = 0; i < riders.size(); i++) {
			if ((riders.get(i)).getId() == riderId)
				return true;
		}
		return false;
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		Integer riderId = generateId(getRiderIds());

		if (name == null)
			throw new IllegalArgumentException("Name invalid (is null).");
		else if (yearOfBirth < 1900)
			throw new IllegalArgumentException("Year of birth cannot be less than 1900.");
		for (int i = 0; i < teams.size(); i++) {
			if ((teams.get(i)).getId() == teamID) {
				Rider newRider = new Rider(teamID, riderId, name, yearOfBirth);
				riders.add(newRider);
				(teams.get(i)).addRiderId(riderId);
				return riderId;
			}
		}
		throw new IDNotRecognisedException("Team ID of "+teamID+" not found");
	}

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		int riderIndex;

		for (int i = 0; i < riders.size(); i++) {
			if ((riders.get(i)).getId() == riderId) {
				riders.remove(i);
				for (int j = 0; j < teams.size(); j++) {
					riderIndex = ((teams.get(i)).getRiderIds()).indexOf(riderId);
					if (riderId >= 0) {
						(teams.get(i)).removeRiderId(riderIndex);
						return;
					}
				}
			}
		}
		throw new IDNotRecognisedException("Rider ID of "+riderId+" not found");
	}

	@Override
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints)
			throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointsException,
			InvalidStageStateException {
		if (isRiderIdPresent(riderId) == false)
			throw new IDNotRecognisedException("Rider ID of "+riderId+" not found");
		for (int i = 0; i < stages.size(); i++) {
			if (stages.get(i).getId() == stageId) {
				if (stages.get(i).getState() != StageState.WAITING)
					throw new InvalidStageStateException("This stage is not waiting for results");
				if (checkpoints.length != stages.get(i).getSegmentIds().size() + 1)
					throw new InvalidCheckpointsException("Size of checkpoints must be # of segments + 1");
				if (stages.get(i).getRiderResults().get(riderId) != null)
					throw new DuplicatedResultException("Rider ID of "+riderId+" has results already recorded");
				stages.get(i).addToRiderResults(riderId, checkpoints);
				return;
			}
		}
		throw new IDNotRecognisedException("Stage ID of "+stageId+" not found");
	}

	private Duration getDurationFromLocalTime(LocalTime localTime) {
		Duration retDuration = Duration.ofSeconds(localTime.toSecondOfDay());
		return retDuration;
	}

	@Override
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		LocalTime[] tempTimes;
		if (isRiderIdPresent(riderId) == false)
			throw new IDNotRecognisedException("Rider ID of "+riderId+" not found");
		for (int i = 0; i < stages.size(); i++) {
			if (stages.get(i).getId() == stageId) {
				tempTimes = stages.get(i).getResultsForRider(riderId);
				if (tempTimes == null)
						return new LocalTime[0];
				LocalTime[] retTimes = new LocalTime[tempTimes.length + 1];
				for (int j = 0; j < tempTimes.length; j++)
					retTimes[j] = tempTimes[j];
				retTimes[retTimes.length - 1] = retTimes[retTimes.length - 2].minus(getDurationFromLocalTime(retTimes[0]));
				return (retTimes);
			}
		}
		throw new IDNotRecognisedException("Stage ID of "+stageId+" not found");
	}

	@Override
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		LocalTime[] tempTimes;
		LocalTime retTime;
		if (isRiderIdPresent(riderId) == false)
			throw new IDNotRecognisedException("Rider ID of "+riderId+" not found");
		for (int i = 0; i < stages.size(); i++) {
			if (stages.get(i).getId() == stageId) {
				tempTimes = stages.get(i).getResultsForRider(riderId);
				if (tempTimes == null) {
					retTime = null;
				} else {
					retTime = tempTimes[tempTimes.length - 1];
					//TODO: the actual math for this
				}
				return retTime;
			}
		}
		throw new IDNotRecognisedException("Stage ID of "+stageId+" not found");
	}

	@Override
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		if (isRiderIdPresent(riderId) == false)
			throw new IDNotRecognisedException("Rider ID of "+riderId+" not found");
		for (int i = 0; i < stages.size(); i++) {
			if (stages.get(i).getId() == stageId)
				stages.get(i).removeResultsForRider(riderId);
		}
		throw new IDNotRecognisedException("Stage ID of "+stageId+" not found");
	}

	@Override
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eraseCyclingPortal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		// TODO Auto-generated method stub

	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// TODO Auto-generated method stub
		return null;
	}

}
