package sm;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

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
    int width;

    // constructor
    public HomepageListArrayAdapter(Context context, int resourceId, ArrayList recipes, int screenWidth){
        super(context, resourceId, recipes);
        resource = resourceId;
        inflater = LayoutInflater.from(context);
        this.context = context;
        width = screenWidth;
    }

    /*
     *  Will set up the layout for each row in the list in ListView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = (LinearLayout)inflater.inflate( resource, null);
        Recipe recipe = (Recipe) getItem( position );

        ImageView item = (ImageView) convertView.findViewById(R.id.item);

        int buttonWidth = width/2;
        int buttonHeight = buttonWidth;
        item.setLayoutParams(new LinearLayout.LayoutParams(buttonWidth,buttonHeight));
        item.setPadding(3, 3, 3, 3);
        //item.setAdjustViewBounds(true);
        ImageView imageView = recipe.getImage();


        if (imageView != null) {
            Drawable drawable = imageView.getDrawable();

            item.setImageDrawable(drawable);
            //item.setAdjustViewBounds(true);
            /*System.out.println("Item: " + item.getLayoutParams().width + " " + item.getLayoutParams().height);
            System.out.println("Image: " + item.getDrawable().getBounds().width() + " " + item.getDrawable().getBounds().height());
            System.out.println("Image2: " + drawable.getBounds().width() + " " + drawable.getBounds().height());*/
        } else {
            throw new RuntimeException("imageView is null!");
        }

        return convertView;

    }
}
