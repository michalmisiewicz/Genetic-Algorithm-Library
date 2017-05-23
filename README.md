## Generyczny algorytm ewolucyjny
#### Biblioteka jest generyczną implementacją algorytmu ewolucyjnego. Pozwala na rozwiązanie dowolnego problemu, modelowanego za pomocą obiektów.
###
### Sposób użycia biblioteki
```java
Population population = new Population(10, geneTypes, geneValues);
population.generatePopulation();

GeneticAlgorithm ga = new GeneticAlgorithm(population, evaluateFitness);
ga.enableCrossover(0.80, 6);
ga.enableMutation(0.10);
ga.run();
```
###
#### Do poprawnego działania algorytmu należy zaimplementować interfejs CallableWithArgument liczący współczynnik przystosowania osobnika
```java
private static CallableWithArgument<Double, Chromosome> evaluateFitness = (chromosome) ->
{
    List<Gene> genome = chromosome.getGenome();
    factor = someComputation(genome);
    return factor;
};
```
###
### Przykład użycia algorytmu
#### Problem szeregowania zbioru N symulacji na klastrze M komputerów
```java
public class Test
{
    public static void main(String args[]) throws ProbabilityException, ElitismException, ParentSizeException
    {
        List<Computer> computerList = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < 10; i++)
        {
            computerList.add(new Computer(i,
                    random.nextInt(1000) + 100,
                    random.nextInt(1000) + 1000));
        }

        List<Simulation> simulationList = new ArrayList<>();
        for (int i = 0; i < 1000; i++)
        {
            simulationList.add(new Simulation(i,
                    random.nextInt(500) + 100,
                    random.nextInt(1000) + 2000));
        }

        Population population = new Population(10, simulationList, computerList);

        // Generujemy losową populajce
        population.generatePopulation();

        GeneticAlgorithm ga = new GeneticAlgorithm(population, evaluateFitness);

        ga.enableCrossover(0.80, 6);
        ga.enableMutation(0.10);

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
```
####
### Computer.java
```java
public class Computer
{
    private int id;
    private int instructionPerSecond;
    private int ram;

    ...

    public Computer(int id, int instructionPerSecond, int ram)
    {
        this.id = id;
        this.instructionPerSecond = instructionPerSecond;
        this.ram = ram;
    }
}
```
####
### Simulation.java
```java
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
```
