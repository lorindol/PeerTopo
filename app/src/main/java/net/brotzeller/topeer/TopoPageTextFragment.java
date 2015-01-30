package net.brotzeller.topeer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import net.brotzeller.topeer.topo.TopoContent;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by martin on 27.01.15.
 */
public class TopoPageTextFragment extends Fragment {
    protected TopoContent topo;

    public void setTopo(TopoContent newtopo) {
        topo = newtopo;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View rootView = inflater.inflate(R.layout.fragment_page_text, container, false);

        final ListView textList = (ListView) rootView.findViewById(R.id.textList);

        HashMap<String, String> t = (HashMap<String, String>) refurbish(topo.texts);
        HashMapAdapter a = new HashMapAdapter(
                getActivity(),
                t
        );
        textList.setAdapter(a);

        //return (LinearLayout)inflater.inflate(R.layout.fragment_page_text, container, false);
        return rootView;
    }

    private LinkedHashMap<String, String> refurbish(Map<String, String> texts) {
        String desc = texts.get("description").replaceAll("[\\s\\r\\n]+", " ");
        String navi = texts.get("navigation").replaceAll("[\\s\\r\\n]+", " ");
        String hike = texts.get("hike").replaceAll("[\\s\\r\\n]+", " ");
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("Beschreibung", desc);
        map.put("Anfahrt", navi);
        map.put("Zustieg", hike);
        // TODO: i18n
        for (String key: texts.keySet()) {
            if (key == "name" || key == "description" || key == "navigation" || key == "hike") {
               // nop
            } else {
                map.put(key, map.get(key));
            }
        }
        return map;
    }
}
