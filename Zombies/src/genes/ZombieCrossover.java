package genes;
import chromosome.BitChromosome;
import chromosome.Chromosome;
import chromosome.BitArrayChromosome;
import chromosome.Chromosome;

/**
 * Class: ZombieCrossover
 * The purpose of this class is to allow a zombie to
 * transform a human into a fellow zombie.  When a human
 * crosses over with a zombie the two will produce a zombie.
 * The produced zombie will have the same intelligence as the
 * human parent did.  The produced zombie will have the same 
 * chromosome as the human parent did except for the fact that
 * the par of the chromosome mapped to determine weather a 
 * phenotype is a zombie or human will be changed to a zombie.
 * @author Robert Winchell
 */
public class ZombieCrossover implements GeneticOperator
{

	private double morphProbability;
	
	
	public ZombieCrossover(double morphProbability)
	{
		this.morphProbability = morphProbability;
	}
	/**
	 * This method performs a crossover between a zombie phenotype
	 * and a human phenotype.  The returned chromosome will be 
	 * the same as the human parent except for the fact that the
	 * child's first gene will determine that it is now a zombie.
	 * @param chromosomes
	 * 		The zombie and human parents that will produce the 
	 * returned zombie chromosome.
	 * @return
	 * 	The crossed over zombie chromosome.
	 */
	public Chromosome[] performOperation(Chromosome[] chromosomes)
			throws IllegalArgumentException 
	{
	
		//throws an exception if the passed in array is not of size 1
		if(chromosomes.length != 1)
		{
			throw new IllegalArgumentException("There must be exactly one " +
			"chromosomes in the passed in chromosomes array.");
		}
		
		//array that will hold the result of the crossover.
		Chromosome[] spawn = new Chromosome[1];
		//System.out.println("HERE");
		//int[][] template = new int[3][3];
		//chromosomes[0]=new BitArrayChromosome(template, true);
		System.out.println(chromosomes[0]);
		BitArrayChromosome theChrom = (BitArrayChromosome)chromosomes[0];
		BitArrayChromosome child = (BitArrayChromosome)theChrom.clone();
		//System.out.println("HERE2");	
		child.setBit(0,0,0);
		//System.out.println("HERE3");	
		spawn[0] = child;		
		return spawn;
	}

}
