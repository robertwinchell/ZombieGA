package evolution;

import java.util.*;
import critter.*;
import fitness.*;
import genes.*;
import chromosome.*;

/**
 * Explicit Evolution Strategy w/ Elitist approach
 * Sets the population up to be "evolved" and makes sure to 
 * keep the best Phenotype in each generation
 * 
 * @author Zach Winchell
 */
public class ExplicitEvolutionStrategy extends EvolutionStrategy
{
	/**
	 * default constructor.
	 */
	public ExplicitEvolutionStrategy()
	{
	}
	/**
	 * Evolve population method, sends the population through the Gene Manipulator
	 * and Selection Strategy
	 */
	public Population evolvePopulation(Population pop,
			SelectionStrategy strategy, GeneManipulator manipulator) 
	{
		Population newPop = new Population();
		Random r = new Random();
		
		 //makes sure evolved pop is as big as the original
		while(newPop.size() < pop.size()-1)
		{
			//holds the chromosomes returned by the selection
			//strategy
			Chromosome[] selected = strategy.selectChromosomes(pop);
			
			//holds the chromosomes that have been manipulated by
			//the passed in manipulators.  This array is of size 1.
			//System.out.println(selected);
			Chromosome[] manipulated = manipulator.performGeneticOperations(selected);
			
			//creates a phenotype of the only chromosome in the
			//array of manipulated chromosomes.
			Phenotype phenotype = new Phenotype(manipulated[0],r.nextInt(50),r.nextInt(50), 10);
			
			newPop.addPhenotype(phenotype);
		}
		//Elitist approach - implements it by keeping the best phenotype 
		//from each generation
		newPop.add(pop.getBestPhenotype());
		
		return newPop;
	}
	
}

