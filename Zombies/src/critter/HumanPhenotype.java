package critter;

import chromosome.*;


/**
 * Class: HumanPhenotype
 * The purpose of this class is to hold and represent
 * a human chromosome.  A human phenotype must have its
 * chromosome start with a 1 or true value.  This first
 * value is the part of the chromosome that is mapped to 
 * store the race of the phenotye.  The rest of the zombie
 * chromosome will be mapped for other purposes determined
 * by the use of the genetic algorithm.
 * 
 * @author Allan Dancer
 * 
 */
public class HumanPhenotype extends Phenotype
{
	
	/**
	 * Instantiates the human phenotype.  The human phenotype
	 * will always have its first digit of its chromosome be a
	 * one.  The starting fitness value of this phenotype will
	 * be dafaulted to one.
	 * 
	 * @param myChromosome
	 * 		-The chromosome that will be instantiated into
	 * the human phenotype.  The first digit of this phenotype
	 * will always be a zone at instantiation.
	 */
	public HumanPhenotype(Chromosome myChromosome, int xloc, int yloc, double strength)
	{
		//call to the super creating it with the passed in
		//chromosome.
		super(myChromosome, xloc, yloc, strength);
		
		
		
		//access and make sure the gene that is dedicated to tell
		//the race of the phenotype is set correctly to one for this
		//human phenotype.
		BitArrayChromosome chrom = (BitArrayChromosome)super.getChromosome();
		chrom.setBit(0,0,1);
			
	}
	
	
	
}
