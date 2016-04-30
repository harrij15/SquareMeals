package sm;

import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by putriz on 3/6/2016.
 * The Recipe class stores information for every recipe.
 */

public class Recipe {

    private String name; // name of recipe
    private ImageView image; // picture of the recipe
    private ArrayList<String> ingredients;
    private String description;
    private int cook_time;
    private String link;

    /**
     *  Default constructor of Recipe.
     *
     */
    /*public Recipe(){
        this.name = "recipe_name";
        this.image = "recipe_image";
        this.ingredients = new ArrayList<String>();
        this.description = "recipe_description";
        this.cook_time = 0;
    }*/

    // constructor
    public Recipe(String name, ArrayList<String> ingredients, ImageView image, String description, int cook_time, String link){
        super();
        this.name = name;
        this.ingredients = ingredients;
        this.image = image;
        this.cook_time = cook_time;
        this.description = description;
        this.link = link;
    }


    // METHODS

    /**
     * Get the name of the recipe.
     * @return the name of the recipe
     */
    public String getName(){
        return name;
    }

    /**
     * Set the name of the recipe.
     * @param name, a new name for the recipe
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Get the image of the recipe.
     * @return the string name of the image of the recipe.
     */
    public ImageView getImage(){
        return image;
    }

    /**
     * Set the image of the recipe.
     * @param image, a string of the name of the image
     */
    public void setImage(ImageView image){
        this.image = image;
    }


    // returns the description of the recipe
    public String getDescription(){
        return description;
    }
    // set the description of the image
    public void setDescription(String description) {
        this.description = description;
    }

    public String[] getIngredients() {
        String[] ingredientsArray = new String[ingredients.size()];
        for (int i = 0; i < ingredients.size(); ++i) {
            ingredientsArray[i] = ingredients.get(i);
        }
        return ingredientsArray;
    }
    public void setIngredients( ArrayList<String> ingredients ){
        this.ingredients = ingredients;
    }

    public int getCook_time() {
        return cook_time;
    }
    public void setCook_time(int cook_time) {
        this.cook_time = cook_time;
    }

    public String getLink() { return link; }

}
