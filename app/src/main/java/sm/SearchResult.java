package sm;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.util.ArrayList;

/**
 * Created by harrij15 on 4/5/2016.
 */
// Similar to Recipe class to handle search results
public class SearchResult {
    private String name; // name of recipe
    private String image; // picture of the recipe
    private ArrayList<String> ingredients;
    private String description;
    private int cook_time;

    // constructor
    public SearchResult(String name, ArrayList<String> ingredients, String image, String description, int cook_time){
        super();
        this.name = name;
        this.ingredients = ingredients;
        this.image = image;
        this.cook_time = cook_time;
        this.description = description;
    }


    // methods

    // returns the name of the recipe
    public String getName(){
        return name;
    }

    // returns the name of the image
    public String getImage(){
        return image;
    }

    // sets the image of the recipe
    /*public void setImage(String image){
        this.image = image;
    }*/

    // returns the description of the recipe
    public String getDescription(){
        return description;
    }
}
