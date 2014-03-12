package graphics;

import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.Image;
import java.awt.Shape;

/**
 * Resizes Shapes and Images to any specified size.
 * <p>
 * This code was developed for classroom use at Regis University.
 * Any other use is prohibited without permission from the 
 * faculty member and author, David Bahr.
 * 
 * @author David Bahr
 */
public class RescaleTransform 
{
	/**
	 * Resizes any image to the specified width and height.
	 * 
	 * @param i  The image that will be resized.
	 * @param newWidth  The width that to which the shape will be scaled.
	 * @param newHeight The height that to which the shape will be scaled.
	 * @return the resized image, or null if the image is null.
	 */
	public static Image resize(Image i, int newWidth, int newHeight)
	{
		if(i != null)
		{
			i  = i.getScaledInstance(
				newWidth, 
				newHeight, 
				Image.SCALE_FAST);
		}
		
		return i;
	}
	
	/**
	 * Resizes any picture to the specified width and height.
	 * 
	 * @param p  The picture that will be resized.
	 * @param newWidth  The width that to which the picture will be scaled.
	 * @param newHeight The height that to which the picture will be scaled.
	 * @return the resized picture, or null if the picture is null.
	 */
	public static Picture resize(Picture p, int newWidth, int newHeight)
	{
		if(p != null)
		{
			if(p.isImage())
			{
				p = new Picture(
					resize(p.getImage(), newWidth, newHeight));
			}
			else
			{
				p = new Picture(
					resize(p.getShape(), newWidth, newHeight), 
					p.getShapeColor());				
			}
		}
		
		return p;
	}
	
	/**
	 * Resizes any shape to the specified width and height.  Ensures
	 * that the top left corner of the shape is at the same 
	 * coordinates as the unscaled shape.
	 * 
	 * @param s  The shape that will be resized.
	 * @param newWidth  The width that to which the shape will be scaled.
	 * @param newHeight The height that to which the shape will be scaled.
	 * @return the resized Shape, or null if the shape is null.
	 */
	public static Shape resize(Shape s, int newWidth, int newHeight)
	{
		if(s != null)
		{
			//get the coordinates at the top left
			double originalX = s.getBounds2D().getMinX();
			double originalY = s.getBounds2D().getMinY();
			
			//find the original height and width
			Rectangle2D boundingBox = s.getBounds2D();
			double width = boundingBox.getWidth();
			double height = boundingBox.getHeight();

			//rescale the size
			AffineTransform scalingTransform = 
				AffineTransform.getScaleInstance(
				newWidth/width, 
				newHeight/height);		
			s = scalingTransform.createTransformedShape(s);
			
			//adjust position to be at the original coordinates
			//(so has same top left coordinates)
			double newX = s.getBounds2D().getMinX();
			double newY = s.getBounds2D().getMinY();			
			AffineTransform translationTransform = 
				AffineTransform.getTranslateInstance(
				originalX - newX, 
				originalY - newY);
			s = translationTransform.createTransformedShape(s);
		}
		
		return s;
	}
}
