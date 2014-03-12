package genes;

import chromosome.*;

/**
 * Genetic Operator interface
 * forces genetic operators to employ the perform operation method
 * @author Allan Dancer
 */
public interface GeneticOperator 
{
	public abstract Chromosome[] performOperation(Chromosome[] c) 
		throws IllegalArgumentException;
	

}
