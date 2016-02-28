package sm;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

/**
 * Created by putriz on 2/28/2016.
 */
public class HomepageButtonAdapter extends BaseAdapter {
    private Context context;

    static String[] recipes = {"RECIPE 1", "RECIPE 2", "RECIPE 3",
            "RECIPE 4"};

    // Gets the context so it can be used later
    public HomepageButtonAdapter(Context newContext) { context = newContext; }

    // Total number of items contained in the adapter
    public int getCount() {
        return recipes.length;
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
            button.setLayoutParams(new GridView.LayoutParams(400,400));
            button.setPadding(2, 2, 2, 2);
        } else {
            button = (Button) convertView;
        }

        button.setText(String.format("%s", recipes[position]));
        button.setTextColor(Color.WHITE);
        button.setId(position);
        button.setBackgroundResource(pictures[position]);
        return button;
    }

    // references to our images
    private Integer[] pictures = {
            R.drawable.vegan2, R.drawable.religion2,
            R.drawable.diabetes4, R.drawable.peanuts2
    };



}
