package com.elsealabs.xshot.views;

import com.elsealabs.xshot.graphics.Capture;

import javax.swing.*;
import java.awt.*;

/**
 * PanelImage.java
 *
 * A custom panel that holds a picture and resizes
 * accordingly. Supports mainly images  in  scroll
 * panes.
 */
public class PanelImage extends JPanel {

    private JComponent parent;
    private Capture    capture;

    public PanelImage(JComponent parent, Capture capture)
    {
        this.parent  = parent;
        this.capture = capture;
    }

    public void paint(Graphics gd)
    {
        Graphics2D g = (Graphics2D) gd;

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // DRAW IMAGE

        int imageX = (getWidth() / 2)  - (capture.getBoundedImage().getWidth() / 2);
        int imageY = (getHeight() / 2) - (capture.getBoundedImage().getHeight() / 2);

        if (parent instanceof JScrollPane)
        {
            int padding_x = (int) (parent.getWidth() - capture.getUpdatedBounds().getWidth()) / 2;
            int padding_y = (int) (parent.getHeight() - capture.getUpdatedBounds().getHeight()) / 2;

            ((JScrollPane) parent).getViewport().setViewPosition(
                    new Point(
                            (int) imageX - padding_x,
                            (int) imageY - padding_y
                    )
            );
        }

        g.drawImage(
                capture.getBoundedImage().getBufferedImage(),
                imageX,
                imageY,
                null
        );
    }

}
