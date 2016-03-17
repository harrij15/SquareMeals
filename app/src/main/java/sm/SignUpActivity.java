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
        TextView fillAll = (TextView)findViewById(R.id.fill_all);

        // Logs in user only if all of the fields are filled
        if (username.getText().length() != 0 && password.getText().length() != 0 &&
                name.getText().length() != 0 && email.getText().length() != 0 &&
                confirm.getText().length() != 0 ) {

            Intent loadingIntent = new Intent(this, LoadingActivity.class);

            String user_name = username.getText().toString();
            loadingIntent.putExtra("USERNAME",user_name);
            loadingIntent.putExtra("NAME", user_name);

            startActivity(loadingIntent);



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
