package com.example.vahak;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class SendChats extends AppCompatActivity implements View.OnClickListener {

    private ListView chatListView;
    private ArrayList<String> chatsList;
    private ArrayAdapter adapter;
    private String SelectedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_chats);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.txtSendChats);
        actionBar.setDisplayHomeAsUpEnabled(true);

        SelectedUser = getIntent().getStringExtra("SelectedUser");
        Toast.makeText(SendChats.this , "Chat With " + SelectedUser +" Now!!" , Toast.LENGTH_SHORT).show();

        findViewById(R.id.btnSendMessage).setOnClickListener(SendChats.this);
        chatListView = findViewById(R.id.chatsListView);
        chatsList = new ArrayList<>();
        adapter = new ArrayAdapter(this , android.R.layout.simple_list_item_1 , chatsList);
        chatListView.setAdapter(adapter);

        try {

            ParseQuery<ParseObject> firstUserChatQuery = ParseQuery.getQuery("Chat");
            ParseQuery<ParseObject> secondUserChatQuery = ParseQuery.getQuery("Chat");

            firstUserChatQuery.whereEqualTo("vaSender", ParseUser.getCurrentUser().getUsername());
            firstUserChatQuery.whereEqualTo("vaTargetRecipient", SelectedUser);

            secondUserChatQuery.whereEqualTo("vaSender", SelectedUser);
            secondUserChatQuery.whereEqualTo("vaTargetRecipient", ParseUser.getCurrentUser().getUsername());

            ArrayList<ParseQuery<ParseObject>> allQueries = new ArrayList<>();
            allQueries.add(firstUserChatQuery);
            allQueries.add(secondUserChatQuery);

            ParseQuery<ParseObject> myQueries = ParseQuery.or(allQueries);  // this or() method accepts array list of ParseObjects
            myQueries.orderByAscending("createdAt");

            myQueries.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects.size() > 0 && e == null) {

                        for (ParseObject chatObject : objects) {

                            String vaMessage = chatObject.get("vaMessage").toString();

                            if (chatObject.get("vaSender").equals(ParseUser.getCurrentUser().getUsername())) {

                                vaMessage = ParseUser.getCurrentUser().getUsername() + ": " + vaMessage.toString();

                            }

                            if (chatObject.get("vaSender").equals(SelectedUser)) {

                                vaMessage = SelectedUser + ": " + vaMessage.toString();
                            }

                            chatsList.add(vaMessage);
                        }
                        adapter.notifyDataSetChanged();


                    }
                }
            });

        } catch (Exception e){

            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

        final EditText edtMessage = findViewById(R.id.edtWriteMessage);

        ParseObject chat = new ParseObject("Chat");
        chat.put("vaSender" , ParseUser.getCurrentUser().getUsername());
        chat.put("vaTargetRecipient" , SelectedUser);
        chat.put("vaMessage" , edtMessage.getText().toString() );
        chat.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){

                    Toast.makeText(SendChats.this , "Message From " + ParseUser.getCurrentUser().getUsername()
                            + " Sent To "  + SelectedUser , Toast.LENGTH_SHORT).show();
                    chatsList.add(ParseUser.getCurrentUser().getUsername() + ": " + edtMessage.getText().toString());
                    adapter.notifyDataSetChanged();   // It updates the ListView
                    edtMessage.setText("");           // To clear EditText

                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}