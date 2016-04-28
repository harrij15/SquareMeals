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

    String json, diet;

    boolean guest = false;

    UserDatabaseHelper helper = new UserDatabaseHelper(this);

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
        final String username, name, changed;
        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
            changed = getIntent().getExtras().getString("CHANGED");

            // get diet from the database
            diet = helper.searchPreference(username);

        } else {
            username = "";
            name = "";
            diet = "";
            changed = "";
        }



        // Create text views for loading screen
        TextView greeting = new TextView(this);
        TextView preparing = new TextView(this);

        // Make text for text views
        String emptyString = "";
        String greetingString;
        String preparingString = "Preparing your meals...";

        // Set up greeting for user, based on user input
        if (changed != null && changed.equals("yes")) {
            greetingString = "Saving your changes...";
            preparingString = "";
        }
        else if (name != null && !name.equals("not found") && !name.equals(username) && !name.equals(emptyString)) {
            greetingString = "Hi " + name + "!";
        }
        else if (username != null && !username.equals(emptyString)) { // If username is filled
            greetingString = "Hi " + username + "!";
        } else {
            greetingString = "Welcome to SquareMeals!";
            guest = true;
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

        // Fetch data from internet
        new PrefetchData().execute();
    }

    // Asynchronized task used to fetch data from the internet
    public class PrefetchData extends AsyncTask<Void, Void, Void> {

        // Fetching the data in the background
        @Override
        protected Void doInBackground(Void... arg0) {
            final String emptyString = "";
            try {
                //json = readUrl("http://api.yummly.com/v1/api/recipes?_app_id=app_id&_app_key=app_key&q=chicken");
                //json = "{\"criteria\":{\"q\":\"chicken\",\"allowedIngredient\":null,\"excludedIngredient\":null},\"matches\":[{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/tylEA4bu23U9c9kCm-W78HRHQAv7rugY4Np3ZJ6201KwmeGyQbUeLwNuTa_18oYOHwVVIJT8pXv2nV9mBFj-=s90-c\"},\"sourceDisplayName\":\"Chocolate Slopes\",\"ingredients\":[\"chicken breasts\",\"taco seasoning\",\"plain greek yogurt\",\"black beans\",\"salsa\",\"Mexican cheese blend\",\"tortilla chips\"],\"id\":\"Cheesy-Mexican-Chicken-Casserole-1558719\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/bG7fU2rMxZGIi8sD9uPy3MIJxCW25T3-ZbQlNbumxO8tias-sySBB0izTqGnGixeYYR03b3pKot6jmlSyNPOaeM=s90\"],\"recipeName\":\"Cheesy Mexican Chicken Casserole\",\"totalTimeInSeconds\":1800,\"attributes\":{\"course\":[\"Main Dishes\"],\"cuisine\":[\"Kid-Friendly\"]},\"flavors\":null,\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/PFGtVC_FmmJoPxLfJIckIDpZ_D4ePE2TH5dOchXhCpnrjeGfDLmS8Qy4y35novx21qE-UQr7ciTYND9UCtv9=s90-c\"},\"sourceDisplayName\":\"Taste and Tell\",\"ingredients\":[\"brown sugar\",\"ketchup\",\"soy sauce\",\"sherry\",\"fresh ginger\",\"minced garlic\",\"boneless skinless chicken breasts\"],\"id\":\"Grilled-Huli-Huli-Chicken-1560301\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/Sdun81FuDHadU_Xecv9kGPesCsAdLxihaXl35L_AWV_3EGUaHCJaKbbhVC9xpye_cesrTSAJ89gH3TaZxiyg=s90\"],\"recipeName\":\"Grilled Huli Huli Chicken\",\"totalTimeInSeconds\":30600,\"attributes\":{\"course\":[\"Main Dishes\"],\"cuisine\":[\"Barbecue\"]},\"flavors\":{\"piquant\":0.0,\"meaty\":0.5,\"bitter\":0.8333333333333334,\"sweet\":0.5,\"sour\":0.16666666666666666,\"salty\":0.8333333333333334},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/biFN8wAMjf8rdDYUnW4-fmH4-kMVixgnGutoYChT5Ls9EkDlhQN80c5PXKdmxQ4b-bz4hoHBS1JU0Tr3MgxoXQ=s90-c\"},\"sourceDisplayName\":\"Serena Bakes Simply from Scratch\",\"ingredients\":[\"chicken thighs\",\"dijon mustard\",\"honey\",\"apple cider vinegar\",\"fresh rosemary\",\"salt\",\"ground pepper\"],\"id\":\"Honey-Mustard-Chicken-1526254\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/rbSNQiCE2qeUQpXTaW2rAGpQ-c6xXwyIO5PPY8CNmIHPyvkLVB2IQ5PM41CuhtGODca16VoTD1R0LSvCgnA4=s90\"],\"recipeName\":\"Honey Mustard Chicken\",\"totalTimeInSeconds\":4800,\"attributes\":{\"course\":[\"Main Dishes\"]},\"flavors\":{\"piquant\":0.16666666666666666,\"meaty\":0.8333333333333334,\"bitter\":0.16666666666666666,\"sweet\":0.5,\"sour\":0.16666666666666666,\"salty\":0.16666666666666666},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/v2jeGxuQ9HEMgABZBbsFeqUvNYn8i7sOp3CXP9ryZ_NL9SelnfKiapsNZTHTEAngFFQfaNw-RPCY24-LpsrW=s90-c\"},\"sourceDisplayName\":\"The Lemon Bowl\",\"ingredients\":[\"boneless skinless chicken breasts\",\"salsa verde\",\"beer\",\"cumin\",\"salt\",\"pepper\",\"corn tortillas\"],\"id\":\"Slow-Cooker-Salsa-Verde-Chicken-Tacos-1559821\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/l8FmJAVsgHR68Kh2JJ9FitYHNFvNQC79uNiJ8mSN3_H6b_DkmcVovoyzYVkEuen1LReyuL9pQpsnUnMKcIax=s90\"],\"recipeName\":\"Slow Cooker Salsa Verde Chicken Tacos\",\"totalTimeInSeconds\":11100,\"attributes\":{\"course\":[\"Main Dishes\"],\"cuisine\":[\"Mexican\"]},\"flavors\":{\"piquant\":0.8333333333333334,\"meaty\":0.16666666666666666,\"bitter\":0.8333333333333334,\"sweet\":0.16666666666666666,\"sour\":0.16666666666666666,\"salty\":0.8333333333333334},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/WRjk_--SEu8iTTOh9OcQS6s66lSQfY5nQLh--h06Y3-uuWir6jK_uM5v5hEOg7w1lFYJOJYjdUqwvrVLJ_IABA=s90-c\"},\"sourceDisplayName\":\"Rasa Malaysia\",\"ingredients\":[\"chicken thighs\",\"garlic\",\"honey\",\"soy sauce\",\"lime juice\",\"cayenne pepper\",\"salt\",\"chopped parsley\",\"lime wedges\"],\"id\":\"Honey-Lime-Chicken-1556223\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/7l6QVUneCM6hqfXIVGM7fe-uy9zfOqlJwWm0GsDkPnz9SkWc-SgrF1SA4WXvMdib1uu1o1aibjpGlxJSYeO56w=s90\"],\"recipeName\":\"Honey Lime Chicken\",\"totalTimeInSeconds\":1500,\"attributes\":{\"course\":[\"Main Dishes\"]},\"flavors\":{\"piquant\":0.3333333333333333,\"meaty\":0.6666666666666666,\"bitter\":0.3333333333333333,\"sweet\":0.16666666666666666,\"sour\":0.6666666666666666,\"salty\":0.5},\"rating\":4}]}";
                // TODO - Fill in app id and app key of url!
                // Add to url based on preferences
                String url = "http://api.yummly.com/v1/api/recipes?_app_id=app_id&_app_key=app_key";
                if (!diet.equals(emptyString)) {
                    url = url + "&q=" + diet;
                }
                url += "&requirePictures=true";
                //System.out.println(url);

               /* String emptyString = "";

                if (!diet.equals(emptyString)) {
                    switch(diet) {
                        case "Lacto vegetarian":
                            url += "%26allowedDiet%255B%255D%3D388%5ELacto%20vegetarian";
                            break;
                        case "Ovo vegetarian":
                            url += "%26allowedDiet%255B%255D%3D389%5EOvo%20vegetarian";
                            break;
                        case "Pescetarian":
                            url += "%26allowedDiet%255B%255D%3D390%5EPescetarian";
                            break;
                        case "Vegan":
                            url += "%26allowedDiet%255B%255D%3D391%5EVegan";
                            break;
                        case "Vegetarian":
                            //url += "&allowedDiet%5B%5D=387^Lacto-ovo vegetarian";
                            url += "%26allowedDiet%255B%255D%3D387%5ELacto-ovo+vegetarian";
                            break;
                        case "Paleo":
                            url += "%26allowedDiet%255B%255D%3D403%5EPaleo";
                            break;
                        default:
                            break;
                    }


                }
*/
                //json = readUrl(url);
                json = "{\"criteria\":{\"q\":\"chicken\",\"allowedIngredient\":null,\"excludedIngredient\":null},\"matches\":[{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/tylEA4bu23U9c9kCm-W78HRHQAv7rugY4Np3ZJ6201KwmeGyQbUeLwNuTa_18oYOHwVVIJT8pXv2nV9mBFj-=s90-c\"},\"sourceDisplayName\":\"Chocolate Slopes\",\"ingredients\":[\"chicken breasts\",\"taco seasoning\",\"plain greek yogurt\",\"black beans\",\"salsa\",\"Mexican cheese blend\",\"tortilla chips\"],\"id\":\"Cheesy-Mexican-Chicken-Casserole-1558719\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/bG7fU2rMxZGIi8sD9uPy3MIJxCW25T3-ZbQlNbumxO8tias-sySBB0izTqGnGixeYYR03b3pKot6jmlSyNPOaeM=s90\"],\"recipeName\":\"Cheesy Mexican Chicken Casserole\",\"totalTimeInSeconds\":1800,\"attributes\":{\"course\":[\"Main Dishes\"],\"cuisine\":[\"Kid-Friendly\"]},\"flavors\":null,\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/PFGtVC_FmmJoPxLfJIckIDpZ_D4ePE2TH5dOchXhCpnrjeGfDLmS8Qy4y35novx21qE-UQr7ciTYND9UCtv9=s90-c\"},\"sourceDisplayName\":\"Taste and Tell\",\"ingredients\":[\"brown sugar\",\"ketchup\",\"soy sauce\",\"sherry\",\"fresh ginger\",\"minced garlic\",\"boneless skinless chicken breasts\"],\"id\":\"Grilled-Huli-Huli-Chicken-1560301\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/Sdun81FuDHadU_Xecv9kGPesCsAdLxihaXl35L_AWV_3EGUaHCJaKbbhVC9xpye_cesrTSAJ89gH3TaZxiyg=s90\"],\"recipeName\":\"Grilled Huli Huli Chicken\",\"totalTimeInSeconds\":30600,\"attributes\":{\"course\":[\"Main Dishes\"],\"cuisine\":[\"Barbecue\"]},\"flavors\":{\"piquant\":0.0,\"meaty\":0.5,\"bitter\":0.8333333333333334,\"sweet\":0.5,\"sour\":0.16666666666666666,\"salty\":0.8333333333333334},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/biFN8wAMjf8rdDYUnW4-fmH4-kMVixgnGutoYChT5Ls9EkDlhQN80c5PXKdmxQ4b-bz4hoHBS1JU0Tr3MgxoXQ=s90-c\"},\"sourceDisplayName\":\"Serena Bakes Simply from Scratch\",\"ingredients\":[\"chicken thighs\",\"dijon mustard\",\"honey\",\"apple cider vinegar\",\"fresh rosemary\",\"salt\",\"ground pepper\"],\"id\":\"Honey-Mustard-Chicken-1526254\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/rbSNQiCE2qeUQpXTaW2rAGpQ-c6xXwyIO5PPY8CNmIHPyvkLVB2IQ5PM41CuhtGODca16VoTD1R0LSvCgnA4=s90\"],\"recipeName\":\"Honey Mustard Chicken\",\"totalTimeInSeconds\":4800,\"attributes\":{\"course\":[\"Main Dishes\"]},\"flavors\":{\"piquant\":0.16666666666666666,\"meaty\":0.8333333333333334,\"bitter\":0.16666666666666666,\"sweet\":0.5,\"sour\":0.16666666666666666,\"salty\":0.16666666666666666},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/v2jeGxuQ9HEMgABZBbsFeqUvNYn8i7sOp3CXP9ryZ_NL9SelnfKiapsNZTHTEAngFFQfaNw-RPCY24-LpsrW=s90-c\"},\"sourceDisplayName\":\"The Lemon Bowl\",\"ingredients\":[\"boneless skinless chicken breasts\",\"salsa verde\",\"beer\",\"cumin\",\"salt\",\"pepper\",\"corn tortillas\"],\"id\":\"Slow-Cooker-Salsa-Verde-Chicken-Tacos-1559821\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/l8FmJAVsgHR68Kh2JJ9FitYHNFvNQC79uNiJ8mSN3_H6b_DkmcVovoyzYVkEuen1LReyuL9pQpsnUnMKcIax=s90\"],\"recipeName\":\"Slow Cooker Salsa Verde Chicken Tacos\",\"totalTimeInSeconds\":11100,\"attributes\":{\"course\":[\"Main Dishes\"],\"cuisine\":[\"Mexican\"]},\"flavors\":{\"piquant\":0.8333333333333334,\"meaty\":0.16666666666666666,\"bitter\":0.8333333333333334,\"sweet\":0.16666666666666666,\"sour\":0.16666666666666666,\"salty\":0.8333333333333334},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/WRjk_--SEu8iTTOh9OcQS6s66lSQfY5nQLh--h06Y3-uuWir6jK_uM5v5hEOg7w1lFYJOJYjdUqwvrVLJ_IABA=s90-c\"},\"sourceDisplayName\":\"Rasa Malaysia\",\"ingredients\":[\"chicken thighs\",\"garlic\",\"honey\",\"soy sauce\",\"lime juice\",\"cayenne pepper\",\"salt\",\"chopped parsley\",\"lime wedges\"],\"id\":\"Honey-Lime-Chicken-1556223\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/7l6QVUneCM6hqfXIVGM7fe-uy9zfOqlJwWm0GsDkPnz9SkWc-SgrF1SA4WXvMdib1uu1o1aibjpGlxJSYeO56w=s90\"],\"recipeName\":\"Honey Lime Chicken\",\"totalTimeInSeconds\":1500,\"attributes\":{\"course\":[\"Main Dishes\"]},\"flavors\":{\"piquant\":0.3333333333333333,\"meaty\":0.6666666666666666,\"bitter\":0.3333333333333333,\"sweet\":0.16666666666666666,\"sour\":0.6666666666666666,\"salty\":0.5},\"rating\":4}]}";

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        // Tells the transition view that the data has been
        // fetched and the homepage activity can be started
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            transitionView.setHomepageReady(true);

        }

    }

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
        Intent homepageIntent;
        if (guest) {
            homepageIntent = new Intent(LoadingActivity.this,HomepageGuestActivity.class);
        } else {
            homepageIntent = new Intent(LoadingActivity.this,HomepageActivity.class);
        }

        String username, name;
        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
        } else {
            username = "";
            name = "";
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

