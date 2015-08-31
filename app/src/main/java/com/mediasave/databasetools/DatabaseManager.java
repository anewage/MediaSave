package com.mediasave.databasetools;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

/**
 * Created by Amir on 8/28/2015.
 */
public class DatabaseManager {

    public final static int USERNAME_INDEX = 0;
    public final static int ACCESS_TOKEN_INDEX = 1;
    public final static int PROFILE_PIC_URL_INDEX = 2;

    private final String DATABASE_PATH = "/data/data/com.mediasave.mediasave/databases/mediaSaveData";
    private final String USERS_TABLE_NAME = "MediaSaveUsers";
    private SQLiteDatabase database;

    private static DatabaseManager instance;

    public static DatabaseManager getInstance() {
        if (instance == null){
            instance = new DatabaseManager();
        }
        return instance;
    }

    private DatabaseManager() {
        //first of all creating or openning the database which holds the access tokens
        database = SQLiteDatabase.openOrCreateDatabase(DATABASE_PATH , null);

        //Creating the table if it does not exists, if so, there will be no new table!
        database.execSQL("CREATE TABLE IF NOT EXISTS "
                + USERS_TABLE_NAME
                + " (username VARCHAR, access_token VARCHAR" +
                ", profile_pic_url VARCHAR);");
    }

    public Vector initDatabase() {
        Vector results = new Vector();

        //checking the access_tokens availabe in the table
        Cursor c = database.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + ";", null);

        //initializing the cursor just to be sure (Sikilmiya birdan!)
        c.moveToFirst();

        if (c.getCount() != 0){
            //getting the data of user(s)
            while(!c.isAfterLast()){
                String[] userData = new String[3];
                for (int i = 0; i<3; i++){
                    System.out.println("found in the database: " +  c.getString(i));
                    userData[i] = c.getString(i);
                }
                results.addElement(userData);
                c.moveToNext();
            }
        }

        c.close();

        return results;
    }

    public void insertOrReplaceIntoTable(String[] args) {
        String command = "INSERT OR REPLACE INTO "
                + USERS_TABLE_NAME
                + " VALUES ( '"
                + args[DatabaseManager.USERNAME_INDEX] + "' , '"
                + args[DatabaseManager.ACCESS_TOKEN_INDEX] + "' , '"
                + args[DatabaseManager.PROFILE_PIC_URL_INDEX]
                + "');";
        database.execSQL(command);
        System.out.println("user info inserted into table!");
        Cursor c = database.rawQuery("SELECT * FROM " + USERS_TABLE_NAME + ";", null);
        c.moveToFirst();
        String data = "";
        System.out.println("cursor size: " + c.getCount());
        while(!c.isAfterLast()){
            for (int i = 0; i<3; i++){
                data += c.getString(i) + "\t";
            }
            c.moveToNext();
        }
        c.close();
        System.out.println("current data in database: " + data);
    }

}
