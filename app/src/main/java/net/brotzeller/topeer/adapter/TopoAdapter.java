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

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by martin on 08.01.15.
 */
public class TopoAdapter extends ArrayAdapter<TopoOverview.TopoInfo> {
    private final Context context;

    public TopoAdapter(Context context, List<TopoOverview.TopoInfo> objects) {
        super(context, R.layout.topo_row_layout, objects);

        Collections.sort(objects, new TopoOverview.TopoComparator(TopoOverview.sortAspect.name));

        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.topo_row_layout, parent, false);
        view.setBackgroundResource(R.drawable.gradient_route);

        TextView textView1 = (TextView) view.findViewById(R.id.title);
        textView1.setText(getItem(position).getName());

        TextView textView2 = (TextView) view.findViewById(R.id.subtitle);
        textView2.setText(getItem(position).getDescription());

        TextView explanation = (TextView) view.findViewById(R.id.explanation);
        explanation.setText(getItem(position).filename);

        BitmapDrawable hist = new Histogram(context.getResources()).hist(getItem(position).getHistbins());
        ImageView moreImage = (ImageView) view.findViewById(R.id.histogram);
        moreImage.setImageDrawable(hist);


        return view;

    }


}
