package de.mayflower.peertopo.app.util;


import de.mayflower.peertopo.app.MainActivity;
import de.mayflower.peertopo.app.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

import android.widget.Toast;
import java.util.ArrayList;
import android.widget.AdapterView.OnItemClickListener;

import android.util.Log;

/**
 * Created by martin on 04.05.14.
 */
public class TopoAdapter extends ArrayAdapter<TopoInfo> implements OnItemClickListener {
    private final Activity context;
    private final ArrayList<TopoInfo> topos;
    public TopoAdapter(Activity context, ArrayList<TopoInfo> topos)
    {
        super(context, R.layout.routeitem, topos);
        this.context = context;
        this.topos = topos;
    }

    @Override
    public View getView(int position, View existingTemplate, ViewGroup parent)
    {
        ViewHolder holder;
        View itemTemplate;

        itemTemplate = existingTemplate;
        if (existingTemplate == null)
        {
            LayoutInflater inflater = context.getLayoutInflater();
            itemTemplate = inflater.inflate(R.layout.topoitem, null, true);
            holder = new ViewHolder();
            holder.Name = (TextView) itemTemplate.findViewById(R.id.Name);
            holder.Description = (TextView) itemTemplate.findViewById(R.id.Description);
            holder.Routecount = (TextView) itemTemplate.findViewById(R.id.Routecount);
            itemTemplate.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) existingTemplate.getTag();
        }

        // Set the text to display
        TopoInfo info = topos.get(position);
        holder.Name.setText(info.name);
        holder.Description.setText(info.description);
        holder.Routecount.setText(String.format("(%d)",info.routecount));
        return itemTemplate;
    }

    public void onItemClick(AdapterView parent, View view, int position, long id) {
        TopoInfo info = topos.get(position);
        Toast.makeText(context, "You clicked "+info.filename, Toast.LENGTH_LONG).show();
        ((MainActivity)context).goShowTopo(info.filename);
    }



    static class ViewHolder {
        public TextView Name;
        public TextView Description;
        public TextView Routecount;
    }


}
