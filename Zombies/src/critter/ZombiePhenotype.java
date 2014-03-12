
package critter;
import chromosome.*;
/**
 * Class: ZombiePhenotype
 * The purpose of this class is to hold and represent
 * a zombie chromosome.  A zombie phenotype must have its
 * chromosome start with a 0 or false value.  This first
 * value is the part of the chromosome that is mapped to 
 * store the race of the phenotye.  The rest of the zombie
 * chromosome will be mapped for other purposes determined
 * by the use of the genetic algorithm.
 * 
 * @author Allan Dancer
 */
/**
 * Class: ZombiePhenotype
 * The purpose of this class is to hold and represent
 * a zombie chromosome.  A zombie phenotype must have its
 * chromosome start with a 0 or false value.  This first
 * value is the part of the chromosome that is mapped to 
 * store the race of the phenotye.  The rest of the zombie
 * chromosome will be mapped for other purposes determined
 * by the use of the genetic algorithm.
 * 
 * @author Allan Dancer
 */
public class ZombiePhenotype extends Phenotype
{
	
	/**
	 * Instantiates the Zombie phenotype.  The zombie phenotype
	 * will always have its first digit of its chromosome be a
	 * zero.  The starting fitness value of this phenotype will
	 * be dafaulted to zero.
	 * 
	 * @param myChromosome
	 * 		-The chromosome that will be instantiated into
	 * the zombie phenotype.  The first digit of this phenotype
	 * will always be a zero at instantiation.
	 */
	public ZombiePhenotype(Chromosome myChromosome, int xloc, int yloc, double strength)
	{
		//call to the super creating it with the passed in
		//chromosome.
		super(myChromosome, xloc, yloc, strength);
		
		//access and make sure the gene that is dedicated to tell
		//the race of the phenotype is set correctly to zero for this
		//zombie phenotype.
		BitArrayChromosome chrom = (BitArrayChromosome)super.getChromosome();
		chrom.setBit(0,0,0);
	}
	
	
}
