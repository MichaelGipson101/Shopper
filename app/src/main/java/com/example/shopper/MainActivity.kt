package com.example.shopper

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.example.shopper.DBHandler
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.shopper.R
import com.example.shopper.ShoppingLists
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import android.widget.CursorAdapter
import android.widget.ListView
import androidx.appcompat.widget.Toolbar
import com.example.shopper.ViewList
import com.example.shopper.MainActivity
import com.example.shopper.CreateList

class MainActivity : AppCompatActivity() {
    //declare intent
    //var intent: Intent? = null

    //declare a DBHandler
    var dbHandler: DBHandler? = null

    //declare a shoppinglists cursoradapter
    var shoppingListsCursorAdapter: CursorAdapter? = null

    //declare a listview
    var shopperListView: ListView? = null

    /**
     * this method intitializes the action bar and view of the activity
     * @param savedInstanceState
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //Initialize the view and action bar of the main activity
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //initialize dbhandler
        dbHandler = DBHandler(this, null)
        //initialize cursoradapter
        shoppingListsCursorAdapter = ShoppingLists(this, dbHandler!!.shoppingList, 0)
        //initialize listview
        shopperListView = findViewById<View>(R.id.shopperListView) as ListView

        //set shoppinglists cursoradapter on listview
        shopperListView!!.adapter = shoppingListsCursorAdapter

        //set onitemclicklistener
        shopperListView!!.onItemClickListener = OnItemClickListener { adapterView, view, position, id ->
            /**
             * this method gets called when an item in the listview is clicked
             * @param adapterView shopperlistview
             * @param view mainactivity view
             * @param position [position of the clicked item
             * @param id database of the clicked item
             */
            /**
             * this method gets called when an item in the listview is clicked
             * @param adapterView shopperlistview
             * @param view mainactivity view
             * @param position [position of the clicked item
             * @param id database of the clicked item
             */

            //initialize intent for the viewlist activity
            intent = Intent(this@MainActivity, ViewList::class.java)

            //put the database id in the intent
            intent!!.putExtra("_id", id)

            //start the viewlist activity
            startActivity(intent)
        }
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
        menuInflater.inflate(R.menu.menu_main, menu)
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
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * this method gets called when the add floating action button is clicked/
     * it starts the create list activity.
     * @param view
     */
    fun openCreateList(view: View?) {
        // initialize an intent for the main activity and start it
        intent = Intent(this, CreateList::class.java)
        startActivity(intent)
    }
}