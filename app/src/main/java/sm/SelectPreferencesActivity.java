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
import android.widget.Toast;

/**
 * This page can be accessed by the user immediately after the user signs up or
 * if the user chooses to edit his/her preferences from the menu bar.
 * Preferences available to choose from:
 * Vegetarian, Vegan, Pescetarian, Lacto+Vegetarian, Ovo+Vegetarian, Paleo, Not Sure/None
 */
public class SelectPreferencesActivity extends AppCompatActivity {

    enum Preference {
        VEGETARIAN,
        VEGAN,
        PESCATARIAN,
        LACTO_VEGETARIAN,
        OVO_VEGETARIAN,
        PALEOLITHIC,
        NOT_SURE,
        NONE
    }

    CheckedTextView checked;
    int id;

    UserDatabaseHelper helper = new UserDatabaseHelper(this);
    String username, name , email, password, flag;
    String diet, json, inputDiet;
    Preference inputDiet2 = Preference.NONE;
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
        // TODO: find a way to let user choose multiple
        vegetarianBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if (checked != null) {
                    checked.setChecked(false);
                }
                checked = vegetarianBox;
                //id = 1;
                inputDiet2 = Preference.VEGETARIAN;

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
                //id = 2;
                inputDiet2 = Preference.VEGAN;

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
                //id = 3;
                inputDiet2 = Preference.PESCATARIAN;

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
                //id = 4;
                inputDiet2 = Preference.LACTO_VEGETARIAN;

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
                //id = 5;
                inputDiet2 = Preference.OVO_VEGETARIAN;

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
                //id = 6;
                inputDiet2 = Preference.PALEOLITHIC;

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
                //id = 7;
                inputDiet2 = Preference.NOT_SURE;

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
                //id = 8;
                inputDiet2 = Preference.NONE;

                if (((CheckedTextView) v).isChecked()) {
                    ((CheckedTextView) v).setChecked(false);
                } else {
                    ((CheckedTextView) v).setChecked(true);
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                switch(inputDiet2) {
                    case VEGETARIAN:
                        diet = "Vegetarian";
                        break;
                    case VEGAN:
                        diet = "Vegan";
                        break;
                    case PESCATARIAN:
                        diet = "Pescetarian";
                        break;
                    case LACTO_VEGETARIAN:
                        diet = "Lacto+vegetarian";
                        break;
                    case OVO_VEGETARIAN:
                        diet = "Ovo+vegetarian";
                        break;
                    case PALEOLITHIC:
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

                // update the database on the new diet info
                long id = helper.searchID(username);
                String pass = helper.searchPassword(username);
                String email_ = helper.searchEmail(username);
                boolean update = helper.updateData(id,username,name,pass,email_,diet);
                if (update) {
                    // success message
                    Toast p = Toast.makeText(SelectPreferencesActivity.this,"Update was successful!", Toast.LENGTH_SHORT);
                    p.show();
                }

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

            // create a new user and insert it into the database
            User user = new User(username,name,password,email,diet);
            helper.insertUser(user);
            helper.close();

            startActivity(loadingIntent);
            finish();
        }
    }

}
