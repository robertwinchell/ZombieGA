/*
 * Created on Jan 29, 2008
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package chromosome;

/**
 * 
 * Chromosome interface with the methods which must be implented in
 * another class
 * @author winch036
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public interface Chromosome 
{
	/**
	 * returns a new instance of the same
	 * chromosome.
	 */
	public Object clone();
	
	/**
	 *retrieves and returns the string representation of
	 *the chromosome.
	 */
	public String toString();
	
	/**
	 * returns true if the two chromosomes are the same
	 * @param o - the object to be compared to
	 * @return - a boolean of true if the chromosomes are
	 * the same
	 */
	public boolean equals(Object o);
	
	/**
	 * Returns a partition of the chromosome in a new
	 * chromosome that is a gene.
	 * @param start - the starting index
	 * @param end - the ending index
	 * @return - the chromosome representation of the gene
	 */
	public Chromosome getGene(int start, int end);
}

