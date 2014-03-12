package fitness;

import critter.*;
/**
 * Fitness Evaluator Class
 * evaluates and assigns the fitness value to each critter in the
 * Popualtion
 * @author Zach Winchell
 */
public class FitnessEvaluator 
{
	/**
	 *Takes a population and evaluates it according to the assigned fitness
	 * function the gives it a numerical value which represents the fitness.
	 * In this case, it is between 0 and 1, higher fitness values are 
	 * traditionally better.
	 * 
	 * @param population - the population to be evaluated
	 * 		
	 * @param fitnessFunction - the fitness function that 
	 * is evaluating the population
	 * 		
	 */
	public static void assignFitnessValuesToPhenotypes(Population
	population, FitnessFunction fitnessFunction)
	{
		//increments through the population
		for(int i = 0; i < population.size(); i++)
		{
			Phenotype phenotype = (Phenotype)population.get(i);
			
			//calls the fitness function to evaluate a Phenotypes 
			//fitness
			double fitness = fitnessFunction.evaluateFitness(phenotype);

			//sets the phenotypes fitness
			phenotype.setFitness(fitness);
		}
	}
}

