package com.elsealabs.xshot.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import com.elsealabs.xshot.capture.Capture;

public class PanelCaptureNew extends JPanel
{
	private JComponent parent;
	private JFrame     frame;
	
	private boolean debug = true;
	
	private int width;
	private int height;
	
	private Dimension frameSize;
	private Point viewportPoint;
	
    private int paddingWidth;
    private int collisionWidth;
    private int collisionHeight;
	
    private Rectangle imageWhole;
    private Rectangle imageNorth;
    private Rectangle imageSouth;
    private Rectangle imageEast;
    private Rectangle imageWest;
	
	private Capture capture;
	
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
	}
	
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
			g.drawRect(imageWhole.x, imageWhole.y, imageWhole.width, imageWhole.height);
			
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

}