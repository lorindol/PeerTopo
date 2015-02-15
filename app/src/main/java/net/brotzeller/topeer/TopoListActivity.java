package net.brotzeller.topeer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import net.brotzeller.topeer.adapter.TopoAdapter;
import net.brotzeller.topeer.topo.TopoOverview;


/**
 * An activity representing a list of Topos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link TopoPagedDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p/>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link TopoListFragment} and the item details
 * (if present) is a {@link TopoDetailFragment}.
 * <p/>
 * This activity also implements the required
 * {@link TopoListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class TopoListActivity extends FragmentActivity
        implements TopoListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topo_list);

        if (findViewById(R.id.topo_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((TopoListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.topo_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link TopoListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(TopoDetailFragment.ARG_ITEM_ID, id);
            TopoDetailFragment fragment = new TopoDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.topo_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, TopoPagedDetailActivity.class);
            detailIntent.putExtra(TopoDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_topo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Registry registry = Registry.getInstance();
        TopoAdapter topoAdapter = (TopoAdapter) ((TopoListFragment)getSupportFragmentManager()
                .findFragmentById(R.id.topo_list)).getListAdapter();
        switch(id) {
            case R.id.settings_sort_file:
                registry.setTopoSort(TopoOverview.sortAspect.filename);
                topoAdapter.sort();
                break;
            case R.id.settings_sort_name:
                registry.setTopoSort(TopoOverview.sortAspect.name);
                topoAdapter.sort();
                break;
            case R.id.settings_sort_routecount:
                registry.setTopoSort(TopoOverview.sortAspect.routecount);
                topoAdapter.sort();
                break;
            case R.id.settings_sort_beginner:
                registry.setTopoSort(TopoOverview.sortAspect.beginner);
                topoAdapter.sort();
                break;
            case R.id.settings_sort_expert:
                registry.setTopoSort(TopoOverview.sortAspect.expert);
                topoAdapter.sort();
                break;
            default:
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
