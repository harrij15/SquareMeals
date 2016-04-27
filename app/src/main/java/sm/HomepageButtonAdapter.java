package sm;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

/**
 * Created by putriz on 2/28/2016.
 */
public class HomepageButtonAdapter extends BaseAdapter {
    private Context context;

    //static String[] recipes = {"RECIPE 1", "RECIPE 2", "RECIPE 3",
            //"RECIPE 4", "RECIPE 5", "RECIPE 6"};
    ArrayList<Recipe> recipes;

    // Gets the context so it can be used later
    public HomepageButtonAdapter(Context newContext, ArrayList<Recipe> recipesList) {
        context = newContext;
        recipes = recipesList;
    }

    // Total number of items contained in the adapter
    public int getCount() {
        return recipes.size();
    }

    // Required for structure
    public Object getItem(int position) { return null; }

    // Required for structure. Can be used to get id of an item
    public long getItemId(int position) {
        return position;
    }


    public View getView(int position,
                        View convertView, ViewGroup parent) {
        Button button;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            button = new Button(context);
            button.setLayoutParams(new GridView.LayoutParams(500,500));
            button.setPadding(3, 3, 3, 3);
        } else {
            button = (Button) convertView;
        }

        button.setText(String.format("%s", recipes.get(position).getName()));
        button.setTextColor(Color.WHITE);
        button.setId(position);
        //button.setBackgroundResource(recipes.get(position).getImage().getImageAlpha());

        return button;
    }

    /*// references to our images
    private Integer[] pictures = {
            R.drawable.dairy2, R.drawable.shellfish2,
            R.drawable.fish2, R.drawable.peanuts2,
            R.drawable.gluten2, R.drawable.eggs2
    };*/

}
