package sm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by putriz on 3/6/2016.
 * The Recipe class stores information for every recipe.
 */

public class Recipe {

    private String name; // name of recipe
    private String image; // picture of the recipe
    private ArrayList<String> ingredients;
    private String description;
    private int cook_time;

    /**
     *  Default constructor of Recipe.
     *
     */
    public Recipe(){
        this.name = "recipe_name";
        this.image = "recipe_image";
        this.ingredients = new ArrayList<String>();
        this.description = "recipe_description";
        this.cook_time = 0;
    }

    // constructor
    public Recipe(String name, ArrayList<String> ingredients, String image, String description, int cook_time){
        super();
        this.name = name;
        this.ingredients = ingredients;
        this.image = image;
        this.cook_time = cook_time;
        this.description = description;
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
    public String getImage(){
        return image;
    }

    /**
     * Set the image of the recipe.
     * @param image, a string of the name of the image
     */
    public void setImage(String image){
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

    public ArrayList<String> getIngredients() {
        return ingredients;
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

}
