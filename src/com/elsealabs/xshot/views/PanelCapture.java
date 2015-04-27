package com.elsealabs.xshot.views;

import com.elsealabs.xshot.graphics.Capture;

import javax.swing.*;
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

    public PanelCapture(JComponent parent, JFrame frame, Capture capture)
    {
        this.parent  = parent;
        this.frame   = frame;
        this.capture = capture;

        resized          = false;
        collisionUpdated = false;
        mouseInImage     = false;

        paddingWidth    = 10;
        collisionWidth  = 10;
        collisionHeight = 10;

        imageWhole = new Rectangle();
        imageNorth = new Rectangle();
        imageSouth = new Rectangle();
        imageEast  = new Rectangle();
        imageWest  = new Rectangle();

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

        capture.getBoundedImage().draw(g, (int) imagePoint.getX(), (int) imagePoint.getY());

        if (mouseInImage)
        {
            g.setColor(Color.LIGHT_GRAY);
            g.drawRect((int) imagePoint.getX(), (int) imagePoint.getY(), capture.getBoundedImage().getWidth(), capture.getBoundedImage().getHeight());
        }
    }

    private void _updateImagePosition()
    {
        collisionUpdated = false;

        imagePoint = new Point(
                (getWidth()  / 2) - (capture.getBoundedImage().getWidth()  / 2),
                (getHeight() / 2) - (capture.getBoundedImage().getHeight() / 2)
        );

        // If parent is a JScrolPane, perform extra operations
        // such as setting the position of the viewport.

        if (parent instanceof JScrollPane)
        {
            int padding_x = (int) (parent.getWidth() - capture.getUpdatedBounds().getWidth()) / 2;
            int padding_y = (int) (parent.getHeight() - capture.getUpdatedBounds().getHeight()) / 2;

            ((JScrollPane) parent).getViewport().setViewPosition(
                    new Point(
                            (int) imagePoint.getX() - padding_x,
                            (int) imagePoint.getY() - padding_y
                    )
            );
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
                capture.getBoundedImage().getWidth()  + (paddingWidth * 2),
                capture.getBoundedImage().getHeight() + (paddingWidth * 2)
        );

        // Image's Northern Collision Bounds
        imageNorth.setBounds(
                (int) imagePoint.getX() - (collisionWidth  / 2),
                (int) imagePoint.getY() - (collisionHeight / 2),
                capture.getBoundedImage().getWidth() + collisionWidth,
                collisionHeight
        );

        // Image's Southern Collision Bounds
        imageSouth.setBounds(
                (int) imagePoint.getX() - (collisionWidth / 2),
                (int) imagePoint.getY() + capture.getBoundedImage().getHeight() - (collisionHeight / 2),
                capture.getBoundedImage().getWidth() + collisionWidth,
                collisionHeight
        );

        // Image's Eastern Collision Bounds
        imageEast.setBounds(
               (int) imagePoint.getX() + capture.getBoundedImage().getWidth() - (collisionWidth / 2),
               (int) imagePoint.getY() - (collisionHeight / 2),
               collisionWidth,
               capture.getBoundedImage().getHeight() + collisionHeight
        );

        // Image's Western Collision Bounds
        imageWest.setBounds(
                (int) imagePoint.getX() - (collisionWidth  / 2),
                (int) imagePoint.getY() - (collisionHeight / 2),
                collisionWidth,
                capture.getBoundedImage().getHeight() + collisionHeight
        );

        collisionUpdated = true;
    }

    public void addListeners()
    {

        this.addMouseMotionListener(new MouseMotionListener()
        {

            @Override
            public void mouseDragged(MouseEvent e)
            {
                // TODO: Logic for re-sizing image
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
