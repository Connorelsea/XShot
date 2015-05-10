package com.elsealabs.xshot.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import com.elsealabs.xshot.capture.Capture;
import com.elsealabs.xshot.capture.Capture.AREA;

public class PanelCaptureNew extends JPanel
{
	// Referenced outside objects
	
	private JComponent parent;
	private JFrame     frame;
	private Capture    capture;
	
	// State-related attributes
	
	private boolean debug = true;
	
	// Size-related attributes
	
	private int width;
	private int height;
	
	private Dimension frameSize;
	private Point viewportPoint;
	
	// Information regarding collision
	
    private int paddingWidth;
    private int collisionWidth;
    private int collisionHeight;
	
    private Rectangle imageWhole;
    private Rectangle imageNorth;
    private Rectangle imageSouth;
    private Rectangle imageEast;
    private Rectangle imageWest;
    
    private AREA    currentArea;
    private Point   initial;
    private boolean pressListening;
	
	/**
	 * Creates a new PanelCapture
	 * 
	 * @param parent  The component the capture is inside of
	 * @param frame   The frame the parent and the panel are inside of
	 * @param capture The capture to display in the panel
	 */
	public PanelCaptureNew(JComponent parent, JFrame frame, Capture capture)
	{
		this.parent  = parent;
		this.frame   = frame;
		this.capture = capture;
		
		width  = capture.getTotalBounds().width;
		height = capture.getTotalBounds().height;
		
        imageWhole = new Rectangle();
        imageNorth = new Rectangle();
        imageSouth = new Rectangle();
        imageEast  = new Rectangle();
        imageWest  = new Rectangle();
		
        paddingWidth    = 10;
        collisionWidth  = 20;
        collisionHeight = 20;
		
		frameSize = new Dimension(0, 0);
		
		_addListeners();
	}
	
	/**
	 * Adds relevant listeners required for the panel capture to
	 * function correctly.
	 */
	private void _addListeners()
	{
		addMouseListener(new MouseAdapter()
		{
			@Override
			public void mouseReleased(MouseEvent e)
			{
				pressListening = false;
				super.mouseReleased(e);
			}
			
			@Override
			public void mousePressed(MouseEvent e)
			{
				currentArea = locateArea(e.getPoint());
				
				if (currentArea != null)
				{
					pressListening = true;
					initial = e.getPoint();
					
					System.out.println("Initial Point: (" + initial.x + ", " + initial.y + ")");
				}
				
				super.mousePressed(e);
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				if (currentArea != null && pressListening)
				{
					// Update currentAreas based on whether or not they are being updated
					// on the y-axis or the x-axis;
					
					if (currentArea == Capture.AREA.NORTH || currentArea == Capture.AREA.SOUTH)
					{
						capture.addTo(currentArea, initial.y - e.getPoint().y);
						initial = e.getPoint();
					}
					else if (currentArea == Capture.AREA.EAST || currentArea == Capture.AREA.WEST)
					{
						capture.addTo(currentArea, initial.x - e.getPoint().x);
						initial = e.getPoint();
					}
					else
					{
						
					}
					
					updateCollisionBounds();
					repaint();
				}
				
				super.mouseDragged(e);
			}
		});
	}
	
	/**
	 * Paints the components and handles when information regarding
	 * positioning should be updated
	 */
	public void paint(Graphics gd)
	{
		Graphics2D g = (Graphics2D) gd;
		
		g.clearRect(0, 0, width, height);
		
        if (
                frameSize.getWidth()  != frame.getSize().getWidth() ||
                frameSize.getHeight() != frame.getSize().getHeight()
        )
		{
			repositionViewport();
			updateCollisionBounds();
			
			frameSize = frame.getSize();
		}
		
		g.drawImage(
				capture.getUpdatedImage(),
				capture.getUpdatedBounds().x,
				capture.getUpdatedBounds().y,
				null
		);
		
		if (debug)
		{
			g.setColor(Color.RED);
			g.drawRect(capture.getUpdatedBounds().x, capture.getUpdatedBounds().y, capture.getUpdatedBounds().width, capture.getUpdatedBounds().height);
			
			g.setColor(Color.BLUE);
			g.drawRect(imageEast.x, imageEast.y, imageEast.width, imageEast.height);
			g.drawRect(imageWest.x, imageWest.y, imageWest.width, imageWest.height);
			
			g.setColor(Color.GREEN);
			g.drawRect(imageNorth.x, imageNorth.y, imageNorth.width, imageNorth.height);
			g.drawRect(imageSouth.x, imageSouth.y, imageSouth.width, imageSouth.height);
		}
	}
	
	/**
	 * This takes the width of the viewport and the width of the image
	 * current being displayed and centers the image in the viewport.
	 */
	public void repositionViewport()
	{
		if (parent instanceof JScrollPane)
		{
			JViewport viewport = ((JScrollPane) parent).getViewport();
			
			int proper_width  = (viewport.getWidth()  / 2) - (capture.getUpdatedBounds().width  / 2);
			int proper_height = (viewport.getHeight() / 2) - (capture.getUpdatedBounds().height / 2);
			
			viewportPoint = new Point(
					(500 / 2) + capture.getUpdatedBounds().x - proper_width,
					(500 / 2) + capture.getUpdatedBounds().y - proper_height
			);
			
			viewport.setViewPosition(viewportPoint);
		}
	}
	
	/**
	 * Updates relevant boundaries generated from dimension information that
	 * will be used to calculate collision and image re-sizing.
	 */
    public void updateCollisionBounds()
    {
    	
        // Bounds of entire image, plus padding
        imageWhole.setBounds(
                (int) capture.getUpdatedBounds().getX() - paddingWidth,
                (int) capture.getUpdatedBounds().getY() - paddingWidth,
                capture.getUpdatedImage().getWidth()  + (paddingWidth * 2),
                capture.getUpdatedImage().getHeight() + (paddingWidth * 2)
        );

        // Image's Northern Collision Bounds
        imageNorth.setBounds(
                (int) capture.getUpdatedBounds().getX() - (collisionWidth  / 2),
                (int) capture.getUpdatedBounds().getY() - (collisionHeight / 2),
                capture.getUpdatedImage().getWidth() + collisionWidth,
                collisionHeight
        );

        // Image's Southern Collision Bounds
        imageSouth.setBounds(
                (int) capture.getUpdatedBounds().getX() - (collisionWidth / 2),
                (int) capture.getUpdatedBounds().getY() + capture.getUpdatedImage().getHeight() - (collisionHeight / 2),
                capture.getUpdatedImage().getWidth() + collisionWidth,
                collisionHeight
        );

        // Image's Eastern Collision Bounds
        imageEast.setBounds(
               (int) capture.getUpdatedBounds().getX() + capture.getUpdatedImage().getWidth() - (collisionWidth / 2),
               (int) capture.getUpdatedBounds().getY() - (collisionHeight / 2),
               collisionWidth,
               capture.getUpdatedImage().getHeight() + collisionHeight
        );

        // Image's Western Collision Bounds
        imageWest.setBounds(
                (int) capture.getUpdatedBounds().getX() - (collisionWidth  / 2),
                (int) capture.getUpdatedBounds().getY() - (collisionHeight / 2),
                collisionWidth,
                capture.getUpdatedImage().getHeight() + collisionHeight
        );
    }
    
    /**
     * Quickly locate the area in which the point is contained by.
     * 
     * @param p The point whose area is to be located.
     * @return  The area in which the point is located.
     */
    public Capture.AREA locateArea(Point p)
    {
        if      (imageNorth.contains(p) && imageEast .contains(p)) return Capture.AREA.NORTHEAST;
        else if (imageEast .contains(p) && imageSouth.contains(p)) return Capture.AREA.SOUTHEAST;
        else if (imageSouth.contains(p) && imageWest .contains(p)) return Capture.AREA.SOUTHWEST;
        else if (imageWest .contains(p) && imageNorth.contains(p)) return Capture.AREA.NORTHWEST;

        else if (imageNorth.contains(p)) return Capture.AREA.NORTH;
        else if (imageEast .contains(p)) return Capture.AREA.EAST;
        else if (imageSouth.contains(p)) return Capture.AREA.SOUTH;
        else if (imageWest .contains(p)) return Capture.AREA.WEST;

        else    return null;
    }

}