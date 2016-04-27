package sm;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;

public class RecipeInfoActivity extends AppCompatActivity {
    String name, link;
    int time;
    String[] ingredients;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);



        if (getIntent().getExtras() != null) {
            name = getIntent().getExtras().getString("NAME");
            link = getIntent().getExtras().getString("IMAGE");
            ingredients = getIntent().getExtras().getStringArray("INGREDIENTS");
            time = getIntent().getExtras().getInt("TIME");
        }

        final TextView recipeName = (TextView)findViewById(R.id.recipeName);
        final TextView ingredientString = (TextView)findViewById(R.id.ingredients);
        final TextView cookTime = (TextView)findViewById(R.id.cookTime);
        final TextView recipeLink = (TextView)findViewById(R.id.link);
        final ImageView imageView = (ImageView)findViewById(R.id.recipeImage);

        new AsyncTask<Void,Void,Void>() {
            String newImageString;
            Drawable drawable;
            @Override
            protected Void doInBackground(Void... params) {

                drawable = LoadImageFromWebOperations(link);
                return null;
            }
            @Override
            protected void onPostExecute(Void result) {

                if (drawable != null) {

                    imageView.setImageDrawable(drawable);
                    recipeName.setText(name);
                    String ingredientList = "Ingredients: ";
                    for (int i = 0; i < ingredients.length; ++i) {
                        if (i == ingredients.length-1) {
                            ingredientList += ingredients[i];
                        } else {
                            ingredientList += ingredients[i] + ", ";
                        }
                    }
                    ingredientString.setText(ingredientList);
                    cookTime.setText("Time: ");
                    if (time <= 0 ) {
                        cookTime.setText(cookTime.getText() + "Not specified");
                    } else {
                        int temp_time = time;
                        int hours = temp_time/3600;
                        temp_time -= hours*3600;
                        int mins = temp_time/60;
                        temp_time -= mins*60;
                        int secs = temp_time;
                        String hourString = "" + hours;
                        String minString = "" + mins;
                        String secString = "" + secs;
                        if (hours != 0) {
                            cookTime.setText(cookTime.getText() + " " + hourString + " hours");
                        }
                        if (mins != 0) {
                            cookTime.setText(cookTime.getText() + " " + minString + " minutes");
                        }
                        if (secs != 0) {
                            cookTime.setText(cookTime.getText() + " " + secString + " seconds");
                        }
                    }
                    recipeLink.setText(link);

                } else {
                    throw new RuntimeException("Drawable is null!");
                }
            }
        }.execute();
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
}
