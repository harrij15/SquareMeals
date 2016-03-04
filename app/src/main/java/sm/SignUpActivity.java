package sm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    // Signs up new user
    public void signUp(View view) {

        EditText username = (EditText)findViewById(R.id.user_name);
        EditText password = (EditText)findViewById(R.id.password);
        EditText name = (EditText)findViewById(R.id.real_name);
        EditText email = (EditText)findViewById(R.id.email);
        EditText confirm = (EditText)findViewById(R.id.retype);

        // Logs in user only if all of the fields are filled
        if (username.getText().length() != 0 && password.getText().length() != 0 &&
                name.getText().length() != 0 && email.getText().length() != 0 &&
                confirm.getText().length() != 0 ) {

            //Intent homepageIntent = new Intent(this,homepage.class);  //Don't forget to change the class!
            //startActivity(homepageIntent);

        }


    }
}
