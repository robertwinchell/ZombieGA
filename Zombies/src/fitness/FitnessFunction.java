package fitness;

import critter.*;

/**
 * Fitness Function Interface that must be implemented 
 * for the evaluateFitness method.  This is where the child
 * class will assign a number to each phenotypes fitness and 
 * rank them among the population
 * @author Zach Winchell
 */
public interface FitnessFunction 
{
	/**
	 * Each child class that implements this will 
	 * have different ways to evaluate the fitness of
	 * their population
	 * 
	 * @return - the fitness value
	 * 		
	 */
	public double evaluateFitness(Phenotype p);
}
