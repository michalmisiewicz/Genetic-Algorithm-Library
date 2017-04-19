package algorithm.genetic.example;

/**
 * Created by Michał on 07-04-2017.
 */
public class Simulation
{
    private int id;
    private int ramUsage;
    private int duration;

    public int getId()
    {
        return id;
    }

    public int getRamUsage()
    {
        return ramUsage;
    }

    public int getDuration()
    {
        return duration;
    }

    public Simulation(int id, int ramUsage, int duration)
    {
        this.id = id;
        this.ramUsage = ramUsage;
        this.duration = duration;
    }
}
