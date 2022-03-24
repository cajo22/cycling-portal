package cycling;

import java.io.Serializable;
import java.util.ArrayList;

public class Rider implements Serializable {
    private int teamId;
    private int id;
    private String name;
    private int yearOfBirth;

    public Rider(int teamId, int id, String name, int yearOfBirth)
    {
        this.name = name;
        this.teamId = teamId;
        this.id = id;
        this.yearOfBirth = yearOfBirth;
    }

    public int getId(){return id;}

    public int getTeamId(){return teamId;}

    public String getName() {return name;}

    public int getYearOfBirth(){return yearOfBirth;}

    public String toString()
    {
        return ("Rider [name = "+name+", teamId = "+teamId+", yearOfBirth = "+yearOfBirth+", id = "+id+"]");
    }

}
