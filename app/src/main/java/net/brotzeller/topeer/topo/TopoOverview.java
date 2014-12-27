package net.brotzeller.topeer.topo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by martin on 21.12.14.
 */
public class TopoOverview {
    /**
     * An array of sample (dummy) items.
     */
    public static List<TopoInfo> ITEMS = new ArrayList<TopoInfo>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, TopoInfo> ITEM_MAP = new HashMap<String, TopoInfo>();

    static {
        addItem(new TopoInfo("graefendorf-sued.topo", "Gräfendorf Südseite", "Südseite des Brückenpfeilers", 16));
        addItem(new TopoInfo("graefendorf-west.topo", "Gräfendorf Westseite", "Westseite des Brückenpfeilers", 2));
        addItem(new TopoInfo("graefendorf-nord.topo", "Gräfendorf Nordseite", "Nordseite des Brückenpfeilers", 14));
    }

    private static void addItem(TopoInfo item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.filename, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class TopoInfo {
         public String filename;
         public String name;
         public String description;
         public int routecount;

         public TopoInfo() {  }

         public TopoInfo(String filename, String name, String description, int routecount) {
             this.filename = filename;
             this.name = name;
             this.description = description;
             this.routecount = routecount;
         }

         @Override
         public String toString() {
             return filename + " (" + routecount + ")";
         }
    }
}
