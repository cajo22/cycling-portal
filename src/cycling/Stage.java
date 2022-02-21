package cycling;

import java.time.LocalTime;

public class Stage {
    private String name;
    private String description;
    private Integer duration;
    private LocalTime startTime;
    private StageType type;

	public Stage(String inpName, String inpDescription, Integer inpDuration, LocalTime inpStartTime, StageType inpType)
	{
		name = inpName;
		description = inpDescription;
        duration = inpDuration;
        startTime = inpStartTime;
        type = inpType;
	}

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDuration() {
        return duration;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public StageType getStageType() {
        return StageType;
    }

}
