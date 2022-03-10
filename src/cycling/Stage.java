package cycling;

import cycling.StageType;
import cycling.StageState;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Stage {
    private String name;
    private String description;
    private Double length;
    private LocalDateTime startTime;
    private StageType type;
    private int id;
    private ArrayList<Integer> segmentIds;
    private StageState state;

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
	}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getLength() {
        return length;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public StageType getStageType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Integer> getSegmentIds()
    {
        return segmentIds;
    }

    public StageState getState() { return state; }

    public int[] getSegmentsIdsIntArray()
    {
        int[] retSegmentIds = new int[segmentIds.size()];
        for (int i = 0; i < segmentIds.size(); i++) {
            retSegmentIds[i] = segmentIds.get(i);
        }
        return retSegmentIds;
    }

    public String toString()
    {
        return ("Stage [name = "+name+", description = "+description+", length = "+length+", id = "+id+
                ", startTime = "+startTime+", type = "+type+", id = "+id+", segmentIds = "+segmentIds+"]");
    }

    public void addSegmentId(int segmentId) { segmentIds.add(segmentId); }

    public void removeSegmentId(int segmentIndex) { segmentIds.add(segmentIndex); }

    public void setState(StageState state) { this.state = state; }

}
