package sm;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.lang.reflect.*;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * Created by putriz on 3/6/2016.
 * This class is an adapter for ListView in HomepageActivity.
 * Sets up the layout for the list in Tab 2 of HomepageActivity.
 */

public class HomepageListArrayAdapter extends ArrayAdapter {

    private int resource;
    private LayoutInflater inflater;
    private Context context;

    // constructor
    public HomepageListArrayAdapter(Context context, int resourceId, List recipes){
        super(context, resourceId, recipes);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
        this.context = context;

    }

    /*
     *  Will set up the layout for each row in the list in ListView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = (RelativeLayout) inflater.inflate( resource, null);
        Recipe recipe = (Recipe) getItem( position );

        TextView name = (TextView) convertView.findViewById(R.id.recipe_name);
        name.setText(recipe.getName());

        TextView description = (TextView) convertView.findViewById(R.id.description);
        description.setText(recipe.getDescription());

/*        ImageView imageFood = (ImageView) convertView.findViewById(R.id.icon);
        // String image_name = recipe.getImage();
        //String uri = "drawable/" + recipe.getImage();
        Log.d("Test",recipe.getImage());
        int imageResource = context.getResources().getIdentifier(recipe.getImage(),"id",context.getPackageName());
        imageFood.setImageResource(imageResource);
        Log.d("um",Integer.toString(imageResource));*/
        //int imageResource = context.getResources().getIdentifier(uri,null,context.getPackageName());
        //Drawable image = context.getResources().getDrawable(imageResource, context.getTheme());

        return convertView;

    }
}
