package sm.chat;

import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by patels13 on 4/30/2016.
 */
public class ChatBox{

//    EditText messageInput;
//    Button sendButton;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chat_box);
//
//        // get our input field by its ID
//        messageInput = (EditText) findViewById(R.id.message_input);
//
//
//        // get our button by its ID
//        sendButton = (Button) findViewById(R.id.send_button);
//
//        // set its click listener
//        sendButton.setOnClickListener(this);
//    }
//    @Override
//    public void onClick(View v) {
//        postMessage();
//    }
//    private void postMessage() {}

//        String text = messageInput.getText().toString();
//
//        // return if the text is blank
//        if (text.equals("")) {
//            return;
//        }
//
//
//        RequestParams params = new RequestParams();
//
//        // set our JSON object
//        params.put("text", text);
//        params.put("name", username);
//        params.put("time", new Date().getTime());
//
//        // create our HTTP client
//        AsyncHttpClient client = new AsyncHttpClient();
//    }
//    client.post(MESSAGES_ENDPOINT, params, new JsonHttpResponseHandler(){
//
//        @Override
//        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    messageInput.setText("");
//                }
//            });
//        }
//
//        @Override
//        public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//            Toast.makeText(
//                    getApplicationContext(),
//                    "Something went wrong :(",
//                    Toast.LENGTH_LONG
//            ).show();
//        }
//    });
}
