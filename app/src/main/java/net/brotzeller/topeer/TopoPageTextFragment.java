package net.brotzeller.topeer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import net.brotzeller.topeer.adapter.TextPageAdapter;
import net.brotzeller.topeer.topo.TopoContent;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by martin on 27.01.15.
 */
public class TopoPageTextFragment extends Fragment {
    protected TopoContent topo;

    @Deprecated
    public void setTopo(TopoContent newtopo) {
        topo = newtopo;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View rootView = inflater.inflate(R.layout.fragment_page_text, container, false);

        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();

        topo = ((TopoProvider) this.getActivity()).getTopo();

        if (topo != null) {
            final ListView textList = (ListView) this.getActivity().findViewById(R.id.textList);

            HashMap<String, String> t = (HashMap<String, String>) refurbish(topo.texts);
            TextPageAdapter a = new TextPageAdapter(
                    getActivity(),
                    t
            );
            textList.setAdapter(a);
        }
    }

    private LinkedHashMap<String, String> refurbish(Map<String, String> texts) {
        String desc = texts.get("description").replaceAll("[\\s\\r\\n]+", " ");
        String navi = texts.get("navigation").replaceAll("[\\s\\r\\n]+", " ");
        String hike = texts.get("hike").replaceAll("[\\s\\r\\n]+", " ");
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        map.put("description", desc);
        map.put("navigation", navi);
        map.put("hike", hike);
        for (String key: texts.keySet()) {
            if (key == "name" || key == "description" || key == "navigation" || key == "hike") {
                continue;
            } else {
                map.put(key, texts.get(key));
            }
        }
        return map;
    }
}
