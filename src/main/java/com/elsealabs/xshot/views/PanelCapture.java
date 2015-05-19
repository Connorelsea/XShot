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

import com.elsealabs.xshot.annotation.AnnotationEngine;
import com.elsealabs.xshot.capture.Capture;
import com.elsealabs.xshot.capture.Capture.AREA;

public class PanelCapture extends JPanel
{
	// Referenced outside objects

	private JComponent  parent;
	private JFrame      frame;
	private Capture     capture;
	private JScrollPane scrollPane;
	
	private AnnotationEngine engine;

	// State-related attributes

	private boolean debug = false;
	private boolean saved = false;

	// Size-related attributes

	private int width;
	private int height;

	private Dimension frameSize;
	private Point viewportPoint;

	// Information regarding collision

    private int padWidth;
    private int colWidth;
    private int colHeight;

    private Rectangle imageWhole;
    private Rectangle imageNorth;
    private Rectangle imageSouth;
    private Rectangle imageEast;
    private Rectangle imageWest;

    private AREA    currentArea;
    private Point   initial;
    private boolean pressListening;

    // Image hover hints

    private AREA hoverArea;

	/**
	 * Creates a new PanelCapture
	 *
	 * @param parent  The component the capture is inside of
	 * @param frame   The frame the parent and the panel are inside of
	 * @param capture The capture to display in the panel
	 */
	public PanelCapture(JComponent parent, JFrame frame, Capture capture)
	{
		this.parent  = parent;
		this.frame   = frame;
		this.capture = capture;

		if (parent instanceof JScrollPane) scrollPane = ((JScrollPane) parent);
		
		engine = new AnnotationEngine();

		width  = capture.getTotalBounds().width;
		height = capture.getTotalBounds().height;

        imageWhole = new Rectangle();
        imageNorth = new Rectangle();
        imageSouth = new Rectangle();
        imageEast  = new Rectangle();
        imageWest  = new Rectangle();

        padWidth  = 10;
        colWidth  = 20;
        colHeight = 20;

		frameSize = new Dimension(0, 0);

		_addListeners();
	}

	/**
	 * Adds relevant listeners required f  or the panel capture to
	 * function correctly.
	 */
	private void _addListeners()
	{
		/**
		 * Mouse Listener to track initial point that starts the drag
		 */
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
				if ((currentArea = locateArea(e.getPoint())) != null)
				{
					pressListening = true;
					initial = e.getPoint();
				}
				super.mousePressed(e);
			}
		});

		/**
		 * Mouse Motion Listener to track dragging
		 */
		addMouseMotionListener(new MouseMotionAdapter()
		{

			/**
			 * Fires the events to cause dragging the image to be recognized.
			 */
			@Override
			public void mouseDragged(MouseEvent e)
			{
				if (currentArea != null && pressListening)
				{
					// Update currentAreas based on whether or not they are being updated
					// on the y-axis or the x-axis;

					switch (currentArea)
					{
						case NORTH:
						case SOUTH:
							capture.addTo(currentArea, initial.y - e.getPoint().y);
							break;

						case EAST:
						case WEST:
							capture.addTo(currentArea, initial.x - e.getPoint().x);
							break;

						case NORTHWEST:
							capture.addTo(Capture.AREA.NORTH, initial.y - e.getPoint().y);
							capture.addTo(Capture.AREA.WEST , initial.x - e.getPoint().x);
							break;

						case NORTHEAST:
							capture.addTo(Capture.AREA.NORTH, initial.y - e.getPoint().y);
							capture.addTo(Capture.AREA.EAST , initial.x - e.getPoint().x);
							break;

						case SOUTHWEST:
							capture.addTo(Capture.AREA.SOUTH, initial.y - e.getPoint().y);
							capture.addTo(Capture.AREA.WEST , initial.x - e.getPoint().x);
							break;

						case SOUTHEAST:
							capture.addTo(Capture.AREA.SOUTH, initial.y - e.getPoint().y);
							capture.addTo(Capture.AREA.EAST , initial.x - e.getPoint().x);
							break;

						default:
							break;
					}

					initial = e.getPoint();

					updateCollisionBounds();
					repaint();
				}

				super.mouseDragged(e);
			}

			/**
			 * Updates the image visuals based on the position of the mouse.
			 */
			@Override
			public void mouseMoved(MouseEvent e)
			{
				if (imageWhole.contains(e.getPoint())) hoverArea = locateArea(e.getPoint());
				else hoverArea = null;

				repaint();
				super.mouseMoved(e);
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

		/**
		 * This ensures that the viewport will only be repositioned when the user
		 * is re-sizing the frame.
		 */
        if (
                frameSize.getWidth()  != frame.getSize().getWidth() ||
                frameSize.getHeight() != frame.getSize().getHeight()
        )
		{
			repositionViewport();
			updateCollisionBounds();

			frameSize = frame.getSize();
		}

        // Draw the capture's image with it's most recently updated bounds
		g.drawImage(
				capture.getUpdatedImage(),
				capture.getUpdatedBounds().x,
				capture.getUpdatedBounds().y,
				null
		);

		// Draw the image's visual hints

		if (hoverArea == null)
		{
			g.setColor(Color.LIGHT_GRAY);
			g.draw(capture.getUpdatedBounds());
		}
		else
		{
			g.setColor(Color.black);
			g.draw(capture.getUpdatedBounds());;
		}

		if (debug)
		{
			g.setColor(Color.RED);
			g.draw(capture.getUpdatedBounds());

			g.setColor(Color.BLUE);
			g.draw(imageEast);
			g.draw(imageWest);

			g.setColor(Color.GREEN);
			g.draw(imageNorth);
			g.draw(imageSouth);
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
			JViewport viewport = scrollPane.getViewport();

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
                capture.getUpdatedBounds().x - padWidth,
                capture.getUpdatedBounds().y - padWidth,
                capture.getUpdatedBounds().width  + (padWidth * 2),
                capture.getUpdatedBounds().height + (padWidth * 2)
        );

        // Image's Northern Collision Bounds
        imageNorth.setBounds(
                capture.getUpdatedBounds().x - (colWidth  / 2),
                capture.getUpdatedBounds().y - (colHeight / 2),
                capture.getUpdatedBounds().width + colWidth,
                colHeight
        );

        // Image's Southern Collision Bounds
        imageSouth.setBounds(
                capture.getUpdatedBounds().x - (colWidth / 2),
                capture.getUpdatedBounds().y + capture.getUpdatedBounds().height - (colHeight / 2),
                capture.getUpdatedBounds().width + colWidth,
                colHeight
        );

        // Image's Eastern Collision Bounds
        imageEast.setBounds(
               capture.getUpdatedBounds().x + capture.getUpdatedBounds().width - (colWidth / 2),
               capture.getUpdatedBounds().y - (colHeight / 2),
               colWidth,
               capture.getUpdatedBounds().height + colHeight
        );

        // Image's Western Collision Bounds
        imageWest.setBounds(
                capture.getUpdatedBounds().x - (colWidth  / 2),
                capture.getUpdatedBounds().y - (colHeight / 2),
                colWidth,
                capture.getUpdatedBounds().height + colHeight
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

        else if (imageWhole.contains(p)) return Capture.AREA.WHOLE;

        else    return null;
    }

    public void setSaved(boolean saved)
    {
    	this.saved = saved;
    }

    public boolean isSaved()
    {
    	return saved;
    }

}