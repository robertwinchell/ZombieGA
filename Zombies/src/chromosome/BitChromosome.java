
package chromosome;


import numbers.ALifeRandom;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.BitSet;
import java.util.Random;

/*
 * BitChromosome class, very similar to BitSet class with more
 * methods that are modified, this holds the chromosome and
 * instantiates a new BitSet and new Chromosome length
 * 
 * @author winch036
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BitChromosome implements Chromosome 
{
	private BitSet theChromosome;
	private int chromosomeLength;
	
	/**
	 * Instantiates and assigns a random BitSet
	 * of the specified length to the instance variable
	 * theChromosome.
	 * 
	 * Instantiates and assigns the parameter given to 
	 * the instance variable chromosomeLength.
	 * @param length - the length of theChromosome
	 */
	public BitChromosome(int length)
	{
		//instantiates theChromosome
		//all bits in the BitSet are defaulted to 0.
		this.theChromosome = new BitSet(length);
		this.chromosomeLength = length;
		
		//retrieves the static instance of random for use in this class.
		Random randNumGen = ALifeRandom.getRandomNumberGenerator();
		
		for(int i = 0; i < length; i++)
		{
			int randomNum = randNumGen.nextInt(2);
			//if the random number we generated is a 1
			//a 1 will be set in the index of theChromosome
			//that the loop is in.
			if(randomNum == 1)
			{
				//sets the bit of the specified
				//index to 1.
				theChromosome.set(i);
			}
		}
	}
	
	/**
	 * Creates a BitChromosome with the given Bitset and length
	 * of that bitset.
	 * @param theChromosome The previously initialized BitSet
	 * that is theChromosome which is istantiated.
	 * @param chromosomeLength - the length of the chromosome
	 */
	public BitChromosome(BitSet theChromosome, int chromosomeLength)
	{
		this.theChromosome = theChromosome;
		this.chromosomeLength = chromosomeLength;
	}
	
	/**
	 * Takes a string of int values and initializes the values
	 * of this string into the BitSet of theChromosome.
	 * Takes the length of this string and initializes it to be the 
	 * length of the chromosome.
	 * @param bits - what will be instantiated into theChromosome.
	 */
	public BitChromosome(String bits)
	{
		super();
		
		this.chromosomeLength = bits.length();
		this.theChromosome = new BitSet(chromosomeLength);
		
		for(int i = 0; i < chromosomeLength; i++)
		{
			if(bits.charAt(i) == '1')
			{
				//changes the bit at this location to
				//a 1.
				theChromosome.set(i);
			}
		}
	}
		
	/**
	 * returns an exact replica of the BitChromosome.
	 */
	public Object clone()
	{		
		return new BitChromosome(this.toString());	
	}	
	
	/**
	 * returns the String representation of the data inside
	 * the chromosome.  This string returns all ones and zeros
	 * in the chromosome including any leading zeros.
	 */
	public String toString()
	{
			String theString = "";
			//traverses the length of theChromosome
			for(int i = 0; i < chromosomeLength; i++)
			{
				boolean theBoolean = theChromosome.get(i);
			
				//if the bitset returns true then we add a one
				//to our string
				if(theBoolean == true)
				{
					theString += 1;
				}
				//if we failed to add a one we must
				//add a zero
				else
				{
					theString += 0;
				}
			}
			return theString;
	}
	
	/**
	 * Determines if two BitChromosomes are the same.
	 * This will return true if the string representation
	 * of the two BitChromosomes and the two objects being the
	 * same class.
	 */
	public boolean equals(Object o)
	{
		boolean sameClass = false;
		boolean sameString = false;
		
		//checks to see if the two objects are of
		//the same class
		if(this.getClass().equals(o.getClass()))
		{
			sameClass = true;
		}
		
		//checks to see if the two objects have 
		//the same toString values
		if(this.toString().equals(o.toString()))
		{
			sameString = true;
		}
		
		if(sameClass && sameString)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Returns a part of the chromosome that begins at 
	 * "start" (inclusive) and and ends at "end" (exclusive).
	 */
	public Chromosome getGene(int start, int end)		
	{
		//calculates the length of the gene
		int length = end - start;
		//creates a new bitset that is the gene requested.	
		BitSet theBitSet = theChromosome.get(start, end);
		//turns the gene requested into a BitChromosome
		//and returns the gene as a BitChromosome which is a 
		//chromosome.
		Chromosome theChromosome = new BitChromosome(theBitSet, length);
		return theChromosome;
	}
	
	/**
	 * Gets and returns the length of the BitChromosome.
	 */
	public int length()
	{
		return chromosomeLength;
	}
	
	/**
	 * Takes an index and a boolean value and sets that index
	 * to be that boolean value in the chromosome.
	 * @param index - the index at which the method will set 
	 * the bit to the given boolean.
	 * @param bool - boolean value set at the given index.
	 */
	public void setBit(int index, boolean bool)
	{
		theChromosome.set(index, bool);
	}
	
	/**
	 * Takes the given index and flips the bit at
	 * that index.
	 * @param index - the index where the bit will be
	 * flipped in the chromosome
	 */
	public void flipBit(int index)
	{
		theChromosome.flip(index);
	}
	
	/**
	 * Calculates and returns the BigInteger representation
	 * of the chromosome.
	 * @return - the BigInteger representation
	 * of this chromosome.
	 */
	public BigInteger toBigInteger()
	{
		return new BigInteger(this.toString(), 2);
	}
	
	/**
	 * calculates and returns the BigDecimal representation
	 * of the chromosome.
	 * @return - the BigDecimal representation of the chromosome
	 */
	public BigDecimal toBigDecimal()
	{
		return new BigDecimal(this.toBigInteger());
	}
}
