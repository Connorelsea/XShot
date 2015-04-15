package com.elsealabs.xshot.file;

import java.nio.file.Path;
import java.util.ArrayList;

/**
 * PathPool.java
 *
 * Generates and contains the paths commonly used in this software.
 */
public class PathPool {

    private PathPool pool;

    public static final String PROGRAM_DIR  = "PROGRAM_DIR";
    public static final String SAVE_DEFAULT = "SAVE_DEFAULT";

    private ArrayList<Path> paths;

    private PathPool()
    {
        paths = new ArrayList<Path>();
        determineEnvironment();
    }

    public PathPool getInstance()
    {
        if (pool == null)
        {
            pool = new PathPool();
        }
        return pool;
    }

    public Path get(String name)
    {
        return paths.stream()
                .filter(x -> x.equals(name))
                .findFirst().get();
    }

    public void determineEnvironment()
    {
        // TODO: Determine OS, generate paths accordingly
    }

}