package com.elsealabs.xshot.graphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * ColorGlobalSet.java
 *
 * ColorGlobalSet contains instances of ColorContainer and only ever has
 * one instantiated instance per program.
 *
 * The singular instance of ColorGlobalSet can be obtained from anywhere
 * in the program, allowing powerful re-usability and themeing.
 */
public class ColorGlobalSet {

    private static ColorGlobalSet instance;
    private ArrayList<ColorContainer> containers;

    public ColorGlobalSet()
    {
        containers = new ArrayList<ColorContainer>();
    }

    public static ColorGlobalSet getInstance()
    {
        if (instance == null) {
            instance = new ColorGlobalSet();
        }
        return instance;
    }

    public void addContainer(ColorContainer container)
    {
        System.out.println("Adding container " + container.getSet());
        containers.add(container);
    }

    public ColorContainer get(String name)
    {
        return containers.stream()
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .get();
    }

    public ColorContainer getDefaultOf(String setName)
    {
        return containers.stream()
                .filter(e -> e.getSet().equals(setName))
                .filter(e -> e.isDefault())
                .findFirst()
                .get();
    }

}