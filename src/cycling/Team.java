package cycling;

import java.util.ArrayList;

public class Team {
    private String name;
    private String description;
    private int id;
    private ArrayList<Integer> riderIds;
    
    public Team(String name, String description, int id)
    {
        this.name = name;
        this.description = description;
        this.id = id;
        this.riderIds = new ArrayList<Integer>();
    }

    public int getId(){return id;}

    public String getName() {return name;}

    public String getDescription()
    {
        return description;
    }

    public ArrayList<Integer> getRiderIds()
    {
        return riderIds;
    }

    public int[] getRiderIdsIntArray()
    {
        int[] retRiderIds = new int[riderIds.size()];
        for (int i = 0; i < riderIds.size(); i++) {
            retRiderIds[i] = riderIds.get(i);
        }
        return retRiderIds;
    }

    public String toString()
    {
        return ("Team [name = "+name+", description = "+description+", riderIds = "+riderIds+", id = "+id+"]");
    }

    public void addRiderId(int riderId) {
        riderIds.add(riderId);
    }

    public void removeRiderId(int riderIndex) {
        riderIds.remove(riderIndex);
    }
}
