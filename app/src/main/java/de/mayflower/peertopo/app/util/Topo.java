package de.mayflower.peertopo.app.util;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


import android.content.res.AssetManager;
/**
 * Created by martin on 27.04.14.
 */
public class Topo {
    private ArrayList<RouteInfo> Routes;


    private AssetManager assets;

    public Topo (AssetManager assets) {
        Routes = null;
        this.assets = assets;
    }
    public ArrayList<RouteInfo> getRoutes() {
        if (Routes == null) {
            loadRoute();
        }
        return Routes;
    }
    protected void loadRoute()
    {
        XmlPullParserFactory pullParserFactory;
        XmlPullParser pullParser = null;
        InputStream asset;

        try {
            pullParserFactory = XmlPullParserFactory.newInstance();
            pullParser = pullParserFactory.newPullParser();
            pullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

            asset = assets.open("routes.xml");
            if (pullParser != null) {
                try {
                    pullParser.setInput(asset, null);
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
            }

            this.parseXML(pullParser);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseXML(XmlPullParser parser) throws XmlPullParserException, IOException
    {
        int eventType = parser.getEventType();
        RouteInfo currentRoute = null;

        boolean setName = false;
        boolean setDifficulty = false;
        boolean setIndex = false;

        Routes = null;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name;

            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    Routes = new ArrayList<RouteInfo>();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();

                    if (name.equalsIgnoreCase("route")) {
                        currentRoute = new RouteInfo();
                    } else if (currentRoute != null) {
                        if (name.equalsIgnoreCase("name")) {
                            currentRoute.setName(parser.nextText());
                            setName = true;
                        } else if (name.equals("difficulty")) {
                            currentRoute.setDifficulty(parser.nextText());
                            setDifficulty = true;
                        } else if (name.equals("index")) {
                            currentRoute.setIndex(parser.nextText());
                            setIndex = true;
                        }
                        if (setName && setIndex && setDifficulty) {
                            Routes.add(currentRoute);
                            currentRoute = null;
                            setName = false;
                            setIndex = false;
                            setDifficulty = false;
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

}
