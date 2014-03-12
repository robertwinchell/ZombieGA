package evolution;
import chromosome.*;
import fitness.*;
import genes.*;
import critter.*;
/**
 * ALife Factory class
 * Abstract class that creates new instances of
 * every part needed to create a critter simulation by having the child
 * class call all the needed methods from previous classes.
 
 * 
 * @author winch036
 *
 */
public abstract class ALifeFactory 
{
	
	public abstract Chromosome getChromosome();
	
	
	public abstract EvolutionStrategy getEvolutionStrategy();
	
	
	public abstract FitnessFunction getFitnessFunction();
	
	
	public abstract GeneManipulator getGeneManipulator();
	
	
	public abstract GeneticOperator[] getGeneticOperators();
	
	
	public abstract Phenotype getPhenotype();
	
	
	public abstract Population getPopulation();
	
	
	public abstract SelectionStrategy getSelectionStrategy();
	
	
	public abstract int getMaximumGeneration();
	

	public abstract FitnessEvaluator getFitnessEvaluator();
	
	
	
}
