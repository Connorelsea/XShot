package com.elsealabs.xshot.graphics;

import java.awt.*;

/**
 * BoundRectangle.java
 *
 * An implementation of a Rectangle with extended functionality
 * to enhance the Rectangle class when used for the purpose  of
 * measuring boundaries or handling collision
 */
public class BoundRectangle extends Rectangle {

    public void draw(Graphics2D g, Color color)
    {
        g.setColor(color);
        g.drawRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }

    public void fill(Graphics2D g, Color color)
    {
        g.setColor(color);
        g.fillRect((int) getX(), (int) getY(), (int) getWidth(), (int) getHeight());
    }


}