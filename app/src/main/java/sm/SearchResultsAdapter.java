package sm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by harrij15 on 4/4/2016.
 */
// Handles SearchResearch objects on view
public class SearchResultsAdapter extends ArrayAdapter {

    private int resource;
    private LayoutInflater inflater;
    private Context context;

    // constructor
    public SearchResultsAdapter(Context context, int resourceId, List recipes){
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
        SearchResult recipe = (SearchResult) getItem( position );


        TextView name = (TextView) convertView.findViewById(R.id.recipe_name);
        name.setText(recipe.getName());

        TextView description = (TextView) convertView.findViewById(R.id.description);
        description.setText(recipe.getDescription());

        ImageView icon = (ImageView) convertView.findViewById(R.id.icon);
        ImageView imageView = recipe.getImage();

        if (imageView != null) {
            icon.setImageDrawable(recipe.getImage().getDrawable());
        } else {
            throw new RuntimeException("imageView is null!");
        }

        return convertView;

    }


}
