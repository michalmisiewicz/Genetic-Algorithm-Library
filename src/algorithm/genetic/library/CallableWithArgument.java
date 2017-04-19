package algorithm.genetic.library;

/**
 * Created by Michał on 07-04-2017.
 */

@FunctionalInterface
public interface CallableWithArgument<V, T>
{
    public V call(T argument);
}
