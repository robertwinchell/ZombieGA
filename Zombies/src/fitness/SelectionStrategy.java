package fitness;

import chromosome.*;
import critter.*;
/**
 * Selection Strategy class
 * children will ahve to implement to selectChromosome method
 * @author Zach Winchell
 */
public abstract class SelectionStrategy 
{
	/**
	 * Implements a strategy for selecting chromosomes out
	 * of a population based on the criteria defined by
	 * child classes.
	 * @param pop
	 * 		The population for which this selection strategy
	 * is used upon
	 * @return
	 * 		The chromosomes that have been selected via the 
	 * strategy implemented by the child classes.
	 */
	public abstract Chromosome[] selectChromosomes(Population pop);
}
