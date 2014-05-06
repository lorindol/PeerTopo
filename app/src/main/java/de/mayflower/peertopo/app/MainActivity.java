package de.mayflower.peertopo.app;

import de.mayflower.peertopo.app.util.TopoInfo;
import de.mayflower.peertopo.app.util.TopoAdapter;
import de.mayflower.peertopo.app.util.TopoGatherer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.AdapterView;
import android.content.Intent;
import android.widget.ListView;
import java.util.ArrayList;

import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;

public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            showTopos();
        } catch (Exception e) {
            Log.v("MainActivity", "Exception: "+e.getMessage());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goShowTopo(String filename) {
        Intent i = new Intent(this, ShowTopoActivity.class);
        i.putExtra("file", filename);
        startActivity(i);
    }

    private void showTopos() {
        ArrayList<TopoInfo> items;

        ListView listTopos = (ListView)findViewById(R.id.listTopos);

        items = TopoGatherer.getAllTopos();
        TopoAdapter adapter = new TopoAdapter(this, items);
        listTopos.setAdapter(adapter);
        listTopos.setOnItemClickListener(adapter);
    }
}
