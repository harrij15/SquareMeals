package sm;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
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

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        String json, inputDiet = "";
        if (getIntent().getExtras() != null) {
            json = getIntent().getExtras().getString("JSON");
            inputDiet = getIntent().getExtras().getString("DIET");
        } else {
            json = "";
            inputDiet = "";
        }

        //String json = "{\"criteria\":{\"q\":\"chicken\",\"allowedIngredient\":null,\"excludedIngredient\":null},\"matches\":[{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/tylEA4bu23U9c9kCm-W78HRHQAv7rugY4Np3ZJ6201KwmeGyQbUeLwNuTa_18oYOHwVVIJT8pXv2nV9mBFj-=s90-c\"},\"sourceDisplayName\":\"Chocolate Slopes\",\"ingredients\":[\"chicken breasts\",\"taco seasoning\",\"plain greek yogurt\",\"black beans\",\"salsa\",\"Mexican cheese blend\",\"tortilla chips\"],\"id\":\"Cheesy-Mexican-Chicken-Casserole-1558719\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/bG7fU2rMxZGIi8sD9uPy3MIJxCW25T3-ZbQlNbumxO8tias-sySBB0izTqGnGixeYYR03b3pKot6jmlSyNPOaeM=s90\"],\"recipeName\":\"Cheesy Mexican Chicken Casserole\",\"totalTimeInSeconds\":1800,\"attributes\":{\"course\":[\"Main Dishes\"],\"cuisine\":[\"Kid-Friendly\"]},\"flavors\":null,\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/PFGtVC_FmmJoPxLfJIckIDpZ_D4ePE2TH5dOchXhCpnrjeGfDLmS8Qy4y35novx21qE-UQr7ciTYND9UCtv9=s90-c\"},\"sourceDisplayName\":\"Taste and Tell\",\"ingredients\":[\"brown sugar\",\"ketchup\",\"soy sauce\",\"sherry\",\"fresh ginger\",\"minced garlic\",\"boneless skinless chicken breasts\"],\"id\":\"Grilled-Huli-Huli-Chicken-1560301\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/Sdun81FuDHadU_Xecv9kGPesCsAdLxihaXl35L_AWV_3EGUaHCJaKbbhVC9xpye_cesrTSAJ89gH3TaZxiyg=s90\"],\"recipeName\":\"Grilled Huli Huli Chicken\",\"totalTimeInSeconds\":30600,\"attributes\":{\"course\":[\"Main Dishes\"],\"cuisine\":[\"Barbecue\"]},\"flavors\":{\"piquant\":0.0,\"meaty\":0.5,\"bitter\":0.8333333333333334,\"sweet\":0.5,\"sour\":0.16666666666666666,\"salty\":0.8333333333333334},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/biFN8wAMjf8rdDYUnW4-fmH4-kMVixgnGutoYChT5Ls9EkDlhQN80c5PXKdmxQ4b-bz4hoHBS1JU0Tr3MgxoXQ=s90-c\"},\"sourceDisplayName\":\"Serena Bakes Simply from Scratch\",\"ingredients\":[\"chicken thighs\",\"dijon mustard\",\"honey\",\"apple cider vinegar\",\"fresh rosemary\",\"salt\",\"ground pepper\"],\"id\":\"Honey-Mustard-Chicken-1526254\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/rbSNQiCE2qeUQpXTaW2rAGpQ-c6xXwyIO5PPY8CNmIHPyvkLVB2IQ5PM41CuhtGODca16VoTD1R0LSvCgnA4=s90\"],\"recipeName\":\"Honey Mustard Chicken\",\"totalTimeInSeconds\":4800,\"attributes\":{\"course\":[\"Main Dishes\"]},\"flavors\":{\"piquant\":0.16666666666666666,\"meaty\":0.8333333333333334,\"bitter\":0.16666666666666666,\"sweet\":0.5,\"sour\":0.16666666666666666,\"salty\":0.16666666666666666},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/v2jeGxuQ9HEMgABZBbsFeqUvNYn8i7sOp3CXP9ryZ_NL9SelnfKiapsNZTHTEAngFFQfaNw-RPCY24-LpsrW=s90-c\"},\"sourceDisplayName\":\"The Lemon Bowl\",\"ingredients\":[\"boneless skinless chicken breasts\",\"salsa verde\",\"beer\",\"cumin\",\"salt\",\"pepper\",\"corn tortillas\"],\"id\":\"Slow-Cooker-Salsa-Verde-Chicken-Tacos-1559821\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/l8FmJAVsgHR68Kh2JJ9FitYHNFvNQC79uNiJ8mSN3_H6b_DkmcVovoyzYVkEuen1LReyuL9pQpsnUnMKcIax=s90\"],\"recipeName\":\"Slow Cooker Salsa Verde Chicken Tacos\",\"totalTimeInSeconds\":11100,\"attributes\":{\"course\":[\"Main Dishes\"],\"cuisine\":[\"Mexican\"]},\"flavors\":{\"piquant\":0.8333333333333334,\"meaty\":0.16666666666666666,\"bitter\":0.8333333333333334,\"sweet\":0.16666666666666666,\"sour\":0.16666666666666666,\"salty\":0.8333333333333334},\"rating\":4},{\"imageUrlsBySize\":{\"90\":\"https://lh3.googleusercontent.com/WRjk_--SEu8iTTOh9OcQS6s66lSQfY5nQLh--h06Y3-uuWir6jK_uM5v5hEOg7w1lFYJOJYjdUqwvrVLJ_IABA=s90-c\"},\"sourceDisplayName\":\"Rasa Malaysia\",\"ingredients\":[\"chicken thighs\",\"garlic\",\"honey\",\"soy sauce\",\"lime juice\",\"cayenne pepper\",\"salt\",\"chopped parsley\",\"lime wedges\"],\"id\":\"Honey-Lime-Chicken-1556223\",\"smallImageUrls\":[\"https://lh3.googleusercontent.com/7l6QVUneCM6hqfXIVGM7fe-uy9zfOqlJwWm0GsDkPnz9SkWc-SgrF1SA4WXvMdib1uu1o1aibjpGlxJSYeO56w=s90\"],\"recipeName\":\"Honey Lime Chicken\",\"totalTimeInSeconds\":1500,\"attributes\":{\"course\":[\"Main Dishes\"]},\"flavors\":{\"piquant\":0.3333333333333333,\"meaty\":0.6666666666666666,\"bitter\":0.3333333333333333,\"sweet\":0.16666666666666666,\"sour\":0.6666666666666666,\"salty\":0.5},\"rating\":4}]}";

        JSONObject obj = new JSONObject();
        List<SearchResult> recipeList = new ArrayList<>();
        ListView listView = (ListView)findViewById(R.id.search_list);

        // Parse json string to get desired info
        try {
            obj = new JSONObject(json);
            JSONArray matchesArray = obj.getJSONArray("matches");
            for (int i = 0; i < matchesArray.length(); ++i) {

                String name = matchesArray.getJSONObject(i).getString("recipeName");
                String ingredientString = matchesArray.getJSONObject(i).getString("ingredients");
                String imageString = matchesArray.getJSONObject(i).getString("smallImageUrls");
                //String description = ;
                String cook_time = matchesArray.getJSONObject(i).getString("totalTimeInSeconds");

                ArrayList<String> ingredients = parseIngredientsList(ingredientString);

                //imageString = "https://lh3.googleusercontent.com//bG7fU2rMxZGIi8sD9uPy3MIJxCW25T3-ZbQlNbumxO8tias-sySBB0izTqGnGixeYYR03b3pKot6jmlSyNPOaeM=s90";
                //Bitmap image = getImage(imageString);
                int time = Integer.parseInt(cook_time);

                recipeList.add(new SearchResult(name,ingredients,imageString,name,time));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        handleIntent(getIntent());
        // Put results on search results page
        SearchResultsAdapter adapter = new SearchResultsAdapter(this, R.layout.homepage_list, recipeList);
        listView.setAdapter(adapter);


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
    private Bitmap getImage(String imageString) {

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

        try {
            java.net.URL url = new java.net.URL(stringFragment);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        //return stringFragment;
    }

}
