package zejs.com.mycookbook;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

/**
 * Created by harrij15 on 1/19/2016.
 */
public class ButtonAdapter extends BaseAdapter {
    private Context context;
    static String[] restrictions = {"VEGAN", "RELIGION", "DIABETES",
                                     "ALLERGIES"};
    // Gets the context so it can be used later
    public ButtonAdapter(Context newContext) {
        context = newContext;
    }

    // Total number of items contained in the adapter
    public int getCount() {
        return restrictions.length;
    }

    // Required for structure
    public Object getItem(int position) {
        return null;
    }

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
            button.setLayoutParams(new GridView.LayoutParams(400,220));
            button.setPadding(8, 8, 8, 8);
        } else {
            button = (Button) convertView;
        }

        button.setText(String.format("%s", restrictions[position]));
        button.setTextColor(Color.WHITE);
        button.setId(position);
        button.setBackgroundResource(pictures[position]);
        return button;
    }

    // references to our images
    private Integer[] pictures = {
            R.drawable.vegan2,R.drawable.religion2,
            R.drawable.diabetes4, R.drawable.peanuts2
    };
}
