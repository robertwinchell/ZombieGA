
package numbers;

/**
 * ALifeRandom class, used to create the individual bits
 * in each chromosome, meaning the 0s and 1s
 * @author Robert Winchell
 */ 
import java.util.Random;

public class ALifeRandom
{
	//private Random instance
	private static Random aLifeRandom = new Random();
	
	//private constructor, appropriate for Singleton pattern
	private ALifeRandom()
	{
		super();
	}
	
	/*
	 * get Random Number method with returns static instance
	 * of ALifeRandom
	 */
	public static Random getRandomNumberGenerator()
	{
		
			return aLifeRandom;

	}
	
}
