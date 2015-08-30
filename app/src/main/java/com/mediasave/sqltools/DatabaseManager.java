package com.mediasave.sqltools;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.mediasave.mediasave.R;
import com.mediasave.mediasave.WelcomeActivity;

import java.util.Vector;

/**
 * Created by Amir on 8/28/2015.
 */
public class DatabaseManager {

    public final static int USERNAME_INDEX = 0;
    public final static int ACCESS_TOKEN_INDEX = 1;
    public final static int PROFILE_PIC_URL_INDEX = 2;

    private WelcomeActivity welcome;
    private SQLiteDatabase database;

    public DatabaseManager(WelcomeActivity welcome) {
        this.welcome = welcome;
    }

    public Vector initDatabase() {
        Vector results = new Vector();

        //first of all creating or openning the database which holds the access tokens
        database = welcome.openOrCreateDatabase(welcome.getString(R.string.database_name), Context.MODE_PRIVATE, null);

        //Creating the table if it does not exists, if so, there will be no tables created!
        database.execSQL("CREATE TABLE IF NOT EXISTS " + welcome.getString(R.string.table_name) + " (username VARCHAR, access_token VARCHAR, profile_pic_url VARCHAR);");

        //checking the access_tokens availabe in the table
        Cursor c = database.rawQuery("SELECT * FROM " + welcome.getString(R.string.table_name) + ";", null);

        //initializing the cursor just to be sure (Sichilmiya birdan!)
        c.moveToFirst();

        if (c.getCount() != 0){
            //getting the data of user(s)
            while(c.moveToNext()) {
                String[] userData = new String[3];
                for (int i = 0; i<3; i++){
                    userData[i] = c.getString(i);
                }
                results.addElement(userData);
            }
        }

        return results;
    }

}
