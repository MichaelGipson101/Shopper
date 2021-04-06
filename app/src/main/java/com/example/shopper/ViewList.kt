package com.example.shopper

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ViewList : AppCompatActivity() {
    //delcare a bundle and a long used to get and store the data sent from
    //the main activity
    var bundle: Bundle? = null
    var id: Long = 0

    //declare dbhandler
    var dbHandler: DBHandler? = null

    //declare intent
    //var intent: Intent? = null

    //declare a shoppinglistitems cursor adapter
    var shoppingListItemsAdapter: ShoppingListItems? = null

    //declare listview
    var itemListView: ListView? = null

    //declare notification manager used to show the notification
    var notificationManagerCompat: NotificationManagerCompat? = null

    //declare string that will store the shopping list name
    var shoppingListName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //initialize the bundle
        bundle = getIntent().extras

        //use bundle to get id and store it in id field
        id = bundle!!.getLong("_id")

        //initialize dbhandler
        dbHandler = DBHandler(this, null)

        //call getshoppinglistname method and store its return in the string
        shoppingListName = dbHandler!!.getShoppingListName(id.toInt())

        //set the title of the viewlist activity to the shoppinglist name
        this.title = shoppingListName
        itemListView = findViewById<View>(R.id.itemListView) as ListView

        //initialize shoppinglistitems cursor adapter
        shoppingListItemsAdapter = ShoppingListItems(this, dbHandler!!.getShoppingListItems(id.toInt()), 0)
        itemListView!!.adapter = shoppingListItemsAdapter

        //register an onitemclicklistener to the listview
        itemListView!!.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            /**
             * This method gets called when an item in the listview is clicked
             * @param parent item list view
             * @param view viewlist activity view
             * @param position position of clicked item
             * @param id database id of clicked item
             */
            /**
             * This method gets called when an item in the listview is clicked
             * @param parent item list view
             * @param view viewlist activity view
             * @param position position of clicked item
             * @param id database id of clicked item
             */

            //call method that updates the clicked items item_has to true
            //if it's false
            updateItem(id)

            //initialize intent for viewitem activity
            intent = Intent(this@ViewList, ViewItem::class.java)

            //put the database id in the intent
            intent!!.putExtra("_id", id)

            //put the database id in the intent
            intent!!.putExtra("_list_id", this@ViewList.id)


            //start the viewitem activity
            startActivity(intent)
        }

        //set the subtitle to the totalcost of the shoppinglist
        toolbar.subtitle = "Total Cost: $" + dbHandler!!.getShoppingListTotalCost(id.toInt())

        //initialize the notification manager
        notificationManagerCompat = NotificationManagerCompat.from(this)
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
        menuInflater.inflate(R.menu.menu_view_list, menu)
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
                intent!!.putExtra("_id", id)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * This method gets called when the add floating action button is clicked
     * it starts the additem activity
     * @param view Viewlist View
     */
    fun openAddItem(view: View?) {
        // initialize an intent for the add item activity and start it
        intent = Intent(this, AddItem::class.java)
        //put the database id in the intent
        intent!!.putExtra("_id", id)
        startActivity(intent)
    }

    /**
     * this method updates the clicked items item_has to true
     * if it's false
     * @param id database id of the clicked item
     */
    fun updateItem(id: Long) {
        //checking if clicked item is unpurchased
        if (dbHandler!!.isItemUnpurchased(id.toInt()) == 1) {
            //make clicked item purchased
            dbHandler!!.updateItem(id.toInt())
            //refresh listview with updated data
            shoppingListItemsAdapter!!.swapCursor(dbHandler!!.getShoppingListItems(this.id.toInt()))
            shoppingListItemsAdapter!!.notifyDataSetChanged()

            //display toast
            Toast.makeText(this, "Item purchased!", Toast.LENGTH_LONG).show()
        }
        if (dbHandler!!.getUnpurchasedItems(this.id.toInt()) == 0) {
            //initialize notification
            val notification = NotificationCompat.Builder(this,
                    App.CHANNEL_SHOPPER_ID)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Shopper")
                    .setContentText("$shoppingListName completed!").build()

            //show notification
            notificationManagerCompat!!.notify(1, notification)
        }
    }

    fun deleteList(menuItem: MenuItem?) {
        //delete shoppinglist from db
        dbHandler!!.deleteShoppingList(id.toInt())

        //display list deleted toast
        Toast.makeText(this, "List Deleted!", Toast.LENGTH_LONG).show()
    }
}