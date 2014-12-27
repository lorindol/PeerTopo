package net.brotzeller.topeer.topo;


import net.brotzeller.topeer.exception.TopoException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


public class xmlAnalyzer
{
    private static xmlAnalyzer instance;

    protected ArrayList<RouteInfo> routes;
    protected String imagename;
    public Map<String, String> texts;

    protected XmlPullParserFactory pullParserFactory;
    protected XmlPullParser pullParser;

    public static xmlAnalyzer getInstance() {
        if (xmlAnalyzer.instance == null) {
            xmlAnalyzer.instance = new xmlAnalyzer();
        }

        return xmlAnalyzer.instance;
    }

    private xmlAnalyzer() {
        if ((pullParser == null) || (pullParserFactory == null)) {
            try {
                pullParserFactory = XmlPullParserFactory.newInstance();
                pullParser = pullParserFactory.newPullParser();
                pullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            } catch (XmlPullParserException e) {
                e.getMessage();
            }
        }
    }


    public Map<String, String> getTexts() {
        return texts;
    }
    public ArrayList<RouteInfo> getRoutes() {
        return routes;
    }

    public void setInput(byte[] filename) throws XmlPullParserException
    {
        pullParser.setInput(new ByteArrayInputStream(filename), null);
    }

    public void analyzeFile() throws XmlPullParserException, TopoException {
        Stack<String> cellar = new Stack<String>();
        String top;
        int eventType;
        String name;
        RouteInfo currentRoute = new RouteInfo();

        try {
            eventType = pullParser.getEventType();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            throw new XmlPullParserException("Error getting input", pullParser, e);
        }

        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    routes = new ArrayList<RouteInfo>();
                    texts = new HashMap<String, String>();
                    break;
                case XmlPullParser.START_TAG:
                    name = pullParser.getName();
                    if (name.equalsIgnoreCase("topo")) {
                        cellar.push(name.toLowerCase());
                    } else {
                        top = cellar.peek();
                        cellar.push(top + "::" + name.toLowerCase());
                    }
                    if (cellar.peek().endsWith("route")) {
                        currentRoute = new RouteInfo();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = pullParser.getName();
                    top = cellar.peek();
                    if (top.endsWith(name.toLowerCase())) {
                        cellar.pop();
                    } else {
                        String message = "Malformed XML: Endtag " + name + " Stacktop: " + cellar.peek();
                        throw new TopoException(message);
                    }
                    if (name.equalsIgnoreCase("route")) {
                        routes.add(currentRoute);
                    } else if (top.endsWith("route::name") && name.equalsIgnoreCase("name")) {
                        String currentName = currentRoute.getName();
                        currentRoute.setName((currentName != null) ? currentName : "");
                    } else if (top.endsWith("route::difficulty") && name.equalsIgnoreCase("difficulty")) {
                        String currentDifficulty = currentRoute.getDifficulty();
                        currentRoute.setDifficulty((currentDifficulty != null) ? currentDifficulty : "");
                    }
                    break;
                case XmlPullParser.TEXT:
                    String text = pullParser.getText();
                    top = cellar.peek();

                    if (top.endsWith("topo::image::name")) {
                        imagename = text;
                    } else if (top.endsWith("route::index")) {
                            currentRoute.setIndex(text);
                    } else if (top.endsWith("route::name")) {
                        currentRoute.setName(text);
                    } else if (top.endsWith("route::difficulty")) {
                        currentRoute.setDifficulty(text);
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
            try {
                eventType = pullParser.next();
            } catch (IOException e) {
                throw new TopoException("I/O error while parsing: "+e.getMessage());
            }
        }
    }

}
