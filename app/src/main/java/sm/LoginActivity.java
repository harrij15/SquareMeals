package sm;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    UserDatabaseHelper helper = new UserDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //Pushes on sign up view on click of sign up button
    public void signUp(View view) {
        Intent newIntent = new Intent(this,SignUpActivity.class);
        startActivity(newIntent);
    }

    //Logs in user, saving the username to greet them on the next activity
    public void logIn(View view) {

        EditText username = (EditText)findViewById(R.id.user_name);
        EditText password = (EditText)findViewById(R.id.password);
        TextView incorrect = (TextView)findViewById(R.id.incorrect);

        // Logs in user only if the username/password fields are filled
        if (username.getText().length() != 0 && password.getText().length() != 0) {    // If user/pass are both filled

            // See if the username and password match up
            String username_ = username.getText().toString();
            String password_ = password.getText().toString();
            String pass = helper.searchPassword(username_);
            String name = helper.searchName(username_);

            if (!pass.equals(password_)){
            // if username and password don't match
                incorrect.setVisibility(View.VISIBLE);

            } else {

                Intent loadingIntent = new Intent(this, LoadingActivity.class);

                // This will save the username to be used in LoadingActivity
                String user_name = username.getText().toString();
                loadingIntent.putExtra("USERNAME",user_name);
                loadingIntent.putExtra("NAME",name);

                // Default value until database is implemented
                String diet = "None";
                loadingIntent.putExtra("DIET",diet);

                startActivity(loadingIntent);
            }

        } else {    //Tell user to fill in correct information
            incorrect.setVisibility(View.VISIBLE);
        }
    }

    public void guestEnter(View view) {
        Intent loadingIntent = new Intent(this, LoadingActivity.class);
        startActivity(loadingIntent);
    }

    // Allows the user use the enter key on the keyboard to go
    // straight from the last EditText to the next activity
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            logIn(null);
            return true;
        }
        return false;
    }

    // Allows the user to click on the screen to hide the keyboard
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }
}
