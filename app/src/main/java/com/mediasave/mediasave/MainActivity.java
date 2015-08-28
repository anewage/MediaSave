package com.mediasave.mediasave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mediasave.InstagramAPIEndPoint.AuthenticatedUser;
import com.mediasave.InstagramAPIEndPoint.EndPoint;

public class MainActivity extends AppCompatActivity {

    private EndPoint endPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the authenticated user's data from intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String [] userData = extras.getStringArray(getString(R.string.authenticated_user_data));

        //creating a new data structure to hold the user credentials
        AuthenticatedUser user = new AuthenticatedUser(userData);
        endPoint = EndPoint.getInstance();
        endPoint.setUser(user);

        loadFeed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void loadFeed() {
        //TODO
    }
}
