package algorithm.genetic.library;

import pszt.ga.exceptions.ParentSizeException;
import pszt.ga.exceptions.ProbabilityException;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * Created by Michał on 2017-04-07.
 */
public class GeneticAlgorithm
{
    private final int ALGORITHM_ITERATION = 100;

    private Population population;
    private final List<? extends  Object> geneValues;
    private CallableWithArgument<Double, Chromosome> evaluateFitness;
    private double mutationProbability = 0;
    private double crossoverProbability = 0;
    private int parentSize = 0;

    public GeneticAlgorithm(Population population, CallableWithArgument<Double, Chromosome> evaluateFunction)
    {
        this.population = population;
        this.geneValues = population.getGeneValues();
        this.evaluateFitness = evaluateFunction;
    }

    public void enableMutation(double probability) throws ProbabilityException
    {
        if(probability < 0 || probability > 1) throw new ProbabilityException();
        mutationProbability = probability;
    }

    public void enableCrossover(double probability, int parentSize) throws ProbabilityException, ParentSizeException
    {
        if(probability < 0 || probability > 1) throw new ProbabilityException();
        if(parentSize < 0 || parentSize > population.getPopulationSize()) throw new ParentSizeException();
        if(parentSize % 2 == 1) throw new ParentSizeException();

        this.crossoverProbability = probability;
        this.parentSize = parentSize;
    }


    public void run()
    {
        assert mutationProbability != 0 && crossoverProbability != 0: "Mutacja i krzyżowanie są wyłączne";
        
        int i = 0;
        List<Chromosome> currentPopulation = population.getChromosomes();
        Map<Chromosome, Double> parentsRating = populationEvaluation(currentPopulation);
        DoubleSummaryStatistics stats = populationStats(parentsRating);

        //Chromosome best = parentsRating.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey();

        printPopulationStats(i, stats);
        while (i < ALGORITHM_ITERATION)
        {
            List<Chromosome> children = parentSelection(parentsRating);
            if(crossoverProbability != 0) crossOver(children);
            if(mutationProbability != 0) mutate(children);
            Map<Chromosome, Double> childernRating = populationEvaluation(children);
            Map<Chromosome, Double> newPopulationRating = selectNewPopulation(parentsRating, childernRating);
            stats = populationStats(newPopulationRating);
            printPopulationStats(i, stats);
            currentPopulation = new LinkedList<>(newPopulationRating.keySet());
            parentsRating = newPopulationRating;
            i++;
        }
        i = 7;
    }

    private void printPopulationStats(int generation, DoubleSummaryStatistics stats)
    {
        System.out.println("Population " + generation + " fitness= " + stats.getMax() + " average= " + stats.getAverage());
    }

    private DoubleSummaryStatistics populationStats(Map<Chromosome, Double> populationRating)
    {
        return populationRating.entrySet().stream().collect(Collectors.summarizingDouble(entry -> entry.getValue()));
    }

    private Map<Chromosome, Double> populationEvaluation(List<Chromosome> chromosomes)
    {
        Map<Chromosome, Double> result = new HashMap<>();
        for(Chromosome chromosome: chromosomes)
            {
            double fitness = evaluateFitness.call(chromosome);
            result.put(chromosome, fitness);
        }
        return result;
    }

    private List<Chromosome> parentSelection(Map<Chromosome, Double> evaluatedPopulation)
    {
        List<Chromosome> result = new LinkedList<>();

        double totalFitness = evaluatedPopulation.entrySet().stream().collect(Collectors.summingDouble(entry -> entry.getValue()));


        for(int i = 0; i < parentSize; i++)
        {
            double rand = ThreadLocalRandom.current().nextDouble() * totalFitness;
            double accumulator = 0;

            Chromosome parent = null;

            for(Map.Entry<Chromosome, Double> entry: evaluatedPopulation.entrySet())
            {
                accumulator += entry.getValue();

                if(rand <= accumulator)
                {
                    parent = new Chromosome(entry.getKey());
                    break;
                }
            }
            assert parent != null : "Parent is null :(";
            result.add(parent);
        }

        return result;
    }

    private void crossOver(List<Chromosome> parents)
    {
        for(int i = 0; i < parents.size(); i += 2)
        {
            Operators.crossover(parents.get(i), parents.get(i + 1), crossoverProbability);
        }
    }

    private void mutate(List<Chromosome> parents)
    {
        for (Chromosome chromosome: parents)
            for(Gene gene: chromosome.getGenome())
                Operators.mutate(gene, geneValues, mutationProbability);
    }

    private Map<Chromosome, Double> selectNewPopulation(Map<Chromosome, Double> ancestors, Map<Chromosome, Double> children)
    {
        Map<Chromosome, Double> sum = new HashMap<>();
        sum.putAll(ancestors);
        sum.putAll(children);

        Map<Chromosome, Double> newPopulation = sum.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .limit(population.getPopulationSize())
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

        return newPopulation;
    }
}
