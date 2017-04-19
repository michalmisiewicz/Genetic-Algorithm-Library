package algorithm.genetic.example;

/**
 * Created by Michał on 07-04-2017.
 */
public class Computer
{
    private int id;
    private int instructionPerSecond;
    private int ram;

    public int getId()
    {
        return id;
    }

    public int getInstructionPerSecond()
    {
        return instructionPerSecond;
    }

    public int getRam()
    {
        return ram;
    }

    public Computer(int id, int instructionPerSecond, int ram)
    {
        this.id = id;
        this.instructionPerSecond = instructionPerSecond;
        this.ram = ram;
    }
}
