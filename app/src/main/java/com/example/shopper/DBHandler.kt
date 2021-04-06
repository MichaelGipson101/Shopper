package com.example.shopper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.CursorFactory
import android.database.sqlite.SQLiteOpenHelper

class DBHandler
/**
 * Create a version of the Shopper database
 * @param context reference to the activity that initializes a DBHandler
 * @param factory null
 */
(context: Context?, factory: CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {
    /**
     * Creates Shopper db tables
     * @param sqLiteDatabase reference to shopper db
     */
    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        //define create statement fdr shopping list table and store it
        //in a string
        val query = "CREATE TABLE " + TABLE_SHOPPING_LIST + "(" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT, " +
                COLUMN_LIST_STORE + " TEXT, " +
                COLUMN_LIST_DATE + " TEXT);"

        //execute the statement
        sqLiteDatabase.execSQL(query)

        //define create statement fdr shopping list table and store it
        //in a string
        val query2 = "CREATE TABLE " + TABLE_SHOPPING_LIST_ITEM + "(" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT, " +
                COLUMN_ITEM_PRICE + " DECIMAL(10,2), " +
                COLUMN_ITEM_QUANTITY + " INTEGER, " +
                COLUMN_ITEM_HAS + " TEXT, " +
                COLUMN_ITEM_LIST_ID + " INTEGER);"

        //execute the statement
        sqLiteDatabase.execSQL(query2)
    }

    /**
     * Creates a new version of the Shopper db
     * @param sqLiteDatabase reference to shopper db
     * @param oldVersion old version of shopper db
     * @param newVersion new version of shopper db
     */
    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

        //define drop statement and store it in a string
        val query = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST

        //execute the drop statement
        sqLiteDatabase.execSQL(query)

        //define drop statement and store it in a string
        val query2 = "DROP TABLE IF EXISTS " + TABLE_SHOPPING_LIST_ITEM

        //execute the drop statement
        sqLiteDatabase.execSQL(query2)

        //call the method that creates
        onCreate(sqLiteDatabase)
    }

    /**
     * this method gets called when the add button in the action bar of the
     * createlist activity gets clicked. It inserts a new row into the shopping list table
     * @param name shopping list name
     * @param store shopping list store
     * @param date shopping list date
     */
    fun addShoppingList(name: String?, store: String?, date: String?) {
        //get reference to the shopper database
        val db = writableDatabase

        //initialize a contentvalues object
        val values = ContentValues()

        //put data into contentvalues object
        values.put(COLUMN_LIST_NAME, name)
        values.put(COLUMN_LIST_STORE, store)
        values.put(COLUMN_LIST_DATE, date)

        //insert data into contentvalues object into shoppinglist table
        db.insert(TABLE_SHOPPING_LIST, null, values)

        //close db reference
        db.close()
    }//get reference to the shopper database

    //define select statement and store it in a string

    //execute the select statement and return it as a cursor
    /**
     * this method gets called when the mainactivity is created. It will
     * select and return all of the data in the shoppinglist table
     * @return Cursor that contains all data in the shoppinglist table
     */
    val shoppingList: Cursor
        get() {
            //get reference to the shopper database
            val db = writableDatabase

            //define select statement and store it in a string
            val query = "SELECT * FROM " + TABLE_SHOPPING_LIST

            //execute the select statement and return it as a cursor
            return db.rawQuery(query, null)
        }

    /**
     * this method gets called when the viewlist activity is started
     * @param id shopping list id
     * @return shopping list name
     */
    fun getShoppingListName(id: Int): String {
        //get reference to the shopper database
        val db = writableDatabase

        //declare and initialize the string that will be returned
        var name = ""

        //define select statement
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + id

        //execute select statement and store it in a cursor
        val cursor = db.rawQuery(query, null)

        //move to the first row in the cursor
        cursor.moveToFirst()

        //;check that name component of cursor isn't null
        if (cursor.getString(cursor.getColumnIndex("name")) != null) {
            //get the name component of the cursor and store it in a string
            name = cursor.getString(cursor.getColumnIndex("name"))
        }

        //close reference to shopper db
        db.close()

        //return shopping list name
        return name
    }

    /**
     * this method gets called when the viewlist activity is started
     * @param id shopping list id
     * @return shopping list name
     */
    fun getItemName(id: Int): String {
        //get reference to the shopper database
        val db = writableDatabase

        //declare and initialize the string that will be returned
        var name = ""

        //define select statement
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_ID + " = " + id

        //execute select statement and store it in a cursor
        val cursor = db.rawQuery(query, null)

        //move to the first row in the cursor
        cursor.moveToFirst()

        //;check that name component of cursor isn't null
        if (cursor.getString(cursor.getColumnIndex("name")) != null) {
            //get the name component of the cursor and store it in a string
            name = cursor.getString(cursor.getColumnIndex("name"))
        }

        //close reference to shopper db
        db.close()

        //return shopping list name
        return name
    }

    /**
     *
     * This method gets called when the add button in the action bar of the additem activity gets clicked
     * @param name item name
     * @param price item price
     * @param quantity item quantity
     * @param listid id of the shoppinglist to which the item is being added
     */
    fun addItemToList(name: String?, price: Double?, quantity: Int?, listid: Int?) {
        //get reference to the shopper database
        val db = writableDatabase

        //initialize a contentvalues object
        val values = ContentValues()

        //put data into contentvalues object
        values.put(COLUMN_ITEM_NAME, name)
        values.put(COLUMN_ITEM_PRICE, price)
        values.put(COLUMN_ITEM_QUANTITY, quantity)
        values.put(COLUMN_ITEM_HAS, "false")
        values.put(COLUMN_ITEM_LIST_ID, listid)

        //insert data into contentvalues object into shoppinglistitem table
        db.insert(TABLE_SHOPPING_LIST_ITEM, null, values)

        //close db reference
        db.close()
    }

    /**
     * This method gets called when the viewlist activity is launched
     * @param listid shopping list ID
     * @return Cursor that contains all of the items associated with the specific shopping list id
     */
    fun getShoppingListItems(listid: Int): Cursor {
        //get reference to the shopper database
        val db = writableDatabase

        //define select statement and store it in a string
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_LIST_ID + " = " + listid

        //execute the select statement and return it as a cursor
        return db.rawQuery(query, null)
    }

    /**
     * This method gets called when the viewItem activity is launched
     * @param itemid item id
     * @return Cursor that contains all of the items associated with the specific shopping list id
     */
    fun getItems(itemid: Int): Cursor {
        //get reference to the shopper database
        val db = writableDatabase

        //define select statement and store it in a string
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_ID + " = " + itemid

        //execute the select statement and return it as a cursor
        return db.rawQuery(query, null)
    }

    /**
     * This method gets called when an item in the viewlist activity is clicked.
     *
     * @param itemId db id of the clicked item
     * @return 1 if item is unpurchased, otherwise 0
     */
    fun isItemUnpurchased(itemId: Int): Int {

        //get reference to shopper dp
        val db = writableDatabase

        //define select statement and store it in a string
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_HAS + " = \"false\" " +
                "AND " + COLUMN_ITEM_ID + " = " + itemId

        //execute select statement and store result in a cursor
        val cursor = db.rawQuery(query, null)

        //return a count of the number of rows in the cursor
        return cursor.count
    }

    /**
     * This method gets called when an item in the viewlist activity is clicked.
     * It sets the item's item_has value to true
     * @param itemId db id of clicked item
     */
    fun updateItem(itemId: Int) {
        //get reference to shopper dp
        val db = writableDatabase

        //define update statement and store it in a string
        val query = "UPDATE " + TABLE_SHOPPING_LIST_ITEM + " SET " +
                COLUMN_ITEM_HAS + " = \"true\" " + "WHERE " +
                COLUMN_ITEM_ID + " = " + itemId

        //execute the update statement
        db.execSQL(query)

        //close the db connection
        db.close()
    }

    /**
     * this method is going to get called when the viewitem activity is started
     * @param itemId database id of clicked item
     * @return Cursor that contains all of the data associated with the clicked shoppinglistiem
     */
    fun getShoppingListItem(itemId: Int): Cursor {
        //get reference to shopper dp
        val db = writableDatabase

        //define select statement
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_ID + " = " + itemId

        //execute select statement
        return db.rawQuery(query, null)
    }

    /**
     * This method gets called when the delete button in the action bar of the view item activity gets clicked
     * @param itemId database id of the shopping list item to be deleted
     */
    fun deleteShoppingListItem(itemId: Int) {
        val db = writableDatabase

        //define a delete statement and store it in a string
        val query = "DELETE FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_ID + " = " + itemId

        //execute statement
        db.execSQL(query)

        //close db ref
        db.close()
    }

    /**
     * This method gets called when the delete button in the action bar of the
     * view list activity gets clicked, it deletes a row in the shoppinglistitem
     * and shoppinglist tables
     * @param listId
     */
    fun deleteShoppingList(listId: Int) {
        val db = writableDatabase

        //define a delete statement and store it in a string
        val query1 = "DELETE FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_LIST_ID + " = " + listId

        //execute statement
        db.execSQL(query1)

        //define a delete statement and store it in a string
        val query2 = "DELETE FROM " + TABLE_SHOPPING_LIST +
                " WHERE " + COLUMN_LIST_ID + " = " + listId

        //execute statement
        db.execSQL(query2)

        //close db ref
        db.close()
    }

    /**
     * This method gets called when the view list activity is started
     * @param listID db id of shopping list
     * @return total cost of shopping list
     */
    fun getShoppingListTotalCost(listID: Int): String {
        val db = writableDatabase

        //declare and initialize the string returned by the method
        var cost = ""

        //declare a Double that will be used to compute the total cost
        var totalCost = 0.0

        //define select statement
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_LIST_ID + " = " + listID

        //execute select statement and store in a cursor
        val c = db.rawQuery(query, null)

        //loop through rows in cursor
        while (c.moveToNext()) {
            //add the cost of the current row into the total
            totalCost += c.getDouble(c.getColumnIndex("price")) *
                    c.getInt(c.getColumnIndex("quantity"))
        }

        //convert to string
        cost = totalCost.toString()

        //close db reference
        db.close()

        //return String
        return cost
    }

    /**
     * This method gets called when a shopping list item is clicked in the viewlist activity
     * @param listId database id of the shopping list on which the shoppinglistitem exists
     * @return number of unpurchased shopping list items on the specified shopping list
     */
    fun getUnpurchasedItems(listId: Int): Int {
        //get reference to database
        val db = writableDatabase

        //define select statement
        val query = "SELECT * FROM " + TABLE_SHOPPING_LIST_ITEM +
                " WHERE " + COLUMN_ITEM_HAS + " = \"false\" " +
                " AND " + COLUMN_ITEM_LIST_ID + " = " + listId

        //execute select statement
        val cursor = db.rawQuery(query, null)

        //return a count of the number of iutems in the cursor
        return cursor.count
    }

    companion object {
        //initialize constants for the DB name and version
        const val DATABASE_NAME = "shopper.db"
        const val DATABASE_VERSION = 2
        const val TABLE_SHOPPING_LIST = "shoppinglist"
        const val COLUMN_LIST_ID = "_id"
        const val COLUMN_LIST_NAME = "name"
        const val COLUMN_LIST_STORE = "store"
        const val COLUMN_LIST_DATE = "date"
        const val TABLE_SHOPPING_LIST_ITEM = "shoppinglistitem"
        const val COLUMN_ITEM_ID = "_id"
        const val COLUMN_ITEM_NAME = "name"
        const val COLUMN_ITEM_PRICE = "price"
        const val COLUMN_ITEM_QUANTITY = "quantity"
        const val COLUMN_ITEM_HAS = "item_has"
        const val COLUMN_ITEM_LIST_ID = "list_id"
    }
}