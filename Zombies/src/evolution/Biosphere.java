package evolution;

import java.util.*;

import observe.Interaction;
import critter.*;
import chromosome.*;
import fitness.*;
import genes.*;
import data.*;

import graphics.*;
import numbers.*;

/**
 *Driver for the Zombie Apocalypse Simulation
 *
 *@author Robert Winchell
 */
public class Biosphere 
{

	
	/**
	 * Main class, uses the Factory to call
	 * all the Evolution variables and methods
	 * @param args
	 */
	public static void main(String[] args) 
	{
		
		System.out.println("TESTING");
		//System.out.exit(0);//--------------------------------
		
		//Creates the factories, populations and all other 
		//variables to be used for the simulation
		ALifeFactory zombieFactory = new ZombieFactory();
		ALifeFactory humanFactory = new HumanFactory();
		

		Population zPop = zombieFactory.getPopulation();
		Population hPop = humanFactory.getPopulation();
		
		EvolutionStrategy zevolutionStrategy = zombieFactory.getEvolutionStrategy();
		FitnessEvaluator zfitnessEvaluator = zombieFactory.getFitnessEvaluator();
		FitnessFunction zfitnessFunction = zombieFactory.getFitnessFunction();
		GeneManipulator zmanipulator = zombieFactory.getGeneManipulator();
		SelectionStrategy zselectionStrategy = zombieFactory.getSelectionStrategy();
		
		EvolutionStrategy hevolutionStrategy = humanFactory.getEvolutionStrategy();
		FitnessEvaluator hfitnessEvaluator = humanFactory.getFitnessEvaluator();
		FitnessFunction hfitnessFunction = humanFactory.getFitnessFunction();
		GeneManipulator hmanipulator = humanFactory.getGeneManipulator();
		SelectionStrategy hselectionStrategy = humanFactory.getSelectionStrategy();
		
		//--------------------------------
		
		//Filepaths for human images
		String[] filePath = new String[4];
		filePath[0] = "H:\\My Pictures\\human0.JPG";
		filePath[1] = "H:\\My Pictures\\human1.JPG";
		
		//New simulation grid for the graphics
		ZombieApocalypseWorldViewer world = new ZombieApocalypseWorldViewer(
				BioVariables.GRAPHICS_WIDTH,BioVariables.GRAPHICS_LENGTH);
		
		//Random number generator
		Random r=ALifeRandom.getRandomNumberGenerator();
		

       
        //Contains a count for food at any position, a count for human and zombies
        //Also contains a n array list at each location showing any 
        //phenotypes currently in that location.
		
		//Makes the Environments to the same size specified for the World Viewer
        int[][] foodEnvironment = new int[BioVariables.GRAPHICS_WIDTH]
        	[BioVariables.GRAPHICS_LENGTH];
        int[][] humanEnvironment = new int[BioVariables.GRAPHICS_WIDTH]
        	[BioVariables.GRAPHICS_LENGTH];
        int[][] zombieEnvironment = new int[BioVariables.GRAPHICS_WIDTH]
        	[BioVariables.GRAPHICS_LENGTH];
        ArrayList[][] humanPhenoEnvironment = new ArrayList[BioVariables.GRAPHICS_WIDTH]
        	[BioVariables.GRAPHICS_LENGTH];
        ArrayList[][] zombiePhenoEnvironment = new ArrayList[BioVariables.GRAPHICS_WIDTH]
        	[BioVariables.GRAPHICS_LENGTH];
        
        //Passes the environments to the Environment class
        Environment environment = new Environment(foodEnvironment, 
        		humanEnvironment,zombieEnvironment, humanPhenoEnvironment,
        		zombiePhenoEnvironment);
        
        //Instantiate an array list at every position in the human and zombie
        //pheno environments.
        for (int i=0; i<humanPhenoEnvironment.length; i++)
        {
            for (int j=0; j<humanPhenoEnvironment[i].length; j++)
            {
            	humanPhenoEnvironment[i][j] = new ArrayList();
            }
        }
        
        for (int i=0; i<zombiePhenoEnvironment.length; i++)
        {
            for (int j=0; j<zombiePhenoEnvironment[i].length; j++)
            {
            	zombiePhenoEnvironment[i][j] = new ArrayList();
            }
        }
        
        
        //New Interaction
        Interaction interact = new Interaction();
       
        //Time delay for the graphics between redisplays
		long timeDelay = BioVariables.GRAPHICS_DELAY;
		
		//int value for the while loop
		int i = 1;
//-----------------------------------------
	
		

//-------------------------------------------	
		//Sets the phenotypes to random locations
        for (int n=0; n<hPop.getSize(); n++)
        {
        	hPop.getPhenotype(n).setXloc(r.nextInt(BioVariables.GRAPHICS_WIDTH));
			hPop.getPhenotype(n).setYloc(r.nextInt(BioVariables.GRAPHICS_LENGTH));
        }
        
        for (int n=0; n<zPop.size(); n++)
        {
        		
        	zPop.getPhenotype(n).setXloc(r.nextInt(BioVariables.GRAPHICS_WIDTH));
			zPop.getPhenotype(n).setYloc(r.nextInt(BioVariables.GRAPHICS_LENGTH));
        }
        
        System.out.println("BEGIN SIMULATION");
        
        	//runs through as many as the specified max generation states
			while(i <= BioVariables.MAX_GENERATION)
        	{
				//System.out.println("here1");
        		
				//Add a random number of food each generation at a random position
        		for (int i2=0; i2 < r.nextInt(BioVariables.MAX_FOOD); i2++)
        		{
        			int currX = r.nextInt(BioVariables.GRAPHICS_WIDTH);
        			int currY = r.nextInt(BioVariables.GRAPHICS_LENGTH);
            		world.addFoodToGrid(currX, currY);
            		environment.addFood(currX, currY);
        		}
        		
        		//Add the humans to grid at the given location
        		//Also, add a count of that phenotype at that location to the environment
        		//and add that phenotype to the environment.
        		for(int i2 = 0; i2 < hPop.getSize();i2++)
        		{
        			world.setCritterImage(filePath[0]);
        			world.addCritterToGrid(hPop.getPhenotype(i2).getXloc(), hPop.getPhenotype(i2).getYloc());
        			environment.addHuman(hPop.getPhenotype(i2).getXloc(), hPop.getPhenotype(i2).getYloc());
        			environment.addHumanPhenotype(hPop.getPhenotype(i2), hPop.getPhenotype(i2).getXloc(),
        					hPop.getPhenotype(i2).getYloc());
        			
        			
        		}
        		//Add the zombies to grid at the given location
        		//Also, add a count of that phenotype at that location to the environment
        		//and add that phenotype to the environment.
        		for(int i2 = 0; i2<zPop.getSize(); i2++)
        		{
        			//System.out.println(zPop.getPhenotype(i2).getXloc()+ " "+ zPop.getPhenotype(i2).getYloc());
        			
        			world.addZombieToGrid(zPop.getPhenotype(i2).getXloc(), zPop.getPhenotype(i2).getYloc());
        			environment.addZombie(zPop.getPhenotype(i2).getXloc(), zPop.getPhenotype(i2).getYloc());
        			environment.addZombiePhenotype(zPop.getPhenotype(i2), zPop.getPhenotype(i2).getXloc(),
        					zPop.getPhenotype(i2).getYloc());
        			
        		}
        	
        		//time delay before placing the graphics
        		world.delay(timeDelay);
        		world.redisplay();

        		//Subtracts the Zombie Phenotypes from the zombie environment then updates the 
        		//x and y coordinates
        		for(int i2 = 0; i2 < zPop.getSize();i2++)
        		{	
        			//System.out.println("The zombie current size of the zombiePopulation is" + zPop.getSize());
        			environment.subtractZombiePhenotype(zPop.getPhenotype(i2), 
        				zPop.getPhenotype(i2).getXloc(), zPop.getPhenotype(i2).getYloc());
        			environment.subtractZombie(zPop.getPhenotype(i2).getXloc(), 
        					zPop.getPhenotype(i2).getYloc());
        			
        			if(environment.getZombie(zPop.getPhenotype(i2).getXloc(), 
        					zPop.getPhenotype(i2).getYloc())<1)
        			{
        				
        				world.removeImageFromGrid(zPop.getPhenotype(i2).getXloc(), 
            					zPop.getPhenotype(i2).getYloc());
        				
        			}
        			//updates the x and y locations
        			interact.updateZombie(zPop.getPhenotype(i2),environment, hPop, zPop);
        		}
        		
        		//Remove the human from the grid at the given location
        		//Also removes a count from the location in the environment
        		//and removes the last phenotype at that location.
        		//It also calls the update method to move.
        		for(int i2 = 0; i2 < hPop.getSize();i2++)
        		{	
        			environment.subtractHumanPhenotype(hPop.getPhenotype(i2), 
        				hPop.getPhenotype(i2).getXloc(), hPop.getPhenotype(i2).getYloc());
        			environment.subtractHuman(hPop.getPhenotype(i2).getXloc(), 
        					hPop.getPhenotype(i2).getYloc());
        			
        			if(environment.getHuman(hPop.getPhenotype(i2).getXloc(), 
        					hPop.getPhenotype(i2).getYloc())<1)
        			{
        				world.removeImageFromGrid(hPop.getPhenotype(i2).getXloc(), 
            					hPop.getPhenotype(i2).getYloc());
        				
        			}
        			
        			/*
        			if(hPop.getPhenotype(i2).getStrength()<1)
        			{
        				System.out.println("PHENOTYPE: "+hPop.getPhenotype(i2));
        				System.out.println("HOW MANY HUMANS: "+
        					(environment.getHumanEnvironment())[hPop.getPhenotype(i2).getXloc()] 
        					[hPop.getPhenotype(i2).getYloc()]);

        				Phenotype critter = hPop.getPhenotype(i2);
        				
        				hPop.removePhenotype(critter);
        				environment.subtractHumanPhenotype(critter,
        						critter.getXloc(), critter.getYloc());
        				
        				environment.subtractHuman(critter.getXloc(), 
        						critter.getYloc());
        			}
        			*/
        			//else
        			//{
        				interact.updateHuman(hPop.getPhenotype(i2),environment, hPop);
        			//}
        		}

        		i++;
			
        		
        	}
			

			//Addsthe zombies and Humans back to the grid at their new x and y coordinates
    		for (int n=0; n<hPop.getSize(); n++)
    		{
    			world.addCritterToGrid(hPop.getPhenotype(n).getXloc(), hPop.getPhenotype(n).getYloc());
    		}
    		
    		for (int n=0; n<zPop.getSize(); n++)
    		{
    			world.addZombieToGrid(zPop.getPhenotype(n).getXloc(), zPop.getPhenotype(n).getYloc());
    		}
    		
    		world.redisplay();

			System.out.println("Human population has: " + hPop.getSize());
			System.out.println("Zombie population has: " + zPop.getSize());
    		System.out.println("END SIMULATION");
    		
        }
		
	}

       
      
        
		
	




