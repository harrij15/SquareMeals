package sm;

import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Pushes on sign up view on click of sign up button
    public void signUp(View view) {
        Intent newIntent = new Intent(this,SignUpActivity.class);
        startActivity(newIntent);
    }

    public void logIn(View view) {

        EditText username = (EditText)findViewById(R.id.user_name);
        EditText password = (EditText)findViewById(R.id.password);
        TextView incorrect = (TextView)findViewById(R.id.incorrect);

        // Logs in user only if the username/password fields are filled
        if (username.getText().length() != 0 && password.getText().length() != 0) {    // If user/pass are both filled

            Intent homepageIntent = new Intent(this, HomepageActivity.class);

            // This will save the username to be used in HomepageActivity
            String user_name = username.getText().toString();
            String name_ = user_name;
            homepageIntent.putExtra("USERNAME",user_name);
            homepageIntent.putExtra("NAME",name_);

            startActivity(homepageIntent);

        } else {    //Tell user to fill in correct information
            incorrect.setVisibility(View.VISIBLE);
        }
    }

    public void guestEnter(View view) {
        Intent homepageIntent = new Intent(this, HomepageGuestActivity.class);
        startActivity(homepageIntent);

    }
}
