package com.mediasave.activities;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.mediasave.databasetools.DatabaseManager;

import java.util.Vector;

public class WelcomeActivity extends AppCompatActivity {

    public static final String USER_DATA = "authenticated_user_data";
    public static final String USER_COUNT = "users_count";

    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Full Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


        //here goes my code!
        //Splash Screen
        Thread goToNextActivity = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //TODO go to next activity
                    init();
                }

            }
        };

        goToNextActivity.start();
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
        dbManager = DatabaseManager.getInstance();
        Vector data = dbManager.initDatabase();
        if (data.isEmpty()){
            //open up the login screen in order to get the access_token
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }
        else {
            switch (data.size()){
                case 1:
                    // here we have only one authenticated user
                    Intent mainIntent = new Intent(this, MainActivity.class);

                    String [] userData = (String[]) data.firstElement();
                    mainIntent.putExtra(USER_DATA, userData);

                    startActivity(mainIntent);

                    break;

                default:
                    // multi-user
                    Intent intent = new Intent(this, MultiUserActivity.class);

                    intent.putExtra(USER_COUNT, data.size());
                    for (int i=0 ; i<data.size(); i++) {
                        intent.putExtra("User" + i, (String[]) data.elementAt(i));
                    }
                    startActivity(intent);
                    break;
            }
        }
        finish();
    }

}
