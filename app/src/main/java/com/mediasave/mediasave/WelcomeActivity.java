package com.mediasave.mediasave;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.mediasave.sqltools.DatabaseManager;

import java.util.Vector;

public class WelcomeActivity extends AppCompatActivity {

    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //here goes my code!
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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

    private void init() {
        dbManager = new DatabaseManager(this);
        Vector data = dbManager.initDatabase();
        if (data.isEmpty()){
            //open up the login screen in order to get the access_token
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }
        else {
            switch (data.size()){
                case 1:
                    //TODO

                    /*
                    Passing user's credentials to MainActivity, it should be started
                     */

                    // our app has only one authenticated user
                    Intent feedIntent = new Intent(this, MainActivity.class);

                    String [] userData = (String[]) data.firstElement();
                    feedIntent.putExtra(getString(R.string.authenticated_user_data) , userData );

                    startActivity(feedIntent);

                    break;

                default:
                    //TODO

                    /*
                    Passing the users data to MultiUserActivity, it should be started
                     */

                    // multi-user
                    Intent intent = new Intent(this, MultiUserActivity.class);

                    intent.putExtra(getString(R.string.multi_user_count), data.size());
                    for (int i=0 ; i<data.size(); i++) {
                        intent.putExtra("User" + i, (String[]) data.elementAt(i));
                    }

                    startActivity(intent);

                    break;
            }
        }

        //close the current activity
//        finish();
    }

}
