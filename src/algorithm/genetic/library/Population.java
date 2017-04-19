package algorithm.genetic.library;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Michał on 2017-03-26.
 */
public class Population
{
    private int populationSize;
    private List<Chromosome> chromosomeList;
    private final List<? extends  Object> geneType;
    private final List<? extends  Object> geneValues;

    public List<Chromosome> getChromosomes()
    {
        return chromosomeList;
    }

    public List<? extends Object> getGeneType()
    {
        return geneType;
    }

    public List<? extends Object> getGeneValues()
    {
        return geneValues;
    }

    public int getPopulationSize()
    {
        return populationSize;
    }

    public Population(int populationSize, List<? extends Object> geneType, List<? extends  Object> geneValues)
    {
        this.populationSize = populationSize;
        this.geneType = geneType;
        this.geneValues = geneValues;
        this.chromosomeList = new LinkedList<>();
    }

    public void generatePopulation()
    {
        for(int i = 0; i < populationSize; i++)
        {
            List<Gene> genome = new ArrayList<>();
            for (Object gene : geneType)
            {
                int index = ThreadLocalRandom.current().nextInt(geneValues.size());
                genome.add(new Gene(gene, geneValues.get(index)));
            }
            Chromosome chromosome = new Chromosome(genome);
            chromosomeList.add(chromosome);
        }
    }


}
