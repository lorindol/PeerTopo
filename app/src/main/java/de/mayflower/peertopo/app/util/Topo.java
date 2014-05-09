package de.mayflower.peertopo.app.util;

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

import android.util.Log;


/**
 * Created by martin on 02.05.14.
 */
public class Topo {
    public Map<String, byte[]> content;
    public ArrayList<RouteInfo> Routes;
    public Map<String, String> texts;
    public String imagename;
    protected Activity activity;
    protected String archivename;
    protected TopoInfo info = null;

    public Topo(Activity a, String toponame) {
        initialize(a, toponame);
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
                int separator = archivename.lastIndexOf("/")+1;
                info.name = archivename.substring(separator, archivename.length());
            }
            if (info.description == null) {
                info.description = "";
            }
            info.name = info.name + " (ext)";
        }
        return info;
    }
    public void loadTopo() {
        openArchive();
        readTextfile();
        checkTopoIntegrity();
    }
    protected void openArchive() {
        try {
            InputStream is;
            if (archivename.contains("/")) {
                is = new FileInputStream(archivename);
            } else {
                is = activity.getResources().getAssets().open(archivename);
            }
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(is));
            try {
                ZipEntry ze;
                while ((ze = zis.getNextEntry()) != null) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int count;
                    while ((count = zis.read(buffer)) != -1) {
                        baos.write(buffer, 0, count);
                    }
                    String filename = ze.getName();
                    byte[] bytes = baos.toByteArray();
                    content.put(filename, bytes);
                }
            } finally {
                zis.close();
            }
        } catch(Exception e) {
            Toast.makeText(activity, "Fähler: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public byte[] getTopoEntry(String filename) {
        return content.get(filename);
    }

    protected void readTextfile()
    {
        XmlPullParserFactory pullParserFactory;
        XmlPullParser pullParser = null;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            pullParser = pullParserFactory.newPullParser();
            pullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            if (pullParser != null) {
                pullParser.setInput(new ByteArrayInputStream(getTopoEntry("routes.xml")), null);
            }

            analyzeTextfile(pullParser);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    protected void analyzeTextfile(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        int eventType = parser.getEventType();
        Stack<String> keller = new Stack<String>();
        RouteInfo currentRoute = new RouteInfo();
        String name;
        String top;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    Routes = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("topo")) {
                        keller.push(name.toLowerCase());
                    } else {
                        top = keller.peek();
                        keller.push(top + "::" + name.toLowerCase());
                    }
                    if (keller.peek().endsWith("route")) {
                        currentRoute = new RouteInfo();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    top = keller.peek();
                    if (top.endsWith(name.toLowerCase())) {
                        keller.pop();
                    } else {
                        String message = "Malformed XML: Endtag " + name + " Stacktop: " + keller.peek();
                        throw new IOException(message);
                    }
                    if (name.equalsIgnoreCase("route")) {
                        Routes.add(currentRoute);
                    } else if (top.endsWith("route::name") && name.equalsIgnoreCase("name")) {
                        currentRoute.Name = (currentRoute.Name!=null) ? currentRoute.Name : "";
                    } else if (top.endsWith("route::difficulty") && name.equalsIgnoreCase("difficulty")) {
                        currentRoute.Difficulty = (currentRoute.Difficulty!=null) ? currentRoute.Difficulty : "";
                    }
                    break;
                case XmlPullParser.TEXT:
                    String text = parser.getText();
                    top = keller.peek();

                    if (top.endsWith("topo::image::name")) {
                        imagename = text;
                    } else if (top.endsWith("route::index")) {
                        currentRoute.Index = text;
                    } else if (top.endsWith("route::name")) {
                        currentRoute.Name = text;
                    } else if (top.endsWith("route::difficulty")) {
                        currentRoute.Difficulty = text;
                    } else if (top.endsWith("topo::name")) {
                        texts.put("name", text);
                    } else if (top.endsWith("topo::description")) {
                        texts.put("description", text);
                    } else {
                        // TODO: find out how to do this correctly
                        // ignore text where none is expected, newlines are also yielded
                    }
                    break;
            }
            eventType = parser.next();
        }

    }
    protected void checkTopoIntegrity() {
        ArrayList<String> message = new ArrayList<String>();
        if (imagename.isEmpty()) {
            message.add("No imagename found in topo");
        } else if (content.get(imagename).length  == 0) {
            message.add("Imagefile not found");
        }
        if(Routes.size() == 0) {
            message.add("No routes found in topo");
        } else {
            for(RouteInfo r : Routes) {
                if(!r.checkIntegrity()) {
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