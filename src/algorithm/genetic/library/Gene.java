package algorithm.genetic.library;

/**
 * Created by Michał on 2017-03-26.
 */
public class Gene
{
    private Object geneType;
    private Object geneValue;

    public Object getGeneValue()
    {
        return geneValue;
    }

    public Object getGeneType()
    {
        return geneType;
    }

    public void setGeneValue(Object geneValue)
    {
        this.geneValue = geneValue;
    }

    public Gene(Object geneType, Object geneValue)
    {
        this.geneType = geneType;
        this.geneValue = geneValue;
    }

    public Gene(Gene g)
    {
        this.geneType = g.geneType;
        this.geneValue = g.geneValue;
    }
}
