package net.brotzeller.topeer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import net.brotzeller.topeer.adapter.FeaturePageAdapter;
import net.brotzeller.topeer.topo.TopoContent;

/**
 * Created by martin on 27.01.15.
 */
public class TopoPageFeatureFragment extends Fragment {
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
        View rootView = inflater.inflate(R.layout.fragment_page_feature, container, false);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        topo = ((TopoProvider) this.getActivity()).getTopo();

        if (topo != null) {
            final GridView textList = (GridView) this.getActivity().findViewById(R.id.featureGrid);

            FeaturePageAdapter a = new FeaturePageAdapter(
                    getActivity(),
                    topo.features
            );
            textList.setAdapter(a);
        }

    }
}
