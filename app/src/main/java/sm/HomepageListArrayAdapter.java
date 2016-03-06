package sm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by putriz on 3/6/2016.
 */
public class HomepageListArrayAdapter extends ArrayAdapter<String> {
    //private Context context;
    //private String[] recipes;

    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    // constructor
    public HomepageListArrayAdapter(Context context, int textViewResourceId, ArrayList<String> recipes){
        super(context, textViewResourceId, recipes);
        for (int i = 0; i < recipes.size(); ++i){
            mIdMap.put(recipes.get(i),i);
        }
    }

    @Override
    public long getItemId(int position) {
        String item = getItem(position);
        return mIdMap.get(item);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

/*    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.homepage_list, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.firstLine);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(recipes[position]);

        return rowView;
    }*/
}
