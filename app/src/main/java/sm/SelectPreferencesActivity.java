package sm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class SelectPreferencesActivity extends AppCompatActivity {
    CheckedTextView checked;
    int id;

    UserDatabaseHelper helper = new UserDatabaseHelper(this);
    String username, name , email, password, flag;
    String diet, json, inputDiet;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_preferences);


        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
            email = getIntent().getExtras().getString("EMAIL");
            password = getIntent().getExtras().getString("PASSWORD");

            json = getIntent().getExtras().getString("JSON");
            inputDiet = getIntent().getExtras().getString("DIET");
            if(getIntent().getExtras().getString("FLAG")!=null){
                flag = getIntent().getExtras().getString("FLAG");

            } else {
                flag = "";
            }

        } else {
            username = "";
            name = "";
            email = "";
            json = "";
            inputDiet = "";
            flag = "";
        }

        if ("EditExisting".equals(flag)) {
            TextView header = (TextView)findViewById(R.id.dietTextView);
            header.setText("What's your new diet?");
        }

        final CheckedTextView vegetarianBox = (CheckedTextView) findViewById(R.id.VegetarianCheck);
        final CheckedTextView veganBox = (CheckedTextView) findViewById(R.id.VeganCheck);
        final CheckedTextView pescBox = (CheckedTextView) findViewById(R.id.PescCheck);
        final CheckedTextView lactoBox = (CheckedTextView) findViewById(R.id.LactoCheck);
        final CheckedTextView ovoBox = (CheckedTextView) findViewById(R.id.OvoCheck);
        final CheckedTextView paleoBox = (CheckedTextView) findViewById(R.id.PaleoCheck);
        final CheckedTextView notSureBox = (CheckedTextView) findViewById(R.id.NotSureCheck);
        final CheckedTextView noneBox = (CheckedTextView) findViewById(R.id.NoneCheck);

        final Button submitButton = (Button)findViewById(R.id.submitPref);

        // Each of these handles when user selects a preference
        // TO-DO: find a way to let user choose multiple
        vegetarianBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (checked != null) {
                    checked.setChecked(false);
                }
                checked = vegetarianBox;
                id = 1;

                if (((CheckedTextView) v).isChecked()) {
                    ((CheckedTextView) v).setChecked(false);
                } else {
                    ((CheckedTextView) v).setChecked(true);
                }
            }
        });

        veganBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (checked != null) {
                    checked.setChecked(false);
                }
                checked = veganBox;
                id = 2;

                if (((CheckedTextView) v).isChecked()) {
                    ((CheckedTextView) v).setChecked(false);
                } else {
                    ((CheckedTextView) v).setChecked(true);
                }
            }
        });

        pescBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (checked != null) {
                    checked.setChecked(false);
                }
                checked = pescBox;
                id = 3;

                if (((CheckedTextView) v).isChecked()) {
                    ((CheckedTextView) v).setChecked(false);
                } else {
                    ((CheckedTextView) v).setChecked(true);
                }
            }
        });

        lactoBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (checked != null) {
                    checked.setChecked(false);
                }
                checked = lactoBox;
                id = 4;

                if (((CheckedTextView) v).isChecked()) {
                    ((CheckedTextView) v).setChecked(false);
                } else {
                    ((CheckedTextView) v).setChecked(true);
                }
            }
        });

        ovoBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (checked != null) {
                    checked.setChecked(false);
                }
                checked = ovoBox;
                id = 5;

                if (((CheckedTextView) v).isChecked()) {
                    ((CheckedTextView) v).setChecked(false);
                } else {
                    ((CheckedTextView) v).setChecked(true);
                }
            }
        });

        paleoBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (checked != null) {
                    checked.setChecked(false);
                }
                checked = paleoBox;
                id = 6;

                if (((CheckedTextView) v).isChecked()) {
                    ((CheckedTextView) v).setChecked(false);
                } else {
                    ((CheckedTextView) v).setChecked(true);
                }
            }
        });

        notSureBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (checked != null) {
                    checked.setChecked(false);
                }
                checked = notSureBox;
                id = 7;

                if (((CheckedTextView) v).isChecked()) {
                    ((CheckedTextView) v).setChecked(false);
                } else {
                    ((CheckedTextView) v).setChecked(true);
                }
            }
        });

        noneBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (checked != null) {
                    checked.setChecked(false);
                }
                checked = noneBox;
                id = 8;

                if (((CheckedTextView) v).isChecked()) {
                    ((CheckedTextView) v).setChecked(false);
                } else {
                    ((CheckedTextView) v).setChecked(true);
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                switch(id) {
                    case 1:
                        diet = "Vegetarian";
                        break;
                    case 2:
                        diet = "Vegan";
                        break;
                    case 3:
                        diet = "Pescetarian";
                        break;
                    case 4:
                        diet = "Lacto+vegetarian";
                        break;
                    case 5:
                        diet = "Ovo+vegetarian";
                        break;
                    case 6:
                        diet = "Paleo";
                        break;
                    /*case 7:
                        diet = "Not Sure";
                        break;
                    case 8:
                        diet = "None";
                        break;*/
                    default:
                        diet = "";
                        break;
                }

                submit();

            }
        });
    }

    // Submit preferences
    public void submit() {
        if("EditExisting".equals(flag)){
            //System.out.println("got the new information");
            Intent newdata = new Intent(this, MyDietActivity.class);
            position = 0 ;
            newdata.putExtra("ItemPosition", position);
            newdata.putExtra("USERNAME", username);
            newdata.putExtra("NAME",name);
            newdata.putExtra("JSON",json);
            newdata.putExtra("DIET", diet);
            if (!inputDiet.equals(diet)) {
                newdata.putExtra("CHANGED","yes");
            }
            // Notify calling activity the user accepted changes.
            setResult(RESULT_OK, newdata);
            // End execution.
            startActivity(newdata);
            finish();
        }
        else{
            Intent loadingIntent = new Intent(this, LoadingActivity.class);

            loadingIntent.putExtra("DIET", diet);
            loadingIntent.putExtra("USERNAME", username);
            loadingIntent.putExtra("NAME", name);

            // create new user and insert it into the database

            User user = new User(username,name,password,email);
            user.setPreference(diet);
            helper.insertUser(user);

            startActivity(loadingIntent);
            finish();
        }
    }

}
