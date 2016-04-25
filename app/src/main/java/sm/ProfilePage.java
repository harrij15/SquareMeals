package sm;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class ProfilePage extends AppCompatActivity {
    ImageView profilepic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        profilepic=(ImageView) findViewById(R.id.imageViewUserImage);

        profilepic.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent();
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
}
