package evolution;

import java.util.ArrayList;

import chromosome.*;
import critter.*;


import evolution.ALifeFactory;
import evolution.EvolutionStrategy;
import evolution.ExplicitEvolutionStrategy;
import fitness.FitnessEvaluator;
import fitness.FitnessFunction;
import fitness.SelectionStrategy;
import fitness.StandardSelectionStrategy;
import genes.GeneManipulator;
import genes.GeneticOperator;
import genes.HumanCrossover;
import genes.Mutation;

import critter.Phenotype;
import critter.Population;

import java.util.ArrayList;
import java.util.Random;

import chromosome.BitArrayChromosome;
import chromosome.Chromosome;


import numbers.ALifeRandom;



/**
 * Our zombie factory for completely new instantiation of our previous classes.
 * @author Allan Dancer
 * 
 */
public class ZombieFactory extends ALifeFactory
{
	//instance variable for chance of our operations
	private double probabilityOfOperations = 1.0;

	//instance variable for our array of genetic operators which will
	// obviously occur
	private GeneticOperator[] operators;

	//instance variable for mutation probability
	private double mutationProbability = 0.0;

	//instance variable for our max generation
	private int maximumGeneration = 30;

	//instance variable for our population size
	private int populationSize = 2;

	//instance variable for makes a new population
	private Population population = new Population();
	
	//2d array for setting
	int[][] temp = new int[3][];

	//random variable for checking
	Random r = new Random();
	
	/**
	 * Our constructor for the zombie factory. Sets up a population
	 * of zombie phenotypes.
	 */
	public ZombieFactory()
	{
		super();
		temp[0] = new int[1];
		temp[1]= new int[275];
		temp[2] = new int[45];
		
		for (int i = 0; i < populationSize; i++)
		{
			
			// making a BitChromosome with a given length
			Chromosome chromosome = new BitArrayChromosome(temp, true);

			// casting the BitChromosome to a Phenotype
			Phenotype phenotype = new Phenotype(chromosome,r.nextInt(20),r.nextInt(20), 10 );
			
			// adding the Phenotype to the population
			population.add(phenotype);
		}
	}

	/**
	 * returns a new chromosome of our chromsome length
	 */
	public Chromosome getChromosome()
	{
		
		temp[0] = new int[1];
		temp[1]= new int[275];
		temp[2] = new int[45];
		
		
		Chromosome chromosome =  new BitArrayChromosome(temp, true);
		return chromosome;
	}

	/**
	 * Returns a new ExplicitEvolutionStrategy
	 */
	public EvolutionStrategy getEvolutionStrategy()
	{
		
		return new ExplicitEvolutionStrategy();
	}

	/**
	 * returns a new fitness Evaluator
	 */
	public FitnessEvaluator getFitnessEvaluator()
	{
		FitnessEvaluator fitnessEvaluator = new FitnessEvaluator();
		return fitnessEvaluator;
	}

	/**
	 * returns a new fitness Function
	 */
	public FitnessFunction getFitnessFunction()
	{
		return null;
		//return new ZombieFitnessFunction?(geneLength, lowerBound,
			//	upperBound);
	}

	/**
	 * returns a new gene manipulator
	 */
	public GeneManipulator getGeneManipulator()
	{
		return new GeneManipulator(probabilityOfOperations,
				getGeneticOperators());
	}

	/**
	 * returns our genetic operations stores in our array
	 */
	public GeneticOperator[] getGeneticOperators()
	{
		operators = new GeneticOperator[2];
		operators[0] = new HumanCrossover(1);
		operators[1] = new Mutation(mutationProbability);
		return operators;
	}

	/**
	 * returns our maximum generation
	 */
	public int getMaximumGeneration()
	{
		return maximumGeneration;
	}

	/**
	 * returns a new Phenotype
	 */
	public Phenotype getPhenotype()
	{
		return new Phenotype(this.getChromosome(),r.nextInt(20),r.nextInt(20),10);
	}

	/**
	 * This method returns the population of zombies.
	 */
	public Population getPopulation()
	{
		ArrayList population = new ArrayList();

		for (int i = 0; i < populationSize; i++)
		{
			population.add(this.getPhenotype());
		}
		return new Population(population);
	}
	/**
	 * returns a new standard selection strategy
	 */
	public SelectionStrategy getSelectionStrategy()
	{
		return new StandardSelectionStrategy();
	}

}