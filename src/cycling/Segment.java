package cycling;

import cycling.SegmentType;

import java.io.Serializable;

public class Segment implements Serializable {
    private SegmentType type;
    private Double location;
    private Double averageGradient; // Always 0 for sprints.
    private Double length; // Always 0 for sprints.
    private int id;

    public Segment(SegmentType type, Double location, Double averageGradient, Double length, int id)
    {
        this.type = type;
        this.location = location;
        this.averageGradient = averageGradient;
        this.length = length;
        this.id = id;
    }

    public SegmentType getSegmentType() { return type; }

    public Double getLocation() { return location; }

    public Double getAverageGradient() { return averageGradient; }

    public Double getLength() { return length; }

    public int getId() { return id; }

    public String toString()
    {
        return ("Segment [type = "+type+", location = "+location+", averageGradient = "+averageGradient+
                ", length = "+length+", id = "+id+"]");
    }
}
