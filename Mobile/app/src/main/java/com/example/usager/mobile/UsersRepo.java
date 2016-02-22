package com.example.usager.mobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mic on 21/02/2016.
 */
public class UsersRepo {

    private DbHelper dbHelper;

    public UsersRepo(Context context) {
        dbHelper = new DbHelper(context);
    }

    //insert de nouvelle donnée
    public int Insert(Users userToAdd) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Users.KEY_firstName, userToAdd.firstName);
        values.put(Users.KEY_lastName, userToAdd.lastName);
        values.put(Users.KEY_email,userToAdd.email);
        values.put(Users.KEY_pw, userToAdd.pw);
        values.put(Users.KEY_birth, userToAdd.birth.toString());
        values.put(Users.KEY_postal, userToAdd.postal);
        values.put(Users.KEY_dog, userToAdd.dog);

        // Inserting Row
        long UsersID = db.insert(Users.TABLE, null, values);
        db.close(); // Closing database connection
        return (int) UsersID;
    }

    //supprime le user
    public void DeleteByID(int Users_Id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(Users.TABLE, Users.KEY_ID + "= ?", new String[] { String.valueOf(Users_Id) });
        db.close();
    }

    public void Update(Users userToUpdate) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Users.KEY_firstName, userToUpdate.firstName);
        values.put(Users.KEY_lastName, userToUpdate.lastName);
        values.put(Users.KEY_email,userToUpdate.email);
        values.put(Users.KEY_pw, userToUpdate.pw);
        values.put(Users.KEY_birth, userToUpdate.birth.toString());
        values.put(Users.KEY_postal, userToUpdate.postal);
        values.put(Users.KEY_dog, userToUpdate.dog);

        // It's a good practice to use parameter ?, instead of concatenate string
        db.update(Users.TABLE, values, Users.KEY_ID + "= ?", new String[] { String.valueOf(userToUpdate.ID) });
        db.close(); // Closing database connection
    }

    public ArrayList<HashMap<String, String>> getUsersList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  "
                + Users.KEY_ID
                +","+ Users.KEY_firstName
                +","+ Users.KEY_lastName
                +","+ Users.KEY_email
                +","+ Users.KEY_pw
                +","+ Users.KEY_birth
                +","+ Users.KEY_postal
                +","+ Users.KEY_dog
                + " FROM " + Users.TABLE;

        //Users Users = new Users();
        ArrayList<HashMap<String, String>> UsersList = new ArrayList<HashMap<String, String>>();

        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> Users = new HashMap<String, String>();
                Users.put("ID", cursor.getString(cursor.getColumnIndex(Users.KEY_ID)));
                Users.put("firstName", cursor.getString(cursor.getColumnIndex(Users.KEY_firstName)));
                Users.put("lastName", cursor.getString(cursor.getColumnIndex(Users.KEY_lastName)));
                Users.put("email", cursor.getString(cursor.getColumnIndex(Users.KEY_email)));
                Users.put("pw", cursor.getString(cursor.getColumnIndex(Users.KEY_pw)));
                Users.put("birth", cursor.getString(cursor.getColumnIndex(Users.KEY_birth)));
                Users.put("postal", cursor.getString(cursor.getColumnIndex(Users.KEY_postal)));
                Users.put("dog", cursor.getString(cursor.getColumnIndex(Users.KEY_dog)));
                UsersList.add(Users);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return UsersList;

    }

    public Users getUsersById(int ID){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery =  "SELECT  "
                + Users.KEY_ID
                +","+ Users.KEY_firstName
                +","+ Users.KEY_lastName
                +","+ Users.KEY_email
                +","+ Users.KEY_pw
                +","+ Users.KEY_birth
                +","+ Users.KEY_postal
                +","+ Users.KEY_dog
                +" FROM " + Users.TABLE
                + " WHERE " +
                Users.KEY_ID + "=?";// It's a good practice to use parameter ?, instead of concatenate string

        int iCount =0;
        Users usersToGet = new Users();

        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(ID) } );

        if (cursor.moveToFirst()) {
            do {
                usersToGet.ID = cursor.getInt(cursor.getColumnIndex(Users.KEY_ID));
                usersToGet.firstName = cursor.getString(cursor.getColumnIndex(Users.KEY_firstName));
                usersToGet.lastName = cursor.getString(cursor.getColumnIndex(Users.KEY_lastName));
                usersToGet.email = cursor.getString(cursor.getColumnIndex(Users.KEY_email));
                usersToGet.pw = cursor.getString(cursor.getColumnIndex(Users.KEY_pw));
                usersToGet.birth = cursor.getString(cursor.getColumnIndex(Users.KEY_birth));
                usersToGet.postal = cursor.getString(cursor.getColumnIndex(Users.KEY_postal));
                usersToGet.dog = cursor.getString(cursor.getColumnIndex(Users.KEY_dog));


            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return usersToGet;
    }
}
