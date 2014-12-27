package net.brotzeller.topeer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import net.brotzeller.topeer.exception.TopoException;
import net.brotzeller.topeer.topo.TopoContent;
import net.brotzeller.topeer.topo.TopoOverview;

/**
 * A fragment representing a single Topo detail screen.
 * This fragment is either contained in a {@link TopoListActivity}
 * in two-pane mode (on tablets) or a {@link TopoDetailActivity}
 * on handsets.
 */
public class TopoDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private TopoContent mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TopoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            // TODO: find out how to use a loader
            String filename = TopoOverview.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID)).filename;
            Activity a = getActivity();
            try {
                mItem = new TopoContent(filename, a);
                mItem.initialize();
            } catch (TopoException e) {
                Toast.makeText(a, "Fähler: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(a, TopoListActivity.class);
                startActivity(i);
            }
            //mItem = TopoOverview.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_topo_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.topo_detail)).setText(mItem.texts.toString() +" hurz!");
        }

        return rootView;
    }
}
