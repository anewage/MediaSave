package com.mediasave.sqltools;


import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.mediasave.mediasave.R;

import java.util.Vector;

/**
 * Created by Amir on 8/28/2015.
 */
public class DatabaseManager {

    public final static int USERNAME_INDEX = 0;
    public final static int ACCESS_TOKEN_INDEX = 1;
    public final static int PROFILE_PIC_URL_INDEX = 2;

    private Activity parent;
    private SQLiteDatabase database;

    public DatabaseManager(Activity parent) {
        this.parent = parent;

        //first of all creating or openning the database which holds the access tokens
        database = this.parent.openOrCreateDatabase(this.parent.getString(R.string.database_name), Context.MODE_PRIVATE, null);

        
        /*
        should be deleted // TODO: 8/29/2015
         */
        database.execSQL("DROP TABLE TabViewUsers;");

        //Creating the table if it does not exists, if so, there will be no tables created!
        database.execSQL("CREATE TABLE IF NOT EXISTS " + parent.getString(R.string.table_name) + " (username VARCHAR, access_token VARCHAR, profile_pic_url VARCHAR);");
    }

    public Vector initDatabase() {
        Vector results = new Vector();

        //checking the access_tokens availabe in the table
        Cursor c = database.rawQuery("SELECT * FROM " + parent.getString(R.string.table_name) + ";", null);

        //initializing the cursor just to be sure (Sichilmiya birdan!)
        c.moveToFirst();

        if (c.getCount() != 0){
            //getting the data of user(s)
            while(c.moveToNext()){
                String[] userData = new String[3];
                for (int i = 0; i<3; i++){
                    userData[i] = c.getString(i);
                }
                results.addElement(userData);
            }
        }

        return results;
    }

    public void insertOrReplaceIntoTable(String[] args) {
        String command = "INSERT OR REPLACE INTO "
                + parent.getString(R.string.table_name)
                + " VALUES ( '"
                + args[DatabaseManager.USERNAME_INDEX] + "' , '"
                + args[DatabaseManager.ACCESS_TOKEN_INDEX] + "' , '"
                + args[DatabaseManager.PROFILE_PIC_URL_INDEX]
                + "');";
        database.execSQL(command);
        System.out.println("user info inserted into table!");
    }

}
