package sm.search;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by harrij15 on 4/5/2016.
 */
// Similar to Recipe class to handle search results
public class SearchResult {
    private String name; // name of recipe
    private ImageView image; // picture of the recipe
    private ArrayList<String> ingredients;
    private String description;
    private int cook_time;
    private String link;
    private String imageString;

    // constructor
    public SearchResult(String name, ArrayList<String> ingredients, ImageView image, String description, int cook_time, String imgString, String lnk){
        super();
        this.name = name;
        this.ingredients = ingredients;
        this.image = image;
        this.cook_time = cook_time;
        this.description = description;
        this.link = lnk;
        this.imageString = imgString;
    }


    // methods

    // returns the name of the recipe
    public String getName(){
        return name;
    }

    // returns the name of the image
    public ImageView getImage(){
        return image;
    }

    // returns the string associated with the picture
    public String getImageString() {return imageString;}

    public String getLink() {return link;}

    public String[] getIngredients() {
        String[] ingredientsArray = new String[ingredients.size()];
        for (int i = 0; i < ingredients.size(); ++i) {
            ingredientsArray[i] = ingredients.get(i);
        }
        return ingredientsArray;
    }

    public int getTime() { return cook_time; }

    // returns the description of the recipe
    public String getDescription(){
        return description;
    }
}
