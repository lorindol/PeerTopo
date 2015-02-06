package net.brotzeller.topeer.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.brotzeller.topeer.R;
import net.brotzeller.topeer.xml.TopoType;

import java.util.ArrayList;
import java.util.HashMap;

import static net.brotzeller.topeer.R.plurals.hikeminutes_value;

public class FeaturePageAdapter extends PageAdapter {
    protected Context context;
    private TopoType.Features mData;
    private ArrayList<String> mKeys;
    private ArrayList<String> mValues;
    private ArrayList<Integer> mIcons;
    public FeaturePageAdapter(Context context, TopoType.Features features){
        this.context = context;
        setContext(context);
        collectFeatures(features);
    }

    @Override
    public int getCount() {
        return mKeys.size();
    }

    @Override
    public Object getItem(int position) {
        return mValues.get(position);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        View view = getPageView(R.layout.feature_grid_cell_layout, parent);

        ImageView iconview = (ImageView) view.findViewById(R.id.icon);
        iconview.setImageResource(mIcons.get(pos));

        TextView textView1 = (TextView) view.findViewById(R.id.title);
        textView1.setText(mKeys.get(pos));

        TextView textView2 = (TextView) view.findViewById(R.id.description);
        textView2.setText(mValues.get(pos));

        return view;
    }

    protected void collectFeatures(TopoType.Features features) {
        mKeys = new ArrayList<String>();
        mValues = new ArrayList<String>();
        mIcons = new ArrayList<Integer>();
        int pos = 0;
        if (features.height !=  null) {
            mKeys.add(pos, lookupString("height"));
            mValues.add(pos, features.height);
            mIcons.add(pos, lookupIcon("icon_height"));
            pos++;
        }
        if (features.hikeminutes != null) {
            Resources res = context.getResources();
            String min;
            try {
                min = res.getQuantityString(hikeminutes_value, features.hikeminutes, features.hikeminutes);
            }catch (Exception e) {
                Log.e("Topeer", "Error getting quantity String "
                +hikeminutes_value+" for a value of "+features.hikeminutes);
                Log.e("Topeer", e.getClass()+": "+e.getMessage());
                min="#########";

            }
            mKeys.add(pos, lookupString("hikeminutes"));
            mValues.add(pos, min);
            mIcons.add(pos, lookupIcon("icon_hike"));
            pos++;
        }
        if (features.children != null) {
            mKeys.add(pos, lookupString("children"));
            mValues.add(pos, lookupString("children_" + features.children));
            mIcons.add(pos, lookupIcon("icon_children_" + features.children));
            pos++;
        }
        for(String ali : features.alignment) {
            mKeys.add(pos, lookupString("alignment"));
            mValues.add(pos, lookupString("alignment_" + ali.toLowerCase()));
            mIcons.add(pos, lookupIcon("icon_alignment_" + ali.toLowerCase()));
            pos++;
        }
        for(String inc : features.inclination) {
            mKeys.add(pos, lookupString("inclination"));
            mValues.add(pos, lookupString("inclination_" + inc));
            mIcons.add(pos, lookupIcon("icon_inclination_" + inc));
            pos++;
        }
        for(String sun : features.sunny) {
            mKeys.add(pos, lookupString("sunny"));
            mValues.add(pos, lookupString("sunny_" + sun));
            mIcons.add(pos, lookupIcon("icon_sunny_" + sun));
            pos++;
        }

    }
}
