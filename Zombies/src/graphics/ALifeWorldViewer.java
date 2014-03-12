package graphics;

/**
 * This is the simplest available class for displaying artificial life graphics.
 * Start with this class, and if you desire more flexibility (for example, the
 * ability to specify your own images), then replace this class with the more
 * flexible <code>StandardWorldViewer</code>.
 * <p>
 * Students of software engineering will appreciate that this class uses the
 * Facade design pattern to hide more complicated details of the
 * <code>StandardWorldViewer</code> class. (You might argue that it is also an
 * Adapter pattern because it composed of the <code>StandardWorldViewer</code>;
 * but it doesn’t really change or adapt anything.)
 * <p>
 * This class provides a graphics window that displays artificial life critters
 * using a "standard" simulation with critters, food, and walls. Default images
 * are used for the critters, food, and walls. This code does not keep track of
 * the location of critters or food and is strictly designed to display images;
 * users will have to write separate code that keeps track of the critter
 * locations, food locations, etc.
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
 * ALifeWorldViewer world = new ALifeWorldViewer(10, 10);
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
 * This code was developed for classroom use at Regis University. Any other use
 * is prohibited without permission from the faculty member and author, David
 * Bahr.
 * 
 * @author David Bahr, Robert Winchell
 */
public class ALifeWorldViewer
{
    //-------------------------------------------------
    //instance variables

    //The graphics class.
    private StandardWorldViewer world = null;

    /**
     * Creates a default-size grid with the specified number of cells on which
     * the critters can be displayed.
     * 
     * @param numRows
     *            The number of rows (the number of grid cells in the vertical
     *            direction).
     * @param numCols
     *            The number of columns (the number of grid cells in the
     *            horizontal direction).
     */
    public ALifeWorldViewer(int numRows, int numCols)
    {
        world = new StandardWorldViewer(numRows, numCols);
    }

    /**
     * Creates a grid with the specified number of cells on which the critters
     * can be displayed.
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
    public ALifeWorldViewer(int numRows, int numCols,
        int frameWidth, int frameHeight)
    {
        world = new StandardWorldViewer(numCols, numRows,
            frameWidth, frameHeight);
    }

    //-------------------------------------------------
    //public methods

    /**
     * Adds a critter to the specified position on the graphic window's grid.
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
        world.addCritterToGrid(row, col);
    }

    /**
     * Adds food to the specified position on the graphic window's grid.
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
        world.addFoodToGrid(row, col);
    }

    /**
     * Adds a wall to the specified position on the graphic window's grid.
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
        world.addWallToGrid(row, col);
    }

    /**
     * Delays the program for the specified number of milliseconds. Useful when
     * the graphics are moving too fast.
     * 
     * @param timeDelay
     *            The delay in milliseconds.
     */
    public void delay(long timeDelay)
    {
        world.delay(timeDelay);
    }

    /**
     * Updates the graphics window to reflect any changes.
     */
    public void redisplay()
    {
        world.redisplay();
    }

    /**
     * Removes the shape displayed at the specified position on the graphic
     * window's grid.
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
    public void removeImageFromGrid(int row, int col)
    {
        world.removeImageFromGrid(row, col);
    }
}
