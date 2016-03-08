package sm;

import java.util.ArrayList;

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

    // constructor
    public Recipe(String name, ArrayList<String> ingredients, String image, String description, int cook_time){
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
    public void setImage(String image){
        this.image = image;
    }

    // returns the description of the recipe
    public String getDescription(){
        return description;
    }


}
