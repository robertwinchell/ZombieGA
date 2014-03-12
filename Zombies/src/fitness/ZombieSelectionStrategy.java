package fitness;

import java.util.Random;

import numbers.ALifeRandom;
import chromosome.Chromosome;
import critter.Phenotype;
import critter.Population;
/**
 * 
 * @author winch036
 *
 */
public class ZombieSelectionStrategy extends SelectionStrategy
{

	public ZombieSelectionStrategy()
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
		Chromosome[] selections = new Chromosome[1];

		//goes through once
		for(int i = 0; i < selections.length; i++)
		{
			double current = r.nextDouble();
			//System.out.println("got here in SS");
			while(current > cumulitiveFitness)
			{
				//gets the fitness for the Phenotype
				fitnessSum += population.getPhenotype(index).getFitness();
				
				//calculates cumulative fitness for each time through
				cumulitiveFitness = fitnessSum / totalFitness;
				
				index++;
			}
			//System.out.println(selections[0]);
			Phenotype selection = population.getPhenotype(index-1);
			
			selections[0]=selection.getChromosome();
			
			//System.out.println(selections[0]);
			
			//System.out.println("got here in SS2");
			//resets the values to start over
			fitnessSum = 0.0;
			cumulitiveFitness = 0.0;
			index = 0;
		}
		
		return selections;
	}
	

}
