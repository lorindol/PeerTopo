package net.brotzeller.topeer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.viewpagerindicator.IconPagerAdapter;

import java.util.List;

/**
 * Created by martin on 27.01.15.
 */
public class TopoPageAdapter extends FragmentPagerAdapter implements IconPagerAdapter {

    private List<Fragment> fragments;
    /**
     * @param fm
     * @param fragments
     */
    public TopoPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentPagerAdapter#getItem(int)
     */
    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getIconResId(int i) {
        switch(i % 3) {
            case 0:
                return R.drawable.icon_mountain_selector;
            case 1:
                return R.drawable.icon_sun_selector;
            case 2:
            default:
                return R.drawable.icon_text_selector;
        }
    }

    /* (non-Javadoc)
         * @see android.support.v4.view.PagerAdapter#getCount()
         */
    @Override
    public int getCount() {
        return this.fragments.size();
    }
}
