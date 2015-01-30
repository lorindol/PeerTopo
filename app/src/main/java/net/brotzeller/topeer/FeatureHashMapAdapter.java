package net.brotzeller.topeer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;

public class FeatureHashMapAdapter extends BaseAdapter {
    private Context context;
    private HashMap<String, Pair<String, Integer>> mData = new HashMap<String, Pair<String, Integer>>();
    private String[] mKeys;
    public FeatureHashMapAdapter(Context context, HashMap<String, Pair<String, Integer>> data){
        this.context = context;
        mData  = data;
        mKeys = mData.keySet().toArray(new String[data.size()]);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(mKeys[position]);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        String key = mKeys[pos];
        Pair<String, Integer> content = (Pair<String, Integer>) getItem(pos);
        String value = content.first;
        Integer icon = content.second;

        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.feature_grid_cell_layout, parent, false);

        ImageView iconview = (ImageView) view.findViewById(R.id.icon);
        iconview.setImageResource(icon);

        TextView textView1 = (TextView) view.findViewById(R.id.title);
        textView1.setText(key);

        TextView textView2 = (TextView) view.findViewById(R.id.description);
        textView2.setText(value);

        //return convertView;
        return view;
    }
}
