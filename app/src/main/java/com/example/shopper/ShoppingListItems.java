package com.example.shopper;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ShoppingListItems extends CursorAdapter {
    /**
     * Initialize a shoppinglistitems cursoradapter
     * @param context reference to the activity that initializes the shoppinglistitems cursoradapter
     * @param c reference to the cursor that contains the data selected from the shoppinglistitems table
     * @param flags determines special behavior of the cursoradapter. we don't want any special
     *              behavior o we will always pass 0.
     */
    public ShoppingListItems(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    /**
     * Make a new view to hold the data in the cursor
     * @param context reference to the activity that initializes the shoppinglistitems cursoradapter
     * @param cursor reference to the cursor that contains the data selected from the shoppinglistitems table
     * @param parent reference to itemlistview that will contain the new view created by this method.
     * @return reference to the new view
     */
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.li_item_list, parent, false);
    }

    /**
     * Binds new view to data in cursor
     * @param view refrerence to new view
     * @param context reference to the activity that initializes the shoppinglists cursoradapter
     * @param cursor reference to the cursor that contains the data selected from the shoppinglist table
     */
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ((TextView) view.findViewById(R.id.nameTextView)).
                setText(cursor.getString(cursor.getColumnIndex("name")));
        ((TextView) view.findViewById(R.id.priceTextView)).
                setText(cursor.getString(cursor.getColumnIndex("price")));
        ((TextView) view.findViewById(R.id.quantityTextView)).
                setText(cursor.getString(cursor.getColumnIndex("quantity")));
        ((TextView) view.findViewById(R.id.hasTextView)).
                setText("Item Purchased? " + cursor.getString(cursor.getColumnIndex("item_has")));
    }
}
