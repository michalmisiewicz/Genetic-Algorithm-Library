package algorithm.genetic.library;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Michał on 2017-03-26.
 */
public class Chromosome
{
    private List<Gene> genome;

    public List<Gene> getGenome()
    {
        return genome;
    }

    public Chromosome(List<Gene> genome)
    {
        this.genome = genome;
    }

    public Chromosome(Chromosome c)
    {
        genome = new LinkedList<>();
        for(Gene gene: c.genome)
        {
            this.genome.add(new Gene(gene));
        }
    }
}
