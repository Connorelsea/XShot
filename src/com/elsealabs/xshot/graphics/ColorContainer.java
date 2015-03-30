package com.elsealabs.xshot.graphics;

import java.awt.*;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public class ColorContainer {

    private String name;
    private HashMap<String, Color> map;

    public ColorContainer(String name, HashMap<String, Color> map)
    {
        this.name = name;
        this.map  = map;
    }

    public Color getColor(String identity)
    {
        return map.get(identity);
    }

    public String getName()
    {
        return name;
    }

}
