package sm;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.ListView;

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

public class LoadResultsActivity extends AppCompatActivity {
    String query, diet, oldJson,  json, username, name, flag;
    ImageView imageView;
    ArrayList<Drawable> drawableList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_results);

        imageView = new ImageView(this);
        drawableList = new ArrayList<>();
        if (getIntent().getExtras() != null) {
            query = getIntent().getExtras().getString("QUERY");
            diet = getIntent().getExtras().getString("DIET");
            oldJson = getIntent().getExtras().getString("JSON");
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
            flag = getIntent().getExtras().getString("FLAG");
        } else {
            query = "";
            diet = "";
            oldJson = "";
            username = "";
            name = "";
            flag = "";
        }

        new PrefetchData().execute();
    }

    // Asynchronized task used to fetch data from the internet
    public class PrefetchData extends AsyncTask<Void, Void, Void> {

        //String json;
        // Fetching the data in the background
        @Override
        protected Void doInBackground(Void... arg0) {

            try {
                //json = readUrl("http://api.yummly.com/v1/api/recipes?_app_id=app_id&_app_key=app_key&q=chicken");
                //json = "{\"criteria\":{\"q\":\"chicken\",\"allowedIngredient\":null,\"excludedIngredient\":null},\"matches\":[{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/tylEA4bu23U9c9kCm-W78HRHQAv7rugY4Np3ZJ6201KwmeGyQbUeLwNuTa_18oYOHwVVIJT8pXv2nV9mBFj-=s90-c\"},\"sourceDisplayName\":\"Chocolate Slopes\",\"ingredients\":[\"chicken breasts\",\"taco seasoning\",\"plain greek yogurt\",\"black beans\",\"salsa\",\"Mexican cheese blend\",\"tortilla chips\"],\"id\":\"Cheesy-Mexican-Chicken-Casserole-1558719\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/bG7fU2rMxZGIi8sD9uPy3MIJxCW25T3-ZbQlNbumxO8tias-sySBB0izTqGnGixeYYR03b3pKot6jmlSyNPOaeM=s90\"],\"recipeName\":\"Cheesy Mexican Chicken Casserole\",\"totalTimeInSeconds\":1800,\"attributes\":{\"course\":[\"Main Dishes\"],\"cuisine\":[\"Kid-Friendly\"]},\"flavors\":null,\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/PFGtVC_FmmJoPxLfJIckIDpZ_D4ePE2TH5dOchXhCpnrjeGfDLmS8Qy4y35novx21qE-UQr7ciTYND9UCtv9=s90-c\"},\"sourceDisplayName\":\"Taste and Tell\",\"ingredients\":[\"brown sugar\",\"ketchup\",\"soy sauce\",\"sherry\",\"fresh ginger\",\"minced garlic\",\"boneless skinless chicken breasts\"],\"id\":\"Grilled-Huli-Huli-Chicken-1560301\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/Sdun81FuDHadU_Xecv9kGPesCsAdLxihaXl35L_AWV_3EGUaHCJaKbbhVC9xpye_cesrTSAJ89gH3TaZxiyg=s90\"],\"recipeName\":\"Grilled Huli Huli Chicken\",\"totalTimeInSeconds\":30600,\"attributes\":{\"course\":[\"Main Dishes\"],\"cuisine\":[\"Barbecue\"]},\"flavors\":{\"piquant\":0.0,\"meaty\":0.5,\"bitter\":0.8333333333333334,\"sweet\":0.5,\"sour\":0.16666666666666666,\"salty\":0.8333333333333334},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/biFN8wAMjf8rdDYUnW4-fmH4-kMVixgnGutoYChT5Ls9EkDlhQN80c5PXKdmxQ4b-bz4hoHBS1JU0Tr3MgxoXQ=s90-c\"},\"sourceDisplayName\":\"Serena Bakes Simply from Scratch\",\"ingredients\":[\"chicken thighs\",\"dijon mustard\",\"honey\",\"apple cider vinegar\",\"fresh rosemary\",\"salt\",\"ground pepper\"],\"id\":\"Honey-Mustard-Chicken-1526254\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/rbSNQiCE2qeUQpXTaW2rAGpQ-c6xXwyIO5PPY8CNmIHPyvkLVB2IQ5PM41CuhtGODca16VoTD1R0LSvCgnA4=s90\"],\"recipeName\":\"Honey Mustard Chicken\",\"totalTimeInSeconds\":4800,\"attributes\":{\"course\":[\"Main Dishes\"]},\"flavors\":{\"piquant\":0.16666666666666666,\"meaty\":0.8333333333333334,\"bitter\":0.16666666666666666,\"sweet\":0.5,\"sour\":0.16666666666666666,\"salty\":0.16666666666666666},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/v2jeGxuQ9HEMgABZBbsFeqUvNYn8i7sOp3CXP9ryZ_NL9SelnfKiapsNZTHTEAngFFQfaNw-RPCY24-LpsrW=s90-c\"},\"sourceDisplayName\":\"The Lemon Bowl\",\"ingredients\":[\"boneless skinless chicken breasts\",\"salsa verde\",\"beer\",\"cumin\",\"salt\",\"pepper\",\"corn tortillas\"],\"id\":\"Slow-Cooker-Salsa-Verde-Chicken-Tacos-1559821\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/l8FmJAVsgHR68Kh2JJ9FitYHNFvNQC79uNiJ8mSN3_H6b_DkmcVovoyzYVkEuen1LReyuL9pQpsnUnMKcIax=s90\"],\"recipeName\":\"Slow Cooker Salsa Verde Chicken Tacos\",\"totalTimeInSeconds\":11100,\"attributes\":{\"course\":[\"Main Dishes\"],\"cuisine\":[\"Mexican\"]},\"flavors\":{\"piquant\":0.8333333333333334,\"meaty\":0.16666666666666666,\"bitter\":0.8333333333333334,\"sweet\":0.16666666666666666,\"sour\":0.16666666666666666,\"salty\":0.8333333333333334},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/WRjk_--SEu8iTTOh9OcQS6s66lSQfY5nQLh--h06Y3-uuWir6jK_uM5v5hEOg7w1lFYJOJYjdUqwvrVLJ_IABA=s90-c\"},\"sourceDisplayName\":\"Rasa Malaysia\",\"ingredients\":[\"chicken thighs\",\"garlic\",\"honey\",\"soy sauce\",\"lime juice\",\"cayenne pepper\",\"salt\",\"chopped parsley\",\"lime wedges\"],\"id\":\"Honey-Lime-Chicken-1556223\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/7l6QVUneCM6hqfXIVGM7fe-uy9zfOqlJwWm0GsDkPnz9SkWc-SgrF1SA4WXvMdib1uu1o1aibjpGlxJSYeO56w=s90\"],\"recipeName\":\"Honey Lime Chicken\",\"totalTimeInSeconds\":1500,\"attributes\":{\"course\":[\"Main Dishes\"]},\"flavors\":{\"piquant\":0.3333333333333333,\"meaty\":0.6666666666666666,\"bitter\":0.3333333333333333,\"sweet\":0.16666666666666666,\"sour\":0.6666666666666666,\"salty\":0.5},\"rating\":4}]}";
                // TODO - Fill in app id and app key of url!
                // Add to url based on preferences
                String url = "http://api.yummly.com/v1/api/recipes?_app_id=app_id&_app_key=app_key";
                url += query;
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

            Intent searchIntent = new Intent(LoadResultsActivity.this, SearchActivity.class);

            String query;
            if (getIntent().getExtras() != null) {
                query = getIntent().getExtras().getString("QUERY");
            } else {
                query = "";
            }
            searchIntent.putExtra("USERNAME",username);
            searchIntent.putExtra("NAME",name);
            searchIntent.putExtra("DIET",diet);
            searchIntent.putExtra("FLAG",flag);
            searchIntent.putExtra("QUERY", query);
            searchIntent.putExtra("JSON", json);
            searchIntent.putExtra("OLDJSON", oldJson);
            startActivity(searchIntent);

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

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "");
            return d;
        } catch (Exception e) {
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
            //intent.putExtra("FLAG",flag);
            startActivity(intent);
            return true;
        }

        return false;
    }
}
