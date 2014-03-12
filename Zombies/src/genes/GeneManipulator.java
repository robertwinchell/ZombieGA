package genes;  
 

import java.util.ArrayList;

import java.util.Random;

import chromosome.*;

import numbers.*;



/**

 * Performs a series of genetic operations like crossover and mutation. Use this

 * class when you want a series of genetic operations performed. Add each

 * GeneticOperator (like Mutation and Crossover) one by one. Then when

 * "performGeneticOperations" is called, each GeneticOperator will be performed

 * in the order given (by calling its "performOperation" method). The output of

 * each GeneticOperator is fed as input into the next GeneticOperator.

 * <p>

 * Each genetic operation is handled by another class (for example, a Crossover

 * class and/or a Mutation class) that is called from this class.

 * <p>

 * This class uses a variant of the Observer and Decorator patterns to chain

 * together a series of GeneticOperators. This class acts as the Observable, and

 * each GeneticOperator class is an Observer. The GeneticOperator's method

 * "performOperation" acts as the observer pattern's "update" method. The method

 * "performGeneticOperations" in this class acts as the observer pattern's

 * "notify". The "performGeneticOperations" also treats each GeneticOperator as

 * a Decorator, effectively chaining the output of one GeneticOperator into the

 * input of the next GeneticOperator.

 * <p>

 * The order in which the series of genetic operations are performed is given by

 * the order in which they are added.

 * 

 * @author Robert Winchell, Allan Dancer, David Bahr

 */

import java.util.ArrayList;
import java.util.Random;
import chromosome.*;

/**
 * Performs a series of genetic operations like crossover and mutation. Use this
 * class when you want a series of genetic operations performed. Add each
 * GeneticOperator (like Mutation and Crossover) one by one. Then when
 * "performGeneticOperations" is called, each GeneticOperator will be performed
 * in the order given (by calling its "performOperation" method). The output of
 * each GeneticOperator is fed as input into the next GeneticOperator.
 * <p>
 * Each genetic operation is handled by another class (for example, a Crossover
 * class and/or a Mutation class) that is called from this class.
 * <p>
 * This class uses a variant of the Observer and Decorator patterns to chain
 * together a series of GeneticOperators. This class acts as the Observable, and
 * each GeneticOperator class is an Observer. The GeneticOperator's method
 * "performOperation" acts as the observer pattern's "update" method. The method
 * "performGeneticOperations" in this class acts as the observer pattern's
 * "notify". The "performGeneticOperations" also treats each GeneticOperator as
 * a Decorator, effectively chaining the output of one GeneticOperator into the
 * input of the next GeneticOperator.
 * <p>
 * The order in which the series of genetic operations are performed is given by
 * the order in which they are added.
 * 
 * @author David Bahr, Allan Dancer
 */

public class GeneManipulator

{

    //The genetic operators that will be used by the 
	//performGeneticOperations method.

    private ArrayList operators = new ArrayList();

 

    //The probability that any genetic operations will 
    //happen. Sometimes it is useful to not perform any 
    //genetic operations (just let the chromosomes
    //pass through unchanged). This is usually called the
    //crossover probability in the literature, but the 
    //literature's choice of name is poor, because
    //genetic operations do not have to be crossover!

    private double probabilityOfOperations = 1.0;

 

    /**
     * Creates a GeneticManipulator that performs crossover, mutation, or any
     * other specified GeneticOperations.
     * 
     * @param probabilityOfOperations
     *            The probability that any genetic operations will happen
     *            (sometimes it is useful to not perform any genetic operations
     *            and just let the chromosomes pass through unchanged). A 0.0
     *            means operation never happen, and a 1.0 means operations
     *            always happen. If the value is outside this range, then the
     *            default is 1.0 (operations always happen). Note: This
     *            parameter is usually called the crossover probability in the
     *            literature, but the literature's choice of name is poor,
     *            because genetic operations do not have to be crossover!
     */

    public GeneManipulator(double probabilityOfOperations)
    {
    	this.probabilityOfOperations = probabilityOfOperations;
    }

 

    /**
     * Creates a GeneticManipulator that performs crossover, mutation, or any
     * other specified GeneticOperations.
     * 
     * @param probabilityOfOperations
     *            The probability that any genetic operations will happen
     *            (sometimes it is useful to not perform any genetic operations
     *            and just let the chromosomes pass through unchanged). A 0.0
     *            means operation never happen, and a 1.0 means operations
     *            always happen. If the value is outside this range, then the
     *            default is 1.0 (operations always happen). Note: This
     *            parameter is usually called the crossover probability in the
     *            literature.
     * @param geneticOperators
     *            Genetic operators that will be added in the order that they are
     *            given. May be null.
     */
    
    public GeneManipulator(double probabilityOfOperations,

        GeneticOperator[] geneticOperators)
    {
    	this.probabilityOfOperations = probabilityOfOperations;
    	
    	//adds all of the passed in genetic operators to the
    	//operators Arraylist.
    	for(int i = 0; i < geneticOperators.length; i++)
    	{
    		this.operators.add(geneticOperators[i]);
    	}
    }

    /**
     * Adds a genetic operator to the list of operations that should be
     * performed when the method performGeneticOperations is called. The order
     * in which the series of genetic operations are performed is given by the
     * order in which they are added here.
     * 
     * @param operator
     *            A genetic operation that will be performed when the method
     *            performGeneticOperations is called.
     */

    public void addGeneticOperator(GeneticOperator operator)

    {
    	this.operators.add(operator);

    }

 

    /**
     * Removes all GeneticOperators from the list. If "performGeneticOperations"
     * is called immediately after this method, then no operations will be
     * performed.
     */
    
    public void clearAllGeneticOperators()
    {
    	ArrayList nothing = new ArrayList();
    	this.operators = nothing;
    }

 

    /**
     * Removes a genetic operator from the list of operations that will be
     * performed when the method performGeneticOperations is called.
     * 
     * @param operator
     *            The genetic operation that will be removed from the list of
     *            operations performed by the method "performGeneticOperations".
     */

    public void removeGeneticOperator(GeneticOperator operator)
    {
    	this.operators.remove(operator);
    }

 

    /**
     * Performs a series of genetic operations on the specified chromosomes.
     * The genetic operations are added by the "addGeneticOperator" method. The
     * operations are performed in the order in which they were added. If no
     * GeneticOperators have been added (or if they are all removed or cleared),
     * then no operations are performed by this method.
     * 
     * @param chromosomes
     *            The chromosomes on which the genetic operations are performed.
     * 
     * @return An array of one or more chromosomes that are the result of the
     *         genetic operators.
     */
    public Chromosome[] performGeneticOperations(Chromosome[] chromosomes)
    {
    	//traverses through the operators array and for each
    	//operator in there it calls the operator to do its
    	//operation.
    	for(int i = 0; i < this.operators.size(); i++)
    	{
    		GeneticOperator opperator = (GeneticOperator)this.operators.get(i);
    		System.out.println(chromosomes);
    		System.out.println(chromosomes[0]);
    		System.out.println(opperator);
    		chromosomes = opperator.performOperation(chromosomes);
    	}
    	
    	return chromosomes;
    }

}
