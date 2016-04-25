package sm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    UserDatabaseHelper helper = new UserDatabaseHelper(this);

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
        TextView fillAll = (TextView)findViewById(R.id.fill_all);

        // Logs in user only if all of the fields are filled
        if (username.getText().length() != 0 && password.getText().length() != 0 &&
                name.getText().length() != 0 && email.getText().length() != 0 &&
                confirm.getText().length() != 0 ) {

            String user_name = username.getText().toString();
            String name_ = name.getText().toString();
            String email_ = email.getText().toString();
            String password_ = password.getText().toString();
            String confirm_ = confirm.getText().toString();

            if (password_.length() < 8 || password_.length() > 16) {
                // if the length of the password is less than the min or greater than the max
                Toast pass = Toast.makeText(SignUpActivity.this,"Enter a password in 8-16 characters!", Toast.LENGTH_SHORT);
                pass.show();

            } else if (user_name.length() > 15) {
                // if the length of the username exceeds the max number of characters
                Toast pass = Toast.makeText(SignUpActivity.this,"Username is too long!", Toast.LENGTH_SHORT);
                pass.show();

            } else if (!password_.equals(confirm_)){
                // if password and confirm don't match!
                Toast pass = Toast.makeText(SignUpActivity.this,"Passwords don't match!", Toast.LENGTH_SHORT);
                pass.show();

            } else if (helper.isUsernameTaken(user_name)) {
                // If the desired usernmae already exists in the database
                Toast pass = Toast.makeText(SignUpActivity.this,"Username already taken!", Toast.LENGTH_SHORT);
                pass.show();

            } else {
                // everything is good for sign up

                // insert information into the user database
                User user = new User(user_name);
                user.setName(name_);
                user.setEmail(email_);
                user.setPassword(password_);

                // insert info into database helper
                helper.insertUser(user);

                Intent loadingIntent = new Intent(this, LoadingActivity.class);

                Log.d("name", name_);
                loadingIntent.putExtra("USERNAME", user_name);
                loadingIntent.putExtra("NAME", name_);

                startActivity(loadingIntent);

            }

        } else {  // Change the text color of the instructions to red
            fillAll.setTextColor(0xffff0000);
        }


    }

    // On a keyboard press
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_ENTER: // Allow the user to sign in from keyboard
                signUp(null);
                return true;
            case KeyEvent.KEYCODE_BACK:  // Allow the user to go back to the login screen
                Intent logInIntent = new Intent(this,LoginActivity.class);
                startActivity(logInIntent);
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
