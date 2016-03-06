package sm;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TabHost;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by putriz on 2/23/2016.
 */


public class HomepageActivity extends AppCompatActivity {

    TabHost tabhost;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // Retrieve username from MainActivity
        String username = getIntent().getExtras().getString("USERNAME");
        getSupportActionBar().setTitle("Hello "+username+"!");


        // TABS ------------------------------------------------------------------------------------
        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();

            // Tab 1
            TabHost.TabSpec spec = host.newTabSpec("Top Picks for You");
            spec.setContent(R.id.tab1);
            spec.setIndicator("Top Picks for You");
            host.addTab(spec);

            // Tab 2
            spec = host.newTabSpec("My Cookbook");
            spec.setContent(R.id.tab2);
            spec.setIndicator("My Cookbook");
            host.addTab(spec);
        // -----------------------------------------------------------------------------------------

        // Grid View that shows recipe recommendations in Tab 1
        GridView gridView = (GridView) findViewById(R.id.homepage_tab1_gridView);
        gridView.setAdapter(new HomepageButtonAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hompage, menu);
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
}
