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
#### Do poprawnego działąnia algorytmu należy zaimplementować interfejs CallableWithArgument liczący współczynnik przystosowania osobnika
```java
private static CallableWithArgument<Double, Chromosome> evaluateFitness = (chromosome) ->
{
    List<Gene> genome = chromosome.getGenome();
    factor = someComputation(genome);
    return factor;
};
```
