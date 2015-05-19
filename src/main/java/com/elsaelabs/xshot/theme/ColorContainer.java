package com.elsaelabs.xshot.theme;

import java.awt.*;
import java.util.HashMap;

/**
 * ColorContainer.java
 *
 * A ColorContainer is a HashMap that contains String-valued Color elements.
 * It is used to contain a group of colors that are relevant to each  other,
 * typically for use in themeing the program.
 *
 * For example, for the main window, there  may  be  a  ColorContainer  that
 * holds a color for the buttons, the top bar, etc. There  may  be  multiple
 * ColorContainers with the same String values, and different colors,  which
 * will allow the user to select from a number of color-based themes for the
 * program.
 *
 * ColorContainer also allows colors to be easily  changed  at  one  central
 * point in the program, and having  the  colors  change  instantly  at  the
 * point of their implementation immediately.
 */
public class ColorContainer {

    private String set;
    private String name;
    private boolean defaultOfSet;
    private HashMap<String, Color> map;

    public ColorContainer(String set, String name, HashMap<String, Color> map)
    {
        this.set  = set;
        this.name = name;
        this.map  = map;
        this.defaultOfSet = false;
    }

    public Color getColor(String identity)
    {
        return map.get(identity);
    }

    public void setDefault(boolean value)
    {
        this.defaultOfSet = value;
    }

    public boolean isDefault()
    {
        return defaultOfSet;
    }

    public String getName()
    {
        return name;
    }

    public String getSet()
    {
        return set;
    }

}
