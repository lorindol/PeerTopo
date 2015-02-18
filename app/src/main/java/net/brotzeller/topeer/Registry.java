package net.brotzeller.topeer;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import net.brotzeller.topeer.topo.TopoOverview;

/**
 * Created by martin on 15.02.15.
 */
public class Registry {

    private Activity activity;
    private SharedPreferences sharedPref;
    private TopoOverview.sortAspect TopoSort = TopoOverview.sortAspect.name;

    private static final class InstanceHolder {
        static final Registry INSTANCE = new Registry();
    }

    private Registry () {}
    public void initialize(Activity a) {
        activity = a;
        sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
    }


    private void save (String key, int value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    private int load (String key, int defstring) {
        int defaultValue = TopoOverview.sortAspect.valueOf(activity.getResources().getString(defstring)).ordinal();
        int actualValue = sharedPref.getInt(key, defaultValue);
        return actualValue;
    }
    public static Registry getInstance () {
        return InstanceHolder.INSTANCE;
    }

    public void setTopoSort(TopoOverview.sortAspect sort) {
        TopoSort = sort;
        save("TopoSort", TopoSort.ordinal());
    }
    public TopoOverview.sortAspect getTopoSort() {
        int value = load("TopoSort", R.string.settings_sort_default);
        TopoSort = TopoOverview.sortAspect.values()[value];
        return TopoSort;
    }

}
