package src.cycling;

import cycling.CyclingPortalInterface;

import java.io.*;
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

import java.time.temporal.ChronoUnit;
import java.util.*;

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
	/**
	 * Randomly generate a unique integer ID.
	 *
	 * @param id An array containing existing IDs that should not be generated again.
	 * @return An integer representing an ID that is not used in id.
	 */
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

	/**
	 * Get all the IDs of stages that exist in the system.
	 *
	 * @return An array of ints that represent all stage IDs in the system.
	 */
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

	/**
	 * Get all the IDs of segments that exist in the system.
	 *
	 * @return An array of ints that represent all segment IDs in the system.
	 */
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

	/**
	 * Get the points distribution for a specific segment type.
	 *
	 * @return An array of ints that represent all rider IDs in the system.
	 */
	public int[] getRiderIds() {
		int[] retRiderIds = new int[riders.size()];
		for (int i = 0; i < riders.size(); i++) {
			retRiderIds[i] = (riders.get(i)).getId();
		}
		return retRiderIds;
	}

	/**
	 * Check if a rider of certain ID exists in the system.
	 *
	 * @param riderId The rider ID to check for.
	 * @return true if the rider exists, false otherwise
	 */
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
				for (int j = 0; j < stages.size(); j++) {
					(stages.get(i)).removeFromRiderResults(riderId);
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
					retTime = retTime.truncatedTo(ChronoUnit.SECONDS);
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
		LocalTime[] temp;
		Map<Integer, Long> riderTimes = new HashMap<Integer, Long>();
		int[] ridersRankInStage = new int[riders.size()];
		for (int i = 0; i < stages.size(); i++) {
			if ((stages.get(i)).getId() == stageId) {
				// get times to the nanosecond as a long
				for (int j = 0; j < riders.size(); j++) {
					riderTimes.put(riders.get(j).getId(), (getRiderAdjustedElapsedTimeInStage(stageId, j)).toNanoOfDay());
				}
				// now sort the times and ids
				List<Map.Entry<Integer, Long>> list = new ArrayList<>(riderTimes.entrySet());
				list.sort(Map.Entry.comparingByValue());
				for (int j = 0; j < list.size(); j++) {
					ridersRankInStage[j] = list.get(j).getKey();
				}
				return ridersRankInStage;
			}
		}
		throw new IDNotRecognisedException("Stage ID of "+stageId+" not found");
	}

	@Override
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {
		ArrayList<LocalTime> rankedAdjustedElapsedTimesInStage = new ArrayList<>();
		int[] ridersRankInStage = new int[riders.size()];
		LocalTime adjustedElapsedTime;
		for (int i = 0; i < stages.size(); i++) {
			if ((stages.get(i)).getId() == stageId)
				ridersRankInStage = getRidersRankInStage(stageId);
			for (int j = 0; i < ridersRankInStage.length; j++) {
				int riderId = ridersRankInStage[j];
				adjustedElapsedTime = getRiderAdjustedElapsedTimeInStage(stageId, riderId);
				rankedAdjustedElapsedTimesInStage.add(adjustedElapsedTime);
			}
			LocalTime[] retArray = new LocalTime[riders.size()];
			return rankedAdjustedElapsedTimesInStage.toArray(retArray);
		}
		throw new IDNotRecognisedException("Stage ID of "+stageId+" not found");
	}

	@Override
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// awards points according to fig 1:
		StageType type;
		int[] pointDistribution;
		LocalTime[] ridersAdjustedElapsedTime;
		int[] ridersPointsInStage = new int[riders.size()];
		for (int i = 0; i < stages.size(); i++) {
			if ((stages.get(i)).getId() == stageId) {
				if (!(stages.get(i)).getRiderResults().isEmpty()) {
					type = (stages.get(i)).getStageType();
					if (type == StageType.FLAT) {
						pointDistribution = new int[]{50,30,20,18,16,14,12,10,8,7,6,5,4,3,2};
					}
					else if (type == StageType.MEDIUM_MOUNTAIN) {
						pointDistribution = new int[]{30,25,22,19,17,15,13,11,9,7,6,5,4,3,2};
					}
					else {
						pointDistribution = new int[]{20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};
					}
					ridersAdjustedElapsedTime = getRankedAdjustedElapsedTimesInStage(stageId);
					for (int j = 0; j < ridersAdjustedElapsedTime.length; j++) {
						if (ridersAdjustedElapsedTime[j] == null || j > pointDistribution.length) {
							ridersPointsInStage[j] = 0;
						} else {
							// need to account for riders with equal times
							if (j > 0 && ridersAdjustedElapsedTime[j] == ridersAdjustedElapsedTime[j - 1])
								ridersPointsInStage[j] = ridersPointsInStage[j - 1];
							else
								ridersPointsInStage[j] = pointDistribution[j];
						}
					}
				}
				return (ridersPointsInStage);
			}
		}
		throw new IDNotRecognisedException("Stage ID of "+stageId+" not found");
	}

	/**
	 * Given an array of segment IDs, extract their type.
	 *
	 * @param segmentIds An array of ints representing ids of segments in a stage.
	 * @return An array of SegmentTypes matching segmentIds.
	 *         An empty array if type is invalid.
	 */
	private SegmentType[] extractSegmentTypeArray(int[] segmentIds) {
		SegmentType[] retArray = new SegmentType[segmentIds.length];
		for (int id : segmentIds) {
			for (int i = 0; i < segments.size(); i++) {
				if (segments.get(i).getId() == id) {
					retArray[i] = segments.get(i).getSegmentType();
				}
			}
		}
		return retArray;
	}
	/**
	 * Get the points distribution for a specific segment type.
	 *
	 * @param type The SegmentType to get the distribution of
	 * @return An array of ints that describe the point distribution of the race.
	 *         An empty array if type is invalid.
	 */
	private int[] getPointDistributionForSegment(SegmentType type) {
		switch (type) {
			case C4:
				return new int[]{1};
			case C3:
				return new int[]{2,1};
			case C2:
				return new int[]{5,3,2,1};
			case C1:
				return new int[]{10,8,6,4,2,1};
			case HC:
				return new int[]{20,15,12,10,8,6,4,2,1};
			default:
				return new int[]{};
		}
	}

	@Override
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// Id check stage*, check climbtype?, award points according to fig 2:
		int[] ridersMountainPointsInStage = new int[riders.size()];
		int[] pointDistribution = new int[]{0,0,0,0,0,0,0,0,0};
		for (int i = 0; i < stages.size(); i++) {
			if ((stages.get(i)).getId() == stageId) {
				int[] stageSegmentIds = getStageSegments(stageId);
				SegmentType[] stageSegmentTypes = extractSegmentTypeArray(stageSegmentIds);
				// need to get total point distribution (sum of distribution for each segment)
				for (int j = 0; j < stageSegmentTypes.length; j++) {
					int[] tempPointDistribution = getPointDistributionForSegment(stageSegmentTypes[j]);
					for (int k = 0; k < tempPointDistribution.length; k++) {
						pointDistribution[k] += tempPointDistribution[k];
					}
				}
				// now we can calculate points per rider
				LocalTime[] ridersAdjustedElapsedTime = getRankedAdjustedElapsedTimesInStage(stageId);
				for (int j = 0; j < ridersAdjustedElapsedTime.length; j++) {
					if (ridersAdjustedElapsedTime[j] == null || j > pointDistribution.length) {
						ridersMountainPointsInStage[j] = 0;
					} else {
						// need to account for riders with equal times
						if (j > 0 && ridersAdjustedElapsedTime[j] == ridersAdjustedElapsedTime[j - 1])
							ridersMountainPointsInStage[j] = ridersMountainPointsInStage[j - 1];
						else
							ridersMountainPointsInStage[j] = pointDistribution[j];
					}
				}
			}
			return ridersMountainPointsInStage;
		}
		throw new IDNotRecognisedException("Stage ID of "+stageId+" not found");
	}

	@Override
	public void eraseCyclingPortal() {
		races.clear();
		stages.clear();
		segments.clear();
		teams.clear();
		riders.clear();
	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(filename))) {
			stream.writeObject(races);
			stream.writeObject(stages);
			stream.writeObject(segments);
			stream.writeObject(teams);
			stream.writeObject(riders);
		}
	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(filename))) {
			for (int i = 0; i < 5; i++) {
				Object obj = stream.readObject();
				if (obj instanceof ArrayList)
					switch(i)
					{
					case 0:
						races = (ArrayList<Race>) obj;
					case 1:
						stages = (ArrayList<Stage>) obj;
					case 2:
						segments = (ArrayList<Segment>) obj;
					case 3:
						teams = (ArrayList<Team>) obj;
					case 4:
						riders = (ArrayList<Rider>) obj;
					}
			}
		}
	}

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		for (int i = 0; i < races.size(); i++) {
			if ((races.get(i)).getName() == name) {
				races.remove(i);
				return;
			}
		}
		throw new NameNotRecognisedException("Race named "+name+" not found");
	}

	@Override
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		Map<Integer, Long> riderTimes = new HashMap<Integer, Long>();
		LocalTime[] generalClassificationTimes = new LocalTime[riders.size()];
		long tempNanos;
		LocalTime tempLocalTime;
		for (int i = 0; i < races.size(); i++) {
			if (races.get(i).getId() == raceId) {
				int[] stageIds = races.get(i).getStageIdsIntArray();
				int[] riderIds = getRiderIds();
				for (int j : stageIds) {
					if (!stages.get(j).getRiderResults().isEmpty()) {
						for (int k : riderIds) {
							tempNanos = riderTimes.get(k);
							tempLocalTime = getRiderAdjustedElapsedTimeInStage(j, k);
							if (tempLocalTime == null)
								tempNanos = 0;
							else
								tempNanos += getRiderAdjustedElapsedTimeInStage(j, k).toNanoOfDay();
							riderTimes.put(k, tempNanos);
						}
					}
				}
				// sort it
				List<Map.Entry<Integer, Long>> list = new ArrayList<>(riderTimes.entrySet());
				list.sort(Map.Entry.comparingByValue());
				// from long to LocalTime
				for (int k = 0; k < list.size(); k++) {
					generalClassificationTimes[k] = LocalTime.ofNanoOfDay(list.get(k).getValue());
				}
				return generalClassificationTimes;
			}
		}
		throw new IDNotRecognisedException("Race ID of "+raceId+" not found");
	}

	@Override
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		int[] ridersPointsInRace = new int[riders.size()];
		int[] tempPoints;
		for (int i = 0; i < races.size(); i++) {
			if (races.get(i).getId() == raceId) {
				int[] stageIds = getRaceStages(raceId);
				for (int j : stageIds) {
					tempPoints = getRidersPointsInStage(j);
					if (tempPoints.length == 0)
						return new int[riders.size()];
					for (int k = 0; k < ridersPointsInRace.length; k++) {
						ridersPointsInRace[k] += tempPoints[k];
					}
				}
				return ridersPointsInRace;
			}
		}
		throw new IDNotRecognisedException("Race ID of "+raceId+" not found");
	}

	@Override
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		int[] ridersMountainPointsInRace = new int[riders.size()];
		int[] tempMountainPoints;
		for (int i = 0; i < races.size(); i++) {
			if (races.get(i).getId() == raceId) {
				int[] stageIds = getRaceStages(raceId);
				for (int j : stageIds) {
					tempMountainPoints = getRidersMountainPointsInStage(j);
					if (tempMountainPoints.length == 0)
						return new int[riders.size()];
					for (int k = 0; k < ridersMountainPointsInRace.length; k++) {
						ridersMountainPointsInRace[k] += tempMountainPoints[k];
					}
				}
				return ridersMountainPointsInRace;
			}
		}
		throw new IDNotRecognisedException("Race ID of "+raceId+" not found");
	}

	@Override
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		Map<Integer, Long> riderTimes = new HashMap<Integer, Long>();
		int[] generalClassificationRanks = new int[riders.size()];
		long tempNanos;
		LocalTime tempLocalTime;
		for (int i = 0; i < races.size(); i++) {
			if (races.get(i).getId() == raceId) {
				int[] stageIds = races.get(i).getStageIdsIntArray();
				int[] riderIds = getRiderIds();
				for (int j : stageIds) {
					if (!stages.get(j).getRiderResults().isEmpty()) {
						for (int k : riderIds) {
							tempNanos = riderTimes.get(k);
							tempLocalTime = getRiderAdjustedElapsedTimeInStage(j, k);
							if (tempLocalTime == null)
								tempNanos = 0;
							else
								tempNanos += getRiderAdjustedElapsedTimeInStage(j, k).toNanoOfDay();
							riderTimes.put(k, tempNanos);
						}
					}
				}
				// sort it
				List<Map.Entry<Integer, Long>> list = new ArrayList<>(riderTimes.entrySet());
				list.sort(Map.Entry.comparingByValue());
				// get the key (id)
				for (int k = 0; k < list.size(); k++) {
					generalClassificationRanks[k] = list.get(k).getKey();
				}
				return generalClassificationRanks;
			}
		}
		throw new IDNotRecognisedException("Race ID of "+raceId+" not found");
	}
	/**
	 * Get the ranked list of riders based on the points classification in a race.
	 * <p>
	 * The state of this MiniCyclingPortalInterface must be unchanged if any
	 * exceptions are thrown.
	 *
	 * @param raceId The ID of the race being queried.
	 * @param mode Rank based on points (0) or mountain points (1)
	 * @return A ranked list of riders' IDs sorted descending by the sum of their
	 *         points/mountain points in all stages of the race. That is, the first in
	 *         this list is the winner (more points). An empty list if there is no result
	 *          for any stage in the race.
	 * @throws IDNotRecognisedException If the ID does not match any race in the
	 *                                  system.
	 */
	private int[] getRidersPointClassificationRankGeneric(int raceId, int mode) throws IDNotRecognisedException {
		HashMap<Integer, Integer> riderPointMapping = new HashMap<Integer, Integer>();
		int[] curPoints, curRank;
		int[] ridersClassificationRank = new int[riders.size()];
		for (int i = 0; i < races.size(); i++) {
			if (races.get(i).getId() == raceId) {
				// horribly inefficient but deadline approaching
				int[] stageIds = races.get(i).getStageIdsIntArray();
				for (int j : stageIds) {
					if (mode == 0)
						curPoints = getRidersPointsInStage(j);
					else
						curPoints = getRidersMountainPointsInStage(j);
					curRank = getRidersRankInStage(j);
					if (j == stageIds[0]) {
						// set up the mapping
						for (int k = 0; k < curRank.length; k++) {
							riderPointMapping.put(curRank[k], curPoints[k]);
						}
					} else {
						for (int k = 0; k < curRank.length; k++) {
							riderPointMapping.put(curRank[k], riderPointMapping.get(curRank[k]) + curPoints[k]);
						}
					}
				}
				List<Map.Entry<Integer, Integer>> list = new ArrayList<>(riderPointMapping.entrySet());
				list.sort(Map.Entry.comparingByValue());
				for (int j = 0; j < list.size(); j++) {
					ridersClassificationRank[j] = list.get(j).getKey();
				}
				return ridersClassificationRank;
			}
		}
		throw new IDNotRecognisedException("Race ID of "+raceId+" not found");
	}

	@Override
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		return getRidersPointClassificationRankGeneric(raceId, 0);
	}

	@Override
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		return getRidersPointClassificationRankGeneric(raceId, 1);
	}
}
