package sm.homepage;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
import java.net.URL;
import java.util.ArrayList;

import sm.search.LoadResultsActivity;
import sm.onboarding.LoginActivity;
import sm.profile.MyDietActivity;
import sm.profile.ProfilePage;
import sm.R;
import sm.search.Recipe;
import sm.search.RecipeInfoActivity;

/**
 * Created by putriz on 2/23/2016.
 * This class implements the layout of the homepage for an existing user,
 * including a recommendations tab and a cookbook tab (where the user's recipes are saved)
 */

public class HomepageActivity extends AppCompatActivity {

    TabHost tabhost;
    private Context context;

    ImageView[] imageViewArray;
    int index, screenWidth;
    String username, name, diet, json;
    Intent intent;
    String yummlyLogo;
    ImageView yummlyIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


        // TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);


        // Reading values thrown in from previous activity (either the LoginActivity
        // or the SignUpActivity) if given
        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
            diet = getIntent().getExtras().getString("DIET");
            json = getIntent().getExtras().getString("JSON");

        } else { // Handling the case where no values are thrown in
            username = "";
            name = "";
            diet = "";
            json = "";
        }

        String emptyString = "";

        // Greets the user either with their name or their username
        // For now, every user is required to give their name, but it
        // can be possible to give them a choice in the Settings
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
        final GridView gridView = (GridView) findViewById(R.id.homepage_tab1_gridView);
        //gridView.setAdapter(new HomepageButtonAdapter(this));

        // List View that shows recipes saved in cookbook
        final ListView listView = (ListView) findViewById(R.id.cookbook_list);
        final ArrayList<Recipe> listRecipe = new ArrayList<Recipe>();

        JSONObject obj;
        final ArrayList<Recipe> recipeList = new ArrayList<>();
        index = 0;

        // Parse json string to get desired info
        try {
            //System.out.println(json);
            obj = new JSONObject(json);
            JSONArray matchesArray = obj.getJSONArray("matches");

            // Code for giving the attribution
            // TODO - Get this code to work
            /*JSONObject attribution = obj.getJSONObject("attribution");
            String url = attribution.getString("url");
            String text = attribution.getString("text");
            yummlyLogo = attribution.getString("logo");*/
            //System.out.println(url + " " + text + " " + yummlyLogo);
           /* TextView attributionTextView = (TextView)findViewById(R.id.attribution_text);
            attributionTextView.setText(text);

            TextView attributionURL = (TextView)findViewById(R.id.attribution_url);
            attributionURL.setText(url);*/

            //yummlyIcon = (ImageView)findViewById(R.id.yummly_logo);

            // Getting the images from the json
            imageViewArray = new ImageView[matchesArray.length()];

            // Check for test case where no recipes could be found
            if (matchesArray.length()==0) {
                TextView searchResultsText = (TextView) findViewById(R.id.search_results_text);
                searchResultsText.setText("Oops! We couldn't find anything :(");
            }

            DisplayMetrics displaymetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            screenWidth = displaymetrics.widthPixels;
            intent = new Intent(this, RecipeInfoActivity.class);

            // The actual parse for information
            for (int i = 0; i < matchesArray.length(); ++i) {

                final String recipe = matchesArray.getJSONObject(i).getString("recipeName");
                final String ingredientString = matchesArray.getJSONObject(i).getString("ingredients");
                final String imageString = matchesArray.getJSONObject(i).getString("smallImageUrls");
                final String cook_time = matchesArray.getJSONObject(i).getString("totalTimeInSeconds");
                final ArrayList<String> ingredients = parseIngredientsList(ingredientString);
                final ImageView imageView = new ImageView(this);

                // AsyncTasks are used to run programs in the background, while displaying something else
                // In our app, we show the user a splash screen
                new AsyncTask<Void,Void,Void>() {
                    String newImageString, logoString;
                    Drawable drawable, yummlyDrawable;
                    @Override

                    // Loading the image from the internet
                    // Loading images cannot be done on the main thread
                    protected Void doInBackground(Void... params) {
                        //System.out.println(imageString);
                        //logoString = parseImage(yummlyLogo);

                        //System.out.println(logoString);

                        newImageString = parseImage(imageString);
                       // System.out.println(newImageString);
                        //yummlyDrawable = LoadImageFromWebOperations(logoString);

                        //System.out.println(yummlyDrawable);
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

                            // We want the time to be in a certain format
                            try {
                                time = Integer.parseInt(cook_time);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            // If the image was found, add the recipe
                            if (imageViewArray[index-1] != null) {
                                recipeList.add(new Recipe(recipe, ingredients, imageViewArray[index - 1], recipe, time, newImageString));
                                HomepageListArrayAdapter adapter = new HomepageListArrayAdapter(getApplicationContext(), R.layout.homepage_item, recipeList,screenWidth);
                                gridView.setAdapter(adapter);

                                // This sets up the GridView displayed to the users
                                // For each recipe, its information is gathered as the user clicks on it
                                // This operation only happens once the user clicks on an item
                                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent newIntent = new Intent(intent);
                                        Recipe recipe = (Recipe) gridView.getItemAtPosition(position);
                                        newIntent.putExtra("IMAGE", recipe.getLink());
                                        newIntent.putExtra("INGREDIENTS", recipe.getIngredients());
                                        newIntent.putExtra("TIME", recipe.getCook_time());
                                        newIntent.putExtra("NAME", recipe.getName());
                                        //System.out.println(name);
                                        startActivity(newIntent);
                                    }
                                });
                                //listView.setAdapter(adapter);
                                //gridView.setAdapter(new HomepageButtonAdapter(this,adapter));
                            }
                        } else {
                            throw new RuntimeException("Drawable is null!");
                        }

                        /*if (yummlyDrawable != null) {
                            yummlyIcon.setImageDrawable(yummlyDrawable);
                        }*/ /*else {

                            throw new RuntimeException("yummlyDrawable is null!");
                        }*/
                    }
                }.execute();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }




        // -----------------------------------------------------------------------------------------

    } /* end of onCreate method */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_homepage, menu);

        // Setting up the search icon on the menu
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();

        /*// Get diet preference thrown in
        if (getIntent().getExtras() != null) {
            diet = getIntent().getExtras().getString("DIET");
        } else {
            diet = "";
        }*/

        // Handle query
        if (searchView != null) {

            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);

            // This sets the text hint the user will see when clicking on the icon
            String queryHint = "Search recipes...";
            searchView.setQueryHint(queryHint);

            // Hides the keyboard once the user clicks out of the search field after selecting it
            searchView.setOnQueryTextFocusChangeListener(new SearchView.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    hideKeyboard(v);
                }
            });

            // Once the user enters their search query, the query is then transformed to
            // work with the Yummly API Search Recipe call
            // This will change once the app starts relying on the database
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                // Creates a LoadResultsActivity to display a splash screen while loading the
                // data from the Yummly API
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
                    loadSearchIntent.putExtra("USERNAME",username);
                    loadSearchIntent.putExtra("JSON",json);
                    loadSearchIntent.putExtra("NAME",name);
                    loadSearchIntent.putExtra("FLAG","user");
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

        // Once we implement the settings, this if statement
        // will handle that
        if (id == R.id.action_settings) {

        }

        // Handles the user press of the profile
        // Launches the ProfilePage activity
        if (id == R.id.action_profile) {
            Intent profileIntent = new Intent(HomepageActivity.this,ProfilePage.class);
            profileIntent.putExtra("DIET",diet);
            profileIntent.putExtra("USERNAME",username);
            profileIntent.putExtra("NAME", name);
            profileIntent.putExtra("JSON",json);

            startActivity(profileIntent);
            return true;
        }

        // Handles a user press of the Preferences
        // Launches the MyDietActivity
        if (id == R.id.action_preferences) {

            Intent prefIntent = new Intent(this, MyDietActivity.class);
            prefIntent.putExtra("DIET", diet);
            prefIntent.putExtra("USERNAME", username);
            prefIntent.putExtra("NAME", name);
            prefIntent.putExtra("JSON", json);
            startActivity(prefIntent);
            return true;
        }

        // Handles search
        if (id == R.id.search) {
            SearchView searchView = (SearchView) item.getActionView();
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

    // Allows the user to go back to the LoginActivity
    // when the back button is pressed
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this, LoginActivity.class);
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

        // Replaces all spaces with '+' characters
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

    // Resets search icon to iconified state when the user
    // goes back to the HomePageActivity
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
    // The ingredient string is given in a list format ([a,b,c,d])
    // so we need to parse it in order to get its items
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

    // Loads the image from the Internet
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
