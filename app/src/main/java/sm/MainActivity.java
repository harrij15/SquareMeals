package sm;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SearchView searchView = (SearchView) findViewById(R.id.searchView);
        //Button filterButton = (Button) findViewById(R.id.filter_button);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ButtonAdapter(this));

       // final RelativeLayout currentLayout = (RelativeLayout) findViewById(R.id.main_layout);


        final View testView = new View(this);
        final ImageView imageView = (ImageView)findViewById(R.id.imageView);
        //final EditText usernameField = (EditText) findViewById(R.id.user_name);
        //final EditText passwordField = (EditText) findViewById(R.id.password);



        //ArrayList<EditText> textFields = new ArrayList<EditText>(){usernameField, passwordField};

        testView.setBackgroundColor(0xffffffff);
        //testView.addTouchables(textFields);    *

        Button logInButton = (Button) findViewById(R.id.sign_in_button);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupWindow popupWindow;

                //LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //final View inflatedView = layoutInflater.inflate(R.layout.activity_main,null,false);

                LinearLayout logInLayout = (LinearLayout) findViewById(R.id.login);
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.log_in, logInLayout);

                Resources resources = getResources();
                Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.white);
                RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(resources, bitmap);
                dr.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getWidth()) / 7.0f);


                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                popupWindow = new PopupWindow(layout, size.x - 50, size.y - 500, true);
                popupWindow.setFocusable(true);
                popupWindow.setElevation(60);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(dr);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);

                /**
                 *  When user presses the log-in button in the pop-up window,
                 *  go to the Homepage
                 *  */
                Button logIn = (Button)layout.findViewById(R.id.button);
                logIn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, HomepageActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });

        Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupWindow popupWindow;

                //LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //final View inflatedView = layoutInflater.inflate(R.layout.activity_main,null,false);

                LinearLayout logInLayout = (LinearLayout)findViewById(R.id.sign_up);
                LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View layout = layoutInflater.inflate(R.layout.sign_up, logInLayout);

                Resources resources = getResources();
                Bitmap bitmap = BitmapFactory.decodeResource(resources, R.drawable.white);
                RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(resources,bitmap);
                dr.setCornerRadius(Math.max(bitmap.getWidth(),bitmap.getWidth())/7.0f);


                Display display = getWindowManager().getDefaultDisplay();
                Point size = new Point();
                display.getSize(size);

                popupWindow = new PopupWindow(layout,size.x - 50, size.y - 500, true);
                popupWindow.setFocusable(true);
                popupWindow.setElevation(60);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setBackgroundDrawable(dr);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
            }
        });


        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });  */

    }
}
