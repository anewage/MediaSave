package com.mediasave.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mediasave.networking.InstagramEndPointClient;
import com.mediasave.networking.ResponseHandler;
import com.mediasave.user.AuthenticatedUser;

public class MainActivity extends AppCompatActivity {

    private InstagramEndPointClient client;
    private ResponseHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client = InstagramEndPointClient.getInstance();
        handler = new ResponseHandler(null);

        Intent starter = getIntent();
        String[] userData = starter.getStringArrayExtra(WelcomeActivity.USER_DATA);
        AuthenticatedUser user = new AuthenticatedUser(userData);
        client.setUser(user);

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
        if (isNetworkAvailable()) {
            client.getFeed(null, handler);
        } else {
            // TODO: 8/31/2015
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
}
