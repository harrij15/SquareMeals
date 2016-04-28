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
    String diet, username, name, json, changedFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_diet);
        if(getIntent().getExtras()!= null){
            diet = getIntent().getExtras().getString("DIET");
            //System.out.println("this is the not null diet: "+diet);
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
            json = getIntent().getExtras().getString("JSON");
            //System.out.println("Entering: " + json);
            changedFlag = getIntent().getExtras().getString("CHANGED");
        }
        else{
            diet = "";
            username = "";
            name = "";
            json = "";
            changedFlag = "";
        }
        // Create and populate item collection.
        ArrayList<String> itemList = new ArrayList<>();
        String emptyString = "";
        if (!diet.equals(emptyString) && !diet.equals("None") && !diet.equals("Not Sure")) {
            String dietName;
            switch(diet) {
                case "Lacto+vegetarian":
                    dietName = "Lacto-vegetarian";
                    break;
                case "Ovo+vegetarian":
                    dietName = "Ovo-vegetarian";
                    break;
                default:
                    dietName = diet;
                    break;
            }
            itemList.add(dietName);
        } else {
            itemList.add("Click to insert diet");
        }
       /* String[] itemArray = getResources().getStringArray(R.array.list_view_items);
        for (String item: itemArray) {
            itemList.add(item);
        }*/
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
        Intent intent = new Intent(getApplicationContext(),  SelectPreferencesActivity.class);
        // Add the position of the clicked item and its value as extra data
        intent.putExtra("ItemValue", parent.getItemAtPosition(position).toString());
        intent.putExtra("FLAG", "EditExisting");
        intent.putExtra("USERNAME",username);
        intent.putExtra("NAME",name);
        intent.putExtra("JSON",json);
        intent.putExtra("DIET",diet);
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
                    /*for (int i = 0; i < adapter.getCount(); i++) {
                        if (i != position) {
                            items.add(adapter.getItem(i));
                        } else {
                            items.add(value);
                        }
                    }*/
                    items.add(value);
                    adapter.clear();
                    adapter.addAll(items);
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    public void goBack(View view) {
        Intent intent;
       // System.out.println(changedFlag);
        if (changedFlag != null) {
            intent = new Intent(this,LoadingActivity.class);
            intent.putExtra("CHANGED",changedFlag);
        } else {
            intent = new Intent(this, HomepageActivity.class);
            intent.putExtra("JSON", json);
        }
        intent.putExtra("USERNAME",username);
        intent.putExtra("NAME",name);
        intent.putExtra("DIET",diet);

        startActivity(intent);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent;
            //System.out.println(changedFlag);
            if (changedFlag != null) {
                intent = new Intent(this,LoadingActivity.class);
                intent.putExtra("CHANGED",changedFlag);
            } else {
                intent = new Intent(this,HomepageActivity.class);
                intent.putExtra("JSON", json);
            }
            intent.putExtra("USERNAME",username);
            intent.putExtra("NAME",name);
            intent.putExtra("DIET",diet);
            startActivity(intent);
            return true;
        }

        return false;
    }
}

