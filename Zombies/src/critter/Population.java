/*
 * Created on Feb 5, 2008
 */
package critter;

import chromosome.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * The population class holds a collection of phenotypes(critters) to
 * be used in the artificial life simulations.  The population will change
 * each generation and the changes in the phenotypes of this population is what
 * will make the population change in all its aspects of possible change.
 * @author Allan Dancer
 */
public class Population extends ArrayList
{
	
	/**
	 * Creates a Population with the collection of phenotypes
	 * being the collection that was passed in to this
	 * constructor.
	 * @param theCollection - a collection of Phenotypes
	 * for this population.
	 */
	public Population(Collection theCollection)
	{
		super(theCollection);
	}
	
	/**
	 * Default constructor.  Creates a Population and initializes 
	 * the Collection of phenotypes of this population to be empty.
	 */
	public Population()
	{
		super();
	}
	
	/**
	 * Returns but does not remove the Phenotype at the given 
	 * index (passed in).
	 * @param index - the index of the collection for which
	 * the desired Phenotype will be returned.
	 * @return - the desired phenotype at the requested index.
	 */
	public Phenotype getPhenotype(int index)
	{
	
		return (Phenotype)super.get(index);
	}
	
	/**
	 * Takes the passed in phenotype and adds it to
	 * this population.
	 * @param phenotype - the phenotype to be added
	 * to this population
	 */
	public void addPhenotype(Phenotype phenotype)
	{
		super.add(phenotype);
	}
	
	/**
	 * Adds the given phenotype to the population at the specified
	 * index.
	 * @param index - the index to where the passed in phenotype
	 * will be added to this population.
	 * @param phenotype - The phenotype to be added to the population
	 * at the passed in index.
	 */
	public void setPhenotype(int index, Phenotype phenotype)
	{
		super.add(index, phenotype);
	}
	
	/**
	 * Removes the first instance of the passed in phenotype
	 * from the population.
	 * @param phenotype - the instance of the phenotype to
	 * be removed from this population
	 */
	public void removePhenotype(Phenotype phenotype)
	{
		super.remove(phenotype);
	}
	
	/**
	 * Calculates and returns the average fitness of the population.
	 * This calculation is done by adding up the fitness of each Phenotype
	 * in the population and then dividing this sum be the total number
	 * of phenotypes in the population.
	 * @return - the average fitness of this population
	 */
	public double averageFitness()
	{
		double totalFitness = 0;
		double averageFitness = 0;
		
		//adds up the cumulative fitness of all the Phenotypes
		//in the population.
		for(int i = 0; i < super.size(); i++)
		{
			Phenotype currentPhenotype = (Phenotype)super.get(i);
			totalFitness += currentPhenotype.getFitness();
		}
		
		averageFitness = totalFitness / super.size();
		return averageFitness;
	}
	
	/**
	 * Retrieves and returns the phenotype in the population 
	 * with the highest fitness.  If there are two phenotypes with
	 * equal fitness values the phenotype that appears first in the 
	 * population will be returned.
	 * @return - the Phenotype in the population with
	 * the highest fitness.
	 */
	public Phenotype getBestPhenotype()
	{
		//initializes the best phenotype to be the first
		//so that we have something to compare the other
		//phenotypes to.
		Phenotype best = (Phenotype)super.get(0);
		
		//initialized to be the fitness of the first phenotype
		//in the population
		double highestFitness = best.getFitness();
		
		//traverse through the population and compare each
		//phenotype to the current "best" phenotype.
		for(int i = 0; i < super.size(); i++)
		{
			Phenotype currentPhenotype = (Phenotype)super.get(i);
			double currentFitness = currentPhenotype.getFitness();
			
			//if the current phenotype is more fit than the
			//best one we have so far we need to make the 
			//current phenotype the best.
			if(currentFitness > highestFitness)
			{
				best = currentPhenotype;
				highestFitness = best.getFitness();
			}
		}
		
		return best;
	}
	
	/**
	 * Retrieves and returns the size of the population
	 * @return - the size of the population
	 */
	public int getSize()
	{
		return super.size();
	}
}

