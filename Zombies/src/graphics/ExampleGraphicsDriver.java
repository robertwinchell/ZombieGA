package graphics;

import java.awt.Color;
import java.awt.geom.Ellipse2D;

/**
 * An example class that runs the artificial life graphics. Shows how to make
 * some of the basic method calls. Notice the "wrap-around" boundary conditions.
 * <p>
 * WARNING: Every time an image is drawn, the code erases any image that was
 * previously drawn at that location. The graphics act like a chalkboard. You
 * can draw a banana on the chalkboard, but to then draw an apple in the same
 * place, you will have to erase the banana. See <code>ALifeWorldViewer</code>
 * for more details.
 * <p>
 * To use this grapics code, you will need some other code that keeps track of
 * the positions of each critter and redraws them whenever necessary. E.g., keep
 * an array of the critters' old positions and another array of the critters'
 * new positions. At each time step, remove the critters from the old positions
 * and then redraw them at the new positions.
 * <p>
 * This code shows the simple to use <code>ALifeWorldViewer</code>, but for
 * greater flexibility, use the <code>StandardWorldViewer</code> which lets
 * you set your own images, etc.
 * <p>
 * This code was developed for classroom use at Regis University. Any other use
 * is prohibited without permission from the faculty member and author, David
 * Bahr.
 * 
 * @author David Bahr
 */
public class ExampleGraphicsDriver
{
    /**
     * Run the world's graphics.
     */
    public static void main(String[] args)
    {
    	
    	
        //creates a JFrame with a 10 X 10 grid
        ALifeWorldViewer world = new ALifeWorldViewer(10, 10);

        //some coordinates where we will draw critters
        int i1 = 1, j1 = 1;
        int i2 = 9, j2 = 9;

        world.addCritterToGrid(i1, j1);
        world.addCritterToGrid(i2, j2);
        world.addFoodToGrid(2, 7);
        world.addWallToGrid(4, 1);
        world.redisplay();

        //set a 300 millisecond delay for each graphics change
        //(You may need a longer or shorter delay depending
        //on the speed of your processor and efficiency of
        //your code. You may not need any delay at all for
        //large and time consuming simulations.)
        long timeDelay = 300;
        
        for(int num = 0; num < 20; num++)
        {
            world.removeImageFromGrid(i1, j1);
            world.removeImageFromGrid(i2, j2);
            world.addCritterToGrid(++i1, ++j1);
            world.addCritterToGrid(--i2, --j2);
            world.delay(timeDelay);
            world.redisplay();
        }

        //If you have appropriate images or Shape objects
        //you can add them as follows. Replace the
        //ALifeWorldViewer with a StandardWorldViewer and
        //then set the images.
        //
        //StandardWorldViewer world = new StandardWorldViewer(10, 10);
        //world.setCritterImage("C:/DavidBahr.jpg");
        //world.setFoodImage("C:/banana.gif");
        //world.setWallImage("C:/wall.jpg");
        //
        //0r
        //
        //Ellipse2D.Double circle = new Ellipse2D.Double();
        //circle.setFrameFromDiagonal(0.0, 0.0, 1.0, 1.0);
        //world.setCritterImage(circle, Color.BLACK);
        //world.setFoodImage(circle, Color.CYAN);
        //world.setWallImage(circle, Color.ORANGE);
        //
        //You do not have to set the images, and default
        //shapes are provided. You may also use a mix
        //of shapes and images.
        //
        //The StandardWorldViewer also lets you set the
        //background color, set the cell border color, turn
        //off the automatic image scaling (with the method
        //<code>addPictureToGrid</code>), etc.
        //
        //Everything else remains the same as the example above.
    }
}
