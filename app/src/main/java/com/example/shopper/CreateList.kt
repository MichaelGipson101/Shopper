package com.example.shopper

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.shopper.CreateList
import java.text.SimpleDateFormat
import java.util.*

class CreateList : AppCompatActivity() {
    //declare intent
    //var intent: Intent? = null

    //declare edittexts
    var nameEditText: EditText? = null
    var storeEditText: EditText? = null
    var dateEditText: EditText? = null

    //declare calendar
    var calendar: Calendar? = null

    //declare DBHandler
    var dbHandler: DBHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_list)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        //initialize edittexts
        nameEditText = findViewById<View>(R.id.nameEditText) as EditText
        storeEditText = findViewById<View>(R.id.storeEditText) as EditText
        dateEditText = findViewById<View>(R.id.dateEditText) as EditText

        //initialize calendar
        calendar = Calendar.getInstance()

        //initialize DatePickerDialogue and register and OnDateSetListener to it
        val date = OnDateSetListener { datePicker, year, month, dayOfMonth ->
            /**
             * This method handles the OnDateSet event.
             * @param datePicker DatePickerDialogue view
             * @param year selected year
             * @param month selected month
             * @param dayOfMonth selected day
             */
            /**
             * This method handles the OnDateSet event.
             * @param datePicker DatePickerDialogue view
             * @param year selected year
             * @param month selected month
             * @param dayOfMonth selected day
             */

            //set the year, month, and dayOfMonth selected in the dialog into
            //the calendar
            calendar?.set(Calendar.YEAR, year)
            calendar?.set(Calendar.MONTH, month)
            calendar?.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            //call method that takes date in calendar and places it in dateEditText
            updateDueDate()
        }
        //register an OnClickListener to the dateEditText
        dateEditText!!.setOnClickListener(View.OnClickListener
        /**
         * This method handles the onClick event
         * @param view Createlist view
         */
        { //display DatePickerDialog with current date selected
            DatePickerDialog(this@CreateList,
                    date,
                    calendar!!.get(Calendar.YEAR),
                    calendar!!.get(Calendar.MONTH),
                    calendar!!.get(Calendar.DAY_OF_MONTH)).show()
        })
        //initialize dbhandler
        dbHandler = DBHandler(this, null)
    }

    /**
     * tHIS METHOD is called when a date is set in the DatePickerDialog
     */
    private fun updateDueDate() {
        //create a format for the date in the calendar
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        //apply format to date in calander and set formatted date in dateEditText
        dateEditText!!.setText(simpleDateFormat.format(calendar!!.time))
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
        menuInflater.inflate(R.menu.menu_create_list, menu)
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
     * This method gets called when the add button in the action bar gets clicked
     * @param menuItem add list menu item
     */
    fun createList(menuItem: MenuItem?) {
        //get data input in editTexts and store it in Strings
        val name = nameEditText!!.text.toString()
        val store = storeEditText!!.text.toString()
        val date = dateEditText!!.text.toString()

        //trim strings and see if they are equal to empty string
        if ((name.trim { it <= ' ' } == "") || (store.trim { it <= ' ' } == "") || (date.trim { it <= ' ' } == "")) {
            //display toast
            Toast.makeText(this, "Please enter a name, store, and date!", Toast.LENGTH_LONG).show()
        } else {
            //add list to DB
            dbHandler!!.addShoppingList(name, store, date)
            //display Toast
            Toast.makeText(this, "Shopping list created!", Toast.LENGTH_LONG).show()
        }
    }
}