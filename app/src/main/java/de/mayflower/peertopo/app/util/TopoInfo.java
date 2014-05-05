package de.mayflower.peertopo.app.util;

/**
 * Created by martin on 04.05.14.
 */
public class TopoInfo {
    public String filename;
    public String name;
    public String description;
    public int routecount;

    public TopoInfo() {}
    public TopoInfo(String f, String n, String d, int c) {
        filename = f;
        name = n;
        description = d;
        routecount = c;
    }

    public String toString() {
        return "Name: " + name + ", Description: "
                + description + ", Routecount: "
                + routecount + ", Filename: " + filename;
    }
}
