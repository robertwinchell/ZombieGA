package genes;
import chromosome.*;
import java.util.*;
import java.lang.*;
import numbers.*;
/**
 * Crossover class, takes genes of one parent and combines them randomly
 * with another, basic 2 parent mating
 * 
 * @author Robert Winchell
 */
public class Crossover implements GeneticOperator
{
	//the probability that a crossover will happen.
	private double crossoverProbability;
	
	/**
	 * Initializes the Crossover with the passed in crossover
	 * Probability.
	 * @param crossoverProbability - the chance to perform a 
	 * crossover.
	 */
	public Crossover(double crossoverProbability)
	{
		this.crossoverProbability = crossoverProbability;
	}

	/**
	 * Takes the parents passed in the chromosomes array and determines if they
	 * will breed and make a child via a random number which will either meet or
	 * exceed the probability of breeding.  
	 * If the parents meet the probability of breeding
	 * a child with a random portion of each parents genes will be returned in a 
	 * Chromosome[]of size one.  
	 * If the parents fail to meet their breeding probability a random parent will
	 * be returned.
	 * @param chromosomes - the parents that will either form a child
	 * with selections of each of their genes or have one of them be returned
	 * if they do not meet the crossover probability.
	 * @return - A child with a combination of each of the parents genes.  Or 
	 * willrandomly return one of the parents passed in if they do not meet 
	 * their crossover probability.
	 * @throws IllegalArgumentException - Throws an exception if the passed in 
	 * chromosomes array is not of size two i.e. more or less than two parents 
	 * are passed in.
	 */
	public Chromosome[] performOperation(Chromosome[] chromosomes)
			throws IllegalArgumentException 
	{
		if(chromosomes.length != 2)
		{
			throw new IllegalArgumentException("There must be exactly two " +
			"chromosomes in +the passed in chromosomes array.");
		}
		
		Random randNumGen = ALifeRandom.getRandomNumberGenerator();
		double doICrossover = randNumGen.nextDouble();
		//if we meet the probability we will perform a crossover and return 
		//an array of size one containing the new child.
		if(doICrossover <= crossoverProbability)
		{
			return crossover(chromosomes);
		}
		//if we do not meet the crossover probability we will randomly 
		//return one of the parents.
		else
		{
			//random number of either 0 or 1 to determine which parent will
			//be returned.
			int whichParent = randNumGen.nextInt(2);
			//array to be returned with one parent inside it.
			Chromosome[] returningChromosomes = new Chromosome[1];
			//assigns the randomly selected parent to this new array
			//and returns the array.
			returningChromosomes[0] = chromosomes[whichParent];
			return returningChromosomes;
		}
	}
	
	/**
	 * Helper method for the performOperation method.
	 * Takes two parents and creates a child from random portions of the 
	 * parents genes.  In the crossover the decision of which parent gives 
	 * the first set of genes as well as which parent gives more genes 
	 * (or each parent gives equal genes) is decided randomly each time the
	 * method is called.  This method will return the child in a size 1 
	 * array.
	 * @param parents - the two chromosomes that will crossover to form
	 * the child
	 * @return - An array of size one that contains the child of the two 
	 * parents that performed the crossover
	 */
	private Chromosome[] crossover(Chromosome[] parents)
	{
		//takes the first parent in the array.
		BitChromosome dad = (BitChromosome)parents[0];
		//takes the second parent in the array.
		BitChromosome mom = (BitChromosome)parents[1];
		
		Random randNumGen = ALifeRandom.getRandomNumberGenerator();
		//creates a random number of either 0 or 1 (in this case) to 
		//decide which parent will supply the first set of genes.
		int whichSide = randNumGen.nextInt(parents.length);
		 
		//if we generate a 0 then the dad will supply the first set of genes.
		if(whichSide == 0)
		{
			//randomly selects a number between 0 and the length of the 
			//dad chromosome
			int pointAtCrossover = randNumGen.nextInt(dad.length());
			//takes the section of dads genes determined by the random 
			//number selection and stores them in a new Chromosome 
			//called dadsGenes
			Chromosome dadsGenes = dad.getGene(0, pointAtCrossover);
			//takes the section of moms genes determined by the random 
			//number selection and stores them in a new Chromosome 
			//called momsGenes.
			Chromosome momsGenes = mom.getGene(pointAtCrossover, mom.length());
			//constructs the toString value of mom and dads genes 
			//added together
			String dna = dadsGenes.toString() + momsGenes.toString();
			//creates a new BitChromosome using the string constructor
			//of BitChromosome to form the child.
			BitChromosome child = new BitChromosome(dna);
			//puts the child in an array and returns the array.
			Chromosome[] returnedChild = new Chromosome[1];
			returnedChild[0] = child;
			
			return returnedChild;
		}
		//if we generate a 1 than the mom will supply the first set 
		//of genes.
		else
		{
			//randomly selects a number between 0 and the length of 
			//the dad chromosome
			int pointAtCrossover = randNumGen.nextInt(mom.length());
			//takes the section of moms genes determined by the random
			//number selection and stores them in a new Chromosome 
			//called momsGenes.
			Chromosome momsGenes = mom.getGene(0, pointAtCrossover);
			//takes the section of dads genes determined by the random 
			//number selection and stores them in a new Chromosome 
			//called dadsGenes
			Chromosome dadsGenes = dad.getGene(pointAtCrossover, dad.length());
			//constructs the toString value of mom and dads genes 
			//added together
			String dna = momsGenes.toString() + dadsGenes.toString();
			//creates a new BitChromosome using the string constructor
			//of BitChromosome to form the child.
			BitChromosome child = new BitChromosome(dna);
			//puts the child in an array and returns the array.
			Chromosome[] returnedChild = new Chromosome[1];
			returnedChild[0] = child;
			
			return returnedChild;
			
		}
	}
	
	
}
			
			