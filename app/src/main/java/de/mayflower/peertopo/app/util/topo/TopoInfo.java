package de.mayflower.peertopo.app.util.topo;

/**
 * Created by martin on 04.05.14.
 */
public class TopoInfo
{
    public String filename;
    public String name;
    public String description;
    public int routecount;

    public TopoInfo()
    {

    }

    public TopoInfo(String filename, String name, String description, int routecount) {
        this.filename = filename;
        this.name = name;
        this.description = description;
        this.routecount = routecount;
    }

    public String toString() {
        return "Name: " + this.name + ", Description: "
                + this.description + ", Routecount: "
                + String.valueOf(this.routecount) + ", Filename: " + this.filename;
    }
}
