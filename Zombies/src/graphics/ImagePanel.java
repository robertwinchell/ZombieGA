package graphics;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 * Draws a picture on a panel.
 * <p>
 * This code was developed for classroom use at Regis University.
 * Any other use is prohibited without permission from the 
 * faculty member and author, David Bahr.
 * 
 * @author David Bahr
 */
public class ImagePanel extends JPanel 
{
	//-------------------------------------------------
	//instance variables	
	
	//width and height of the panel
	private int height;
	private int width;
	
	//the picture to be displayed.
	private Picture picture = null;

	//-------------------------------------------------
	//constructors
	
	/**
	 * Creates a panel of the specified height and width.
	 * 
	 * @param height In pixels.
	 * @param width  In pixels.
	 */
	public ImagePanel(int width, int height)
	{
		this(width, height, null);
	}
	
	/**
	 * Creates a panel of the specified height and width, and
	 * adds the specified picture to the panel.
	 * 
	 * @param height In pixels.
	 * @param width  In pixels.
	 * @param p	 The picture to be displayed.
	 */
	public ImagePanel(int width, int height, Picture p)
	{
		super();
		
		this.height = height;
		this.width = width;
		this.picture = p;
	}
	
	//-------------------------------------------------
	//public methods
	
	/**
	 * Get the picture displayed on the panel.
	 * 
	 * @return The picture displayed on the panel.  May be null.
	 */
	public Picture getPicture()
	{
		return picture;
	}
	
	/**
	 * Adds a picture to the panel.  
	 * <p>
	 * This method is automatically called whenever the window is 
	 * resized or otherwise altered.  You can force it to be called by 
	 * using the <code>repaint</code> method of the encompassing JFrame or 
	 * JComponent.  Never call this method directly (or the Graphics 
	 * object may not be specified properly).
	 */
	public void paintComponent(Graphics g)
	{
		//Call the JPanel's paintComponent. This ensures
		//that the background is properly rendered.
		super.paintComponent(g);
		
		//Now add our own graphics to the panel.
		Graphics2D g2 = (Graphics2D) g;
		
		
		if(picture != null)
		{
			if( picture.isImage() && (picture.getImage() != null) )
			{
				g.drawImage(picture.getImage(), 0, 0, null);
			}
			else if(picture.getShape() != null)
			{					
				if(picture.getShapeColor() != null)
				{
					g2.setPaint(picture.getShapeColor());
				}
				g2.fill(picture.getShape());		
			}
		}
	}
	
	/**
	 * Updates the picture displayed on the panel.  This new 
	 * picture will not display until <code>repaint</code> is 
	 * called.
	 * 
	 * @param p The picture displayed on the panel.  If null, 
	 * nothing will be displayed.
	 */
	public void setPicture(Picture p)
	{
		picture = p;
	}
}
