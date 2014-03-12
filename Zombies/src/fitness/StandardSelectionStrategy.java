package fitness;

import critter.*;
import numbers.*;
import java.util.*;
import chromosome.*;

/**
 * Standard Selection Strategy Class
 * implements Selection Strategy method to select the chromosomes
 * for crossover
 * @author Zach Winchell
 */
public class StandardSelectionStrategy extends SelectionStrategy
{
	public StandardSelectionStrategy()
	{
		super();
	}
	
	/**
	 * selects the chromosomes from the Phenotypes to be evaluated
	 * by fitness level
	 * 
	 * @param population - population to be changed
	 * 
	 * @return -Chromosome array that was manipulated
	 * 	
	 */
	public Chromosome[] selectChromosomes(Population population)
	{
		double averageFitness = population.averageFitness();
		
		//total fitness of popualtion
		double totalFitness = averageFitness * population.size();

		Random r= ALifeRandom.getRandomNumberGenerator();
		
		double fitnessSum = 0.0;
		int index = 0;
		double cumulitiveFitness = 0.0;
		
		//holds the two chromosomes
		Chromosome[] selections = new Chromosome[2];

		//goes through twice
		for(int i = 0; i < selections.length; i++)
		{
			double current = r.nextDouble();
			
			while(current > cumulitiveFitness)
			{
				//gets the fitness for the Phenotype
				fitnessSum += population.getPhenotype(index).getFitness();
				
				//calculates cumulative fitness for each time through
				cumulitiveFitness = fitnessSum / totalFitness;
				
				index++;
			}
			Phenotype selection = population.getPhenotype(index-1);
			
			selections[i]= selection.getChromosome();
			//resets the values to start over
			fitnessSum = 0.0;
			cumulitiveFitness = 0.0;
			index = 0;
		}
		
		return selections;
	}
	

}
