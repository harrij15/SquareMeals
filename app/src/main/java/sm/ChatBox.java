package sm;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import com.loopj.android.http.*;


/**
 * Created by patels13 on 4/30/2016.
 */
public class ChatBox extends ActionBarActivity implements View.OnClickListener {

    EditText messageInput;
    Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_box);

        // get our input field by its ID
        messageInput = (EditText) findViewById(R.id.message_input);


        // get our button by its ID
        sendButton = (Button) findViewById(R.id.send_button);

        // set its click listener
        sendButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        postMessage();
    }

    private void postMessage()  {
    }
}
