package de.mayflower.peertopo.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.content.Intent;



public class MainActivity extends Activity {

    private Button buttonConnect;
    private View.OnClickListener connectTapListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonConnect = (Button) findViewById(R.id.pressme);
        connectTapListener = new View.OnClickListener() {
            public void onClick(View v) {
                goShowTopo();
            }
        };
        buttonConnect.setOnClickListener(connectTapListener);

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

    private void goShowTopo() {
        Intent i = new Intent(this, ShowTopoActivity.class);
        startActivity(i);
    }
}
