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
public class HumanCrossover implements GeneticOperator
{
	//the probability that a crossover will happen.
	private double crossoverProbability;
	
	/**
	 * Initializes the Crossover with the passed in crossover
	 * Probability.
	 * @param crossoverProbability - the chance to perform a 
	 * crossover.
	 */
	public HumanCrossover(double crossoverProbability)
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
			"chromosomes in the passed in chromosomes array.");
		}
		
		Random randNumGen = ALifeRandom.getRandomNumberGenerator();
		double doICrossover = randNumGen.nextDouble();
		//if we meet the probability we will perform a crossover and return 
		//an array of size one containing the new child.
		if(doICrossover <= crossoverProbability)
		{
			return crossover(chromosomes);
		}
		else
		{
			return null;
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
		BitArrayChromosome dad = (BitArrayChromosome)parents[0];
		//takes the second parent in the array.
		BitArrayChromosome mom = (BitArrayChromosome)parents[1];
		Chromosome dadsGenes = null;
		Chromosome momsGenes = null;
		int pointAtCrossover = 0;
		String[] dna = new String[3];
		dna[0]="1";
		
		Random randNumGen = ALifeRandom.getRandomNumberGenerator();

		//BEGIN: 2nd row (weights array)
		//randomly selects a number between 0 and the length of the 
		//dad chromosome
		pointAtCrossover = 1+randNumGen.nextInt((dad.length()[1])-1);
		//takes the section of dads genes determined by the random 
		//number selection and stores them in a new Chromosome 
		//called dadsGenes
		dadsGenes = dad.getGene(1,0, pointAtCrossover);
		//takes the section of moms genes determined by the random 
		//number selection and stores them in a new Chromosome 
		//called momsGenes.
		momsGenes = mom.getGene(1,pointAtCrossover, mom.length()[1]);
		//constructs the toString value of mom and dads genes 
		//added together
		dna[1] = dadsGenes.toString() + momsGenes.toString();
		//System.out.println("For Weights: " + pointAtCrossover);
		//System.out.println(dadsGenes.toString() + " | " + momsGenes.toString());
		
		//BEGIN: 3nd row (threshold(Theta) array)
		//randomly selects a number between 0 and the length of the 
		//dad chromosome
		pointAtCrossover = 1+randNumGen.nextInt((dad.length()[2])-1);
		//takes the section of dads genes determined by the random 
		//number selection and stores them in a new Chromosome 
		//called dadsGenes
		dadsGenes = dad.getGene(2,0, pointAtCrossover);
		//takes the section of moms genes determined by the random 
		//number selection and stores them in a new Chromosome 
		//called momsGenes.
		momsGenes = mom.getGene(2,pointAtCrossover, mom.length()[2]);
		//constructs the toString value of mom and dads genes 
		//added together
		dna[2] = dadsGenes.toString() + momsGenes.toString();
		//System.out.println(dadsGenes.toString() + " | " + momsGenes.toString());
		//System.out.println("For Thresholds: " + pointAtCrossover);
		
		
		
		//creates a new BitChromosome using the string constructor
		//of BitChromosome to form the child.
		BitArrayChromosome child = new BitArrayChromosome(dna);
		//puts the child in an array and returns the array.
		Chromosome[] returnedChild = new Chromosome[1];
		returnedChild[0] = child;
		
		return returnedChild;
	}	
}
			
			