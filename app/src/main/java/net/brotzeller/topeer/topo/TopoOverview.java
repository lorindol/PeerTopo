package net.brotzeller.topeer.topo;

import android.content.Context;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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
    }

    public static void addItem(TopoInfo item) {
        ITEM_MAP.put(item.filename, item);
    }

    /**
     * Representation of single topo entry
     */
    public static class TopoInfo implements Comparable{
        public String filename;
        public String name;
        public String description;
        public int routecount;
        public int[] histbins;

        public TopoInfo() {  }

        public TopoInfo(String filename, String name, String description, int routecount, int[] histbins) {
            this.filename = filename;
            this.name = name;
            this.description = description;
            this.routecount = routecount;
            this.histbins = histbins;
        }

        @Override
        public String toString() {
             return filename + " (" + routecount + ")";
         }

        public String getName() {
            return name;
        }
        public String getDescription() { return "(" + routecount + ") " + description; }
        public int[] getHistbins() { return histbins;}
        @Override
        public int compareTo(Object another) {
            return 0;
        }
    }
    public static class TopoComparator implements Comparator<TopoInfo> {
        private sortAspect aspect;
        public TopoComparator(sortAspect aspect) { this.aspect = aspect; }
        public int compare(TopoInfo a, TopoInfo b) {
            switch(aspect) {
                case filename:
                    return a.filename.compareTo(b.filename);
                case name:
                    return a.name.compareTo(b.name);
                case routecount:
                    return b.routecount - a.routecount;
                case beginner:
                    return b.histbins[0] - a.histbins[0];
                case expert:
                    return (b.histbins[3] - a.histbins[3])*100 + b.histbins[2] - a.histbins[2];
                default:
                    return 0;
            }
        }
    }
    public enum sortAspect {
        filename, name, routecount, beginner, expert
    }
}
