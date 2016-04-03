package com.example.esr14.signupplay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ESR14 on 28/03/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "user.db";
    private static final String TABLE_NAME = "user";
    private static final String COLUMN_ID = "id";
    //private static final String COLUMN_NAME = "name";
    //private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_UNAME = "uname";
    private static final String COLUMN_PASS = "password";

    SQLiteDatabase sqLiteDatabase;

    private static final String TABLE_CREATE = "CREATE TABLE user " +
            "(id integer primary key not null, " +
            //"name text, " +
            //"email text, " +
            "uname text not null, " +
            "password text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.sqLiteDatabase = db;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

    public void insertUser(User user) {
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String query = "SELECT * from user";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        int count = cursor.getCount();

        contentValues.put(COLUMN_ID, count);
        contentValues.put(COLUMN_UNAME, user.getUname());
        contentValues.put(COLUMN_PASS, user.getPassword());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public Boolean dataExist() {
        sqLiteDatabase = this.getReadableDatabase();
        String q = "select * from user";
        //sqLiteDatabase.execSQL(q);
        Cursor cursor = sqLiteDatabase.rawQuery(q, null);
        Boolean exist = false;

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {
                Log.i("username", cursor.getString(cursor.getColumnIndex("uname")));
                Log.i("password", cursor.getString(cursor.getColumnIndex("password")));
                Log.i("", "---------------------------------------");
            }
            Log.i("Cursor count", ((Integer.toString(cursor.getCount()))));
            exist = true;
        } else {
            Log.i("INFO", "No records found");
        }

        cursor.close();

        return exist;
    }

    public Boolean removeAllUsers(String tableName) {

        sqLiteDatabase = this.getWritableDatabase();
        String deleteAll = "DELETE FROM user;";
        //String deleteAll = "DROP TABLE user.user;";

        sqLiteDatabase.execSQL(deleteAll);

        Boolean exist = false;
        if (this.dataExist()) {
            exist = true;
        }
        Log.i("Data exists?:", exist.toString());
        sqLiteDatabase.close();

        return exist;

    }

    public Boolean available(String username) {
        sqLiteDatabase = this.getReadableDatabase();
        //String query = "SELECT * FROM user WHERE uname = '" + username + "' COLLATE NOCASE;";
        String query = "SELECT * FROM user WHERE uname = '" + username + "';";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        if (cursor.getCount() > 0) {
            Log.i("names", "already exists");
            cursor.close();
            return false;
        }

        return true;
    }

    public String searchPassword(String uname) {
        sqLiteDatabase = this.getReadableDatabase();
        String querySearchPassword = "SELECT uname, password from " + TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(querySearchPassword, null);
        String username, password;
        password = "not found";
        if (cursor.moveToFirst()) {
            do {
                username = cursor.getString(0);

                if (username.equals(uname)) {
                    password = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        return password;
    }
}
