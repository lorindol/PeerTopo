package net.brotzeller.topeer.adapter;

import android.content.Context;
import android.graphics.pdf.PdfDocument;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.brotzeller.topeer.R;

import java.util.HashMap;

public class TextPageAdapter extends PageAdapter {
    private Context context;
    private HashMap<String, String> mData = new HashMap<String, String>();
    private String[] mKeys;
    public TextPageAdapter(Context context, HashMap<String, String> data){
        this.context = context;
        super.setContext(context);
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

        View view = getPageView(R.layout.texts_row_layout, parent);

        /*
        int tid = context.getResources().getIdentifier(key, "string", context.getPackageName());
        String title = context.getString(tid);
        */
        String title = lookupString(key);

        TextView textView1 = (TextView) view.findViewById(R.id.title);
        textView1.setText(title);

        TextView textView2 = (TextView) view.findViewById(R.id.description);
        textView2.setText(Value);

        /*
        int icid = context.getResources().getIdentifier("icon_"+key, "string", context.getPackageName());
        int icon_drawable = context.getResources().getIdentifier(context.getString(icid), "drawable", context.getPackageName());
        */
        int icon_drawable = lookupIcon("icon_"+key);
        ImageView icon = (ImageView) view.findViewById(R.id.icon);
        icon.setImageResource(icon_drawable);
        //return convertView;
        return view;
    }
}
