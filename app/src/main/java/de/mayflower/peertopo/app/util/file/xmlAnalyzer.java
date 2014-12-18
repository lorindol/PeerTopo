package de.mayflower.peertopo.app.util.file;

import org.apache.http.impl.conn.tsccm.RouteSpecificPool;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;
import de.mayflower.peertopo.app.util.route.RouteInfo;

public class xmlAnalyzer
{
    private static xmlAnalyzer instance;

    protected Stack<String> cellar;
    protected RouteInfo currentRoute;
    protected String name;
    protected String top;
    protected int eventType;
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
        if (!(this.pullParser instanceof XmlPullParser) &&
                !(this.pullParserFactory instanceof XmlPullParserFactory)) {
            try {
                this.pullParserFactory = pullParserFactory.newInstance();
                this.pullParser = pullParserFactory.newPullParser();
                this.pullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            } catch (XmlPullParserException e) {
                e.getMessage();
            }
        }
    }

    public void setInput(byte[] filename) throws FileNotFoundException, XmlPullParserException
    {
        this.pullParser.setInput(new ByteArrayInputStream(filename), null);
    }

    public void analyzeFile() throws XmlPullParserException {
        try {
            this.eventType = this.pullParser.getEventType();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        while (this.eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    this.routes = new ArrayList<RouteInfo>();
                    break;
                case XmlPullParser.START_TAG:
                    name = this.pullParser.getName();
                    if (name.equalsIgnoreCase("topo")) {
                        this.cellar.push(name.toLowerCase());
                    } else {
                        top = this.cellar.peek();
                        this.cellar.push(this.top + "::" + name.toLowerCase());
                    }
                    if (this.cellar.peek().endsWith("route")) {
                        this.currentRoute = new RouteInfo();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = this.pullParser.getName();
                    top = this.cellar.peek();
                    if (top.endsWith(name.toLowerCase())) {
                        this.cellar.pop();
                    } else {
                        String message = "Malformed XML: Endtag " + name + " Stacktop: " + this.cellar.peek();
                        // TODO: Throw something else?
                        throw new XmlPullParserException(message);
                    }
                    if (name.equalsIgnoreCase("route")) {
                        this.routes.add(this.currentRoute);
                    } else if (top.endsWith("route::name") && name.equalsIgnoreCase("name")) {
                        String currentName = this.currentRoute.getName();
                        this.currentRoute.setName((currentName != null) ? currentName : "");
                    } else if (top.endsWith("route::difficulty") && name.equalsIgnoreCase("difficulty")) {
                        String currentDifficulty = this.currentRoute.getDifficulty();
                        this.currentRoute.setDifficulty((currentDifficulty != null) ? currentDifficulty : "");
                    }
                    break;
                case XmlPullParser.TEXT:
                    String text = this.pullParser.getText();
                    top = this.cellar.peek();

                    if (top.endsWith("topo::image::name")) {
                        this.imagename = text;
                    } else if (top.endsWith("route::index")) {
                        this.currentRoute.setIndex(text);
                    } else if (top.endsWith("route::name")) {
                        this.currentRoute.setName(text);
                    } else if (top.endsWith("route::difficulty")) {
                        this.currentRoute.setDifficulty(text);
                    } else if (top.endsWith("topo::name")) {
                        this.texts.put("name", text);
                    } else if (top.endsWith("topo::description")) {
                        this.texts.put("description", text);
                    } else {
                        // TODO: find out how to do this correctly
                        // ignore text where none is expected, newlines are also yielded
                    }
                    break;
            }
            try {
                eventType = this.pullParser.next();
            } catch (IOException e) {
                throw new XmlPullParserException(e.getMessage());
            }
        }
    }

}
