package sm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;



public class UpdateProfileActivity extends AppCompatActivity {
    String username, name, diet, json, user_email;
    EditText realName, userName, userEmail, password, confirmPassword;
    UserDatabaseHelper helper = new UserDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        realName = (EditText)findViewById(R.id.real_name);
        userName = (EditText)findViewById(R.id.user_name);
        userEmail = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        confirmPassword = (EditText)findViewById(R.id.retype);

        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
            diet = getIntent().getExtras().getString("DIET");
            json = getIntent().getExtras().getString("JSON");
            user_email = getIntent().getExtras().getString("EMAIL");
        } else {
            username = "";
            name = "";
            diet = "";
            json = "";
            user_email = "";
        }

        userName.setText(username);
        realName.setText(name);
        userEmail.setText(user_email);

    }

    public void Update_Changes(View view) {

        String user = userName.getText().toString();
        String name = realName.getText().toString();
        String email = userEmail.getText().toString();
        String pass = password.getText().toString();
        String confirm = confirmPassword.getText().toString();

        if (pass.length() == 0 || confirm.length() == 0) {
            // if the passwords fields are empty, just set them back to the original passwords
            pass = helper.searchPassword(username);
            confirm = pass;
        }

        if (!pass.equals(confirm)) {
            // if password and confirm don't match!
            Toast p = Toast.makeText(UpdateProfileActivity.this,"Passwords don't match!", Toast.LENGTH_SHORT);
            p.show();

        } else if (pass.length() < 8 || pass.length() > 16) {
            // if the password doesn't lie within 8-16 characters
            Toast p = Toast.makeText(UpdateProfileActivity.this,"Enter a password in 8-16 characters!", Toast.LENGTH_SHORT);
            p.show();

        } else if (user.length() > 15) {
            // if the username is too long
            Toast p = Toast.makeText(UpdateProfileActivity.this,"Username is too long!", Toast.LENGTH_SHORT);
            p.show();

        } else {
            // everything is good for updating the database
            long id = helper.searchID(username);
            boolean update = helper.updateData(id,user,name,pass,email,diet);
            helper.close();

            if (update) {
                Toast p = Toast.makeText(UpdateProfileActivity.this,"Update was successful!", Toast.LENGTH_SHORT);
                p.show();
            }

            Intent intent = new Intent(this,ProfilePage.class);
            intent.putExtra("DIET", diet);
            intent.putExtra("USERNAME",user);
            intent.putExtra("NAME", name);
            intent.putExtra("JSON", json);
            startActivity(intent);

        }

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
