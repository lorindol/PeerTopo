package net.brotzeller.topeer.xml;

import android.util.Log;

import net.brotzeller.topeer.exception.TopoException;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by martin on 04.01.15.
 */
public class XmlTopoReader {
    private TopoType topo;

    public XmlTopoReader() {  }

    public TopoType parseTopo(byte[] filecontent) throws TopoException {

        Serializer serializer = new Persister();

        String topostuff = new String(filecontent);

        Reader reader = new StringReader(topostuff);
        try {
            topo = serializer.read(TopoType.class, reader, false);
            return topo;
        } catch (Exception e) {
            throw new TopoException("Could not read topo file", e);
        }
    }

    public Map<String, String> getTexts() throws TopoException{
        if (topo == null) {
            throw new TopoException("Topo not loaded, can not get texts");
        }

        Map<String, String> map = new HashMap<String, String>();
        map.put("name", topo.name);
        if (topo.texts != null) {
            map.put("description", topo.texts.description);
            if (topo.texts.hike != null) {
                map.put("hike", topo.texts.description);
            }
            if (topo.texts.hike != null) {
                map.put("navigation", topo.texts.navigation);
            }
        }

        return map;
    }

    protected void generateFixture() {

        TopoType topo = new TopoType();
        topo.ident="testtopo";
        topo.version="1.0";
        topo.name="Test Topo";
        topo.texts = new TopoType.Texts();
        topo.features = new TopoType.Features();
        topo.routes = new ArrayList<Routetype>();

        topo.image = new Imagetype();
        topo.image.name = "image.png";

        Routetype route = new Routetype();
        route.index = "1";
        route.difficulty="6";
        route.name= "Testroute";
        topo.routes.add(route);

        route = new Routetype();
        route.index = "2";
        route.difficulty="5-";
        route.name= "Nocheine Route";
        route.description  = "total langweilig";
        topo.routes.add(route);

        route = new Routetype();
        route.index = "3";
        route.difficulty="5";
        route.name= "W00t";
        topo.routes.add(route);

        topo.texts.description = "Eine gaanz dolle Wand";
        topo.texts.navigation = "Hier lang";

        topo.features.inclination = new ArrayList<String>();
        topo.features.inclination.add("vertical");
        topo.features.inclination.add("slanting");

        topo.features.alignment = new ArrayList<String>();
        topo.features.alignment.add("NE");
        topo.features.alignment.add("E");

        topo.features.children = "yes";
        topo.features.height= "20m";
        topo.features.coordinates = new TopoType.Features.Coordinates();
        topo.features.coordinates.latitude = 50.9;
        topo.features.coordinates.longitude = 9.71;


        Serializer serializer = new Persister();
        StringWriter result = new StringWriter();

        try {
            serializer.write(topo, result);
        } catch (Exception e) {
            Log.i("fixture", e.getMessage());
            e.printStackTrace();
        }

        Log.i("fixture", result.toString());

    }
}
