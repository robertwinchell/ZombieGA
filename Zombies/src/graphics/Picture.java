package graphics;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.Graphics;
import java.awt.image.ImageProducer;
import java.awt.image.ImageObserver;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;

/**
 * A picture is either an image or a shape, so objects of this
 * class will hold one or the other but not both.  This class is 
 * just a convenience wrapper, so that we can pass around a shape 
 * or an image as a single object and not worry about which one 
 * the code should be using. Any method requiring an image as a 
 * parameter can take a Picture, and any method requiring a shape
 * as a parameter can take a Picture.  This code has constructors 
 * but no setters to ensure that Picture objects contains either 
 * an image or a shape but not both.
 * <p>
 * The only significant addition to the <code>Image</code>
 * and <code>Shape</code> classes is that this code allows the 
 * color of a shape to be specified.
 * <p>
 * Comments for each method can be found in <code>Image</code>
 * and <code>Shape</code>.
 * <p>
 * All abstract methods are implemented by simply calling the same 
 * method from the image or shape passed into the constructor.
 * <p>
 * Students of software engineering will appreciate that this class
 * uses the Adapter design pattern, though somewhat altered from
 * its usual form.
 * <p>
 * This code was developed for classroom use at Regis University.
 * Any other use is prohibited without permission from the 
 * faculty member and author, David Bahr.
 * 
 * @author David Bahr
 */
public class Picture extends Image implements Shape
{
	//-------------------------------------------------
	//instance variables	
	
	//shape or image that makes up the picture.
	private Image image = null;
	private Shape shape = null;
	private Color shapeColor = null;
		
	//-------------------------------------------------
	//constructors
	
	/**
	 * Makes the picture be the supplied image.
	 * 
	 * @param i  An image.
	 */	
	public Picture(Image i)
	{
		image = i;
	}
	
	/**
	 * Makes the picture be the supplied shape.
	 * 
	 * @param s  A shape.
	 */	
	public Picture(Shape s)
	{
		shape = s;
	}
	
	/**
	 * Makes the picture be the supplied shape with a 
	 * specified color.
	 * 
	 * @param s  A shape.
	 * @param c  The color of the shape.
	 */	
	public Picture(Shape s, Color c)
	{
		shape = s;
		shapeColor = c;
	}	
	
	//-------------------------------------------------
	//public methods

	/**
	 * Gets the color of a shape if it was specified.
	 * 
	 * @return the color of a shape, or null if no color 
	 * was specified, or null if the there is no shape.
	 */
	public Color getShapeColor()
	{
		return shapeColor;
	}
	
	/**
	 * Gets the image held by this class.
	 * 
	 * @return an image, or null if this class holds a 
	 * shape instead.
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 * Gets the shape held by this class.
	 * 
	 * @return a shape, or null if this class holds an
	 * image instead.
	 */
	public Shape getShape()
	{
		return shape;
	}
	
	/**
	 * Determines whether or not this class contains an
	 * image rather than a shape.
	 * 
	 * @return true if this class contains an image and false
	 * if this class contains a shape.
	 */
	public boolean isImage()
	{
		return (image != null) ? true : false;
	}
	
	/**
	 * Determines whether or not this class contains a
	 * shape rather than an image.
	 * 
	 * @return true if this class contains a shape and false
	 * if this class contains an image.
	 */
	public boolean isShape()
	{
		return (shape != null) ? true : false;
	}
	
	//-------------------------------------------------
	//implementation of Image abstract methods

	/**
	 * See description in Image class.
	 */
	public void flush()
	{
		if(image != null)
		{
			image.flush();
		} 
	}
	
	/**
	 * See description in Image class.
	 */
	public Graphics getGraphics() 
	{
		Graphics value = null;
		if(image != null)
		{
			value = image.getGraphics();
		}

		return value;
	}
			
	/**
	 * See description in Image class.
	 */
	public int getHeight(ImageObserver observer) 
	{
		int value = 0;
		if(image != null)
		{
			value = image.getHeight(observer);
		}

		return value;
	}
	
	/**
	 * See description in Image class.
	 */
	public Object getProperty(String name, ImageObserver observer) 
	{
		Object value = null;
		if(image != null)
		{
			value = image.getProperty(name, observer);
		}

		return value;
	}
	
	/**
	 * See description in Image class.
	 */
	public ImageProducer getSource() 
	{
		ImageProducer value = null;
		if(image != null)
		{
			value = image.getSource();
		}

		return value;
	}

	/**
	 * See description in Image class.
	 */
	public int getWidth(ImageObserver observer) 
	{
		int value = 0;
		if(image != null)
		{
			value = image.getWidth(observer);
		}

		return value;
	}
	
	//-------------------------------------------------
	//implementation of Shape interface methods

	/**
	 * See description in Shape class.
	 */
	public boolean contains(double x, double y)
	{
		boolean value = false;
		if(shape != null)
		{
			value = shape.contains(x, y);
		}
		
		return value;
	}
	
	/**
	 * See description in Shape class.
	 */
	public boolean contains(double x, double y, double w, double h) 
	{
		boolean value = false;
		if(shape != null)
		{
			value = shape.contains(x, y, w, h);
		}
	
		return value;
	}
	
	/**
	 * See description in Shape class.
	 */
	public boolean contains(Point2D p) 
	{
		boolean value = false;
		if(shape != null)
		{
			value = shape.contains(p);
		}
		
		return value;
	}
	
	/**
	 * See description in Shape class.
	 */
	public boolean contains(Rectangle2D r) 
	{
		boolean value = false;
		if(shape != null)
		{
			value = shape.contains(r);
		}
			
		return value;
	}

	/**
	 * See description in Shape class.
	 */
	public Rectangle getBounds() 
	{
		Rectangle value = null;
		if(shape != null)
		{
			value = shape.getBounds();
		}
			
		return value;
	}

	/**
	 * See description in Shape class.
	 */
	public Rectangle2D getBounds2D() 
	{
		Rectangle2D value = null;
		if(shape != null)
		{
			value = shape.getBounds2D();
		}
			
		return value;
	}
	
	/**
	 * See description in Shape class.
	 */
	public PathIterator getPathIterator(AffineTransform at) 
	{
		PathIterator value = null;
		if(shape != null)
		{
			value = shape.getPathIterator(at);
		}
			
		return value;
	}
	
	/**
	 * See description in Shape class.
	 */
	public PathIterator getPathIterator(AffineTransform at, double flatness) 
	{
		PathIterator value = null;
		if(shape != null)
		{
			value = shape.getPathIterator(at, flatness);
		}
			
		return value;
	}

	/**
	 * See description in Shape class.
	 */
	public boolean intersects(double x, double y, double w, double h) 
	{
		boolean value = false;
		if(shape != null)
		{
			value = shape.intersects(x, y, w, h);
		}
			
		return value;
	}

	/**
	 * See description in Shape class.
	 */
	public boolean intersects(Rectangle2D r) 
	{
		boolean value = false;
		if(shape != null)
		{
			value = shape.intersects(r);
		}
			
		return value;
	}
}
