package com.example.shopper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ViewItem : AppCompatActivity() {
    //delcare a bundle and a long used to get and store the data sent from
    //the viewlist activity
    var bundle: Bundle? = null
    var id: Long = 0
    var listId: Long = 0

    //declare a dbhandler
    var dbHandler: DBHandler? = null

    //declare an intent
    //var intent: Intent? = null

    //declare edittexts
    var nameEditText: EditText? = null
    var priceEditText: EditText? = null
    var quantityEditText: EditText? = null

    //declare strings to store the clicked shopping list item's name, price, quantity
    var name: String? = null
    var price: String? = null
    var quantity: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_item)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //initialize the bundle
        bundle = getIntent().extras

        //use bundle to get id and store it in id field
        id = bundle!!.getLong("_id")

        //use bundle to get id and store it in id field
        listId = bundle!!.getLong("_list_id")

        //initialize dbhandler
        dbHandler = DBHandler(this, null)

        //initialize edittexts
        nameEditText = findViewById<View>(R.id.nameEditText) as EditText
        priceEditText = findViewById<View>(R.id.priceEditText) as EditText
        quantityEditText = findViewById<View>(R.id.quantityEditText) as EditText

        //disable edittexts
        nameEditText!!.isEnabled = false
        priceEditText!!.isEnabled = false
        quantityEditText!!.isEnabled = false

        //call the dbhandler method getshoppinglistiem
        val cursor = dbHandler!!.getShoppingListItem(id.toInt())
        cursor.moveToFirst()

        //get the name, price, and quantity in the cursor and store it in strings
        name = cursor.getString(cursor.getColumnIndex("name"))
        price = cursor.getString(cursor.getColumnIndex("price"))
        quantity = cursor.getString(cursor.getColumnIndex("quantity"))

        //set the name price and quantity values in the edittexts
        nameEditText!!.setText(name)
        priceEditText!!.setText(price)
        quantityEditText!!.setText(quantity)
    }

    /**
     * this method further initializes the action bar of the activity.
     * it gets the code (xml) in the menu resource file and incorporates it into the
     * action bar.
     * @param menu menu resource file for the activity
     * @return true
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_view_item, menu)
        return true
    }

    /**
     * This method gets called when a menu item in the overflow menu is
     * selected and it controls what happens when the menu item is selected
     * @param item selected menu item in the overflow menu
     * @return true if menu item is selected, else false
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //get the id of the menu item selected
        return when (item.itemId) {
            R.id.action_home -> {
                // initialize an intent for the main activity and start it
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.action_create_list -> {
                // initialize an intent for the main activity and start it
                intent = Intent(this, CreateList::class.java)
                startActivity(intent)
                true
            }
            R.id.action_add_item -> {
                // initialize an intent for the add item activity and start it
                intent = Intent(this, AddItem::class.java)
                //put the database id in the intent
                intent!!.putExtra("_id", listId)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method gets called when the delete button in the action bar of the view item activity gets clicked
     * @param menuItem database id of the shopping list item to be deleted
     */
    fun deleteItem(menuItem: MenuItem?) {
        //delete shoppinglistitem from db
        dbHandler!!.deleteShoppingListItem(id.toInt())

        //display item deleted toast
        Toast.makeText(this, "Item Deleted!", Toast.LENGTH_LONG).show()
    }
}