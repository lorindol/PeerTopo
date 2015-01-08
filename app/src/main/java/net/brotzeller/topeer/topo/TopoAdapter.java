package net.brotzeller.topeer.topo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.List;

/**
 * Created by martin on 08.01.15.
 */
public class TopoAdapter extends ArrayAdapter<TopoOverview.TopoInfo> {
    private final Context context;

    public TopoAdapter(Context context, List<TopoOverview.TopoInfo> objects) {
        super(context, android.R.layout.simple_list_item_activated_2, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(android.R.layout.simple_list_item_activated_2, parent, false);

        TextView textView1 = (TextView) view.findViewById(android.R.id.text1);
        textView1.setText(getItem(position).getName());

        TextView textView2 = (TextView) view.findViewById(android.R.id.text2);
        textView2.setText(getItem(position).getDescription());

        return view;

    }


}
