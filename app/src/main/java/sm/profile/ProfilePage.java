package sm.profile;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import sm.R;
import sm.database.UserDatabaseHelper;
import sm.homepage.HomepageActivity;

public class ProfilePage extends AppCompatActivity {
    ImageView profilepic;
    String username, name, diet, json, user_email;
    UserDatabaseHelper helper = new UserDatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.profile_page);
        profilepic=(ImageView) findViewById(R.id.imageViewUserImage);

        if (getIntent().getExtras() != null) {
            username = getIntent().getExtras().getString("USERNAME");
            name = getIntent().getExtras().getString("NAME");
            diet = getIntent().getExtras().getString("DIET");
            json = getIntent().getExtras().getString("JSON");
            user_email = helper.searchEmail(username); // get email from the database
        } else {
            username = "";
            name = "";
            diet = "";
            json = "";
            user_email = "";
        }

        TextView realName = (TextView)findViewById(R.id.real_name);
        TextView userName = (TextView)findViewById(R.id.user_name);
        TextView email = (TextView)findViewById(R.id.email);
        System.out.println(name + " " + username);
        realName.setText(name);
        userName.setText(username);
        email.setText(user_email);


        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Contact Image"), 1);
            }
        });
    }

    public void OnActivityResult(int reqCode,int resCode, Intent data){
        if (resCode==RESULT_OK){
            if (reqCode==1){
                profilepic.setImageURI(data.getData());
            }
        }
    }

    public void Update_Changes(View view) {
        Intent intent = new Intent(this, UpdateProfileActivity.class);
        intent.putExtra("DIET",diet);
        intent.putExtra("USERNAME",username);
        intent.putExtra("NAME", name);
        intent.putExtra("JSON",json);
        intent.putExtra("EMAIL",user_email);
        startActivity(intent);
    }

    public void goBack(View view) {
        Intent intent = new Intent(this,HomepageActivity.class);
        intent.putExtra("DIET",diet);
        intent.putExtra("USERNAME",username);
        intent.putExtra("NAME", name);
        intent.putExtra("JSON",json);
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
}
