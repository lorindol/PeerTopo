package net.brotzeller.topeer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.LinearLayout;
import android.widget.ListView;

import net.brotzeller.topeer.topo.RouteAdapter;
import net.brotzeller.topeer.topo.TopoContent;
import net.brotzeller.topeer.xml.Routetype;

/**
 * Created by martin on 27.01.15.
 */
public class TopoPageRouteFragment extends Fragment {
    protected TopoContent topo;

    public void setTopo(TopoContent newtopo) {
        topo = newtopo;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View rootView = inflater.inflate(R.layout.fragment_page_route, container, false);

        // Show the dummy content as text in a TextView.
        if (topo != null) {
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
                    topo.routes
            ));
        }

        return rootView;
    }

    public void onListItemClick(ListView listView, View view, int position, long id) {
        Routetype route = topo.routes.get(position);
        if (route.description != null) {
            showDialog(getActivity(), route.name, route.description);
        }

    }

    private Drawable getTopoImage() {
        byte[] b;

        // TODO: catch error when no image was in file
        b = topo.getImage();
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
