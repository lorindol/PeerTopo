package net.brotzeller.topeer.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import net.brotzeller.topeer.Histogram;
import net.brotzeller.topeer.R;
import net.brotzeller.topeer.topo.TopoOverview;

import java.util.List;

/**
 * Created by martin on 08.01.15.
 */
public class TopoAdapter extends ArrayAdapter<TopoOverview.TopoInfo> {
    private final Context context;

    public TopoAdapter(Context context, List<TopoOverview.TopoInfo> objects) {
        super(context, R.layout.topo_row_layout, objects);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.topo_row_layout, parent, false);
        //View view = inflater.inflate(android.R.layout.simple_list_item_activatend_2, parent, false);
        view.setBackgroundResource(R.drawable.gradient_route);
        //view.setPadding(R.dimen.padding_topos_left, R.dimen.padding_topos_top, R.dimen.padding_topos_right, R.dimen.padding_topos_bottom);

        TextView textView1 = (TextView) view.findViewById(android.R.id.text1);
        textView1.setText(getItem(position).getName());

        TextView textView2 = (TextView) view.findViewById(android.R.id.text2);
        textView2.setText(getItem(position).getDescription());

        BitmapDrawable hist = new Histogram(context.getResources()).hist(getItem(position).getHistbins());
        ImageView moreImage = (ImageView) view.findViewById(R.id.histogram);
        moreImage.setImageDrawable(hist);


        return view;

    }


}
