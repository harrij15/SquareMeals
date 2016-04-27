package sm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import sm.R;

public class UpdateProfileActivity extends AppCompatActivity {
    String username, name, diet, json;
    EditText realName, userName, email, password, confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        realName = (EditText)findViewById(R.id.real_name);
        userName = (EditText)findViewById(R.id.user_name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        confirmPassword = (EditText)findViewById(R.id.retype);

        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
            diet = getIntent().getExtras().getString("DIET");
            json = getIntent().getExtras().getString("JSON");
        } else {
            username = "";
            name = "";
            diet = "";
            json = "";
        }

        userName.setText(username);
        realName.setText(name);
        email.setText("@rpi.edu");

    }

    public void Update_Changes(View view) {

        String user = userName.getText().toString();
        String name = realName.getText().toString();
        Intent intent = new Intent(this,ProfilePage.class);
        intent.putExtra("DIET",diet);
        intent.putExtra("USERNAME",user);
        intent.putExtra("NAME", name);
        intent.putExtra("JSON", json);
        startActivity(intent);

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent keyEvent) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(this,HomepageActivity.class);
            intent.putExtra("DIET",diet);
            intent.putExtra("USERNAME",username);
            intent.putExtra("NAME", name);
            intent.putExtra("JSON",json);
            startActivity(intent);
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
