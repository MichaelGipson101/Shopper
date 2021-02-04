package com.example.shopper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHandler extends SQLiteOpenHelper {
    //initialize constants for the DB name and version
    public static final String DATABASE_NAME = "shopper.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_SHOPPING_LIST = "shoppinglist";
    public static final String COLUMN_LIST_ID = "_id";
    public static final String COLUMN_LIST_NAME = "name";
    public static final String COLUMN_LIST_STORE = "store";
    public static final String COLUMN_LIST_DATE = "date";
    /**
     * Create a version of the Shopper database
     * @param context reference to the activity that initializes a DBHandler
     * @param factory null
     */
    public DBHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    /**
     * Creates Shopper db tables
     * @param sqLiteDatabase reference to shopper db
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    /**
     * Creates a new version of the Shopper db
     * @param sqLiteDatabase reference to shopper db
     * @param oldVersion old version of shopper db
     * @param newVersion new version of shopper db
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
