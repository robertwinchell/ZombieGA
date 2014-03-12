
package chromosome;


import numbers.ALifeRandom;
import java.util.BitSet;
import java.util.Random;

/**
 * 
 * This is a Chromosome class very similar to the BitChromosome. The difference 
 * between the two is that this one stores an array of bits. In the Zombie simulation
 * the structure would look like this:
 * [] (1)
 * [][][][][][][][][][][]...[][] (275)
 * [][][][]...[][] (45)
 * 
 * This class can either take a 2D array at the constructor and use it as the data
 * or as a template. If it is a template, it will fill it up with a random set of numbers.
 * 
 * Additionally, it can also take in an array of strings such as
 * ["1"]
 * ["1010011011011...110"]
 * ["1001011...1101"]
 * and this will also construct the chromosome.
 * 
 * @author Zach Winchell, Allan Dancer
 *
 */
public class BitArrayChromosome implements Chromosome 
{
	private int[][] theChromosome;
	
	/**
	 * This method will take in a 2D array of data and a boolean. If
	 * the boolean is true, the array passed in is used as a template and 
	 * just gets filled up with new random data. If it is set to false, it 
	 * will construct the BitArrayChromosome using the given data.
	 * 
	 * @param data this is either a full or an empty array of data.
	 * 
	 * @param isTemplate this will be true if the user wants to use the data
	 * array as a template.
	 */
	public BitArrayChromosome(int[][] data, boolean isTemplate)
	{
		if (isTemplate)
		{
			//retrieves the static instance of random for use in this class.
			Random randNumGen = ALifeRandom.getRandomNumberGenerator();
			this.theChromosome = new int[data.length][];

			//System.out.println("Creating an array.");
			//System.out.println("Begin randomizing.");
			//instantiates theChromosome
			//all bits in the BitSet are defaulted to 0.
			for (int i=0; i<data.length; i++)
			{
				this.theChromosome[i] = new int[data[i].length];
				//System.out.print(i+ ":   |");
				for (int j=0; j<data[i].length; j++)
				{
					this.theChromosome[i][j] = randNumGen.nextInt(2);
					//System.out.print(theChromosome[i][j] + "|");
				}
				//System.out.println("");
			}
		}
		else
		{
			this.theChromosome = data;
		}
	}	
	
	/**
	 * Takes a string[] of int values and initializes the values
	 * of this string into the int array theChromosome.
	 * 
	 * @param bits is what will be instantiated into theChromosome.
	 */
	public BitArrayChromosome(String[] bits)
	{
		super();
		
		this.theChromosome = new int[bits.length][];
		
		for(int i=0; i<this.theChromosome.length; i++)
		{
			this.theChromosome[i] = new int[bits[i].length()];
			for (int j=0; j<bits[i].length(); j++)
			{
				theChromosome[i][j] = 0;
				if(bits[i].charAt(j) == '1')
				{
					//changes the bit at this location to a 1.
					theChromosome[i][j] = 1;
				}					
			}
		}
	}
		
	/**
	 * Returns an exact replica of the BitChromosome.
	 */
	public Object clone()
	{		
		return new BitArrayChromosome(this.toStringArray());	
	}	
	
	/**
	 * Returns the String[] representation of the data inside
	 * the chromosome.  This string returns all ones and zeros
	 * in the chromosome including any leading zeros. 
	 * 
	 * It will return something like this:
	 * ["1"]
	 * ["1010011011011...110"]
	 * ["1001011...1101"]
	 */
	public String[] toStringArray()
	{
			String[] theString = new String[this.theChromosome.length];
			//traverses the length of theChromosome
			
			for(int i=0; i<theChromosome.length; i++)
			{
				theString[i]="";
				for (int j=0; j<theChromosome[i].length; j++)
				{
					theString[i]+=""+ theChromosome[i][j];
				}
			}
			
			return theString;
	}
	
	/**
	 * Returns the String representation of the data inside
	 * the chromosome.  This string returns all ones and zeros
	 * in the chromosome including any leading zeros. It seperates 
	 * the arrays with a "|".
	 */
	public String toString()
	{
			String theString = "";
			
			//traverses the length of theChromosome
			for(int i=0; i<theChromosome.length; i++)
			{
				for (int j=0; j<theChromosome[i].length; j++)
				{
					theString+="" + theChromosome[i][j];
				}
				theString+="|";
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
	 * This returns a BitChromosome for the 2nd row from the start
	 * to the end.
	 * 
	 * @param start is the inclusive start of the gene.
	 * 
	 * @param end is the end of the gene exclusive.
	 */
	public Chromosome getGene(int start, int end)		
	{
		return this.getGene(1,start,end);
	}
	
	/**
	 * Returns a part of the chromosome that begins at 
	 * "start" (inclusive) and and ends at "end" (exclusive)
	 * at the given row.
	 * 
	 * @param row is the row to get the gene from.
	 * 
	 * @param start is the start (inclusive) of the gene.
	 * 
	 * @param end is the end (exlcusive) of the gene.
	 */
	public Chromosome getGene(int row, int start, int end)		
	{
		//calculates the length of the gene
		int length = end - start;
		//System.out.println(length);
		//creates a new bitset that is the gene requested.	
		BitSet theBitSet = new BitSet(length); 
		for (int i = start; i<end; i++)
		{
			if (this.theChromosome[row][i] == 1)
			{
				theBitSet.set(i-start);
			}
		}
		
		//theBitSet.
		//turns the gene requested into a BitChromosome
		//and returns the gene as a BitChromosome which is a 
		//chromosome.
		Chromosome theChromosome = new BitChromosome(theBitSet,length);
		return theChromosome;
	}
	
	/**
	 * Gets and returns the length of the BitChromosome.
	 */
	public int[] length()
	{
		int[] lengths = new int[theChromosome.length];
		for (int i=0; i<theChromosome.length; i++)
		{
			lengths[i] = theChromosome[i].length;
		}
		return lengths;
	}
	
	/**
	 * Takes an index and an int value and sets that index
	 * to be that int value in the chromosome at the given row.
	 * 
	 * @param rowIndex is the row of gene to get.
	 * 
	 * @param colIndex is the index at which the method will set 
	 * the bit to the given boolean.
	 * 
	 * @param value is value set at the given index.
	 */
	public void setBit(int rowIndex, int colIndex, int value)
	{
		theChromosome[rowIndex][colIndex] = value;
	}
	
	/**
	 * Takes the given index and flips the bit at
	 * that index.
	 * 
	 * @param rowIndex - the index where the bit will be
	 * flipped in the chromosome
	 * 
	 * @param colIndex - the index where the bit will be
	 * flipped in the chromosome
	 */
	public void flipBit(int rowIndex, int colIndex)
	{
		if (theChromosome[rowIndex][colIndex] == 1)
		{
			theChromosome[rowIndex][colIndex] = 0;
		}
		else
		{
			theChromosome[rowIndex][colIndex] = 1;
		}
	}
}
