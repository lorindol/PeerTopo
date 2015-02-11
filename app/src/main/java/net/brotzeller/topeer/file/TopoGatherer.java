package net.brotzeller.topeer.file;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import net.brotzeller.topeer.TopoListActivity;
import net.brotzeller.topeer.exception.TopoException;
import net.brotzeller.topeer.topo.TopoContent;
import net.brotzeller.topeer.topo.TopoOverview;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

/**
 * Created by martin on 11.01.15.
 */
public class TopoGatherer {
    private static Context a;
    public static void readTopos(Context a) {
        //String downloaddir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        //String datadir = Environment.getDataDirectory().getAbsolutePath();
        TopoGatherer.a= a;

        try {
            readTopoAssets();
            //TopoGatherer.readToposFromDir(downloaddir);
            //TopoGatherer.readToposFromDir(datadir);
            Log.i("TopoGatherer", "Reading from external sdcard" );
            TopoGatherer.readToposFromDir("/mnt/ext_sdcard/PeerTopo");
            TopoGatherer.readToposFromDir("/storage/sdcard1/PeerTopo");
            TopoGatherer.readToposFromDir("/storage/sdcard0/PeerTopo");
        } catch (IOException e) {
            // if we cant read, that's that.
            Toast.makeText(a, e.getMessage(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(a, e.toString() + e.getMessage(), Toast.LENGTH_LONG).show();

        }
    }

    public static void readTopoAssets() throws IOException {
        Log.i("TopoGatherer", "searching assets");
        String[] files;
        files = a.getResources().getAssets().list("");
        for(String name : files) {
            Log.i("TopoGatherer", "trying asset "+name);
            int len = name.length();
            if (name.substring(len-5).equals(".topo")) {
                try {
                    addTopoToPool(name);
                } catch (TopoException e) {
                    Toast.makeText(TopoGatherer.a, "Topo "+name+ " could not be parsed.", Toast.LENGTH_LONG);
                    continue;
                }

            }
        }

    }
    protected static void readToposFromDir(String Dir) throws IOException, TopoException {
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

        Log.i("TopoGatherer", "Reading dir " + f.getCanonicalFile());
        //File[] files = f.listFiles();
        File[] files = f.listFiles(textFilter);
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    Log.i("TopoGatherer", "directory: "+file.getCanonicalPath());
                    readToposFromDir(file.getAbsolutePath());
                    System.out.print("directory:");
                } else {
                    Log.i("TopoGatherer", "file: " + file.getCanonicalPath());
                    TopoContent topo = new TopoContent(file.getCanonicalPath());
                    try {
                        addTopoToPool(file.getCanonicalPath());
                    } catch (Exception e) {
                        Toast.makeText(TopoGatherer.a, "Topo "+file.getCanonicalPath()+ " could not be parsed.", Toast.LENGTH_LONG);
                        continue;
                    }
                }
            }
        }
    }
    protected static void addTopoToPool(String file) throws TopoException {

        TopoContent topo = new TopoContent(file, a);
        topo.initialize();
        TopoOverview.TopoInfo info = topo.getInfo();
        TopoOverview.addItem(info);
    }

}
