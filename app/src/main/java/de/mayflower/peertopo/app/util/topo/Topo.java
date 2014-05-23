package de.mayflower.peertopo.app.util.topo;

import android.app.Activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.zip.ZipInputStream;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.zip.ZipEntry;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.util.HashMap;
import java.util.Stack;

import de.mayflower.peertopo.app.util.file.xmlAnalyzer;
import de.mayflower.peertopo.app.util.route.RouteInfo;

public class Topo
{

    public Map<String, byte[]> content;
    public ArrayList<RouteInfo> Routes;
    public Map<String, String> texts;
    public String imagename;
    protected Activity activity;
    protected String archivename;
    protected TopoInfo info = null;

    public Topo(Activity activity, String toponame) {
        initialize(activity, toponame);
    }
    public String toString() {
        return "content: "+content+"\nRoutes: "+Routes +"\ntexts: "+texts+"\nImage"+
                imagename+"\nactivity: "+activity+"\nFile: "+archivename;
    }
    protected void initialize(Activity a, String toponame) {
        content = new HashMap<String, byte[]>();
        Routes = null;
        activity = a;
        archivename = toponame;
        texts = new HashMap<String, String>();
    }


    public TopoInfo getInfo() {
        // TODO: make sure topo has been loaded
        if (info == null) {
            info = new TopoInfo();
            info.routecount = Routes.size();
            info.name = texts.get("name");
            info.filename = archivename;
            info.description = texts.get("description");

            if (info.name == null) {
                info.name = archivename;
            }
            if (info.description == null) {
                info.description = "";
            }
            info.name = info.name + " (ext)";
        }
        return info;
    }

    protected void analyzeTextfile(byte[] filename) throws XmlPullParserException, IOException
    {
        xmlAnalyzer xmlAnalyzer = de.mayflower.peertopo.app.util.file.xmlAnalyzer.getInstance();
        xmlAnalyzer.setInput(filename);
        xmlAnalyzer.analyzeFile();
    }

    protected void checkTopoIntegrity()
    {
        ArrayList<String> message = new ArrayList<String>();
        if (imagename.isEmpty()) {
            message.add("No imagename found in topo");
        } else if (content.get(imagename).length  == 0) {
            message.add("Imagefile not found");
        }

        if(Routes.size() == 0) {
            message.add("No routes found in topo");
        } else {
            for (RouteInfo r : Routes) {
                if (!r.checkIntegrity()) {
                    message.add("Incomplete routes found in topo");
                    break;
                }
            }
        }
        if (!message.isEmpty()) {
            Toast.makeText(activity, "In "+archivename+": "+message, Toast.LENGTH_LONG).show();
        }
    }
}