package graphics;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * A window listener that exits the program when the
 * closeWindow method is called.
 * <p>
 * This code was developed for classroom use at Regis University.
 * Any other use is prohibited without permission from the 
 * faculty member and author, David Bahr.
 * 
 * @author David Bahr
 */
public class WindowEventListener extends WindowAdapter
{	
	/**
	 * Closes the graphics window and exits the program.
	 */
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}
}
