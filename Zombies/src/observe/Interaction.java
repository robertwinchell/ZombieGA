package observe;

import java.util.*;
import java.math.BigDecimal;
import java.math.MathContext;


import neuron.NeuralNet;
import numbers.ALifeRandom;

import data.*;
import evolution.BioVariables;

import critter.*;
import chromosome.BitArrayChromosome;
import chromosome.BitChromosome;
import chromosome.Chromosome;
import genes.*;
/**
 * 
 * @author Robert Winchell, Allan Dancer
 * 
 * This class will update positions of humans and zombies. It will also be able 
 * to check for a human on human collision and breed (with crossover) and check
 * for food (which will give humans higher strength). When a zombie moves, it will
 * check for other humans and convert them if the duel is won.
 *
 */
public class Interaction
{
	//create the genes lengths
	private int weightGene = BioVariables.FACTORY_WEIGHT_GENE_LENGTH;
	private int thetaGene = BioVariables.FACTORY_THETA_GENE_LENGTH;
	
	/**
	 * This will update the human position and eat any food it can find. After the 
	 * possition is update, it will check for food and humans at the new position. 
	 * If there is any food, it will eat it, remove it from grid, and increase the human's
	 * strenght. If it finds a human it will probably breed.
	 * 
	 * @param critter is the human phenotype to be updated.
	 * 
	 * @param environment is the data of the whole environment.
	 * 
	 * @para humanPop is the human Population that may grow.
	 */
	public void updateHuman(Phenotype critter, Environment environment, Population humanPop)
	{
		//Get the environment.
		int[][] foodEnvironment = environment.getFoodEnvironment();
		
		//System.out.println (((BitArrayChromosome)critter.getChromosome()).toString());
		critter.setStrength(critter.getStrength()-1.0);
		
		
		//Get the location of the critter, both x and y.
		int x = critter.getXloc();
		int y = critter.getYloc();
		
		//Get the length of theta genes and length of weight gene.
		critter.setWeightGeneLength(weightGene);
		critter.setThetaGeneLength(thetaGene);
		int lengthOfEachWeightGene = critter.getWeightGeneLength();
		int lengthOfEachThetaGene = critter.getThetaGeneLength();
		
		//Set all the x-1, y-1, x+1, and y+1
		int xMinusOne = x-1;
		int xPlusOne = x+1;
		int yMinusOne = y-1;
		int yPlusOne = y+1;
		
		//Check to be sure that all x-1, y-1, x+1, and y+1 are within array bounds
		//If x is smaller than 0 (out of bounds) then make it be the last x 
		//in the environment (wrap around);
		if (xMinusOne<0)
		{
			//System.out.print("X From " + xMinusOne + " to ");
			xMinusOne = foodEnvironment.length-1;
			//System.out.println(xMinusOne);
		}
		
		//If y is smaller than 0 (out of bounds) then make it be the last y
		//in the environment (wrap around);
		if (yMinusOne<0)
		{
			//System.out.print("Y From " + yMinusOne + " to ");
			yMinusOne = foodEnvironment[0].length-1;
			//System.out.println(yMinusOne);
		}
		
		//If x is greater than max index (out of bounds) then make it be the first x 
		//in the environment (wrap around);
		if (xPlusOne>foodEnvironment.length-1)
		{
			//System.out.print("X From " + xPlusOne + " to ");
			xPlusOne = 0;
			//System.out.println(xPlusOne);
		}
		
		//If y is greater than max index (out of bounds) then make it be the first y
		//in the environment (wrap around);
		if (yPlusOne>foodEnvironment[0].length-1)
		{
			//System.out.print("Y From " + yPlusOne + " to ");
			yPlusOne = 0;
			//System.out.println(yPlusOne);
		}

		//System.out.println("Old X: " +critter.getXloc()+ " Old Y: " +critter.getYloc());
		
		//Get input from 0 (top left corner) to 7 (center left) from
		//the food environment.
		double[] inputArray = new double[8];
		inputArray[0]=foodEnvironment[xMinusOne][yMinusOne];
		inputArray[1]=foodEnvironment[x][yMinusOne];
		inputArray[2]=foodEnvironment[xPlusOne][yMinusOne];			
		inputArray[3]=foodEnvironment[xPlusOne][y];
		inputArray[4]=foodEnvironment[xPlusOne][yPlusOne];
		inputArray[5]=foodEnvironment[x][yPlusOne];
		inputArray[6]=foodEnvironment[xMinusOne][yPlusOne];
		inputArray[7]=foodEnvironment[xMinusOne][y];	
		
		//DEBUG MODE
		/*
		String[] critterArray = ((BitArrayChromosome)critter.getChromosome()).toStringArray();
		for (int i=0; i<critterArray.length; i++)
		{
			System.out.print(i+ ":   |");
			for (int j=0; j<critterArray[i].length(); j++)
			{
				System.out.print(critterArray[i].charAt(j) + "|");
			}
			System.out.println("");
		}*/
		//END DEBUG MODE
		
		
		//BEGIN MAPPING
		//Create the weight array
		double[][][] weightArray = new double[3][][];
		weightArray[0] = new double[8][1];
		weightArray[1] = new double[5][8];
		weightArray[2] = new double[3][5];
		
		//Create the theta array
		double[][] thresholdArray = new double[3][];
		thresholdArray[0] = new double[8];
		thresholdArray[1] = new double[5];
		thresholdArray[2] = new double[3];
		
		//All the weights on the first layer are 1 (100%)
		for (int i=0; i<weightArray[0].length; i++)
		{
			for (int j=0; j<weightArray[0][i].length; j++)
			{
				weightArray[0][i][j] = 1;
			}
		}
		
		//Keep track of current index of the weight string.
		int trackIndex=0;
		
		//For the weights, you got to map them. This maps middle layer.
		//System.out.println("BEGIN MAPPING MIDDLE LAYER");
		for (int i=0; i<weightArray[1].length; i++)
		{
			//System.out.print("*");
			for (int j=0; j<weightArray[1][i].length; j++)
			{
				//Start the current index, at position 0, 0 of the middle layer
				//The initial current index is 
				//[i*(length of weight array[1][i] * (lenght of each weight gene)] +
				//[j*(length of each weight gene)]
				int currentIndex = (i*weightArray[1][i].length*lengthOfEachWeightGene)+
					(j*lengthOfEachWeightGene);
				
				//The track index will become the current index
				//It will update to the last position before it is done.
				trackIndex = currentIndex;
				
				
				//System.out.print(currentIndex + "|");
				
				//Map the gene at this current position to a valuable between 0 and 1
				//Call this mapGene method, it takes in a BitChromosome from two positions,
				//this is the gene, then sets the upper boundary to 1 and lower boundary to 0.
				weightArray[1][i][j] = mapGene(
					((BitArrayChromosome)critter.getChromosome()).getGene
					(1, currentIndex, currentIndex+lengthOfEachWeightGene),0,1);
			}
		}
		
		//After this is done, add the length of the weight gene to get to the ending
		//track index to get the index of where the top layer of the weight vectors
		//starts
		trackIndex+=lengthOfEachWeightGene;
		
		//System.out.println(trackIndex);
		
		//For the weights, you got to map them. This maps top layer.
		//System.out.println("BEGIN MAPPING TOP LAYER");
		for (int i=0; i<weightArray[2].length; i++)
		{
			//System.out.print("x" + weightArray[2].length);
			
			for (int j=0; j<weightArray[2][i].length; j++)
			{
				//Start the current index by adding the trackIndex (pointer of where the program
				//was done getting the weights for the middle layer) to the rest. It is using 
				//the same mathematical equation as the mapping for middle layer.
				int currentIndex = trackIndex+(i*weightArray[2][i].length*lengthOfEachWeightGene)+
					(j*lengthOfEachWeightGene);
				
				//System.out.print(currentIndex + "|");

				//Map the gene at this current position to a valuable between 0 and 1
				//Call this mapGene method, it takes in a BitChromosome from two positions,
				//this is the gene, then sets the upper boundary to 1 and lower boundary to 0.
				weightArray[2][i][j] = mapGene(
					((BitArrayChromosome)critter.getChromosome()).getGene
					(1, currentIndex, currentIndex+lengthOfEachWeightGene),0,1);
			}
		}
		
		//DEBUG MODE
		//Prints out the values for the weight array
		/*
		for(int i=0; i<weightArray.length; i++)
		{
			for (int j=0; j<weightArray[i].length; j++)
			{
				for (int n=0; n<weightArray[i][j].length; n++)
				{
					System.out.print(weightArray[i][j][n] + "|");					
				}
				System.out.println("");
			}
			System.out.println("");
			
		}*/
		//END DEBUG MODE

		//System.out.println("BEGIN MAPPING THRESHOLDS");
		//For the thresholds, you got to map them.
		for (int i=0; i<thresholdArray.length; i++)
		{
			//System.out.print("*");
			for (int j=0; j<thresholdArray[i].length; j++)
			{
				//Get the current index, uses the same equation as the one before this 
				//for weights.
				int currentIndex = (i*weightArray[i].length*lengthOfEachThetaGene)+
					(j*lengthOfEachThetaGene);
				//System.out.print(currentIndex + "|");
				
				//Maps the gene, this time it takes the information from the 
				//Threshold part of the BitArrayChromosome and sets the lower
				//bound to -1, and upper bound to 3.
				thresholdArray[i][j] = mapGene(
					((BitArrayChromosome)critter.getChromosome()).getGene
					(2, currentIndex, currentIndex+lengthOfEachThetaGene),-1,3);
			}
		}
		
		//Create a null output array
		double[] outputArray = null;
		
		//Try to feed weights, thresholds and input array into neural network
		 try
		 {
			//Create a neural network with the critter's weights and thresholds
			 NeuralNet net = new NeuralNet(weightArray, thresholdArray);
		     
			 //Feed the input into the network and store the output
			 outputArray = net.feedForward(inputArray);
		     
			 //For every double in the output array, round up or down if 
			 //it is greater or less than 5
			 for (int i=0; i<outputArray.length; i++)
			 {
				 if (outputArray[i]>0.5)
				 {
					 outputArray[i]=1;
				 }
				 else
				 {
					 outputArray[i]=0;
				 }
			 }
		     
		     
			 //System.out.println(outputArray[0]+" "
					 //+outputArray[1]+" "+outputArray[2]); 
		 }
		 catch(Exception e)
		 {
			 System.out.println(e.getMessage());
		 }
		 
		 //Set the result to a 0
		 //depending on the output (000) to (111) it will map from 0 to 7
		 int result = 0;
		 if (outputArray[0]==1)
		 {
			 result+=4;
		 }
		 if (outputArray[1]==1)
		 {
			 result+=2;
		 }
		 if (outputArray[2]==1)
		 {
			 result+=1;
		 }
		 
		 //System.out.println("Result: " + result);
		 //Move top-left
		 if (result==0)
		 {
			 critter.setXloc(xMinusOne);
			 critter.setYloc(yMinusOne);
		 }
		 //Move top
		 else if (result==1)
		 {
			 critter.setXloc(x);
			 critter.setYloc(yMinusOne);
		 }
		 //Move top-right
		 else if (result==2)
		 {
			 critter.setXloc(xPlusOne);
			 critter.setYloc(yMinusOne);
		 }
		 //Move right
		 else if (result==3)
		 {
			 critter.setXloc(xPlusOne);
			 critter.setYloc(y);
		 }
		 //Move down-right
		 else if (result==4)
		 {
			 critter.setXloc(xPlusOne);
			 critter.setYloc(yPlusOne);
		 }
		 //Move down
		 else if (result==5)
		 {
			 critter.setXloc(x);
			 critter.setYloc(yPlusOne);
		 }
		 //Move down-left
		 else if (result==6)
		 {
			 critter.setXloc(xMinusOne);
			 critter.setYloc(yPlusOne);
		 }
		 //Move left
		 else if (result==7)
		 {
			 critter.setXloc(xMinusOne);
			 critter.setYloc(y);
		 }

		 checkFood(foodEnvironment,critter);
		 checkHuman(((Environment)environment), critter, humanPop);
	}
	
	/**
	 * This will update the zombie's position, and check for humans
	 * at that new position. If a human is found, they will duel 
	 * against each other.
	 * 
	 * @param critter is the zombie to be update.
	 * 
	 * @param environment is the whole data environment in the simulation
	 * 
	 * @param humanPop is the human Population and may be decreased if a human dies.
	 * 
	 * @param zombiePop is the zombie Population and may be increase if the
	 * human dies or decreased if the zombie dies.
	 */
	public void updateZombie(Phenotype critter, Environment environment, 
			Population humanPop, Population zombiePop)
	{
		System.out.println("UPDATE ZOMBIE");
		
		//The food environment for the zombies is the humans
		int[][] humanEnvironment = environment.getHumanEnvironment();
		
		/*BEGIN DEBUG MODE
		System.out.println("X :0.1.2.3.4.5.6.7.8.9.0.1.2.3.4.5.6.7.8.9");
		for (int i=0; i<humanEnvironment.length; i++)
		{
			System.out.print(i);
			if (i<10)
			{
				System.out.print(" ");
			}
			System.out.print(":");
			for (int j=0; j<humanEnvironment[i].length; j++)
			{
				System.out.print(humanEnvironment[i][j] + ".");
			}
			System.out.println("");
		}
		System.exit(0);
		END DEBUG MODE*/
		
		//System.out.println (((BitArrayChromosome)critter.getChromosome()).toString());
		critter.setStrength(critter.getStrength()-1.0);
		
		
		//Get the location of the critter, both x and y.
		int x = critter.getXloc();
		int y = critter.getYloc();
		
		//Get the length of theta genes and length of weight gene.
		critter.setWeightGeneLength(weightGene);
		critter.setThetaGeneLength(thetaGene);
		int lengthOfEachWeightGene = critter.getWeightGeneLength();
		int lengthOfEachThetaGene = critter.getThetaGeneLength();
		
		//Set all the x-1, y-1, x+1, and y+1
		int xMinusOne = x-1;
		int xPlusOne = x+1;
		int yMinusOne = y-1;
		int yPlusOne = y+1;
		System.out.println("UPDATE ZOMBIE3");
		
		//Check to be sure that all x-1, y-1, x+1, and y+1 are within array bounds
		//If x is smaller than 0 (out of bounds) then make it be the last x 
		//in the environment (wrap around);
		if (xMinusOne<0)
		{
			//System.out.print("X From " + xMinusOne + " to ");
			xMinusOne = humanEnvironment.length-1;
			//System.out.println(xMinusOne);
		}
		
		//If y is smaller than 0 (out of bounds) then make it be the last y
		//in the environment (wrap around);
		if (yMinusOne<0)
		{
			//System.out.print("Y From " + yMinusOne + " to ");
			yMinusOne = humanEnvironment[0].length-1;
			//System.out.println(yMinusOne);
		}
		
		//If x is greater than max index (out of bounds) then make it be the first x 
		//in the environment (wrap around);
		if (xPlusOne>humanEnvironment.length-1)
		{
			//System.out.print("X From " + xPlusOne + " to ");
			xPlusOne = 0;
			//System.out.println(xPlusOne);
		}
		
		//If y is greater than max index (out of bounds) then make it be the first y
		//in the environment (wrap around);
		if (yPlusOne>humanEnvironment[0].length-1)
		{
			//System.out.print("Y From " + yPlusOne + " to ");
			yPlusOne = 0;
			//System.out.println(yPlusOne);
		}
		//-----------------------------------------------------
		System.out.println("UPDATE ZOMBIE2");
		//System.out.println("Old X: " +critter.getXloc()+ " Old Y: " +critter.getYloc());
		
		//Get input from 0 (top left corner) to 7 (center left) from
		//the food environment.
		double[] inputArray = new double[8];
		inputArray[0]=humanEnvironment[xMinusOne][yMinusOne];
		inputArray[1]=humanEnvironment[x][yMinusOne];
		inputArray[2]=humanEnvironment[xPlusOne][yMinusOne];			
		inputArray[3]=humanEnvironment[xPlusOne][y];
		inputArray[4]=humanEnvironment[xPlusOne][yPlusOne];
		inputArray[5]=humanEnvironment[x][yPlusOne];
		inputArray[6]=humanEnvironment[xMinusOne][yPlusOne];
		inputArray[7]=humanEnvironment[xMinusOne][y];	
		
		//DEBUG MODE
		/*
		String[] critterArray = ((BitArrayChromosome)critter.getChromosome()).toStringArray();
		for (int i=0; i<critterArray.length; i++)
		{
			System.out.print(i+ ":   |");
			for (int j=0; j<critterArray[i].length(); j++)
			{
				System.out.print(critterArray[i].charAt(j) + "|");
			}
			System.out.println("");
		}*/
		//END DEBUG MODE
		
		
		//BEGIN MAPPING
		//Create the weight array
		double[][][] weightArray = new double[3][][];
		weightArray[0] = new double[8][1];
		weightArray[1] = new double[5][8];
		weightArray[2] = new double[3][5];
		
		//Create the theta array
		double[][] thresholdArray = new double[3][];
		thresholdArray[0] = new double[8];
		thresholdArray[1] = new double[5];
		thresholdArray[2] = new double[3];
		
		//All the weights on the first layer are 1 (100%)
		for (int i=0; i<weightArray[0].length; i++)
		{
			for (int j=0; j<weightArray[0][i].length; j++)
			{
				weightArray[0][i][j] = 1;
			}
		}
		
		//Keep track of current index of the weight string.
		int trackIndex=0;
		
		//For the weights, you got to map them. This maps middle layer.
		//System.out.println("BEGIN MAPPING MIDDLE LAYER");
		for (int i=0; i<weightArray[1].length; i++)
		{
			//System.out.print("*");
			for (int j=0; j<weightArray[1][i].length; j++)
			{
				//Start the current index, at position 0, 0 of the middle layer
				//The initial current index is 
				//[i*(length of weight array[1][i] * (lenght of each weight gene)] +
				//[j*(length of each weight gene)]
				int currentIndex = (i*weightArray[1][i].length*lengthOfEachWeightGene)+
					(j*lengthOfEachWeightGene);
				
				//The track index will become the current index
				//It will update to the last position before it is done.
				trackIndex = currentIndex;
				
				
				//System.out.print(currentIndex + "|");
				
				//Map the gene at this current position to a valuable between 0 and 1
				//Call this mapGene method, it takes in a BitChromosome from two positions,
				//this is the gene, then sets the upper boundary to 1 and lower boundary to 0.
				weightArray[1][i][j] = mapGene(
					((BitArrayChromosome)critter.getChromosome()).getGene
					(1, currentIndex, currentIndex+lengthOfEachWeightGene),0,1);
			}
		}
		
		//After this is done, add the length of the weight gene to get to the ending
		//track index to get the index of where the top layer of the weight vectors
		//starts
		trackIndex+=lengthOfEachWeightGene;
		
		//System.out.println(trackIndex);
		
		//For the weights, you got to map them. This maps top layer.
		//System.out.println("BEGIN MAPPING TOP LAYER");
		for (int i=0; i<weightArray[2].length; i++)
		{
			//System.out.print("x" + weightArray[2].length);
			
			for (int j=0; j<weightArray[2][i].length; j++)
			{
				//Start the current index by adding the trackIndex (pointer of where the program
				//was done getting the weights for the middle layer) to the rest. It is using 
				//the same mathematical equation as the mapping for middle layer.
				int currentIndex = trackIndex+(i*weightArray[2][i].length*lengthOfEachWeightGene)+
					(j*lengthOfEachWeightGene);
				
				//System.out.print(currentIndex + "|");

				//Map the gene at this current position to a valuable between 0 and 1
				//Call this mapGene method, it takes in a BitChromosome from two positions,
				//this is the gene, then sets the upper boundary to 1 and lower boundary to 0.
				weightArray[2][i][j] = mapGene(
					((BitArrayChromosome)critter.getChromosome()).getGene
					(1, currentIndex, currentIndex+lengthOfEachWeightGene),0,1);
			}
		}
		
		//DEBUG MODE
		//Prints out the values for the weight array
		/*
		for(int i=0; i<weightArray.length; i++)
		{
			for (int j=0; j<weightArray[i].length; j++)
			{
				for (int n=0; n<weightArray[i][j].length; n++)
				{
					System.out.print(weightArray[i][j][n] + "|");					
				}
				System.out.println("");
			}
			System.out.println("");
			
		}*/
		//END DEBUG MODE

		//System.out.println("BEGIN MAPPING THRESHOLDS");
		//For the thresholds, you got to map them.
		for (int i=0; i<thresholdArray.length; i++)
		{
			//System.out.print("*");
			for (int j=0; j<thresholdArray[i].length; j++)
			{
				//Get the current index, uses the same equation as the one before this 
				//for weights.
				int currentIndex = (i*weightArray[i].length*lengthOfEachThetaGene)+
					(j*lengthOfEachThetaGene);
				//System.out.print(currentIndex + "|");
				
				//Maps the gene, this time it takes the information from the 
				//Threshold part of the BitArrayChromosome and sets the lower
				//bound to -1, and upper bound to 3.
				thresholdArray[i][j] = mapGene(
					((BitArrayChromosome)critter.getChromosome()).getGene
					(2, currentIndex, currentIndex+lengthOfEachThetaGene),-1,3);
			}
		}
		
		//Create a null output array
		double[] outputArray = null;
		
		//Try to feed weights, thresholds and input array into neural network
		 try
		 {
			//Create a neural network with the critter's weights and thresholds
			 NeuralNet net = new NeuralNet(weightArray, thresholdArray);
		     
			 //Feed the input into the network and store the output
			 outputArray = net.feedForward(inputArray);
		     
			 //For every double in the output array, round up or down if 
			 //it is greater or less than 5
			 for (int i=0; i<outputArray.length; i++)
			 {
				 if (outputArray[i]>0.5)
				 {
					 outputArray[i]=1;
				 }
				 else
				 {
					 outputArray[i]=0;
				 }
			 }
		     
		     
			 //System.out.println(outputArray[0]+" "
					 //+outputArray[1]+" "+outputArray[2]); 
		 }
		 catch(Exception e)
		 {
			 System.out.println(e.getMessage());
		 }
		 
		 //Set the result to a 0
		 //depending on the output (000) to (111) it will map from 0 to 7
		 int result = 0;
		 if (outputArray[0]==1)
		 {
			 result+=4;
		 }
		 if (outputArray[1]==1)
		 {
			 result+=2;
		 }
		 if (outputArray[2]==1)
		 {
			 result+=1;
		 }
		 
		 //System.out.println("Result: " + result);
		 //Move top-left
		 if (result==0)
		 {
			 critter.setXloc(xMinusOne);
			 critter.setYloc(yMinusOne);
		 }
		 //Move top
		 else if (result==1)
		 {
			 critter.setXloc(x);
			 critter.setYloc(yMinusOne);
		 }
		 //Move top-right
		 else if (result==2)
		 {
			 critter.setXloc(xPlusOne);
			 critter.setYloc(yMinusOne);
		 }
		 //Move right
		 else if (result==3)
		 {
			 critter.setXloc(xPlusOne);
			 critter.setYloc(y);
		 }
		 //Move down-right
		 else if (result==4)
		 {
			 critter.setXloc(xPlusOne);
			 critter.setYloc(yPlusOne);
		 }
		 //Move down
		 else if (result==5)
		 {
			 critter.setXloc(x);
			 critter.setYloc(yPlusOne);
		 }
		 //Move down-left
		 else if (result==6)
		 {
			 critter.setXloc(xMinusOne);
			 critter.setYloc(yPlusOne);
		 }
		 //Move left
		 else if (result==7)
		 {
			 critter.setXloc(xMinusOne);
			 critter.setYloc(y);
		 }
		
		 checkZombieFood(environment,critter,humanPop, zombiePop);
	}
	
	/**
	 * This method will check for food at the position of the critter. It will compare
	 * the critter's x and y to the environment. If there is food, it will remove it 
	 * from the environment but also increase the human's strength.
	 * 
	 * @param foodEnvironment is a 2D int array that contains the counts of
	 * food at any place in the environment.
	 * 
	 * @param critter is the human to be checked.
	 */
	private static void checkFood(int[][] foodEnvironment, Phenotype critter)
	{
		if (foodEnvironment[critter.getXloc()][critter.getYloc()]>0)
		{
			critter.setStrength(critter.getStrength() + 
					(3*(foodEnvironment[critter.getXloc()][critter.getYloc()])));
			foodEnvironment[critter.getXloc()][critter.getYloc()]=0;
		}
		
	}
	
	/**
	 * This method will check if there is a human at the zombie's position. If there 
	 * is, the two will duel. If zombie wins, the human will be removed from the 
	 * human population and added to the zombie population. If the human wins, the 
	 * zombie gets removed from the human population.
	 * 
	 * @param environment is the data environment for the whole simulation.
	 * 
	 * @param critter is the zombie to that may duel.
	 * 
	 * @param humanPop is the human population that may be lower if the human dies.
	 * 
	 * @param zombiePop is a zombie population that may be lower if the zombie dies
	 * or increase if the human gets turned into a zombie.
	 */
	private static void checkZombieFood(Environment environment, Phenotype critter, Population humanPop, Population zombiePop)
	{
		System.out.println("BEGIN DUELING");
		
		int[][] foodEnvironment = environment.getHumanEnvironment();
		
		if (foodEnvironment[critter.getXloc()][critter.getYloc()] != 0)
		{
			System.out.println("DUELING REALLY");
			Phenotype human = environment.getHumanPhenotype(critter.getXloc(),critter.getYloc());
			
			Dueling deathmatch = new Dueling(critter, human);
			Phenotype[] duelResults = deathmatch.duelEachOther();
			
			
			//Check to see if human won, phenotype returned must have an initial gene  = 1
			//Human won
			if (((BitArrayChromosome)duelResults[0].getChromosome()).getGene(0, 0, 0).toString().equals("1"))
			{
				System.out.println("HUMAN WON");
				//System.exit(0);
				//Remove the zombie from the zombie population
				zombiePop.removePhenotype(critter);

				//Subtract the zombie phenotype from this position
				environment.subtractZombiePhenotype(critter, critter.getXloc(),critter.getYloc());
				
				//Subtract one from the zombie environment at the x and y possiton
				environment.subtractZombie(critter.getXloc(),critter.getYloc());
				
			}
			
			//if the human has not won this means the zombie has won
			else
			{
				System.out.println("ZOMBIE WON");
				//System.exit(0);
				BitArrayChromosome humanLoser = (BitArrayChromosome)duelResults[1].getChromosome().clone();
				
				//creates a new zombie from the human that lost the duel.  The new zombie keeps
				//its location and its strength value
				ZombiePhenotype previousHuman = new ZombiePhenotype(humanLoser, human.getXloc(),
				human.getYloc(), human.getStrength());
				
				//take the human which lost the duel out of the human population
				humanPop.removePhenotype(human);
				
				//removes the human from the environment.
				environment.subtractHumanPhenotype(human, human.getXloc(), human.getYloc());
				
				//removes human from the environment.
				environment.subtractHuman(human.getXloc(), human.getYloc());
				
				//adds the zombie to the environment
				environment.addZombie(previousHuman.getXloc(), previousHuman.getYloc());
				
				//adds the zombie phenotype to the environment.
				environment.addZombiePhenotype(previousHuman, previousHuman.getXloc(), previousHuman.getYloc());
				
				//adds the new zombie to the zombie population
				zombiePop.addPhenotype(previousHuman);

				critter.setStrength(critter.getStrength() + 
						(5*(foodEnvironment[critter.getXloc()][critter.getYloc()])));
				
				
			}
			System.out.println("DUELING finished");
		}
		System.out.println("DUELING finished 2");
	}
	
	/**
	 * This will check for a human at the human's new position. If the humans collide,
	 * they may breed. 
	 * 
	 * @param environment is the data environment for the whole simulation
	 * 
	 * @param critter is the human that may breed
	 * 
	 * @param population is the human population that may increase if they breed.
	 */
	private static void checkHuman(Environment environment, Phenotype critter, Population population)
	{
		//sets the crossover probability.
		double crossoverProb = (.8);
		//critter.getStrength()/70
		
		//Gets a random generator
		Random random = ALifeRandom.getRandomNumberGenerator();
		
		//Check if it will cross over
		if (random.nextDouble()< crossoverProb)
		{
			//check if there is a human at the new location
			if (environment.getHuman(critter.getXloc(), critter.getYloc())>0)
			{
				//if there is, get the parent.
				Phenotype parent2 = 
					environment.getHumanPhenotype(critter.getXloc(), critter.getYloc());
				
				//create a new crossover.
				HumanCrossover hc = 
					new HumanCrossover((critter.getStrength()+parent2.getStrength())/100);
				
				//create an array holding the new parents.
				Chromosome[] parents = new Chromosome[2];
				parents[0]=critter.getChromosome();
				parents[1] = parent2.getChromosome();
				
				//try to perform the crossover
				Chromosome[] spawn = hc.performOperation(parents);
				if(spawn != null)
				{
					Phenotype pheno = new Phenotype(spawn[0], critter.getXloc(),critter.getYloc(),25);
				
					population.add(pheno);
					environment.addHuman(critter.getXloc(),critter.getYloc());
					environment.addHumanPhenotype(pheno, critter.getXloc(),critter.getYloc());
				}
				
			}
		}
		
	}
	
	/**
	 * This method will map the gene a given value between the low and upper bound.
	 * 
	 * @param gene is the gene that will be mapped.
	 * 
	 * @param lowerBound is the lower bound of the mapping.
	 * 
	 * @param upperBound is the upper bound of the mapping.
	 * 
	 * @return a mapped double value for either weights or theta.
	 */
	private static double mapGene(Chromosome gene, 
				double lowerBound, double upperBound)
		{
			//Executes the given formula in the method comment
			BigDecimal chromAsBase10 = ((BitChromosome)gene).toBigDecimal();
			BigDecimal range = new BigDecimal(upperBound - lowerBound);
			BigDecimal numerator = chromAsBase10.multiply(range);
			BigDecimal two = new BigDecimal(2); 
			BigDecimal denominator = two.pow(((BitChromosome)gene).length());
			denominator = denominator.subtract(new BigDecimal(1));

			BigDecimal fraction = numerator.divide(denominator, new MathContext(6));
			fraction = fraction.add(new BigDecimal(lowerBound));
			//System.out.println(fraction.doubleValue());
			return fraction.doubleValue();
		}

}

