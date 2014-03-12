package observe;

import genes.ZombieCrossover;

import java.util.*;

import genes.*;
import chromosome.BitArrayChromosome;
import chromosome.BitChromosome;
import chromosome.Chromosome;
import numbers.ALifeRandom;
import critter.Phenotype;

/**
 * This is our dueling class for fighting between zombies and humans
 * Basically it is set up to the modern day view of zombie behavior. 
 * When fighting, a zombie and a human have certain energy.  If the human
 * has more energy, the human wins the battle and the zombie dies.  However,
 * if the zombie wins, the human becomes a zombie and keeps all the same 
 * genetics.  The human will no longer be able to evolve but the zombies
 * now have one less human to worry about and the humans have another zombie 
 * to deal with.  On top of this, zombies always receive a +20 to their strength
 * simply because of their fighting resilience.  The only way a true zombie dies
 * is if its head gets cut off.  All other parts of the body can be severely 
 * wounded and still have no mental or physical affects on the zombie.  So it 
 * only seems fair to give them a +20 bonus to strength.
 * 
 * @author Allan Dancer, Robert Winchell.  
 */
public class Dueling implements Observer
{
	/*
	 * Instance variable for our human phenotype
	 * From this variable we will call the strength
	 * of the Phenotype and we will figure out if
	 * its a zombie or human.
	 */
	private Phenotype humanphenotype;
	
	/*
	 * Instance variable for our zombie phenotype
	 * If the first gene is a zero it is a zombie.
	 */
	private Phenotype zombiephenotype;

	/*
	 * Instance variable for our human strength
	 * It is compared to the zombie strength and 
	 * if the human strength is higher it will 
	 * win.
	 */
	private double humanStrength;
	
	/*
	 * Instance variable for our zombie strength
	 * It is compared to the human strength and 
	 * if the human strength is higher it will 
	 * win.
	 */
	private double zombieStrength;

	/**
	 * Basic Constructor for the dueling class
	 */
	public Dueling()
	{
		super();
	}

	/**
	 * 
	 * Another constructor that takes in two phenotypes and determines
	 *  which is a human and which is a zombie.  It checks the first
	 *  gene of the first phenotype passed in to determine if it is a 
	 *  zombie.  If the first gene is a '0', its a zombie.  Otherwise,
	 *  the first phenotype is a human.
	 * 
	 * @param h the instance Phenotype gets set to either a human or zombie.
	 * @param z the instance Phenotype gets set to either a human or zombie.
	 */
	public Dueling(Phenotype h, Phenotype z)
	{
		//variable for the first gene in the first phenotype
		int humanOrZombie;
		
		//checks the value of the first gene
		humanOrZombie = h.getChromosome().toString().charAt(0);

		/*
		 * if the first gene is a 1 it is a human
		 * and therefore our human phenotype becomes
		 * the first variable past in.  And the 
		 * human strength is set to that phenotypes
		 * stength
		 */
		if (humanOrZombie == 1)
		{
			this.humanphenotype = h;
			this.humanStrength = h.getStrength();
			this.zombiephenotype = z;
			this.zombieStrength = z.getStrength();
		} 
		
		/*
		 * Same as the previous if statement but
		 * it sets up the zombie.
		 */
		else
		{
			this.zombiephenotype = h;
			this.zombieStrength = h.getStrength();
			this.humanphenotype = z;
			this.humanStrength = z.getStrength();
		}

	}

	/**
	 * This is where the zombie and human duel each other.  We add
	 * a little randomness to the zombie's strength to vary things
	 * up a little...almost like a dice roll but still in the zombies favor
	 * Finally, either a human is returned with -10 strength and a dead zombie (strength =0),
	 * or the human is turned into a zombie and both zombies are returned (original zombie still
	 * loses -10 strength).
	 * @return returns a phenotype array of either two zombies or a human with a dead zombie
	 */
	public Phenotype[] duelEachOther()
	{
		// variable for which ever phenotype wins
		Phenotype winningPhenotype = new Phenotype(null, 0, 0, 0.0);

		// if zombie wins the human has to become a zombie
		// so this variable is set up for this
		Phenotype zombifiedHumanPhenotype = new Phenotype(null, 0, 0,
				0.0);

		/*
		 * Phenotype array that returns just a single human if the human wins.
		 * Or it returns the zombie and the zombified human(now a zombie) in the
		 * array.
		 */
		Phenotype[] phenotypeArray = new Phenotype[2];

		
		// sets up the human chromosome for zombification
		//Chromosome chromosome = new BitArrayChromosome(this.humanphenotype);
		
		BitArrayChromosome bitArrayChromosome = (BitArrayChromosome) this.humanphenotype.getChromosome();
		
		// passes the human chromosome in an array for zombieCrossover
		Chromosome[] chromArray = {(BitArrayChromosome)this.humanphenotype.getChromosome()};
		
		//Another array after zombieCrossover
		Chromosome[] afterCrossoverArray = new BitChromosome[1];
		

		// instatiate the zombiecrossover for zombification
		ZombieCrossover zombiecrossover = new ZombieCrossover(1.0);

		// a sum of the two phenotypes strength
		double sum = this.humanphenotype.getStrength()
				+ this.zombiephenotype.getStrength();

		// print the sum...for checking
		if (sum<0)
		{
			sum = 0;
		}
		System.out.println(("SUM: " + sum));

		// a random instance
		Random random = ALifeRandom.getRandomNumberGenerator();
		/*
		 * adds variability to the sum for the zombie. Since the zombies are
		 * naturally stronger we give a + 20 to the strength. hehe alright go
		 * zombies!!!
		 */
		sum = random.nextInt((int) (sum + 20));
		

		// prints out the sum again just to check.
		System.out.println(sum);

		
		
		/*
		 * checks to see if the zombie wins based on the sum
		 * and the extra strength varibility gives the zombie
		 * the edge in most cases...so we should see a lot 
		 * more "zombie wins."
		 */
		if (sum >= this.humanStrength)
		{
			System.out.println("Zombie Wins");

			// Zombie wins but we still need to take away 10 strength for dueling
			winningPhenotype = this.zombiephenotype;
			winningPhenotype.setStrength((winningPhenotype
					.getStrength() - 10));

			//Checks the strength 
			System.out.println(winningPhenotype.getStrength());

			/*
			 * passing in the human chromosome for zombification then returns
			 * the human with the first bit fliped so now its a zombie!!!!!!!!!!
			 */
			afterCrossoverArray = zombiecrossover.performOperation(chromArray);


			// sets the new zombie to a chromsome
			//chromosome = afterCrossoverArray[0];

			// sets the new zombie chromosome to a phentoype
			zombifiedHumanPhenotype.setChromosome(afterCrossoverArray[0]);

			// puts both zombies into the pheotype array
			phenotypeArray[0] = winningPhenotype;
			phenotypeArray[1] = zombifiedHumanPhenotype;

		}

		/*
		 * If the zombie didn't win, the human has to win
		 * and it gets passed back into the array with 
		 * a dead zombie.
		 */
		else
		{
			// testing here for the human
			System.out.println("Human Wins");

			// human wins and we minus 10 strength.
			winningPhenotype = this.humanphenotype;
			winningPhenotype.setStrength((winningPhenotype
					.getStrength() - 10));
			
			//zombie dies so it has no strength.
			this.zombiephenotype.setStrength(0);

			System.out.println(winningPhenotype.getStrength());

			// no need to put anything else but the human
			// he/she got lucky this time and won.
			phenotypeArray[0] = winningPhenotype;
			phenotypeArray[1] = this.zombiephenotype;

		}
		return phenotypeArray;

	}

	/**
	 * a getter for Humanstrength for checking purposes
	 * @return returns the human strength
	 */
	public double getHumanStrength()
	{

		this.humanStrength = this.humanphenotype.getStrength();
		return this.humanStrength;
	}

	/**
	 * a getter for the zombie strength for checking purposes
	 * @return returns the zombie strength
	 */
	public double getZombieStrength()
	{
		this.zombieStrength = this.humanphenotype.getStrength();

		return this.zombieStrength;
	}
	
	/**
	 * sets the human strength
	 * @param strength sets the instance variable strength to a double
	 */
	public void setHumanStrength(double strength)
	{
		this.humanStrength = strength;
	}
	
	/**
	 * sets the zombie strength
	 * @param strength sets the instance variable strength to a double
	 */
	public void setZombieStrength(double strength)
	{
		this.zombieStrength = strength;
	}

	/**
	 * updates the obsever
	 */
	public void update(Observable observable, Object object)
	{
		
	}
}
