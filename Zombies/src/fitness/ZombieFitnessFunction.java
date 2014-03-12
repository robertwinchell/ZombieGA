package fitness;

import java.math.BigDecimal;
import java.math.MathContext;

import chromosome.*;
import critter.Phenotype;
/**
 * Fitness Function for the Zombies/Humans
 * 
 * @author Zach Winchell
 *
 */
public class ZombieFitnessFunction
{
	//stores the upper bound for our fitness evaluation
	private double lowerBound;
	//stores the lower bound for our fitness evaluation
	private double upperBound;
	/**
	 * 
	 * @param lowerBound
	 * @param upperBound
	 */
	public ZombieFitnessFunction(double lowerBound, double upperBound)
	{
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	/**
	 * @param p
	 * 		The phenotype which will be evaluated in 
	 * terms of getting close to pi.
	 * @return
	 * 		The fitness value of the passed in phenotype
	 * will not be larger than 1.0
	 */
	public double evaluateFitness(Phenotype p) 
	{
		double strength = p.getStrength();
		
		strength = strength * .01;
		
		return strength;
			
		
	}
	
}
	


