

package net.brotzeller.topeer.xml;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.math.BigInteger;
import java.util.List;


@Root(name="topo")
public class TopoType {

    @Element(required = true)
    public String name;
    @Element(required = true)
    public TopoType.Texts texts;
    @Element(required = false)
    public TopoType.Features features;
    @Element(required = true)
    public Imagetype image;
    @ElementList(inline=true, required = false)
    public List<Routetype> routes;
    @Attribute(name = "version")
    public String version;
    @Attribute(name = "ident")
    public String ident;

    @Root(name="features", strict=false)
    public static class Features {

        @Element(required = false)
        public String height;
        @Element(required = false)
        public Integer hikeminutes;
        @ElementList(inline=true, entry="alignment", required = false)
        public List<String> alignment;
        @ElementList(required = false, entry="sunny", inline=true)
        public List<String> sunny;
        @Element(required = false)
        public String children;
        @ElementList(required = false, inline=true, entry="inclination")
        public List<String> inclination;
        @Element(required = false)
        public TopoType.Features.Coordinates coordinates;

        public static class Coordinates {
            @Element(required=true, name = "lat")
            public double latitude;
            @Element(required=true, name = "long")
            public double longitude;
        }
    }


    @Root(name="texts", strict=false)
    public static class Texts {
        @Element(required = true)
        public String description;
        @Element(required = false)
        public String navigation;
        @Element(required = false)
        public String hike;
        @Element(required = false)
        public String author;
        @Element(required = false)
        public String license;
    }
    public enum InclinationEnum {
        vertical,
        overhanging,
        roof,
        slanting
    }
    public enum SunnyEnum {
        morning,
        afternoon,
        allday,
        shady
    }
    public enum AlignmentEnum {
        N,
        S,
        E,
        W,
        NE,
        NW,
        SE,
        SW
    }
}
