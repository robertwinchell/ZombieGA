package evolution;
import critter.*;
import fitness.*;
import genes.*;

/**
 * Evolution Strategy abstract class
 * contains the evolve population method which all evolution
 * strategies must employ differently
 * 
 * @author Allan Dancer
 * 
 */

public abstract class EvolutionStrategy 
{
	/**
	 *Evolve Population method calls constructors needed depending on the 
	 * genetic operators required for the type of evolution
	 */
	public abstract Population evolvePopulation(Population pop,
	SelectionStrategy strategy, GeneManipulator manipulator);
}