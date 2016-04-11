package sm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;

public class SelectPreferencesActivity extends AppCompatActivity {
    CheckedTextView checked;
    int id;
    String username, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_preferences);


        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
        } else {
            username = "";
            name = "";
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

                String diet = "";

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
                        diet = "Lacto vegetarian";
                        break;
                    case 5:
                        diet = "Ovo vegetarian";
                        break;
                    case 6:
                        diet = "Paleo";
                        break;
                    case 7:
                        diet = "Not Sure";
                        break;
                    case 8:
                        diet = "None";
                        break;
                    default:
                        break;
                }

                submit(diet);

            }
        });
    }

    // Submit preferences
    public void submit(String diet) {

        Intent loadingIntent = new Intent(this, LoadingActivity.class);

        loadingIntent.putExtra("DIET", diet);
        loadingIntent.putExtra("USERNAME", username);
        loadingIntent.putExtra("NAME", name);

        startActivity(loadingIntent);
        finish();
    }
}
