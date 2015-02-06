package net.brotzeller.topeer.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import net.brotzeller.topeer.R;

abstract public class PageAdapter extends BaseAdapter {
    protected Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    protected String lookupString(String identifier) {
        String description;
        try {
            int tid = this.context.getResources().getIdentifier(identifier, "string", this.context.getPackageName());
             description = this.context.getString(tid);
        } catch (Exception e) {
             Log.e("Topeer", "Could not find identifier " + identifier + " in strings: " + e.getMessage());
             description = "########";
        }
        return description;
    }
    protected int lookupIcon(String identifier) {
        int icon_drawable;
        try {
            int icid = this.context.getResources().getIdentifier(identifier, "string", context.getPackageName());
            icon_drawable = this.context.getResources().getIdentifier(this.context.getString(icid), "drawable", this.context.getPackageName());
        } catch (Exception e) {
            Log.e("Topeer", "Could not find iconi " + identifier + ": " + e.getMessage());
            icon_drawable = R.drawable.box;
        }
        return icon_drawable;
    }

    protected View getPageView(int Idee, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(Idee, parent, false);
        return view;
    }
}
