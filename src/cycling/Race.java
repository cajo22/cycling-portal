package cycling;

import java.io.Serializable;
import java.util.ArrayList;

public class Race implements Serializable {
	private String name;
	private String description;
	private int id;
	private ArrayList<Integer> stageIds;

	public Race(String name, String description, int id)
	{
		this.name = name;
		this.description = description;
		this.id = id;
		this.stageIds = new ArrayList<Integer>();
	}

	public int getId(){return id;}

	public String getName() {return name;}

	public String getDescription()
	{
		return description;
	}

	public ArrayList<Integer> getStageIds()
	{
		return stageIds;
	}

	public int[] getStageIdsIntArray()
	{
		int[] retStageIds = new int[stageIds.size()];
		for (int i = 0; i < stageIds.size(); i++) {
			retStageIds[i] = stageIds.get(i);
		}
		return retStageIds;
	}

	public String toString()
	{
		return ("Race [name = "+name+", description = "+description+", stageIds = "+stageIds+", id = "+id+"]");
	}

	public void addStageId(int stageId) {
		stageIds.add(stageId);
	}

	public void removeStageId(int stageIndex) {
		stageIds.remove(stageIndex);
	}
}
