package cycling;

import cycling.StageType;
import java.time.LocalDateTime;

public class Stage {
    private String name;
    private String description;
    private Double length;
    private LocalDateTime startTime;
    private StageType type;
    private int id;

	public Stage(String name, String description, Double length, LocalDateTime startTime, StageType type, int id)
	{
		this.name = name;
		this.description = description;
        this.length = length;
        this.startTime = startTime;
        this.type = type;
        this.id = id;
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

}
