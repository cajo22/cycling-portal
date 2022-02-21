package cycling;

public class Race {
	private String name;
	private String description;
	private Integer[] stageIds;

	public Race(String inpName, String inpDescription)
	{
		name = inpName;
		description = inpDescription;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public Integer[] getStageIds()
	{
		return stageIds;
	}
}
