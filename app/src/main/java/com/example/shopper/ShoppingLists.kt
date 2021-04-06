package com.example.shopper

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.TextView

/**
 * The ShoppingLists class will map the data selected from the shoppinglist table
 * in the cursor to the li_shopping_list resource file
 */
class ShoppingLists
/**
 * Initialize a shoppinglists cursoradapter
 * @param context reference to the activity that initializes the shoppinglists cursoradapter
 * @param c reference to the cursor that contains the data selected from the shoppinglist table
 * @param flags determines special behavior of the cursoradapter. we don't want any special
 * behavior o we will always pass 0.
 */
(context: Context?, c: Cursor?, flags: Int) : CursorAdapter(context, c, flags) {
    /**
     * Make a new view to hold the data in the cursor
     * @param context reference to the activity that initializes the shoppinglists cursoradapter
     * @param cursor reference to the cursor that contains the data selected from the shoppinglist table
     * @param parent reference to shopperlistview that will contain the new view created by this method.
     * @return reference to the new view
     */
    override fun newView(context: Context, cursor: Cursor, parent: ViewGroup): View {
        return LayoutInflater.from(context).inflate(R.layout.li_shopping_list, parent, false)
    }

    /**
     * Binds new view to data in cursor
     * @param view refrerence to new view
     * @param context reference to the activity that initializes the shoppinglists cursoradapter
     * @param cursor reference to the cursor that contains the data selected from the shoppinglist table
     */
    override fun bindView(view: View, context: Context, cursor: Cursor) {
        (view.findViewById<View>(R.id.nameTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("name"))
        (view.findViewById<View>(R.id.storeTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("store"))
        (view.findViewById<View>(R.id.dateTextView) as TextView).text = cursor.getString(cursor.getColumnIndex("date"))
    }
}