package cycling;

import cycling.StageType;
import cycling.StageState;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Stage implements Serializable {
    private String name;
    private String description;
    private Double length;
    private LocalDateTime startTime;
    private StageType type;
    private int id;
    private ArrayList<Integer> segmentIds;
    private StageState state;
    private Dictionary<Integer, LocalTime[]> riderResults;

	public Stage(String name, String description, Double length, LocalDateTime startTime, StageType type, int id)
	{
		this.name = name;
		this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.type = type;
        this.id = id;
        this.segmentIds = new ArrayList<Integer>();
        this.state = StageState.IDLE;
        this.riderResults = new Hashtable<Integer, LocalTime[]>();
	}

    /**
     * Get the name of the stage.
     *
     * @return The name as a string.
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of the stage.
     *
     * @return The description as a string.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the length of the stage.
     *
     * @return The length as a double.
     */
    public Double getLength() {
        return length;
    }

    /**
     * Get the start time of the stage.
     *
     * @return The start time as a LocalDateTime.
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    /**
     * Get the type of the stage.
     *
     * @return The type as a StageType.
     */
    public StageType getStageType() {
        return type;
    }

    /**
     * Get the ID of the stage.
     *
     * @return The ID as an int.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the ids of the stage's segments.
     *
     * @return An ArrayList of the segment IDs.
     */
    public ArrayList<Integer> getSegmentIds()
    {
        return segmentIds;
    }

    /**
     * Get the state of the stage.
     *
     * @return The state as a StageState.
     */
    public StageState getState() { return state; }


    /**
     * Get the ids of the stage's segments as an array.
     *
     * @return An array of the segment IDs, which are ints.
     */
    public int[] getSegmentsIdsIntArray()
    {
        int[] retSegmentIds = new int[segmentIds.size()];
        for (int i = 0; i < segmentIds.size(); i++) {
            retSegmentIds[i] = segmentIds.get(i);
        }
        return retSegmentIds;
    }

    /**
     * Get the results of all riders registered in the stage.
     *
     * @return A dictionary mapping rider ID to their results (an array of LocalTime).
     */
    public Dictionary<Integer, LocalTime[]> getRiderResults() { return riderResults; }

    /**
     * Get the results of a particular rider.
     *
     * @param int The ID of the rider whose results should be returned.
     * @return An array of LocalTimes - the times that the rider passed each checkpoint.
     */
    public LocalTime[] getResultsForRider(int riderId) { return riderResults.get(riderId); }

    /**
     * Remove the results of a particular rider.
     *
     * @param int The ID of the rider whose results should be removed.
     */
    public void removeResultsForRider(int riderId) {
        if (riderResults.get(riderId) != null)
                riderResults.remove(riderId);
    }

    /**
     * Get the stage data as a string.
     *
     * @return A string showing the state of the stage's variables.
     */
    public String toString()
    {
        return ("Stage [name = "+name+", description = "+description+", length = "+length+", id = "+id+
                ", startTime = "+startTime+", type = "+type+", id = "+id+", segmentIds = "+segmentIds+
                ", riderResults = "+riderResults+"]");
    }

    /**
     * Add a segment ID to the stage.
     *
     * @param int The ID of the segment that should be added.
     */
    public void addSegmentId(int segmentId) { segmentIds.add(segmentId); }

    /**
     * Remove a segment ID from the stage.
     *
     * @param int The ID of the segment that should be removed.
     */
    public void removeSegmentId(int segmentIndex) { segmentIds.add(segmentIndex); }

    /**
     * Set the stage's state (idle or waiting for results.)
     *
     * @param StageState The state to set the stage to.
     */
    public void setState(StageState state) { this.state = state; }

    /**
     * Update/add a rider's results to the stage.
     *
     * @param int The ID of the rider whose results should be added.
     * @param LocalTime[] The rider's results for that stage.
     */
    public void addToRiderResults(int riderId, LocalTime[] checkpoints) {
        riderResults.put(riderId, checkpoints);
    }

    /**
     * Remove a rider's results from the stage.
     *
     * @param int The ID of the rider whose results should be removed.
     */
    public void removeFromRiderResults(int riderId) {
        riderResults.remove(riderId);
    }

}
