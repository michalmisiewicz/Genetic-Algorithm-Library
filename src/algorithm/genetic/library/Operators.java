package algorithm.genetic.library;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Michał on 08-04-2017.
 */
public class Operators
{
    public static void crossover(Chromosome p1, Chromosome p2, double probability)
    {
        double rand = ThreadLocalRandom.current().nextDouble();

        if(rand <= probability)
        {
            int genomeSize = p1.getGenome().size();
            int crossoverPoint = ThreadLocalRandom.current().nextInt(genomeSize - 2) + 1;

            for (int i = 0; i < crossoverPoint; i++)
            {
                Gene temp = p1.getGenome().remove(crossoverPoint);
                p2.getGenome().add(0, temp);
                temp = p2.getGenome().remove(crossoverPoint + 1);
                p1.getGenome().add(0, temp);
            }
        }
    }

    public static void mutate(Gene gene, List<? extends  Object> geneValues, double probability)
    {
        double rand = ThreadLocalRandom.current().nextDouble();

        if(rand <= probability)
        {
            int index = ThreadLocalRandom.current().nextInt(geneValues.size());
            gene.setGeneValue(geneValues.get(index));
        }
    }
}
