package net.brotzeller.topeer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.viewpagerindicator.IconPageIndicator;
import com.viewpagerindicator.PageIndicator;

import net.brotzeller.topeer.exception.TopoException;
import net.brotzeller.topeer.topo.TopoContent;
import net.brotzeller.topeer.topo.TopoOverview;

import java.util.List;
import java.util.Vector;

import static net.brotzeller.topeer.TopoDetailFragment.*;

public class TopoPagedDetailActivity extends FragmentActivity {
	/** maintains the pager adapter*/
	private TopoPageAdapter mPagerAdapter;
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_topo_paged_detail);
		//initialsie the pager
        TopoContent topo = null;
        if (null != getIntent().getStringExtra(ARG_ITEM_ID)) {
            // TODO: find out how to use a loader
            String filename = TopoOverview.ITEM_MAP.get(getIntent().getStringExtra(ARG_ITEM_ID)).filename;
            topo = loadTopo(filename);
            setTitle(topo.getText("name"));
        }
		this.initialisePaging(topo);
	}

	/**
	 * Initialise the fragments to be paged
	 */
	private void initialisePaging(TopoContent topo) {

		List<Fragment> fragments = new Vector<Fragment>();
        // TODO: add checks if data available, add icons for indicator here.
        TopoPageRouteFragment routes = (TopoPageRouteFragment)
                Fragment.instantiate(this, TopoPageRouteFragment.class.getName());
        routes.setTopo(topo);
        fragments.add((Fragment)routes);
        TopoPageFeatureFragment features = (TopoPageFeatureFragment)
                Fragment.instantiate(this, TopoPageFeatureFragment.class.getName());
        features.setTopo(topo);
        fragments.add((Fragment)features);
        TopoPageTextFragment texts = (TopoPageTextFragment)
                Fragment.instantiate(this, TopoPageTextFragment.class.getName());
        texts.setTopo(topo);
        fragments.add((Fragment)texts);
		this.mPagerAdapter  = new TopoPageAdapter(super.getSupportFragmentManager(), fragments);
		//
		ViewPager pager = (ViewPager)super.findViewById(R.id.viewpager);
		pager.setAdapter(this.mPagerAdapter);
        PageIndicator mIndicator = (IconPageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(pager);
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topo_paged_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private TopoContent loadTopo(String filename) {
        try {
            TopoContent Item = new TopoContent(filename, this);
            Item.initialize();
            return Item;
        } catch (TopoException e) {
            Intent i = new Intent(this, TopoListActivity.class);
            startActivity(i);
        }
        return null;
    }
}