package com.example.shopper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.CursorAdapter;


public class DBHandler extends SQLiteOpenHelper {
    //initialize constants for the DB name and version
    public static final String DATABASE_NAME = "shopper.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_SHOPPING_LIST = "shoppinglist";
    public static final String COLUMN_LIST_ID = "_id";
    public static final String COLUMN_LIST_NAME = "name";
    public static final String COLUMN_LIST_STORE = "store";
    public static final String COLUMN_LIST_DATE = "date";

    public static final String TABLE_SHOPPING_LIST_ITEM = "shoppinglistitem";
    public static final String COLUMN_ITEM_ID = "_id";
    public static final String COLUMN_ITEM_NAME = "name";
    public static final String COLUMN_ITEM_PRICE = "price";
    public static final String COLUMN_ITEM_QUANTITY = "quantity";
    public static final String COLUMN_ITEM_HAS = "item_has";
    public static final String COLUMN_ITEM_LIST_ID = "list_id";
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
        //define create statement fdr shopping list table and store it
        //in a string
        String query = "CREATE TABLE " + TABLE_SHOPPING_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_STORE + " TEXT, " +
                COLUMN_LIST_DATE + " TEXT);";

        //execute the statement
        sqLiteDatabase.execSQL(query);

        //define create statement fdr shopping list table and store it
        //in a string
        String query2 = "CREATE TABLE " + TABLE_SHOPPING_LIST_ITEM + "(" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT, " +
                COLUMN_ITEM_PRICE + " DECIMAL(10,2), " +
                COLUMN_ITEM_QUANTITY + " INTEGER, " +
                COLUMN_ITEM_HAS + " TEXT, " +
                COLUMN_ITEM_LIST_ID + " INTEGER);";

        //execute the statement
        sqLiteDatabase.execSQL(query2);
    }

    /**
     * Creates a new version of the Shopper db
     * @param sqLiteDatabase reference to shopper db
     * @param oldVersion old version of shopper db
     * @param newVersion new version of shopper db
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        //define drop statement and store it in a string
        String query = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST;

        //execute the drop statement
        sqLiteDatabase.execSQL(query);

        //define drop statement and store it in a string
        String query2 = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST_ITEM;

        //execute the drop statement
        sqLiteDatabase.execSQL(query2);

        //call the method that creates
        onCreate(sqLiteDatabase);
    }

    /**
     * this method gets called when the add button in the action bar of the
     * createlist activity gets clicked. It inserts a new row into the shopping list table
     * @param name shopping list name
     * @param store shopping list store
     * @param date shopping list date
     */
    public void addShoppingList(String name, String store, String date) {
        //get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        //initialize a contentvalues object
        ContentValues values = new ContentValues();

        //put data into contentvalues object
        values.put(COLUMN_LIST_NAME, name);
        values.put(COLUMN_LIST_STORE, store);
        values.put(COLUMN_LIST_DATE, date);

        //insert data into contentvalues object into shoppinglist table
        db.insert(TABLE_SHOPPING_LIST, null, values);

        //close db reference
        db.close();
    }

    /**
     * this method gets called when the mainactivity is created. It will
     * select and return all of the data in the shoppinglist table
     * @return Cursor that contains all data in the shoppinglist table
     */
    public Cursor getShoppingList() {
        //get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        //define select statement and store it in a string
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST;

        //execute the select statement and return it as a cursor
        return db.rawQuery(query, null);
    }

    /**
     * this method gets called when the viewlist activity is started
     * @param id shopping list id
     * @return shopping list name
     */
    public String getShoppingListName(int id) {
        //get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        //declare and initialize the string that will be returned
        String name = "";

        //define select statement
        String query = "SELECT * FROM " + TABLE_SHOPPING_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id;

        //execute select statement and store it in a cursor
        Cursor cursor = db.rawQuery(query, null);

        //move to the first row in the cursor
        cursor.moveToFirst();

        //;check that name component of cursor isn't null
        if ((cursor.getString(cursor.getColumnIndex("name")) != null)) {
            //get the name component of the cursor and store it in a string
            name = cursor.getString(cursor.getColumnIndex("name"));
        }

        //close reference to shopper db
        db.close();

        //return shopping list name
        return name;

    }

    /**
     *
     * This method gets called when the add button in the action bar of the additem activity gets clicked
     * @param name item name
     * @param price item price
     * @param quantity item quantity
     * @param listid id of the shoppinglist to which the item is being added
     */
    public void addItemToList(String name, Double price, Integer quantity, Integer listid) {
        //get reference to the shopper database
        SQLiteDatabase db = getWritableDatabase();

        //initialize a contentvalues object
        ContentValues values = new ContentValues();

        //put data into contentvalues object
        values.put(COLUMN_ITEM_NAME, name);
        values.put(COLUMN_ITEM_PRICE, price);
        values.put(COLUMN_ITEM_QUANTITY, quantity);
        values.put(COLUMN_ITEM_HAS, "false");
        values.put(COLUMN_ITEM_LIST_ID, listid);

        //insert data into contentvalues object into shoppinglistitem table
        db.insert(TABLE_SHOPPING_LIST_ITEM, null, values);

        //close db reference
        db.close();
    }
}
