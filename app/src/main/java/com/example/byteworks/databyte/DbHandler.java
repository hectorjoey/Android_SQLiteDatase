package com.example.byteworks.databyte;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;


public class DbHandler extends SQLiteOpenHelper {

    String TAG = this.getClass().getSimpleName();
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "phone_book_db";
    private static final String TABLE_Users = "userdetails";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL = "email";
   private static final String KEY_PHONE = "phone";

    public DbHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tableDataType = "TEXT";
        StringBuilder createTableBuilder = new StringBuilder("CREATE TABLE ").append(TABLE_Users).append("(");
        createTableBuilder.append(KEY_FIRST_NAME).append(" ").append(tableDataType).append(", ")
                .append(KEY_LAST_NAME).append(" ").append(tableDataType).append(", ")
                .append(KEY_EMAIL).append(" ").append(tableDataType).append(", ")
                .append(KEY_PHONE).append(" ").append(tableDataType).append(" ");

        createTableBuilder.append(")");

        Log.e(TAG, "onCreate Creating Table: " + createTableBuilder.toString());

        db.execSQL(createTableBuilder.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_Users);
        // Create tables again
        onCreate(db);
    }
    // **** CRUD (Create, Read, Update, Delete) Operations ***** //

    // Adding new User Details
    void insertUserDetails(String first_name, String last_name, String email, String phone) {



        //Get the Data Repository in write mode
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_FIRST_NAME, first_name);
        cValues.put(KEY_LAST_NAME, last_name);
        cValues.put(KEY_EMAIL, email);
      cValues.put(KEY_PHONE, phone);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Users, null, cValues);
        db.close();
    }
    // Get User Details

    public ArrayList<HashMap<String, String>> GetUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
        String query = "SELECT first_name, last_name, email , phone FROM " + TABLE_Users;

        Log.e(TAG, "GetUsers: " + query );

        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> user = new HashMap<>();
            user.put("first_name", cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
            user.put("last_name", cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
            user.put("email", cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
           user.put("phone", cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
            userList.add(user);
        }

        cursor.close();
        return userList;
    }

//    // Get User Details based on userid
//    public ArrayList<HashMap<String, String>> GetUserByUserId(int userid) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ArrayList<HashMap<String, String>> userList = new ArrayList<>();
//        String query = "SELECT first_name, last_name, email, phone FROM " + TABLE_Users;
//        Cursor cursor = db.query(TABLE_Users, new String[]{KEY_FIRST_NAME, KEY_LAST_NAME, KEY_EMAIL}, KEY_ID + "=?", new String[]{String.valueOf(userid)}, null, null, null, null);
//        if (cursor.moveToNext()) {
//            HashMap<String, String> user = new HashMap<>();
//            user.put("first_name", cursor.getString(cursor.getColumnIndex(KEY_FIRST_NAME)));
//            user.put("last_name", cursor.getString(cursor.getColumnIndex(KEY_LAST_NAME)));
//            user.put("email", cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
//            //user.put("phone",cursor.getString(cursor.getColumnIndex(KEY_PHONE)));
//            userList.add(user);
//        }
//        return userList;
//    }
//// Delete User Details
//public void DeleteUser(int userid){
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_Users, KEY_ID+" = ?",new String[]{String.valueOf(userid)});
//        db.close();
//        }
//// Update User Details
//public int UpdateUserDetails(String lastname, String email, int id){
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cVals = new ContentValues();
//        cVals.put(KEY_LAST_NAME, lastname);
//        cVals.put(KEY_EMAIL, email);
//        int count = db.update(TABLE_Users, cVals, KEY_ID+" = ?",new String[]{String.valueOf(id)});
//        return  count;
//        }
}