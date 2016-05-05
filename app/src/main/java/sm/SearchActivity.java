package sm;


import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ImageView[] imageViewArray;
    int index;
    String json, username, name, diet, flag, oldJson;
    Intent intent;
    int time;
    String yummlyLogo;
    ImageView yummlyIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        if (getIntent().getExtras() != null) {
            oldJson = getIntent().getExtras().getString("OLDJSON");
            diet = getIntent().getExtras().getString("DIET");
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
            flag = getIntent().getExtras().getString("FLAG");
            json = getIntent().getExtras().getString("JSON");
        } else {
            oldJson = "";
            diet = "";
            username = "";
            name = "";
            flag = "";
        }



        JSONObject obj;
        final List<SearchResult> recipeList = new ArrayList<>();
        final ListView listView = (ListView)findViewById(R.id.search_list);
        intent = new Intent(this, RecipeInfoActivity.class);

        // Parse json string to get desired info
        try {
            obj = new JSONObject(json);
            JSONArray matchesArray = obj.getJSONArray("matches");

            /*JSONObject attribution = obj.getJSONObject("attribution");
            String url = attribution.getString("url");
            String text = attribution.getString("text");
            yummlyLogo = attribution.getString("logo");*/
            //System.out.println(url + " " + text + " " + yummlyLogo);
            /*TextView attributionTextView = (TextView)findViewById(R.id.attribution_text);
            attributionTextView.setText(text);

            TextView attributionURL = (TextView)findViewById(R.id.attribution_url);
            attributionURL.setText(url);*/

            //yummlyIcon = (ImageView)findViewById(R.id.yummly_logo);

            imageViewArray = new ImageView[matchesArray.length()];

            if (matchesArray.length()==0) {
                TextView searchResultsText = (TextView) findViewById(R.id.search_results_text);
                searchResultsText.setText("Oops! We couldn't find anything :(");
            }

            index = 0;
            for (int i = 0; i < matchesArray.length(); ++i) {

                final String name = matchesArray.getJSONObject(i).getString("recipeName");
                final String ingredientString = matchesArray.getJSONObject(i).getString("ingredients");
                final String imageString = matchesArray.getJSONObject(i).getString("smallImageUrls");
                final String cook_time = matchesArray.getJSONObject(i).getString("totalTimeInSeconds");
                final ArrayList<String> ingredients = parseIngredientsList(ingredientString);
                final ImageView imageView = new ImageView(this);

                new AsyncTask<Void,Void,Void>() {
                    String newImageString, logoString;
                    Drawable drawable, yummlyDrawable;
                    @Override
                    protected Void doInBackground(Void... params) {
                        //logoString = parseImage(yummlyLogo);
                        newImageString = parseImage(imageString);
                        //yummlyDrawable = LoadImageFromWebOperations(logoString);

                        drawable = LoadImageFromWebOperations(newImageString);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Void result) {

                        if (drawable != null) {

                            imageView.setImageDrawable(drawable);
                            imageViewArray[index] = imageView;
                            index++;

                            time = -1;

                            try {
                                time = Integer.parseInt(cook_time);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if (imageViewArray[index-1] != null) {
                                SearchResult newResult = new SearchResult(name, ingredients, imageViewArray[index - 1], name, time, newImageString);
                                recipeList.add(newResult);
                                SearchResultsAdapter adapter = new SearchResultsAdapter(getApplicationContext(), R.layout.homepage_list, recipeList);
                                listView.setAdapter(adapter);

                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        Intent newIntent = new Intent(intent);
                                        SearchResult searchResult = (SearchResult) listView.getItemAtPosition(position);
                                        newIntent.putExtra("IMAGE",searchResult.getLink());
                                        newIntent.putExtra("INGREDIENTS",searchResult.getIngredients());
                                        newIntent.putExtra("TIME",searchResult.getTime());
                                        newIntent.putExtra("NAME",searchResult.getName());
                                        //System.out.println(name);
                                        startActivity(newIntent);
                                    }
                                });
                            }
                        } else {
                            throw new RuntimeException("Drawable is null!");
                        }

                        /*if (yummlyDrawable != null) {
                            yummlyIcon.setImageDrawable(yummlyDrawable);
                        }*/

                    }
                }.execute();

            }
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            String guestString = "guest";
            Intent intent;
            if (flag.equals(guestString)) {
                intent = new Intent(this,HomepageGuestActivity.class);
            } else {
                intent = new Intent(this,HomepageActivity.class);
            }
            intent.putExtra("USERNAME",username);
            intent.putExtra("NAME",name);
            intent.putExtra("JSON",oldJson);
            intent.putExtra("DIET",diet);

            startActivity(intent);
            return true;
        }

        return false;
    }

}
