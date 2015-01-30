package net.brotzeller.topeer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.HashMap;

public class HashMapAdapter extends BaseAdapter {
    private Context context;
    private HashMap<String, String> mData = new HashMap<String, String>();
    private String[] mKeys;
    public HashMapAdapter(Context context, HashMap<String, String> data){
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
        String Value = getItem(pos).toString();

        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(android.R.layout.simple_list_item_activated_2, parent, false);

        TextView textView1 = (TextView) view.findViewById(android.R.id.text1);
        textView1.setText(key);

        TextView textView2 = (TextView) view.findViewById(android.R.id.text2);
        textView2.setText(Value);

        //return convertView;
        return view;
    }
}
