package de.mayflower.peertopo.app.util;

import java.io.IOException;
import java.io.File;
import java.io.FilenameFilter;

import java.util.ArrayList;

import android.app.Activity;
import android.util.Log;

/**
 * Created by martin on 05.05.14.
 */
public class TopoGatherer {
    public static ArrayList<TopoInfo> topos = null;

    public static void initialize() {
        topos = new ArrayList<TopoInfo>();
    }

    public static ArrayList<TopoInfo> getAllTopos() {
        //ArrayList<TopoInfo> topos = new ArrayList<TopoInfo>();

        topos.add(new TopoInfo("graefendorf-sued.topo", "Gräfendorf Südseite", "Südseite des Brückenpfeilers", 16));
        topos.add(new TopoInfo("graefendorf-west.topo", "Gräfendorf Westseite", "Westseite des Brückenpfeilers", 2));
        topos.add(new TopoInfo("graefendorf-nord.topo", "Gräfendorf Nordseite", "Nordseite des Brückenpfeilers", 14));
        //topos.add(new TopoInfo("/mnt/ext_sdcard/PeerTopo/graefendorf-nord.topo", "Gräfendorf Nordseite (ext)", "Nordseite des Brückenpfeilers", 14));

        return topos;
    }
    public static void readToposFromDir(String Dir) throws IOException
    {
        File f = new File(Dir); // current directory

        FilenameFilter textFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(".topo")) {
                //if (lowercaseName.endsWith(".txt"))) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        Log.v("TopoGatherer", "Reading dir "+f.getCanonicalFile());
        //File[] files = f.listFiles();
        File[] files = f.listFiles(textFilter);
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    readToposFromDir(file.getAbsolutePath());
                    System.out.print("directory:");
                } else {
                    System.out.print("     file:");
                    Topo topo = new Topo((Activity)null, file.getCanonicalPath());
                    topo.loadTopo();
                    topos.add(topo.getInfo());

                }
                System.out.println(file.getCanonicalPath());
            }
        }
    }
}
