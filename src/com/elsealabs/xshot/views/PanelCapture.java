package com.elsealabs.xshot.views;

import com.elsealabs.xshot.graphics.Capture;

import javax.swing.*;
import java.awt.*;

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

    private Point     imagePoint;
    private Dimension frameSize;

    public PanelCapture(JComponent parent, JFrame frame, Capture capture)
    {
        this.parent  = parent;
        this.frame   = frame;
        this.capture = capture;

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
            frameSize.getWidth()  != frame.getSize().getWidth() &&
            frameSize.getHeight() != frame.getSize().getHeight()
        )
        {
            System.out.println(frameSize);
            System.out.println(frame.getSize());

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

        g.drawImage(
                capture.getBoundedImage().getBufferedImage(),
                (int) imagePoint.getX(),
                (int) imagePoint.getY(),
                null
        );
    }

}
