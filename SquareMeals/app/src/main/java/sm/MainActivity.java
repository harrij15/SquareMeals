package sm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //SearchView searchView = (SearchView) findViewById(R.id.searchView);
        //Button filterButton = (Button) findViewById(R.id.filter_button);
        GridView gridView = (GridView) findViewById(R.id.gridView);
        gridView.setAdapter(new ButtonAdapter(this));

        /*gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            }
        });  */

    }
}
