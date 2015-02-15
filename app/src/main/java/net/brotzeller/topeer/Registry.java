package net.brotzeller.topeer;

import net.brotzeller.topeer.topo.TopoOverview;

/**
 * Created by martin on 15.02.15.
 */
public class Registry {

    private TopoOverview.sortAspect TopoSort = TopoOverview.sortAspect.name;

    private static final class InstanceHolder {
        static final Registry INSTANCE = new Registry();
    }

    private Registry () {}
    public static Registry getInstance () {
        return InstanceHolder.INSTANCE;
    }

    public void setTopoSort(TopoOverview.sortAspect sort) {
        TopoSort = sort;
    }
    public TopoOverview.sortAspect getTopoSort() {
        return TopoSort;
    }

}
