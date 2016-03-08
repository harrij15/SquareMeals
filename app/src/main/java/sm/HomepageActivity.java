package sm;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TabHost;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by putriz on 2/23/2016.
 */

public class HomepageActivity extends AppCompatActivity {

    TabHost tabhost;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        // Retrieve username from MainActivity
        //String username = getIntent().getExtras().getString("USERNAME");

        EditText username = (EditText)findViewById(R.id.user_name);
        if (getSupportActionBar() != null && username != null) {
            if (username.getText().length() != 0) {
                String name = username.getText().toString();
                getSupportActionBar().setTitle("Hello " + name + "!");
            } else {
                getSupportActionBar().setTitle("Hello Guest!");
            }
        }



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

        // Grid View that shows recipe recommendations in Tab 1
        GridView gridView = (GridView) findViewById(R.id.homepage_tab1_gridView);
        gridView.setAdapter(new HomepageButtonAdapter(this));

        // List View that shows recipes saved in cookbook
        ListView listView = (ListView) findViewById(R.id.cookbook_list);
        List listRecipe = new ArrayList();

        ArrayList<String> str = new ArrayList<String>();
        str.add("sugar"); str.add("water");

        listRecipe.add(new Recipe("Recipe 1",str,"fish","delicious",15));
        listRecipe.add(new Recipe("Recipe 2",str,"eggs","awesome",10));

/*       String[] recipes = new String[] {"RECIPE 1", "RECIPE 2", "RECIPE 3"};
        ArrayList<String> recipe_list = new ArrayList<String>();
        for (int i = 0; i < recipes.length; ++i){
            recipe_list.add(recipes[i]);
        }*/

        HomepageListArrayAdapter adapter = new HomepageListArrayAdapter(this, R.layout.homepage_list, listRecipe);
        listView.setAdapter(adapter);

        // -----------------------------------------------------------------------------------------

    } /* end of onCreate method */

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

} /* end of HompageAcitivity class */
