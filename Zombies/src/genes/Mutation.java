package genes;

import java.util.*;
import numbers.*;
import chromosome.*;
/**
 * Mutation class
 * checks the mutation probability and determines the likelyhood 
 * of mutation at each bit of a chromosome then uses that probability
 * to determine if the bit gets flipped, if it gets flipped mutation has occured
 * TO NOTE: lower mutation probability is much mor practical for evolution purposes
 * 
 * @author Robert Winchell, Allan Dancer
 */
public class Mutation implements GeneticOperator
{
	//holds the probability that each bit will mutate.
	private double mutationProbability;
	
	/**
	 * Creates a Mutation and initializes the mutation probability
	 * to be that of the passed in mutation probability.
	 * @param mutationProbability - the probability that each bit
	 * in a chromosome will mutate.
	 */
	public Mutation(double mutationProbability)
	{
		this.mutationProbability = mutationProbability;
	}

	/**
	 * Takes a size one array of chromosomes and mutates performs the
	 * mutation process on this single chromosome.  There is an established
	 * mutation probability in the class by which this chromosome will 
	 * fallow.  Each bit the chromosomes dna will be examined and will have
	 * a chance to mutate.  The returned chromosome may return unchanged or
	 * may be returned with many changes.
	 * @param chromosomes - An array of one chromosome that will
	 * go through the mutation process.
	 * @return - returns a Chromosome array of size one with a 
	 * chromosome that has gone through the mutation process.  This
	 * chromosome may have zero mutations to many mutations depending
	 * on randomness.
	 * @throws - throws an IllegalArgumentException if chromosomes
	 * is not of length 1.
	 */
	public Chromosome[] performOperation(Chromosome[] chromosomes)
			throws IllegalArgumentException 
	{
		//checks if chromosomes is of length one and aborts 
		//while throwing an exception if the chromosomes
		//array is not of size 1.
		if(chromosomes.length != 1)
		{
			throw new IllegalArgumentException("The passed in " +
			"chromosomes array must be of size 1");
		}
		
		BitArrayChromosome theChromosome = (BitArrayChromosome)chromosomes[0];
		Random randNumGen = ALifeRandom.getRandomNumberGenerator();
		
		//generates probabilites for mutation for as many 
		//bits as are in the chromosome.
		for(int i = 0; i < theChromosome.length()[1]; i++)
		{
			//randomly generated double between 0.0 and 1.0
			double doIMutate = randNumGen.nextDouble();
			//if the randomly generated double is less than or
			//equal to the mutation probability we flip the bit
			//of the current index.
			if(doIMutate <= mutationProbability)
			{
				theChromosome.flipBit(1,i);
			}
		}
		
		for(int i = 0; i < theChromosome.length()[2]; i++)
		{
			//randomly generated double between 0.0 and 1.0
			double doIMutate = randNumGen.nextDouble();
			//if the randomly generated double is less than or
			//equal to the mutation probability we flip the bit
			//of the current index.
			if(doIMutate <= mutationProbability)
			{
				theChromosome.flipBit(2,i);
			}
		}
		for(int i = 0; i < theChromosome.length()[0]; i++)
		{
			//randomly generated double between 0.0 and 1.0
			double doIMutate = randNumGen.nextDouble();
			//if the randomly generated double is less than or
			//equal to the mutation probability we flip the bit
			//of the current index.
			if(doIMutate <= mutationProbability)
			{
				theChromosome.flipBit(0,i);
			}
		}
		
		Chromosome[] returner = new Chromosome[1];
		returner[0] = theChromosome;
		
		return returner;
	}
	
	
}
