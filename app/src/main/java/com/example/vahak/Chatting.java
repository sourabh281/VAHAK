package com.example.vahak;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class Chatting extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        ActionBar actionBar = getSupportActionBar();


        listView = findViewById(R.id.listView);
        arrayList = new ArrayList();
        arrayAdapter = new ArrayAdapter(Chatting.this , android.R.layout.simple_list_item_1 , arrayList);

        listView.setOnItemClickListener(Chatting.this);

        try {

            ProgressDialog progressDialog = new ProgressDialog(Chatting.this);
            progressDialog.setMessage(getString(R.string.txtProgressDialog));
            progressDialog.show();

            ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
            // whereNotEqualTo is used since users name should not be displayed in ListView
            parseQuery.whereNotEqualTo("username" , ParseUser.getCurrentUser().getUsername());
            parseQuery.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> users, ParseException e) {

                    if (e == null){

                        if (users.size() > 0){

                            for (ParseUser user : users){

                                arrayList.add(user.getUsername());
                            }
                            listView.setAdapter(arrayAdapter);
                            progressDialog.dismiss();

                        }
                    }

                }
            });

        }catch (Exception e){

            e.printStackTrace();
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu , menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){

            case R.id.logout_user:
                ParseUser.getCurrentUser().logOut();
                finish();
                Intent intent = new Intent(Chatting.this, Login.class);
                startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(Chatting.this , SendChats.class);
       intent.putExtra("SelectedUser" , arrayList.get(position));
        startActivity(intent);


    }
}