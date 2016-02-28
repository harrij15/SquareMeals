package sm;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

/**
 * Created by putriz on 2/23/2016.
 */


public class HomepageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        /**
         * Creates a dropdown menu in the homepage
         */
        Spinner dropdown = (Spinner) findViewById(R.id.spinner);
        String[] dropdown_list = new String[]{"Hello User!","Cookbook","Preferences","Settings"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, dropdown_list);
        dropdown.setAdapter(adapter);

        GridView gridView = (GridView) findViewById(R.id.gridView2);
        gridView.setAdapter(new HomepageButtonAdapter(this));
    }
}
