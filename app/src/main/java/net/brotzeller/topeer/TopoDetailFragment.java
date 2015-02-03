package net.brotzeller.topeer;

import android.app.Activity;
import android.content.Context;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;


import net.brotzeller.topeer.exception.TopoException;
import net.brotzeller.topeer.topo.RouteAdapter;
import net.brotzeller.topeer.topo.TopoContent;
import net.brotzeller.topeer.topo.TopoOverview;
import net.brotzeller.topeer.xml.Routetype;

/**
 * A fragment representing a single Topo detail screen.
 * This fragment is either contained in a {@link TopoListActivity}
 * in two-pane mode (on tablets) or a {@link TopoPagedDetailActivity}
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
            final View contentView = rootView.findViewById(R.id.topoDiagram);
            final ImageView topoDiagram = (ImageView) contentView;
            Drawable bild = getTopoImage();
            topoDiagram.setImageDrawable(bild);

            //((TextView) rootView.findViewById(R.id.topo_detail)).setText(mItem.texts.toString() +" hurz!");
            final View listView = rootView.findViewById(R.id.routeList);
            final ListView routeList = (ListView) listView;

            final AdapterView.OnItemClickListener mOnClickListener
                    = new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                    onListItemClick((ListView)parent, v, position, id);
                }
            };
            routeList.setOnItemClickListener(mOnClickListener);

            routeList.setAdapter(new RouteAdapter(
                getActivity(),
                mItem.routes
            ));
        }

        return rootView;
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
        Routetype route = mItem.routes.get(position);
        if (route.description != null) {
            showDialog(getActivity(), route.name, route.description);
        }

    }


    private Drawable getTopoImage() {
        byte[] b;

        // TODO: catch error when no image was in file
        b = mItem.getImage();
        Drawable image = null;
        if (b != null) {
            image =  new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(b, 0, b.length));
            return image;
        } else {
            return null;
        }
    }
    public static final void showDialog( Context context, String title, String body )
    {
        AlertDialog alertDialog = new AlertDialog.Builder( context ).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(body);
        alertDialog.setButton
        (
            AlertDialog.BUTTON_POSITIVE, "OK",
            new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            }
        );
        alertDialog.show();
    }
}
