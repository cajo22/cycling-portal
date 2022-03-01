package cycling;

public class Race {
	private String name;
	private String description;
	private Integer id;
	private Integer[] stageIds;

	public Race(String name, String description, Integer id)
	{
		this.name = name;
		this.description = description;
		this.id = id;
	}

	public Integer getId(){return id;}

	public String getName() {return name;}

	public String getDescription()
	{
		return description;
	}

	public Integer[] getStageIds()
	{
		return stageIds;
	}

	public String toString()
	{
		return ("Race [name = "+name+", description = "+description+", stageIds = "+stageIds+",id = "+id+"]");
	}
}
