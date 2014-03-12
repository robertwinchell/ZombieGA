package graphics;

import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.awt.Shape;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * This class provides a graphics window that displays artificial life critters
 * using a "standard" simulation with critters, food, and walls. Default images
 * are used for the critters, food, and walls unless otherwise set. This code
 * does not keep track of the location of critters or food and is strictly
 * designed to display images; users will have to write separate code that keeps
 * track of the critter locations, food locations, etc. A simpler graphics class
 * is available at <code>ALifeWorldViewer</code>.
 * <p>
 * By calling <code>addCritterToGrid</code>,<code>addFoodToGrid</code>,
 * or <code>addWallToGrid</code>, the correct image is displayed at the
 * specified location. The method <code>removeImageFromGrid</code> will remove
 * any image (critter, food, or wall) from the specified location.
 * <p>
 * Standard usage:
 * <p>
 * 
 * <pre>
 * StandardWorldViewer world = new StandardWorldViewer(10, 10);
 * world.addCritterToGrid(5, 7);
 * world.addFoodToGrid(5, 8);
 * redisplay();
 * 
 * //now move the shape one cell to the right towards the food
 * world.removeImageFromGrid(5, 7);
 * world.addCritterToGrid(5, 8);
 * redisplay();
 * </pre>
 * 
 * <p>
 * Changes to the graphics will not be shown until redisplay is called.
 * <p>
 * Wrap-around boundary conditions are assumed. So
 * <code>addCritterToGrid(9, 10)</code> on a 10 by 10 grid will add the image
 * to coordinates (9, 0).
 * <p>
 * Note that <code>addCritterToGrid</code>,<code>addFoodToGrid</code>,
 * and <code>addWallToGrid</code> will remove any image that happens to
 * already be at that position. This code is just like a chalkboard. Suppose a
 * banana is drawn on a chalkboard, and then later an apple needs to be drawn in
 * the same place. To draw the apple, we will first have to erase the banana.
 * <p>
 * Again, this code does NOT keep track of the position of food, walls, etc.
 * This code is only a tool for displaying images. Continuing with the previous
 * analogy, a chalkboard displays images, but a chalkboard does not keep track
 * of what was drawn.
 * <p>
 * 
 * @author David Bahr and Robert Winchell
 */
public class ZombieApocalypseWorldViewer extends StandardWorldViewer
{
	private StandardWorldViewer world = null;
	
    //-------------------------------------------------
    //constants

    //colors of the default shapes
    private static final Color DEFAULT_CRITTER_COLOR = Color.RED;

    private static final Color DEFAULT_FOOD_COLOR = Color.GREEN;

    private static final Color DEFAULT_WALL_COLOR = Color.BLACK;

    //-------------------------------------------------
    //instance variables

    //shapes and images for critter's, wall's, and food.
    private Picture critterPicture = null;

    private Picture foodPicture = null;

    private Picture wallPicture = null;

    //-------------------------------------------------
    //constructors

    /**
     * Creates a default-size grid with the specified number of cells on which
     * the critters can be displayed and moved.
     * 
     * @param numRows
     *            The number of rows (the number of grid cells in the vertical
     *            direction).
     * @param numCols
     *            The number of columns (the number of grid cells in the
     *            horizontal direction).
     */
    public ZombieApocalypseWorldViewer(int numRows, int numCols)
    {
        this(numRows, numCols, GridGraphics.DEFAULT_FRAME_WIDTH,
            GridGraphics.DEFAULT_FRAME_HEIGHT);
    }

    /**
     * Creates a grid with the specified number of cells on which the critters
     * can be displayed and moved.
     * 
     * @param numRows
     *            The number of rows (the number of grid cells in the vertical
     *            direction).
     * @param numCols
     *            The number of columns (the number of grid cells in the
     *            horizontal direction).
     * @param frameWidth
     *            The width of the frame in pixels.
     * @param frameHeight
     *            The height of the frame in pixels.
     */
    public ZombieApocalypseWorldViewer(int numRows, int numCols, int frameWidth,
        int frameHeight)
    {
        super(numRows, numCols, frameWidth, frameHeight);

        setTitle("Artificial Life Simulation");
        
        //create the default shapes for critters, etc.
        createDefaultShapes();
    }

    //-------------------------------------------------
    //private methods

    /**
     * Create default shapes for critters, food, and walls. These will be used
     * to fill the cells when there is no user specified image or shape.
     */
    private void createDefaultShapes()
    {
        //CRITTER
        //default shape for a critter is a circle
        Ellipse2D.Double circle = new Ellipse2D.Double();
        circle.setFrameFromDiagonal(getCellWidth() / 10.0 + getLeftInset(),
            getCellHeight() / 10.0 + getTopInset(), 9.0 * getCellWidth() / 10.0
                - getRightInset(), 9.0 * getCellHeight() / 10.0
                - getBottomInset());

        critterPicture = new Picture(circle, DEFAULT_CRITTER_COLOR);

        //FOOD
        //default shape for food is a small square
        Rectangle2D.Double smallSquare = new Rectangle2D.Double();
        smallSquare.setFrameFromDiagonal(getCellWidth() / 4.0 + getLeftInset(),
            getCellHeight() / 4.0 + getTopInset(), 3.0 * getCellWidth() / 4.0
                - getRightInset(), 3.0 * getCellHeight() / 4.0
                - getBottomInset());

        foodPicture = new Picture(smallSquare, DEFAULT_FOOD_COLOR);

        //WALL
        //default shape for a wall is a rectangle
        Rectangle2D.Double bigRectangle = new Rectangle2D.Double();
        bigRectangle.setFrameFromDiagonal(0.0 + getLeftInset(),
            0.0 + getTopInset(), (double) getCellWidth() - getRightInset(),
            (double) getCellHeight() - getBottomInset());

        wallPicture = new Picture(bigRectangle, DEFAULT_WALL_COLOR);
    }

    //-------------------------------------------------
    //public methods

    /**
     * Adds a critter to the specified position on the graphic window's grid. If
     * an image for the critter has not been specified, a default shape is used.
     * <p>
     * The change is not displayed until <code>redisplay</code> is called.
     * 
     * @param row
     *            Vertical position as measured from the top. For example, 4
     *            would be five grid cells downwards from the top (start
     *            counting from 0).
     * @param col
     *            Horizontal position as measured from the left. For example, 3
     *            would be four grid cells to the right of the left side (start
     *            counting from 0).
     */
    
    public void addCritterToGrid(int row, int col)
    {
        boolean rescale = false;
        addPictureToGrid(row, col, critterPicture, rescale);
    }
    /**
     * Adds a zombie to the specified position on the graphic window's grid. If
     * an image for the critter has not been specified, a default shape is used.
     * <p>
     * The change is not displayed until <code>redisplay</code> is called.
     * 
     * @param row
     *            Vertical position as measured from the top. For example, 4
     *            would be five grid cells downwards from the top (start
     *            counting from 0).
     * @param col
     *            Horizontal position as measured from the left. For example, 3
     *            would be four grid cells to the right of the left side (start
     *            counting from 0).
     */
    public void addZombieToGrid(int row, int col)
    {
        boolean rescale = false;
        addPictureToGrid(row, col, wallPicture, rescale);
    }


    /**
     * Adds food to the specified position on the graphic window's grid. If an
     * image for the food has not been specified, a default shape is used.
     * <p>
     * The change is not displayed until <code>redisplay</code> is called.
     * 
     * @param row
     *            Vertical position as measured from the top. For example, 4
     *            would be five grid cells downwards from the top (start
     *            counting from 0).
     * @param col
     *            Horizontal position as measured from the left. For example, 3
     *            would be four grid cells to the right of the left side (start
     *            counting from 0).
     */
    public void addFoodToGrid(int row, int col)
    {
        boolean rescale = false;
        addPictureToGrid(row, col, foodPicture, rescale);
    }

    /**
     * Adds a wall to the specified position on the graphic window's grid. If an
     * image for the wall has not been specified, a default shape is used.
     * <p>
     * The change is not displayed until <code>redisplay</code> is called.
     * 
     * @param row
     *            Vertical position as measured from the top. For example, 4
     *            would be five grid cells downwards from the top (start
     *            counting from 0).
     * @param col
     *            Horizontal position as measured from the left. For example, 3
     *            would be four grid cells to the right of the left side (start
     *            counting from 0).
     */
    public void addWallToGrid(int row, int col)
    {
        boolean rescale = false;
        addPictureToGrid(row, col, wallPicture, rescale);
    }
   


    /**
     * Sets the image for a critter. The image is automatically scaled to the
     * correct size.
     * <p>
     * Critters already added to the graphics will keep their old image or
     * shape. Any new critters subsequently added with
     * <code>addCritterToGrid</code> will use this new image.
     * 
     * @param i
     *            The image displayed for a critter.
     */
    public void setCritterImage(Image i)
    {
        //rescale the image to fit the cells
        critterPicture = new Picture(RescaleTransform.resize(i,
            getAdjustedCellWidth(), getAdjustedCellHeight()));
    }

    /**
     * Sets the shape displayed for a critter. The shape is automatically scaled
     * to the correct size.
     * <p>
     * Critters already added to the graphics will keep their old image or
     * shape. Any new critters subsequently added with
     * <code>addCritterToGrid</code> will use this new shape.
     * 
     * @param s
     *            The shape displayed for a critter.
     * @param c
     *            The color of the shape.
     */
    public void setCritterImage(Shape s, Color c)
    {
        //rescale the shape to fit the cells
        critterPicture = new Picture(RescaleTransform.resize(s,
            getAdjustedCellWidth(), getAdjustedCellHeight()), c);
    }

    /**
     * Sets the image for a critter from the specified file path. The image is
     * automatically scaled to the correct size.
     * <p>
     * Critters already added to the graphics will keep their old image or
     * shape. Any new critters subsequently added with
     * <code>addCritterToGrid</code> will use this new image.
     * 
     * @param filePath
     *            The path to the critter's image.
     */
    public void setCritterImage(String filePath)
    {
        Image critterImage = null;

        try
        {
            critterImage = ImageIO.read(new File(filePath));
        }
        catch(IOException fileProblem)
        {
            System.out
                .println("Class: StandardWorldViewer. Method: setCritterImage. "
                    + "Could not load the file " + filePath + ".");
            fileProblem.printStackTrace();
        }

        //rescale the image to fit the cells
        critterPicture = new Picture(RescaleTransform.resize(critterImage,
            getAdjustedCellWidth(), getAdjustedCellHeight()));
    }

    /**
     * Sets the image for food. The image is automatically scaled to the correct
     * size.
     * <p>
     * Food already added to the graphics will keep its old image or shape. Any
     * new food subsequently added with <code>addFoodToGrid</code> will use
     * this new image.
     * 
     * @param i
     *            The image displayed for food.
     */
    public void setFoodImage(Image i)
    {
        //rescale the image to fit the cells
        foodPicture = new Picture(RescaleTransform.resize(i,
            getAdjustedCellWidth(), getAdjustedCellHeight()));
    }

    /**
     * Sets the shape displayed for food. The shape is automatically scaled to
     * the correct size.
     * <p>
     * Food already added to the graphics will keep its old image or shape. Any
     * new food subsequently added with <code>addFoodToGrid</code> will use
     * this new shape.
     * 
     * @param s
     *            The shape displayed for food.
     */
    public void setFoodImage(Shape s, Color c)
    {
        //rescale the shape to fit the cells
        foodPicture = new Picture(RescaleTransform.resize(s,
            getAdjustedCellWidth(), getAdjustedCellHeight()), c);
    }

    /**
     * Sets the image for food from the specified file path. The image is
     * automatically scaled to the correct size.
     * <p>
     * Food already added to the graphics will keep its old image or shape. Any
     * new food subsequently added with <code>addFoodToGrid</code> will use
     * this new image.
     * 
     * @param filePath
     *            The path to the food's image.
     */
    public void setFoodImage(String filePath)
    {
        Image foodImage = null;

        try
        {
            foodImage = ImageIO.read(new File(filePath));
        }
        catch(IOException fileProblem)
        {
            System.out
                .println("Class: StandardWorldViewer. Method: setFoodImage. "
                    + "Could not load the file " + filePath + ".");
            fileProblem.printStackTrace();
        }

        //rescale the image to fit the cells
        foodPicture = new Picture(RescaleTransform.resize(foodImage,
            getAdjustedCellWidth(), getAdjustedCellHeight()));
    }

    /**
     * Sets the image for a wall. The image is automatically scaled to the
     * correct size.
     * <p>
     * Walls already added to the graphics will keep their old image or shape.
     * Any new walls subsequently added with <code>addWallToGrid</code> will
     * use this new image.
     * 
     * @param i
     *            The image displayed for a wall.
     */
    public void setWallImage(Image i)
    {
        //rescale the image to fit the cells
        wallPicture = new Picture(RescaleTransform.resize(i,
            getAdjustedCellWidth(), getAdjustedCellHeight()));
    }

    /**
     * Sets the shape for a wall. The shape is automatically scaled to the
     * correct size.
     * <p>
     * Walls already added to the graphics will keep their old image or shape.
     * Any new walls subsequently added with <code>addWallToGrid</code> will
     * use this new shape.
     * 
     * @param s
     *            The shape displayed for a wall.
     */
    public void setWallImage(Shape s, Color c)
    {
        //rescale the shape to fit the cells
        wallPicture = new Picture(RescaleTransform.resize(s,
            getAdjustedCellWidth(), getAdjustedCellHeight()), c);
    }

    /**
     * Sets the image for a wall from the specified file path. The image is
     * automatically scaled to the correct size.
     * <p>
     * Walls already added to the graphics will keep their old image or shape.
     * Any new walls subsequently added with <code>addWallToGrid</code> will
     * use this new image.
     * 
     * @param filePath
     *            The path to the wall's image.
     */
    public void setWallImage(String filePath)
    {
        Image wallImage = null;

        try
        {
            wallImage = ImageIO.read(new File(filePath));
        }
        catch(IOException fileProblem)
        {
            System.out
                .println("Class: StandardWorldViewer. Method: setWallImage. "
                    + "Could not load the file " + filePath + ".");
            fileProblem.printStackTrace();
        }

        //rescale the image to fit the cells
        wallPicture = new Picture(RescaleTransform.resize(wallImage,
            getAdjustedCellWidth(), getAdjustedCellHeight()));
    }
    public void removeImageFromGrid(int row, int col)
    {
       super.removeImageFromGrid(row, col);
    }
    
}
