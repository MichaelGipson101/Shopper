package com.example.shopper;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ViewItem extends AppCompatActivity {
    //delcare a bundle and a long used to get and store the data sent from
    //the viewlist activity
    Bundle bundle;
    long id;
    long listId;

    //declare a dbhandler
    DBHandler dbHandler;

    //declare an intent
    Intent intent;

    //declare edittexts
    EditText nameEditText;
    EditText priceEditText;
    EditText quantityEditText;

    //declare strings to store the clicked shopping list item's name, price, quantity
    String name;
    String price;
    String quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize the bundle
        bundle = this.getIntent().getExtras();

        //use bundle to get id and store it in id field
        id = bundle.getLong("_id");

        //use bundle to get id and store it in id field
        listId = bundle.getLong("_list_id");

        //initialize dbhandler
        dbHandler = new DBHandler(this, null);

        //initialize edittexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);
        quantityEditText = (EditText) findViewById(R.id.quantityEditText);

        //disable edittexts
        nameEditText.setEnabled(false);
        priceEditText.setEnabled(false);
        quantityEditText.setEnabled(false);

        //call the dbhandler method getshoppinglistiem
        Cursor cursor = dbHandler.getShoppingListItem((int) id);

        cursor.moveToFirst();

        //get the name, price, and quantity in the cursor and store it in strings
        name = cursor.getString(cursor.getColumnIndex( "name"));
        price = cursor.getString(cursor.getColumnIndex( "price"));
        quantity = cursor.getString(cursor.getColumnIndex( "quantity"));

        //set the name price and quantity values in the edittexts
        nameEditText.setText(name);
        priceEditText.setText(price);
        quantityEditText.setText(quantity);
    }

    /**
     * this method further initializes the action bar of the activity.
     * it gets the code (xml) in the menu resource file and incorporates it into the
     * action bar.
     * @param menu menu resource file for the activity
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_item, menu);
        return true;
    }

    /**
     * This method gets called when a menu item in the overflow menu is
     * selected and it controls what happens when the menu item is selected
     * @param item selected menu item in the overflow menu
     * @return true if menu item is selected, else false
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //get the id of the menu item selected
        switch (item.getItemId()) {
            case R.id.action_home :
                // initialize an intent for the main activity and start it
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_create_list :
                // initialize an intent for the main activity and start it
                intent = new Intent(this, CreateList.class);
                startActivity(intent);
                return true;
            case R.id.action_add_item :
                // initialize an intent for the add item activity and start it
                intent = new Intent(this, AddItem.class);
                //put the database id in the intent
                intent.putExtra("_id", listId);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    /**
     * This method gets called when the delete button in the action bar of the view item activity gets clicked
     * @param menuItem database id of the shopping list item to be deleted
     */
    public void deleteItem(MenuItem menuItem) {
        //delete shoppinglistitem from db
        dbHandler.deleteShoppingListItem((int) id);

        //display item deleted toast
        Toast.makeText(this, "Item Deleted!", Toast.LENGTH_LONG).show();
    }
}