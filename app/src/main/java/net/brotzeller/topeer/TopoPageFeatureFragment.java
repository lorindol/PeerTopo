package net.brotzeller.topeer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import net.brotzeller.topeer.topo.TopoContent;

import java.util.HashMap;

/**
 * Created by martin on 27.01.15.
 */
public class TopoPageFeatureFragment extends Fragment {
    protected TopoContent topo;

    public void setTopo(TopoContent newtopo) {
        topo = newtopo;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View rootView = inflater.inflate(R.layout.fragment_page_feature, container, false);

        final GridView textList = (GridView) rootView.findViewById(R.id.featureGrid);

        FeatureHashMapAdapter a = new FeatureHashMapAdapter(
                getActivity(),
                (HashMap<String, Pair<String, Integer>>) topo.features
        );
        textList.setAdapter(a);

        //return (LinearLayout)inflater.inflate(R.layout.fragment_page_feature, container, false);
        return rootView;
    }
}
