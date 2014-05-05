package de.mayflower.peertopo.app.util;

import java.io.IOException;
import java.io.File;
import java.io.FilenameFilter;

import java.util.ArrayList;

/**
 * Created by martin on 05.05.14.
 */
public class TopoGatherer {

    public static ArrayList<TopoInfo> getAllTopos() {
        ArrayList<TopoInfo> topos = new ArrayList<TopoInfo>();

        topos.add(new TopoInfo("graefendorf-sued.topo", "Gräfendorf Südseite", "Südseite des Brückenpfeilers", 16));
        topos.add(new TopoInfo("graefendorf-west.topo", "Gräfendorf Westseite", "Westseite des Brückenpfeilers", 2));

        return topos;
    }
    protected void getToposFromDir(String Dir) throws IOException
    {
        File f = new File(Dir); // current directory

        FilenameFilter textFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(".topo")) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        File[] files = f.listFiles(textFilter);
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.print("directory:");
            } else {
                System.out.print("     file:");
            }
            System.out.println(file.getCanonicalPath());
        }
    }
}
