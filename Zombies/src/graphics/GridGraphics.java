package graphics;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.border.*;
import javax.swing.BorderFactory;
import javax.swing.JFrame;

/**
 * A generic graphics window that displays images and shapes on a grid and
 * allows them to be moved from one site to another. No default images (or
 * shapes) are supplied. This code is fairly generic and will work with many
 * graphics programs including cellular automata simulations and complex
 * artificial life simulations (beyond the standard "critters, walls, and food"
 * simulation that is handled more simply by the child class
 * <code>StandardWorldViewer</code>) and even more simply by
 * <code>ALifeWorldViewer</code>.
 * <p>
 * Standard usage:
 * <p>
 * 
 * <pre>
 * 
 * //create a circle that will be rescaled automatically 
 * //to the size of the grid
 * boolean rescale = true;
 * Ellipse2D.Double circle = new Ellipse2D.Double();
 * circle.setFrameFromDiagonal(0.0, 0.0, 1.0, 1.0);
 * Picture circlePicture = new Picture(circlePicture, Color.BLUE);
 * 
 * GridGraphics world = new GridGraphics(10, 10);
 * world.addPictureToGrid(5, 7, circlePicture, rescale);
 * redisplay();
 * 
 * //now move the shape one cell downwards
 * world.removeImageFromGrid(5, 7);
 * world.addPictureToGrid(5, 8, circlePicture, rescale);
 * redisplay();
 * </pre>
 * 
 * <p>
 * Changes to the graphics will not be shown until <code>redisplay</code> is
 * called.
 * <p>
 * Wrap-around boundary conditions are assumed. So
 * <code>addPictureToGrid(9, 10, circlePicture, true)</code> on a 10 by 10
 * grid will put the image at coordinates (9, 0).
 * <p>
 * This code was developed for classroom use at Regis University. Any other use
 * is prohibited without permission from the faculty member and author, David
 * Bahr.
 * 
 * @author Allan Dancer, David Bahr
 */
public class GridGraphics
{
    //-------------------------------------------------
    //constants

    /**
     * The default height of the graphics frame in pixels.
     */
    public static final int DEFAULT_FRAME_HEIGHT = 600;

    /**
     * The default width of the graphics frame in pixels.
     */
    public static final int DEFAULT_FRAME_WIDTH = 600;

    //colors of the frame
    private static final Color DEFAULT_BACKGROUND_COLOR = Color.LIGHT_GRAY;

    private static final Color DEFAULT_BORDER_COLOR = Color.BLUE;

    //style of the cell borders
    private static final Border DEFAULT_BORDER_STYLE = BorderFactory
        .createLineBorder(DEFAULT_BORDER_COLOR);

    private static final String DEFAULT_TITLE = "Grid Graphics";

    //-------------------------------------------------
    //instance variables

    //measured in pixels
    private int cellWidth;

    private int cellHeight;

    private int frameWidth;

    private int frameHeight;

    //number of cells
    private int numHorizontalCells;

    private int numVerticalCells;

    //colors of the frame
    private Color backgroundColor = DEFAULT_BACKGROUND_COLOR;

    private Color borderColor = DEFAULT_BORDER_COLOR;

    //the border style
    private Border borderStyle = DEFAULT_BORDER_STYLE;

    //the panels that hold each cell's image
    private ImagePanel[][] cellPanel = null;

    private Container contentPane = null;

    private JFrame viewFrame = null;

    //Images will have to be scaled to the correct size.
    //This requires information about how many pixels the
    //borders of each cell require. These number of pixels
    //is called the inset. Set in the constructor. These
    //values are the inset for each individual cell.
    /**
     * The number of pixels required by the bottom border of a grid cell.
     */
    protected double bottomInset;

    /**
     * The number of pixels required by the left border of a grid cell.
     */
    protected double leftInset;

    /**
     * The number of pixels required by the right border of a grid cell.
     */
    protected double rightInset;

    /**
     * The number of pixels required by the top border of a grid cell.
     */
    protected double topInset;

    //-------------------------------------------------
    //constructors

    /**
     * Creates a grid with the specified number of cells on which the images and
     * shapes can be displayed and moved.
     * 
     * @param numRows
     *            The number of rows (the number of grid cells in the vertical
     *            direction).
     * @param numCols
     *            The number of columns (the number of grid cells in the
     *            horizontal direction).
     */
    public GridGraphics(int numRows, int numCols)
    {
        this(numRows, numCols, DEFAULT_FRAME_WIDTH, DEFAULT_FRAME_HEIGHT);
    }

    /**
     * Creates a grid with the specified number of cells on which the images and
     * shapes can be displayed and moved.
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
    public GridGraphics(int numRows, int numCols, int frameWidth,
        int frameHeight)
    {
        //set instance variables
        this.numHorizontalCells = numCols;
        this.numVerticalCells = numRows;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;

        //Note integer division. Also note that this won't give
        //exactly square grid cells, because the border of the frame
        //will reserve roughly 23 pixels on the top and 4 pixels on
        //the other sides.
        cellWidth = frameWidth / numHorizontalCells;
        cellHeight = frameHeight / numVerticalCells;

        //create the frame
        createFrame();

        //Add each cell's panel to the content pane of the frame.
        //These cells hold an image.
        addCellPanelsToFrame();

        //make the graphics visible
        viewFrame.setVisible(true);

        //calculate insets for proper scaling of image sizes.
        //this must be called after the Jframe is set visible.
        //Otherwise, it doesn't know how much space was taken
        //by the borders.
        calculateCellInsetsForScalingImages();
    }

    //-------------------------------------------------
    //private methods

    /**
     * Each image (or shape) lives in a panel. This creates and adds the panels
     * to the frame.
     */
    private void addCellPanelsToFrame()
    {
        //note that grid layout expects, rows, columns
        contentPane.setLayout(new GridLayout(numVerticalCells,
            numHorizontalCells));

        //create each panel that holds an image or shape
        cellPanel = new ImagePanel[numVerticalCells][numHorizontalCells];
        for(int i = 0; i < numVerticalCells; i++)
        {
            for(int j = 0; j < numHorizontalCells; j++)
            {
                cellPanel[i][j] = new ImagePanel(cellWidth, cellHeight);
                cellPanel[i][j].setBackground(backgroundColor);

                //set a border for the panel
                cellPanel[i][j].setBorder(borderStyle);

                //add the cell to the GridLayout
                contentPane.add(cellPanel[i][j]);
            }
        }
    }

    /**
     * This calculates the space (in pixels) taken by each cell's border.
     */
    private void calculateCellInsetsForScalingImages()
    {
        //Each cell's border takes up space, so we get the number
        //of pixels that the shape must be inset to avoid this
        //border. All cells are the same, so this only need to get
        //the insets for one cell.
        Insets borderInsets = getCell(0, 0).getInsets();
        bottomInset = borderInsets.bottom;
        leftInset = borderInsets.left;
        rightInset = borderInsets.right;
        topInset = borderInsets.top;

        //the JFrame's border also takes up space, so we have to
        //adjust for that as well. Notice that only a fraction of
        //the JFrame's border spacing affects each cell. Note
        //the special attention payed to casting to doubles before
        //division. It makes a big difference!
        Insets frameInsets = viewFrame.getInsets();
        bottomInset += Math.ceil((frameInsets.top + frameInsets.bottom)
            / ((double) getNumVerticalCells()));
        rightInset += Math.ceil((frameInsets.left + frameInsets.right)
            / ((double) getNumHorizontalCells()));
    }

    /**
     * Creates the graphics frame, adds the window listener, etc.
     */
    private void createFrame()
    {
        viewFrame = new JFrame();
        viewFrame.setTitle(DEFAULT_TITLE);
        viewFrame.setSize(frameWidth, frameHeight);
        viewFrame.setBackground(backgroundColor);
        viewFrame.addWindowListener(new WindowEventListener());

        //make it impossible to resize the frame. Otherwise, too
        //much trouble to resize the images and shapes in the
        //GridLayout placed on the content pane.
        viewFrame.setResizable(false);

        contentPane = viewFrame.getContentPane();
    }

    /**
     * Applies wrap-around boundary conditions to make sure that parameter n
     * falls within 0 to m-1. In other words, n modulo m (plus m) if the result
     * is less than zero).
     * 
     * @param n
     *            The number to be wrapped.
     * @param m
     *            The size of the array on which n is wrapped.
     */
    private int wrapAround(int n, int m)
    {
        n %= m;
        if(n < 0)
        {
            n += m;
        }
        return n;
    }

    //-------------------------------------------------
    //protected methods

    /**
     * Calculates the height of a cell adjusted to exclude the cell borders. The
     * cell borders take up space, as do the JFrame's borders.
     * 
     * @return The height in pixels, minus the space taken by borders.
     */
    protected int getAdjustedCellHeight()
    {
        return (int) Math.round(getCellHeight() - topInset - bottomInset);
    }

    /**
     * Calculates the width of a cell adjusted to exclude the cell borders. The
     * cell borders take up space, as do the JFrame's borders.
     * 
     * @return The width in pixels, minus the space taken by Borders.
     */
    protected int getAdjustedCellWidth()
    {
        return (int) Math.round(getCellWidth() - rightInset - leftInset);
    }

    /**
     * The number of pixels each cell is inset from the bottom due to the
     * border.
     * 
     * @return the number of pixels each cell is inset from the bottom.
     */
    protected double getBottomInset()
    {
        return bottomInset;
    }

    /**
     * The number of pixels each cell is inset from the left due to the border.
     * 
     * @return the number of pixels each cell is inset from the left.
     */
    protected double getLeftInset()
    {
        return leftInset;
    }

    /**
     * The number of pixels each cell is inset from the right due to the border.
     * 
     * @return the number of pixels each cell is inset from the right.
     */
    protected double getRightInset()
    {
        return rightInset;
    }

    /**
     * The number of pixels each cell is inset from the top due to the border.
     * 
     * @return the number of pixels each cell is inset from the top.
     */
    protected double getTopInset()
    {
        return topInset;
    }

    //-------------------------------------------------
    //public methods

    /**
     * Adds an image to the specified position on the graphic window's grid.
     * When rescale is set to true, the image is automatically scaled to the
     * correct size for the grid.
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
     * @param p
     *            The picture to be displayed.
     * @param rescale
     *            Rescales the image to fit the grid cell when true.
     */
    public void addPictureToGrid(int row, int col, Picture p, boolean rescale)
    {
        //wraparound boundary conditions
        row = wrapAround(row, numVerticalCells);
        col = wrapAround(col, numHorizontalCells);

        //rescale the image to fit the cells
        if(rescale)
        {
            p = RescaleTransform.resize(p, getAdjustedCellWidth(),
                getAdjustedCellHeight());
        }

        //add to the graphics
        cellPanel[row][col].setPicture(p);
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
        //ok, so this is a cheap solution, but
        //I didn't want the hassle of threading the graphics
        //and having to deal with notifications of changes
        //to the graphics that someone elses code might make
        //while this method was delayed. This way,
        //no synchronization is required.
        long time = System.currentTimeMillis();
        while(System.currentTimeMillis() < time + timeDelay)
        {
        }
    }

    /**
     * Gets the background color of the graphics window.
     * 
     * @return The background color.
     */
    public Color getBackgroundColor()
    {
        return backgroundColor;
    }

    /**
     * Gets the cell at position row, col.
     * 
     * @param row
     *            Vertical position as measured from the top. For example, 4
     *            would be five grid cells downwards from the top (start
     *            counting from 0).
     * @param col
     *            Horizontal position as measured from the left. For example, 3
     *            would be four grid cells to the right of the left side (start
     *            counting from 0).
     * 
     * @return The cell at the specified position.
     */
    public ImagePanel getCell(int row, int col)
    {
        return cellPanel[row][col];
    }

    /**
     * Gets the border used by each cell in the window.
     * 
     * @return The border style used by each cell.
     */
    public Border getCellBorder()
    {
        return borderStyle;
    }

    /**
     * Gets the border color of each cell in the window.
     * 
     * @return The border color.
     */
    public Color getCellBorderColor()
    {
        return borderColor;
    }

    /**
     * Each cell has the same height, which this returns.
     * 
     * @return The cell height in pixels.
     */
    public int getCellHeight()
    {
        return cellHeight;
    }

    /**
     * Each cell has the same width, which this returns.
     * 
     * @return The cell width in pixels.
     */
    public int getCellWidth()
    {
        return cellWidth;
    }

    /**
     * Gets the frame that contains all the graphics, (including the grid
     * cells).
     * 
     * @return The graphics frame.
     */
    public JFrame getFrame()
    {
        return viewFrame;
    }

    /**
     * Gets the number of grid cells in the horizontal direction. (Images and
     * shapes are displayed on the cells.)
     * 
     * @return The number of cells in the horizontal direction.
     */
    public int getNumHorizontalCells()
    {
        return numHorizontalCells;
    }

    /**
     * Gets the number of grid cells in the vertical direction. (Images and
     * shapes are displayed on the cells.)
     * 
     * @return The number of cells in the vertical direction.
     */
    public int getNumVerticalCells()
    {
        return numVerticalCells;
    }

    /**
     * Sets the background color of the graphics.
     * <p>
     * The change is not displayed until <code>redisplay</code> is called.
     * 
     * @param c
     *            The background color.
     */
    public void setBackgroundColor(Color c)
    {
        backgroundColor = c;
        viewFrame.setBackground(backgroundColor);

        //also set background of JPanels in each cell.
        for(int i = 0; i < numVerticalCells; i++)
        {
            for(int j = 0; j < numHorizontalCells; j++)
            {
                if(backgroundColor != null)
                {
                    cellPanel[i][j].setBackground(backgroundColor);
                }
            }
        }
    }

    /**
     * Set the border color for each cell. The border can be effectively removed
     * by setting it to the same color as the background.
     * <p>
     * The change is not displayed until <code>redisplay</code> is called.
     * 
     * @param c
     *            The border color.
     */
    public void setCellBorderColor(Color c)
    {
        borderColor = c;
        borderStyle = BorderFactory.createLineBorder(borderColor);

        for(int i = 0; i < numVerticalCells; i++)
        {
            for(int j = 0; j < numHorizontalCells; j++)
            {
                if(borderColor != null)
                {
                    cellPanel[i][j].setBorder(borderStyle);
                }
            }
        }
    }

    /**
     * Updates the graphics window to reflect any changes.
     */
    public void redisplay()
    {
        viewFrame.repaint();
    }

    /**
     * Removes any image or shape displayed at the specified position on the
     * graphic window's grid.
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
        //wrap-around boundary conditions
        row = wrapAround(row, numVerticalCells);
        col = wrapAround(col, numHorizontalCells);

        //display a blank image
        Picture p = null;
        cellPanel[row][col].setPicture(p);
    }

    /**
     * Sets the title of the graphics window. Note that this can be constantly
     * changed to reflect updated conditions (for example, to show the current
     * generation in an artificial life simulation).
     * 
     * @param title
     *            The string that will be displayed.
     */
    public void setTitle(String title)
    {
        viewFrame.setTitle(title);
        viewFrame.setVisible(true);
    }
}
