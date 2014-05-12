package de.mayflower.peertopo.app.util.route;

import de.mayflower.peertopo.app.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

public class RouteAdapter extends ArrayAdapter<RouteInfo> {
    private final Activity context;
    private final ArrayList<RouteInfo> routes;
    public RouteAdapter(Activity context, ArrayList<RouteInfo> routes)
    {
        super(context, R.layout.routeitem, routes);
        this.context = context;
        this.routes = routes;
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
            itemTemplate = inflater.inflate(R.layout.routeitem, null, true);
            holder = new ViewHolder();
            holder.Index = (TextView) itemTemplate.findViewById(R.id.Index);
            holder.Name = (TextView) itemTemplate.findViewById(R.id.Name);
            holder.Difficulty = (TextView) itemTemplate.findViewById(R.id.Difficulty);
            itemTemplate.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) existingTemplate.getTag();
        }

        // Set the text to display
        RouteInfo info = routes.get(position);
        holder.Index.setText(info.Index);
        holder.Name.setText(info.Name);
        holder.Difficulty.setText(info.Difficulty);
        return itemTemplate;
    }
    static class ViewHolder {
        public TextView Index;
        public TextView Name;
        public TextView Difficulty;
        public TextView Description;
    }
}
