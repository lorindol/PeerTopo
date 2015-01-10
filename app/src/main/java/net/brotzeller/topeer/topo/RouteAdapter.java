package net.brotzeller.topeer.topo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.brotzeller.topeer.R;
import net.brotzeller.topeer.xml.Routetype;

import java.util.List;

/**
 * Created by martin on 10.01.15.
 */
public class RouteAdapter extends ArrayAdapter<Routetype> {
    private final Context context;

    public RouteAdapter(Context context, List<Routetype> objects) {
        super(context, R.layout.route_row_layout, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.route_row_layout, parent, false);
        Routetype item = getItem(position);

        TextView textView0 = (TextView) view.findViewById(R.id.routeidx);
        textView0.setText(item.index);

        TextView textView1 = (TextView) view.findViewById(R.id.routelabel);
        textView1.setText(item.name);

        TextView textView2 = (TextView) view.findViewById(R.id.routedifficulty);
        textView2.setText(item.difficulty);

        TextView textView3 = (TextView) view.findViewById(R.id.routemore);
        textView3.setText((item.description==null)?"":"...");

        return view;

    }
}
