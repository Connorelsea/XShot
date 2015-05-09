package com.elsealabs.xshot.views;

import javax.swing.*;

import com.elsealabs.xshot.capture.Capture;

import java.awt.*;
import java.awt.event.*;

/**
 * PanelCapture.java
 *
 * A custom panel that holds a capture and resizes
 * accordingly. Supports mainly images  in  scroll
 * panes.
 */
public class PanelCapture extends JPanel {

    private JComponent parent;
    private JFrame     frame;
    private Capture    capture;

    private Point      imagePoint;
    private Dimension  frameSize;

    private boolean resized;
    private boolean collisionUpdated;

    private Rectangle imageWhole;
    private Rectangle imageNorth;
    private Rectangle imageSouth;
    private Rectangle imageEast;
    private Rectangle imageWest;

    private boolean mouseInImage;

    private int paddingWidth;
    private int collisionWidth;
    private int collisionHeight;

    private Capture.AREA currentArea;
    private boolean pressListening;
    private boolean iniUpdated;

    private Point ini;
    private Point fin;

    public PanelCapture(JComponent parent, JFrame frame, Capture capture)
    {
        this.parent  = parent;
        this.frame   = frame;
        this.capture = capture;

        resized          = false;
        collisionUpdated = false;
        mouseInImage     = false;

        paddingWidth    = 10;
        collisionWidth  = 20;
        collisionHeight = 20;

        imageWhole = new Rectangle();
        imageNorth = new Rectangle();
        imageSouth = new Rectangle();
        imageEast  = new Rectangle();
        imageWest  = new Rectangle();

        ini = new Point();
        fin = new Point();

        iniUpdated = false;

        addListeners();

        // Set frame size to 0 initially so the paint
        // method performs the initial calculations.
        frameSize = new Dimension(0, 0);
    }

    public void paint(Graphics gd)
    {
        Graphics2D g = (Graphics2D) gd;

        g.setColor(Color.WHITE);
        g.clearRect(0, 0, getWidth(), getHeight());

        // If frame size has changed, re-perform all
        // relevant calculations

        if (
            frameSize.getWidth()  != frame.getSize().getWidth() ||
            frameSize.getHeight() != frame.getSize().getHeight()
        )
        {
            _updateImagePosition();
            _updateCollisionBounds();
        }

        g.drawImage(capture.getUpdatedImage(), capture.getUpdatedBounds().x, capture.getUpdatedBounds().y, null);

        if (mouseInImage)
        {
        	g.setColor(Color.LIGHT_GRAY);
            g.drawRect(imagePoint.x, imagePoint.y, capture.getUpdatedBounds().width, capture.getUpdatedBounds().height);
        }
    }

    private void _updateImagePosition()
    {
        collisionUpdated = false;
        
        int lengthWhole = capture.getFullImage().getWidth() + 500;
        int lengthPart  = capture.getFullImage().getWidth();
        int imageX      = 500 + (int) capture.getUpdatedBounds().getX();
        
        int heightWhole = capture.getFullImage().getHeight() + 500;
        int heightPart  = capture.getFullImage().getHeight();
        int imageY      = 500 + (int) capture.getUpdatedBounds().getY();

        Point viewportRender = new Point(imageX, imageY);

        // If parent is a JScrolPane, perform extra operations
        // such as setting the position of the viewport.

        if (parent instanceof JScrollPane)
        {
//            int padding_x = ((int) parent.getWidth()  / 2) - ((int) capture.getUpdatedBounds().getWidth() / 2);
//            int padding_y = ((int) parent.getHeight() / 2) - ((int) capture.getUpdatedBounds().getHeight() / 2);
//
//            ((JScrollPane) parent).getViewport().setViewPosition(
//                    new Point(
//                            (int) imagePoint.getX() - padding_x,
//                            (int) imagePoint.getY() - padding_y
//                    )
//            );
            
            ((JScrollPane) parent).getViewport().setViewPosition(viewportRender);
        }

        // Set updated frame size
        frameSize = frame.getSize();
    }

    private void _updateCollisionBounds()
    {
    	
        // Bounds of entire image, plus padding
        imageWhole.setBounds(
                (int) imagePoint.getX() - paddingWidth,
                (int) imagePoint.getY() - paddingWidth,
                capture.getUpdatedImage().getWidth()  + (paddingWidth * 2),
                capture.getUpdatedImage().getHeight() + (paddingWidth * 2)
        );

        // Image's Northern Collision Bounds
        imageNorth.setBounds(
                (int) imagePoint.getX() - (collisionWidth  / 2),
                (int) imagePoint.getY() - (collisionHeight / 2),
                capture.getUpdatedImage().getWidth() + collisionWidth,
                collisionHeight
        );

        // Image's Southern Collision Bounds
        imageSouth.setBounds(
                (int) imagePoint.getX() - (collisionWidth / 2),
                (int) imagePoint.getY() + capture.getUpdatedImage().getHeight() - (collisionHeight / 2),
                capture.getUpdatedImage().getWidth() + collisionWidth,
                collisionHeight
        );

        // Image's Eastern Collision Bounds
        imageEast.setBounds(
               (int) imagePoint.getX() + capture.getUpdatedImage().getWidth() - (collisionWidth / 2),
               (int) imagePoint.getY() - (collisionHeight / 2),
               collisionWidth,
               capture.getUpdatedImage().getHeight() + collisionHeight
        );

        // Image's Western Collision Bounds
        imageWest.setBounds(
                (int) imagePoint.getX() - (collisionWidth  / 2),
                (int) imagePoint.getY() - (collisionHeight / 2),
                collisionWidth,
                capture.getUpdatedImage().getHeight() + collisionHeight
        );

        collisionUpdated = true;
    }

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

    public void addListeners()
    {
        this.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                pressListening = false;
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                currentArea = locateArea(e.getPoint());

                if (currentArea != null)
                {
                    pressListening = true;
                    ini = e.getPoint();
                }
                else pressListening = false;

                super.mousePressed(e);
            }
        });

        this.addMouseMotionListener(new MouseMotionListener()
        {

            @Override
            public void mouseDragged(MouseEvent e)
            {
                if (pressListening)
                {
                	System.out.println("MouseEvent.y " + e.getPoint().getY());
                	System.out.println("Image " + capture.getUpdatedBounds());
                	
                    if (currentArea == Capture.AREA.NORTH)
                    {
                    	capture.addTo(Capture.AREA.NORTH, (int) ini.getY() - (int) e.getPoint().getY());
                    	
                    	_updateImagePosition();
                    	_updateCollisionBounds();
                    	
                    	ini = e.getPoint();
                    	repaint();
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                if (imageWhole.contains(e.getPoint()))
                {
                    mouseInImage = true;
                } else
                {
                    mouseInImage = false;
                }

                repaint();
            }

        });

        frame.addComponentListener(new ComponentAdapter() {

            @Override
            public void componentResized(ComponentEvent e) {
                resized = true;
                super.componentResized(e);
            }

        });
    }

}
