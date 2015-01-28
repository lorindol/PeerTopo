package net.brotzeller.topeer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import net.brotzeller.topeer.topo.TopoContent;

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
        return (LinearLayout)inflater.inflate(R.layout.fragment_page_text, container, false);
    }
}
