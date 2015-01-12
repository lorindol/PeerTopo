package net.brotzeller.topeer.topo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by martin on 21.12.14.
 */
public class TopoOverview {
    /**
     * Topos found
     */
    public static Map<String, TopoInfo> ITEM_MAP = new LinkedHashMap<String, TopoInfo>();

    static {
        addItem(new TopoInfo("graefendorf-sued.topo", "Gräfendorf Südseite", "Südseite des Brückenpfeilers", 16));
        addItem(new TopoInfo("graefendorf-ost.topo", "Gräfendorf Ostseite", "Ostseite des Brückenpfeilers", 3));
        addItem(new TopoInfo("graefendorf-west.topo", "Gräfendorf Westseite", "Westseite des Brückenpfeilers", 2));
        addItem(new TopoInfo("graefendorf-nord.topo", "Gräfendorf Nordseite", "Nordseite des Brückenpfeilers", 14));

        addItem(new TopoInfo("eine.topo", "Debug Topo", "Test", 14));
    }

    public static void addItem(TopoInfo item) {
        ITEM_MAP.put(item.filename, item);
    }

    /**
     * Representation of single topo entry
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

        public String getName() {
            return name;
        }
        public String getDescription() {
            return "(" + routecount + ") " + description;
        }
    }
}
