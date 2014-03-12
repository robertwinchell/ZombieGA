
package data;

import java.util.ArrayList;

import critter.Phenotype;

/**
 * Environment class
 * class that contains all the zombie, human and food
 * data into arrays then maps them to the ZombieApocalypseWorldViewer
 * The Interaction class is used to change the x and y coordinates 
 * making the humans move according to their food finding evolution
 * The environments act as markers and placeholders for x and y coordinates
 * 
 * @author Zach Winchell
 */
public class Environment 
{
	//environment arrays for food, zombies and humans
	private int[][] foodEnvironment = null;
	private int[][] humanEnvironment = null;
	private int[][] zombieEnvironment = null;
	
	//Phenotype environments
	private ArrayList[][] humanPhenoEnvironment = null;
	private ArrayList[][] zombiePhenoEnvironment = null;

	/**
	 * Default contructor that sets the passed in environments
	 * in the second constructor
	 * 
	 * @param foodEnvironment - passed in food environment
	 * @param humanEnvironment - passed in human environment
	 * @param zombieEnvironment - passed in zombie environment
	 */
	public Environment(int[][] foodEnvironment, 
		int[][] humanEnvironment, int[][] zombieEnvironment)
	{
		this(foodEnvironment, humanEnvironment, zombieEnvironment, null, null);
	}

/**
 * Sets all the passed in parameters to the global variables
 * 
 * @param foodEnvironment - passed in food environment
 * @param humanEnvironment - passed in human environment
 * @param zombieEnvironment - passed in zombie environment
 * @param humanPhenoEnvironment - passed in human phenotype environment
 * @param zombiePhenoEnvironment - passed in zombie phenotype environment
 */
	public Environment(int[][] foodEnvironment, 
		int[][] humanEnvironment, int[][] zombieEnvironment,
		ArrayList[][] humanPhenoEnvironment, ArrayList[][] zombiePhenoEnvironment)
	{
		this.foodEnvironment = foodEnvironment;
		this.humanEnvironment = humanEnvironment;
		this.zombieEnvironment = zombieEnvironment;
		this.humanPhenoEnvironment = humanPhenoEnvironment;
		this.zombiePhenoEnvironment = zombiePhenoEnvironment;
		System.out.println(this.humanPhenoEnvironment);
	}

	/**
	 * Add food to an environment
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void addFood(int x, int y)
	{
		foodEnvironment[x][y]++;
	}
	/**
	 * Add human to an environment
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void addHuman(int x, int y)
	{
		humanEnvironment[x][y]++;
		
	}
	/**
	 * Add zombie to an environment
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void addZombie(int x, int y)
	{
		zombieEnvironment[x][y]++;
	}
	/**
	 * Add a human phenotype to an environment array
	 * @param human - passed in human Phenotype
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void addHumanPhenotype(Phenotype human, int x, int y)
	{
		humanPhenoEnvironment[x][y].add(humanEnvironment[x][y]-1, human);
	}
	/**
	 * Add a zombie phenotype to an environment array
	 * @param zombie - passed in zombie Phenotype
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */

	public void addZombiePhenotype(Phenotype zombie, int x, int y)
	{
		zombiePhenoEnvironment[x][y].add(zombieEnvironment[x][y]-1, zombie);
	}
	
	/**
	 * Getter for the food environment
	 * @return - current food environment
	 */
	public int[][] getFoodEnvironment()
	{
		return foodEnvironment;
	}
	/**
	 * Getter for the human environment
	 * 
	 * @return - current human environment
	 */
	public int[][] getHumanEnvironment()
	{
		
		return humanEnvironment;
	}
	/**
	 * Getter for the zombie environment
	 * @return - current zombie environment
	 */
	public int[][] getZombieEnvironment()
	{
		return zombieEnvironment;
	}
	
	/**
	 * Getter for the individual food at the passed in x and y coordinates
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @return - the int count for the number of foods at the x and y coordinate
	 */
	public int getFood(int x, int y)
	{
		return foodEnvironment[x][y];
	}
	/**
	 * Getter for the individual humans at the passed in x and y coordinates
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @return - the int count for the number of humans at the x and y coordinate
	 */
	public int getHuman(int x, int y)
	{
		return humanEnvironment[x][y];
	}
	
	/**
	 * Getter for the individual zombies at the passed in x and y coordinates
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @return - the int count for the number of zombies at the x and y coordinate
	 */
	public int getZombie(int x, int y)
	{
		return zombieEnvironment[x][y];
	}
	
	/**
	 * Getter for the zombie phenotype at the x and y parameters
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @return - Zombie phenotype at the x and y coordinates
	 */
	public Phenotype getZombiePhenotype(int x, int y)
	{
		return (Phenotype)zombiePhenoEnvironment[x][y].get(zombieEnvironment[x][y]-1);
	}

	/**
	 * Getter for the human phenotype at the x and y parameters
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @return - human phenotype at the x and y coordinates
	 */
	public Phenotype getHumanPhenotype(int x, int y)
	{
		System.out.println(humanEnvironment[x][y]-1);
		return (Phenotype)humanPhenoEnvironment[x][y].get(humanEnvironment[x][y]-1);
	}
	/**
	 * Setter for a passed in food environment
	 * @param foodEnvironment - passed in food environment
	 */
	public void setFoodEnvironment(int[][] foodEnvironment)
	{
		this.foodEnvironment = foodEnvironment;
	}
	/**
	 * Setter for a passed in human environment
	 * @param humanEnvironment - passed in human environment
	 */
	public void setHumanEnvironment(int[][] humanEnvironment)
	{
		this.humanEnvironment = humanEnvironment;
	}
	/**
	 * Setter for a passed in zombie environment
	 * @param humanEnvironment - passed in zombie environment
	 */
	public void setZombieEnvironment(int[][] zombieEnvironment)
	{
		this.zombieEnvironment = zombieEnvironment;
	}
	/**
	 * Subtract a food from the current food environment at
	 * the x and y coordinates
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void subtractFood(int x, int y)
	{
		foodEnvironment[x][y]--;
	}
	/**
	 * Subtract a human from the current human environment at
	 * the x and y coordinates
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void subtractHuman(int x, int y)
	{
		System.out.println("SUBTRACTING A HUMAN AT X: " + x + " Y: " +y);
		humanEnvironment[x][y]--;
	}
	/**
	 * Subtract a zombie from the current zombie environment at
	 * the x and y coordinates
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void subtractZombie(int x, int y)
	{
		zombieEnvironment[x][y]--;
	}
	/**
	 * Subtracts a human phenotype from the x and y coordinates
	 * @param human - Phenotype to be removed
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void subtractHumanPhenotype(Phenotype human, int x, int y)
	{
		System.out.println("INDEX OF HUMAN PHENO ENVIRONMENT: " + (humanEnvironment[x][y]-1));
		humanPhenoEnvironment[x][y].remove(humanEnvironment[x][y]-1);
	}
	/**
	 * Subtracts a zombie phenotype from the x and y coordinates
	 * @param zombie - Phenotype to be removed
	 * @param x - x coordinate
	 * @param y - y coordinate
	 */
	public void subtractZombiePhenotype(Phenotype zombie, int x, int y)
	{
		zombiePhenoEnvironment[x][y].remove(zombieEnvironment[x][y]-1);
	}
	
}
