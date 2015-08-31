package com.mediasave.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Vector;

public class MultiUserActivity extends AppCompatActivity {

    private Vector usersData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_user);

        //getting the users list from intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        //first let's get the users count
        int usersCount = extras.getInt(WelcomeActivity.USER_COUNT);
        usersData = new Vector();
        for (int i = 0; i<usersCount ; i++){
            String [] data = extras.getStringArray("User" + i);
            usersData.addElement(data);
        }

        /*
        now let's make circle shapes using the users data and their profile pictures!
        (farzin joon dasteto miboose azizam :D)
        each circle shape must have an OnClickListener or something in order to start MainActivity
        with the selected user's
         */
        //TODO
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_multi_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
