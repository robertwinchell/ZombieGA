/*
 * Created on Feb 5, 2008
 */
 
package critter;

import java.util.*;
import java.util.Observable;

import chromosome.*;
/**
 * The phenotype representation of a chromosome.  This critter will
 * be judged by its fitness its phenotype (represented in its chromosome)
 * and will have the goal of being fit to breed and survive for as many
 * generations as possible.
 * @author Allan Dancer and Zach Winchell
 */
public class Phenotype extends Observable
{
	private double strength;
	
	//holds the x coordinate for graphic purposes
	private int xloc;
	
	//holds the y coordinate for graphic purposes
	private int yloc;
	
	//stores the phenotype's chromosome which holds the code
	//for its phenotype.
	private Chromosome myChromosome;
	//stores the phenotype's fitness which will give it a probability
	//of surviving and breeding in its current generation.
	private double fitness;
	
	//stores the length of the weight genes in the
	//nural net
	private int weightGeneLenght;
	
	//stores the length of the theta genes in the
	//nural net.
	private int thetaGeneLength;
	
	/**
	 * Creates a Phenotype.  Instantiates the phenotype's chromosome
	 * to the chromosome passed in and initializes the phenotype's 
	 * fitness to 0 (the lowest value on the ideal scale of 0 and 1).
	 * @param myChromosome - the value of this phenotype's chromosome.
	 */
	public Phenotype(Chromosome myChromosome, int xloc, int yloc, double strength)
	{
		Random r = new Random();
		this.myChromosome = myChromosome;
		fitness = 0;
		if(xloc > 20)
		{
			xloc = r.nextInt(20);
		}
		if(yloc>20)
		{
			yloc = r.nextInt(20);
		}
		this.xloc = xloc;
		this.yloc = yloc;
		this.strength=strength;
	}
	
	/**
	 * Sets the phenotype's fitness to the passed in parameter value.
	 * @param fitness - The new fitness value of the critter.
	 */
	public void setFitness(double fitness)
	{
		this.fitness = fitness;
	}
	
	/**
	 * Accesses and returns the double representation of the
	 * phenotype's fitness.
	 * @return - the double value of this critter's fitness
	 */
	public double getFitness()
	{
		return fitness;
	}
	
	/**
	 * Sets this phenotype's chromosome value to the passed in 
	 * chromosome value.
	 * @param myChromosome - the new value for this phenotype's
	 * chromosome.
	 */
	public void setChromosome(Chromosome myChromosome)
	{
		this.myChromosome = myChromosome;
	}
	
	/**
	 * Accesses and returns the chromosome stored in this
	 * phenotype.
	 * @return - the chromosome value contained in 
	 * this phenotype.
	 */
	public Chromosome getChromosome()
	{
		return myChromosome;
	}
	
	/**
	 * Retrieves and returns the x coordinate for the graphic
	 * location of this Phenotype.
	 * @return
	 * 		The x coordinate for the graphic location
	 * of this Phenotype.
	 */
	public int getXloc()
	{
		return xloc;
	}
	
	/**
	 * Retrieves and returns the y coordinate for the graphic
	 * location of this Zombie Phenotype.
	 * @return
	 * 		The y coordinate for the graphic location
	 * of this Zombie Phenotype.
	 */
	public int getYloc()
	{
		return yloc;
	}
	
	/**
	 * Sets the x coordinate value of this phenotype
	 * to that of the passed in parameter.
	 * @param xloc
	 * 		The new x coordinate for this phenotype.
	 */
	public void setXloc(int xloc)
	{
		this.xloc = xloc;
	}
	
	/**
	 * Sets the y coordinate value of this phenotype
	 * to that of the passed in parameter.
	 * @param yloc
	 * 		The new y coordinate for this phenotype.
	 */
	public void setYloc(int yloc)
	{
		this.yloc = yloc;
	}
	
	/**
	 * Retrieves and returns the length of this phenotypes
	 * weight genes.
	 * @return
	 * 		The length of this phenotypes weight genes
	 * for the nural net.
	 */
	public int getWeightGeneLength()
	{
		return this.weightGeneLenght;
	}
	
	/**
	 * Sets this phenotype's weight gene length for the 
	 * nural net to that of the passed in parameter.
	 * @param weightGeneLength
	 * 		The new value for the gene length of this
	 * phenotypes weights for the nural net.
	 */
	public void setWeightGeneLength(int weightGeneLength)
	{
		this.weightGeneLenght = weightGeneLength;
	}
	
	/**
	 * Retrieves and returns this phenotype's theta gene length
	 * for the nural net.
	 * @return
	 * 		This phenotype's theta gene length for its
	 * nural net.
	 */
	public int getThetaGeneLength()
	{
		return this.thetaGeneLength;
	}
	
	/**
	 * Sets this phenotypes theta gene length to that of the
	 * passed in parameter.
	 * @param thetaGeneLength
	 * 		The new value for this phenotype's theta gene
	 * length for the nural net.
	 */
	public double getStrength()
	{
		return this.strength;
	}
	public void setThetaGeneLength(int thetaGeneLength)
	{
		this.thetaGeneLength = thetaGeneLength;
	}
	public void setStrength(double strength)
	{
		this.strength=strength;
	}
}
