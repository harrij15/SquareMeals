package sm;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TabHost;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by putriz on 2/23/2016.
 */

public class HomepageActivity extends AppCompatActivity {

    TabHost tabhost;
    private Context context;
    ImageView[] imageViewArray;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        // TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);

        String username, name, diet, json;
        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
            diet = getIntent().getExtras().getString("DIET");
            json = getIntent().getExtras().getString("JSON");
        } else {
            username = "";
            name = "";
            diet = "";
            json = "";
        }

        String emptyString = "";

        if (name != null && !name.equals(emptyString)) {
            toolbar.setTitle("Hello " + name + "!");
        }
        else if (username != null && !username.equals(emptyString)) { // If username is filled
            toolbar.setTitle("Hello " + username + "!");
        } else {
            toolbar.setTitle("Hello Guest!");
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
        final ListView listView = (ListView) findViewById(R.id.cookbook_list);
        final List listRecipe = new ArrayList();

        JSONObject obj;
        final List<SearchResult> recipeList = new ArrayList<>();
        index = 0;
        // Parse json string to get desired info
        try {
            obj = new JSONObject(json);
            JSONArray matchesArray = obj.getJSONArray("matches");
            imageViewArray = new ImageView[matchesArray.length()];

            if (matchesArray.length()==0) {
                TextView searchResultsText = (TextView) findViewById(R.id.search_results_text);
                searchResultsText.setText("Oops! We couldn't find anything :(");
            }


            for (int i = 0; i < matchesArray.length(); ++i) {

                final String recipe = matchesArray.getJSONObject(i).getString("recipeName");
                final String ingredientString = matchesArray.getJSONObject(i).getString("ingredients");
                final String imageString = matchesArray.getJSONObject(i).getString("smallImageUrls");
                final String cook_time = matchesArray.getJSONObject(i).getString("totalTimeInSeconds");
                final ArrayList<String> ingredients = parseIngredientsList(ingredientString);
                final ImageView imageView = new ImageView(this);

                new AsyncTask<Void,Void,Void>() {
                    String newImageString;
                    Drawable drawable;
                    @Override
                    protected Void doInBackground(Void... params) {
                        newImageString = parseImage(imageString);
                        drawable = LoadImageFromWebOperations(newImageString);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {

                        if (drawable != null) {

                            imageView.setImageDrawable(drawable);
                            imageViewArray[index] = imageView;
                            index++;

                            int time = -1;

                            try {
                                time = Integer.parseInt(cook_time);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (imageViewArray[index-1] != null) {
                                listRecipe.add(new SearchResult(recipe, ingredients, imageViewArray[index - 1], recipe, time, newImageString));
                                SearchResultsAdapter adapter = new SearchResultsAdapter(getApplicationContext(), R.layout.homepage_list, listRecipe);
                                listView.setAdapter(adapter);
                            }
                        } else {
                            throw new RuntimeException("Drawable is null!");
                        }
                    }
                }.execute();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }



       /* ArrayList<String> str = new ArrayList<String>();
        str.add("sugar"); str.add("water");

        listRecipe.add(new Recipe("Recipe 1",str,"fish","delicious",15));
        listRecipe.add(new Recipe("Recipe 2",str,"eggs","awesome",10));*/

/*       String[] recipes = new String[] {"RECIPE 1", "RECIPE 2", "RECIPE 3"};
        ArrayList<String> recipe_list = new ArrayList<String>();
        for (int i = 0; i < recipes.length; ++i){
            recipe_list.add(recipes[i]);
        }*/

       /* HomepageListArrayAdapter adapter = new HomepageListArrayAdapter(this, R.layout.homepage_list, listRecipe);
        listView.setAdapter(adapter);*/

        // -----------------------------------------------------------------------------------------

    } /* end of onCreate method */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();

        // Get diet preference
        final String diet;
        if (getIntent().getExtras() != null) {
            diet = getIntent().getExtras().getString("DIET");
        } else {
            diet = "";
        }

        // Handle query
        if (searchView != null) {

            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
            String queryHint = "Search recipes...";
            searchView.setQueryHint(queryHint);



            searchView.setOnQueryTextFocusChangeListener(new SearchView.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    hideKeyboard(v);
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Intent loadSearchIntent = new Intent(HomepageActivity.this, LoadResultsActivity.class);

                    query = searchView.getQuery().toString();
                    query = encodeQuery(query);
                    String emptyString = "";
                    if (diet != null && !diet.equals(emptyString)) {
                        query = "&q=" + diet + "+" + query;
                    } else {
                        query = "&q=" + query;
                    }

                    loadSearchIntent.putExtra("DIET", diet);
                    loadSearchIntent.putExtra("QUERY", query);
                    startActivity(loadSearchIntent);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });

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
        if (id == R.id.action_profile) {
            Intent profileIntent = new Intent(HomepageActivity.this,ProfilePage.class);
            startActivity(profileIntent);
            return true;
        }

        if (id == R.id.action_preferences) {
            Intent prefIntent = new Intent(HomepageActivity.this, MyDietActivity.class);
            startActivity(prefIntent);
            return true;
        }

        if (id == R.id.search) {
            SearchView searchView = (SearchView) item.getActionView();
            //searchView.dispatchSetActivated();
            // TODO - Remove unnecessary function calls
            searchView.dispatchSetActivated(true);
            searchView.setPressed(true);
            searchView.setSelected(true);
            searchView.setEnabled(true);
            searchView.setFocusable(true);
            searchView.setIconified(false);
            searchView.requestFocusFromTouch();
            return true;
        }

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

    // Encode inputted query for url
    private String encodeQuery(String query) {
        if (query == null) {
            return "";
        }

        char[] newString = new char[query.length()];

        //char[] newString = new char[query.length()+3];
        /*newString[0] = '&';
        newString[1] = 'q';
        newString[2] = '=';*/

        for (int i = 0; i < query.length(); ++i) {
            if (query.charAt(i) == ' ') {
                //newString[i+3] = '+';
                newString[i] = '+';
            } else {
                //newString[i+3] = query.charAt(i);
                newString[i] = query.charAt(i);
            }
        }

        return new String(newString);
    }

    // Allows the user to click on the screen to hide the keyboard
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        SearchView searchView = (SearchView) findViewById(R.id.search);
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
        } else {
            super.onBackPressed();
        }
    }

    // Parse ingredient string
    private ArrayList<String> parseIngredientsList(String ingredientString) {
        ArrayList<String> ingredientList = new ArrayList<>();
        String stringFragment = "";

        for (int i = 0; i < ingredientString.length(); ++i) {

            if (ingredientString.charAt(i) == '[' ||
                    ingredientString.charAt(i) == '\"')   {
                continue;
            }

            else if (ingredientString.charAt(i) == ','
                    || ingredientString.charAt(i) == ']') {
                ingredientList.add(stringFragment);
                stringFragment = "";
            }

            else {
                stringFragment = stringFragment + ingredientString.charAt(i);
            }
        }

        return ingredientList;
    }

    // Parse image string
    private String parseImage(String imageString) {

        String stringFragment = "";

        for (int i = 0; i < imageString.length(); ++i) {

            if (imageString.charAt(i) == '\"' || imageString.charAt(i) == '[') {
                continue;
            }

            else if (imageString.charAt(i) == ','
                    || imageString.charAt(i) == ']') {
                break;
            }

            else {
                stringFragment = stringFragment + imageString.charAt(i);
            }
        }
        String finalFragment = "";
        int count = 0;
        boolean shouldRemove = true;
        for (int i = 0; i < stringFragment.length(); ++i) {

            if (count == 2) {
                shouldRemove = false;
            }

            if (stringFragment.charAt(i) == 's' && shouldRemove) {
                continue;
            }

            if (stringFragment.charAt(i) == '\\') {
                continue;
            }

            if (stringFragment.charAt(i) == '/') {
                count++;
            }

            finalFragment = finalFragment + stringFragment.charAt(i);
        }

        return finalFragment;
    }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "");
            return d;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }



} /* end of HomepageActivity class */
