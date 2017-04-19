package algorithm.genetic.example;

import algorithm.genetic.library.*;
import pszt.ga.exceptions.ElitismException;
import pszt.ga.exceptions.ParentSizeException;
import pszt.ga.exceptions.ProbabilityException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Michał on 07-04-2017.
 */
public class Test
{
    public static void main(String args[]) throws ProbabilityException, ElitismException, ParentSizeException
    {
        List<Computer> computerList = new ArrayList<>();

        Random random = new Random();

        // Tworzymy farmę komputerów
        for (int i = 0; i < 10; i++)
        {
            computerList.add(new Computer(i,
                    random.nextInt(1000) + 100,
                    random.nextInt(1000) + 1000));
        }

        // Tworzymy symulacje
        List<Simulation> simulationList = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
        {
            simulationList.add(new Simulation(i,
                    random.nextInt(500) + 100,
                    random.nextInt(1000) + 2000));
        }

        // Tworzymy nową populacje
        Population population = new Population(10, simulationList, computerList);

        // Generujemy losową populajce
        population.generatePopulation();

        // Inicjujemy algorytm, przekazujemy początkową populacje i funkcję liczącą ...
        GeneticAlgorithm ga = new GeneticAlgorithm(population, evaluateFitness);

        // Dodajame trochę krzyżowania, a co tam :D
        ga.enableCrossover(0.80, 6);

        // Dobra mutacja nie jest zła
        ga.enableMutation(0.10);

        // Chwila prawdy
        ga.run();
    }

    /**
     * Poglądowa implementacja funkcji obliczjacacej wartość funkcji przetrwania.
     * Muszę tu jeszcze kilka rzeczy dorobić
     */
    private static CallableWithArgument<Double, Chromosome> evaluateFitness = (chromosome) ->
    {
            List<Gene> genome = chromosome.getGenome();

            Map<Object, List<Object>> map = genome.stream()
                    .collect(Collectors.groupingBy(g -> g.getGeneValue(), Collectors.mapping(g -> g.getGeneType(), Collectors.toList())));

            double worstCase = -1;

            for(Map.Entry<Object, List<Object>> entry: map.entrySet())
            {
                Computer computer = (Computer)entry.getKey();
                List<Simulation> simulations = (List<Simulation>)(List<?>)entry.getValue();

                int operationCount = 0;
                for(Simulation simulation: simulations)
                {
                    if(simulation.getRamUsage() > computer.getRam()) return 0.0;
                    operationCount += simulation.getDuration();
                }
                double computingTime = operationCount / computer.getInstructionPerSecond();

                if(computingTime > worstCase) worstCase = computingTime;
            }

            return 1 - worstCase / 1200;
    };
}
