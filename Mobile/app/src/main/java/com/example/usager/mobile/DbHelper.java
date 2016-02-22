package com.example.usager.mobile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mic on 21/02/2016.
 */
public class DbHelper extends SQLiteOpenHelper {

    //doit changer la version de la bd a chaque fois que quelque chose change
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "resto.db";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_STUDENT = "CREATE TABLE " + Users.TABLE  + "("
                + Users.KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + Users.KEY_firstName + " TEXT, "
                + Users.KEY_lastName + " TEXT, "
                + Users.KEY_email + " TEXT, "
                + Users.KEY_pw + " TEXT, "
                + Users.KEY_birth + " TEXT, "
                + Users.KEY_postal + " TEXT, "
                + Users.KEY_dog + " TEXT )";

        db.execSQL(CREATE_TABLE_STUDENT);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // supprime et recréer la table
        db.execSQL("DROP TABLE IF EXISTS " + Users.TABLE);

        // Create tables again
        onCreate(db);

    }
}
