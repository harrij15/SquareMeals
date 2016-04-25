package sm;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by putriz on 2/23/2016.
 * This class implements the layout of the homepage for an existing user,
 * including a recommendations tab and a cookbook tab (where the user's recipes are saved)
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

        String username, name;
        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
        } else {
            username = "";
            name = "";
        }

        String emptyString = "";

        if ((name != null && !name.equals("not found")) && !name.equals(emptyString)) {
            toolbar.setTitle("Hello " + name + "!");
        }
        else if (username != null && !username.equals(emptyString)) { // If username is filled
            toolbar.setTitle("Hello " + username + "!");
        }
        setSupportActionBar(toolbar);



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

        // RANDOM DATA FOR TESTING
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
        getMenuInflater().inflate(R.menu.menu_homepage, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();

        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
        }


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

        /*if (id == R.id.tab1_searchView) {
            onSearchRequested();
            return true;
        }      */

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putString("hello", "world");
        startSearch(null,false,appData,false);
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            return true;
        }
        return false;
    }



} /* end of HomepageAcitivity class */
