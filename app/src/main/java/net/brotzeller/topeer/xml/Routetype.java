package net.brotzeller.topeer.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="route", strict=false)
public class Routetype {

    @Element(name="index", required = true)
    public String index;
    @Element(required = false)
    public String name;
    @Element(required = false)
    public String difficulty;
    @Element(required = false)
    public String description;

    public String toString() {
        return index + ": " + name + " (" + difficulty + ")";
    }
}
