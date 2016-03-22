package sm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

/**
 * Created by putriz on 3/21/2016.
 */

public class HomepageGuestActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_homepage);

        // TOOLBAR
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar_guest);

        toolbar.setTitle("Hello Guest!");
        setSupportActionBar(toolbar);

        // Set GridView
        GridView gridView = (GridView) findViewById(R.id.guest_homepage_gridView);
        gridView.setAdapter(new HomepageButtonAdapter(this));


    }

}
