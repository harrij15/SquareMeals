package sm.onboarding;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import sm.R;
import sm.database.UserDatabaseHelper;

public class SignUpActivity extends AppCompatActivity {

    UserDatabaseHelper helper = new UserDatabaseHelper(this);

    EditText username;
    EditText confirm;
    EditText password;
    EditText name;
    EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = (EditText)findViewById(R.id.real_name);
        username = (EditText)findViewById(R.id.user_name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        confirm = (EditText)findViewById(R.id.retype);

        String username_string, name_string, email_string, password_string, confirm_string;

        confirm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;

                // For some reason, the action ID on the keyboard is "Done",
                // instead of "Send"
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signUp(null);
                    handled = true;
                }

                else if (event != null) {
                    if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                        signUp(null);
                        handled = true;
                    }
                }
                System.out.println(actionId);
                System.out.println(EditorInfo.IME_ACTION_SEND);
                return handled;
            }
        });

        // In order to have the values already filled in
        // if we return back to this page from the SelectPreferencesActivity
        if (getIntent().getExtras() != null) {
            name_string = getIntent().getExtras().getString("NAME");
            username_string = getIntent().getExtras().getString("USERNAME");
            email_string = getIntent().getExtras().getString("EMAIL");
            password_string = getIntent().getExtras().getString("PASSWORD");
            confirm_string = password_string;

            name.setText(name_string);
            username.setText(username_string);
            email.setText(email_string);
            password.setText(password_string);
            confirm.setText(confirm_string);
        }
    }
        //check if email is valid
        public boolean isValidEmailAddress(EditText email) {
        String emails=email.getText().toString();
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(emails);
        return m.matches();
    }


    // Signs up new user
    public void signUp(View view) {

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
                Toast pass = Toast.makeText(SignUpActivity.this,"Enter a password in 8-16 characters!", Toast.LENGTH_LONG);
                pass.show();

            } else if (user_name.length() > 15) {
                // if the length of the username exceeds the max number of characters
                Toast pass = Toast.makeText(SignUpActivity.this,"Username is too long!", Toast.LENGTH_LONG);
                pass.show();

            } else if (!password_.equals(confirm_)){
                // if password and confirm don't match!
                Toast pass = Toast.makeText(SignUpActivity.this,"Passwords don't match!", Toast.LENGTH_LONG);
                pass.show();

            } else if (helper.isUsernameTaken(user_name)) {
                // If the desired username already exists in the database
                Toast pass = Toast.makeText(SignUpActivity.this,"Username already taken!", Toast.LENGTH_LONG);
                pass.show();

            } else if (!isValidEmailAddress(email)) {
            // If the email is invalid
            Toast pass = Toast.makeText(SignUpActivity.this,"Email is invalid!", Toast.LENGTH_LONG);
            pass.show();

            }else {
                // everything is good for sign up

                // insert information into the user database
//                User user = new User(user_name);
//                user.setName(name_);
//                user.setEmail(email_);
//                user.setPassword(password_);

                // insert info into database helper
//                helper.insertUser(user);

                //Intent loadingIntent = new Intent(this, LoadingActivity.class);

                // go into the preferences page
                Intent preferencesIntent = new Intent(this, SelectPreferencesActivity.class);

                preferencesIntent.putExtra("USERNAME", user_name);
                preferencesIntent.putExtra("NAME", name_);
                preferencesIntent.putExtra("EMAIL", email_);
                preferencesIntent.putExtra("PASSWORD", password_);
                preferencesIntent.putExtra("FLAG", "signUp");

                startActivity(preferencesIntent);

                finish();

//                Log.d("name", name_);
//                loadingIntent.putExtra("USERNAME", user_name);
//                loadingIntent.putExtra("NAME", name_);
//
//                startActivity(loadingIntent);

            }
//            Intent preferencesIntent = new Intent(this, SelectPreferencesActivity.class);
//
//            String user_name = username.getText().toString();
//            String name_ = name.getText().toString();
//
//            preferencesIntent.putExtra("USERNAME",user_name);
//            preferencesIntent.putExtra("NAME", name_);
//
//            startActivity(preferencesIntent);
//
//            finish();

        } else {  // Change the text color of the instructions to red
            fillAll.setTextColor(0xffff0000);
        }


    }

    // On a keyboard press
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        switch (keyCode) {
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
