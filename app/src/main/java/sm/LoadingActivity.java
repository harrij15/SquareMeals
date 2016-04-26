package sm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class LoadingActivity extends AppCompatActivity {

    // Drawable that allows view to transition colors
    TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{});

    // View that will change colors
    TransitionView transitionView;

    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Code to access the current hour of the day
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);

        // Choose color scheme depending on the time of day
        if (5 <= hour && hour <= 11) {
            transitionDrawable = (TransitionDrawable) ResourcesCompat.getDrawable(getResources(),R.drawable.transition_morning,getTheme());
        } else if (12 <= hour && hour <= 17) {
            transitionDrawable = (TransitionDrawable) ResourcesCompat.getDrawable(getResources(),R.drawable.transition_afternoon,getTheme());
        } else if (18 <= hour && hour <= 21) {
            transitionDrawable = (TransitionDrawable) ResourcesCompat.getDrawable(getResources(),R.drawable.transition_evening,getTheme());
        } else {
            transitionDrawable = (TransitionDrawable) ResourcesCompat.getDrawable(getResources(),R.drawable.transition_night,getTheme());
        }

        // Create TransitionView
        transitionView = new TransitionView(this,transitionDrawable);


        // Access user information from input
        final String username, name, diet;
        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
            diet = getIntent().getExtras().getString("DIET");
        } else {
            username = "";
            name = "";
            diet = "";
        }

        // Create text views for loading screen
        TextView greeting = new TextView(this);
        TextView preparing = new TextView(this);

        // Make text for text views
        String emptyString = "";
        String greetingString;
        String preparingString = "Preparing your meals...";

        // Set up greeting for user, based on user input
        if (name != null && !name.equals(username) && !name.equals(emptyString)) {
            greetingString = "Hi " + name + "!";
        }
        else if (username != null && !username.equals(emptyString)) { // If username is filled
            greetingString = "Hi " + username + "!";
        } else {
            greetingString = "Welcome to SquareMeals!";
        }

        // Set text of text views
        greeting.setText(greetingString);
        preparing.setText(preparingString);

        // Set color for text
        greeting.setTextColor(Color.WHITE);
        preparing.setTextColor(Color.WHITE);


        // Access screen size
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        // Give greeting TextView an arbitrary size
        greeting.setHeight(size.y / 2);
        greeting.setWidth(size.x);
        preparing.setHeight(size.y / 2);
        preparing.setWidth(size.x);

        // Position greeting TextView on screen and resize text size
        greeting.setGravity(Gravity.CENTER);
        preparing.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        greeting.setY(0);
        preparing.setY(size.y/2);
        greeting.setTextSize(size.y / 35);
        preparing.setTextSize(size.y / 90);

        // Set up content view and add transition view and text views to it
        RelativeLayout relativeLayout = new RelativeLayout(this);
        relativeLayout.addView(transitionView);
        relativeLayout.addView(greeting);
        relativeLayout.addView(preparing);
        setContentView(relativeLayout);

        // Thread to run the transition of colors, while information is gathered
        Thread thread;

        // Run thread to start transitioning color
        thread = new Thread(transitionView.getRunnable());
        thread.start();

        new AsyncTask<Void,Void,Void>() {

            /*Drawable drawable;*/
            @Override
            protected Void doInBackground(Void... params) {
                /*newImageString = parseImage(imageString);
                drawable = LoadImageFromWebOperations(newImageString);*/
                try {
                    String url = "http://api.yummly.com/v1/api/recipes?_app_id=c9c36e2c&_app_key=02f8047150cb8a270d7b5bef56f8d3e3";
                    url = url + "&q=" + diet;
                    json = readUrl(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
                transitionView.setHomepageReady(true);

                /*if (drawable != null) {

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
                        recipeList.add(new SearchResult(name, ingredients, imageViewArray[index - 1], name, time, newImageString));
                        SearchResultsAdapter adapter = new SearchResultsAdapter(getApplicationContext(), R.layout.homepage_list, recipeList);
                        listView.setAdapter(adapter);
                    }
                } else {
                    throw new RuntimeException("Drawable is null!");
                }*/
            }
        }.execute();

        // Fetch data from internet
        //new PrefetchData().execute();
    }

    // Asynchronized task used to fetch data from the internet
    /*public class PrefetchData extends AsyncTask<Void, Void, Void> {

        // These two classes will be modified to
        // organize the JSON that is read in
        private class Item {
            String title;
            String link;
            String description;
        }

        private class Page {
            String title;
            String link;
            String description;
            String criteria;
            List<Item> items;
        }

        private class Attribution {
            String html;
            String url;
            String text;
            String logo;
        }

        // Fetching the data in the background
        @Override
        protected Void doInBackground(Void... arg0) {

            return null;
        }

        // Tells the transition view that the data has been
        // fetched and the homepage activity can be started
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            transitionView.setHomepageReady(true);

        }

    }*/

    // Function to read the URL
    private static String readUrl(String urlString) throws Exception {
        BufferedReader reader = null;
        try {
            URL url = new URL(urlString);
            reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }

    // Starts the homepage activity when both the data has been fetched
    // and the transition view have finished
    private void startHomepageActivity() {
        Intent homepageIntent = new Intent(LoadingActivity.this,HomepageActivity.class);

        String username, name, diet;
        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
            diet = getIntent().getExtras().getString("DIET");
        } else {
            username = "";
            name = "";
            diet = "";
        }

        // This will save the username to be used in HomepageActivity
        //String user_name = username.getText().toString();
        homepageIntent.putExtra("USERNAME", username);
        homepageIntent.putExtra("NAME", name);
        homepageIntent.putExtra("DIET", diet);
        homepageIntent.putExtra("JSON", json);

        startActivity(homepageIntent);
        finish();
    }

    // Implementation of the TransitionView class
    class TransitionView extends View implements Handler.Callback {

        private Handler handler = new Handler(this);
        private int delay = 5000;
        private TransitionDrawable drawable;
        private boolean reverse = false;
        private boolean canStop = false;
        private int iterations = 0;

        // The process that keeps transitioning the two colors
        // back and forth until the data has been fetched
        private Runnable event = new Runnable() {
            @Override
            public void run() {
                iterations += 1;

                // If the data has been fetched and the
                // number of iterations has been reached,
                // start the homepage activity
                if (canStop && iterations >= 3) {

                    //Built-in function to finish the current activity
                    finish();
                    startHomepageActivity();
                    return;
                }

                // Determines whether to transition
                // forward or backward
                if (!reverse) {
                    drawable.startTransition(delay);
                    reverse = true;
                } else {
                    drawable.reverseTransition(delay);
                    reverse = false;
                }

                //Keeps the run function iterating
                handler.postDelayed(event, delay);
            }
        };

        // Constructor of TransitionView
        public TransitionView(Context context_,TransitionDrawable drawable_) {
            super(context_);
            drawable = drawable_;
            setBackground(drawable);
        }

        // Returns the Runnable to start the thread
        public Runnable getRunnable() {
            return event;
        }

        // Required function for this kind of class
        public boolean handleMessage(Message msg) {
            return false;
        }

        // Called to tell TransitionView that the data has been fetched
        public void setHomepageReady(boolean ready) {
            canStop = ready;
        }
    }
}

