package fitness;
import java.math.BigDecimal;

/**
 * This class calculates the fitness of a Phenotype
 * going towards Pi.
 * 
 * @author Allan Dancer, Robert Winchell
 *
 */
import chromosome.*;
import critter.*;

import java.lang.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import critter.*;
import chromosome.*;
/**
 * Pi Fitness Function Class
 * will evaluate a chromosome and assign it an integer value
 * for how close they got to Pi
 * @author Zach Winchell
 */
import java.math.BigDecimal;

import java.lang.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import critter.*;
import chromosome.*;
/**
 * Pi Fitness Function Class
 * will evaluate a chromosome and assign it an integer value
 * for how close they got to Pi
 * @author Zach Winchell
 */
public class PiFitnessFunction implements FitnessFunction
{
	//stores the upper bound for our fitness evaluation
	private double lowerBound;
	//stores the lower bound for our fitness evaluation
	private double upperBound;
	
	public PiFitnessFunction(double lowerBound, double upperBound)
	{
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}
	
	/**
	 * Takes a phenotype whos goal is finding pi and
	 * evaluates how close it is to pi.  This function
	 * will return a double between 0.0 and 1.0 with 1.0 
	 * being what each phenotype strives to become.  Uses
	 * the formula a + (chromAsBase10*(b-a)) / 2^chromosome 
	 * length - 1.
	 * @param p
	 * 		The phenotype which will be evaluated in 
	 * terms of getting close to pi.
	 * @return
	 * 		The fitness value of the passed in phenotype
	 * will not be larger than 1.0
	 */
	public double evaluateFitness(Phenotype p) 
	{
		BitChromosome chromosome = (BitChromosome)p.getChromosome();
		
		//executes the given formula in the method comment
		BigDecimal chromAsBase10 = chromosome.toBigDecimal();
		BigDecimal range = new BigDecimal(upperBound - lowerBound);
		BigDecimal numerator = chromAsBase10.multiply(range);
		BigDecimal one = new BigDecimal(1);
		BigDecimal two = new BigDecimal(2); 
		BigDecimal denominator = two.pow(chromosome.length());
		denominator = denominator.subtract(one);
		BigDecimal fraction = numerator.divide(denominator, new MathContext(6));
		BigDecimal answer = fraction.add(new BigDecimal(lowerBound));
		
		double finalAnswer = answer.doubleValue();
		
		//gets the final fitness value to cap out at one.
		finalAnswer = 1.0 - Math.abs(Math.tan(finalAnswer));
		
		return finalAnswer;
	}
	
}


