package evolution;

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
 * Our human factory for completely new instantiation of our previous classes.
 *  @author Allan Dancer
 * 
 */
public class HumanFactory extends ALifeFactory
{
	//create the size of weight genes and theta gene
	private int weightGene = BioVariables.FACTORY_WEIGHT_GENE_LENGTH;
	private int thetaGene = BioVariables.FACTORY_THETA_GENE_LENGTH;
	
	//instance variable for chance of our operations
	private double probabilityOfOperations = BioVariables.FACTORY_PROB_OF_OPERATIONS;

	// instance variable for our array of genetic operators which will
	// obviously occur
	private GeneticOperator[] operators;
	
	//instance variable mutation probability
	private double mutationProbability = BioVariables.FACTORY_PROB_OF_MUTATION;

	//instance variable our max generation
	private int maximumGeneration = BioVariables.MAX_GENERATION;

	//instance variable for our population size
	private int populationSize = BioVariables.HUMAN_POPULATION_SIZE;

	// makes a new population
	private Population population = new Population();
	
	//2d array for setting
	int[][] temp = new int[3][];

	//random variable for checking
	Random r = new Random();
	
	/**
	 * Our constructor for the zombie factory. Sets up a population
	 * of human phenotypes. Of course, each is different.
	 */
	public HumanFactory()
	{
		super();
		temp[0] = new int[1];
		temp[1]= new int[55*weightGene];
		temp[2] = new int[15*thetaGene];
		
		for (int i = 0; i < populationSize; i++)
		{
			
			// making a BitChromosome with a given length
			Chromosome chromosome = new BitArrayChromosome(temp, true);

			// casting the BitChromosome to a Phenotype
			Phenotype phenotype = new Phenotype(chromosome,r.nextInt(BioVariables.GRAPHICS_WIDTH),
					r.nextInt(BioVariables.GRAPHICS_LENGTH), BioVariables.HUMAN_STRENGTH );
			
			// adding the Phenotype to the population
			population.add(phenotype);
		}
	}

	/**
	 * returns a new chromosome of our chromosome length
	 */
	public Chromosome getChromosome()
	{
		temp[0] = new int[1];
		temp[1]= new int[55*weightGene];
		temp[2] = new int[15*thetaGene];
		
		
		Chromosome chromosome =  new BitArrayChromosome(temp, true);
		System.out.println(chromosome.toString());
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
		//return new HumanFitnessFunction?(geneLength, lowerBound,
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
		return new Phenotype(this.getChromosome(),r.nextInt(BioVariables.GRAPHICS_WIDTH),
				r.nextInt(BioVariables.GRAPHICS_LENGTH), BioVariables.HUMAN_STRENGTH );
	}

	/**
	 * This method returns the population of humans.
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