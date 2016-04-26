package sm;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class MyDietActivity extends ListActivity implements OnItemClickListener {
    public static final int EDIT_REQUEST_CODE = 1;
    // public static final int ADD_REQUEST_CODE = 2;
    // public static final int REMOVE_REQUEST_CODE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_diet);
        // Create and populate item collection.
        ArrayList<String> itemList = new ArrayList<>();
        String[] itemArray = getResources().getStringArray(R.array.list_view_items);
        for (String item: itemArray) {
            itemList.add(item);
        }
        // Initialise the list view adapter.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,                                 // context
                android.R.layout.simple_list_item_1,  // layout used by row views
                itemList);                            // data
        // Attach the adapter to the ListView.
        setListAdapter(adapter);

        // Attach activity as item click listener.
        getListView().setOnItemClickListener(this);
    }

    // Callback method for item clicked events.
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Initialise intent.
        Intent intent = new Intent(getApplicationContext(), EditPref.class);
        // Add the position of the clicked item and its value as extra data.
        intent.putExtra("ItemPosition", position);
        intent.putExtra("ItemValue", parent.getItemAtPosition(position).toString());
        // Start the activity to edit the item value.
        startActivityForResult(intent, EDIT_REQUEST_CODE);
    }

    // Called when the EditItemActivity ends execution.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_REQUEST_CODE) {  // Identify which acitivty finished.
            if (resultCode == RESULT_OK) {         // Make sure the user didn't cancel.
                // Retrieve data returned by the EditItemActivity.
                int position = data.getIntExtra("ItemPosition", -1);
                String value = data.getStringExtra("ItemValue");
                if (position != -1 && value != null) {
                    // Retrieved data is valid, ask adapter to update data set.
                    ArrayAdapter<String> adapter = (ArrayAdapter<String>) getListAdapter();
                    ArrayList<String> items = new ArrayList<String>();
                    for (int i = 0; i < adapter.getCount(); i++) {
                        if (i != position) {
                            items.add(adapter.getItem(i));
                        } else {
                            items.add(value);
                        }
                    }
                    adapter.clear();
                    adapter.addAll(items);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    public void goBack(View view) {
        Intent intent = new Intent(this,HomepageActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this,HomepageActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }
}

