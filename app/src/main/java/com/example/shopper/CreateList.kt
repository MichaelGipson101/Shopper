package com.example.shopper;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class CreateList extends AppCompatActivity {
    //declare intent
    Intent intent;

    //declare edittexts
    EditText nameEditText;
    EditText storeEditText;
    EditText dateEditText;

    //declare calendar
    Calendar calendar;

    //declare DBHandler
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize edittexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        storeEditText = (EditText) findViewById(R.id.storeEditText);
        dateEditText = (EditText) findViewById(R.id.dateEditText);

        //initialize calendar
        calendar = Calendar.getInstance();

        //initialize DatePickerDialogue and register and OnDateSetListener to it
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            /**
             * This method handles the OnDateSet event.
             * @param datePicker DatePickerDialogue view
             * @param year selected year
             * @param month selected month
             * @param dayOfMonth selected day
             */
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                //set the year, month, and dayOfMonth selected in the dialog into
                //the calendar
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                //call method that takes date in calendar and places it in dateEditText
                updateDueDate();
            }
        };
            //register an OnClickListener to the dateEditText
        dateEditText.setOnClickListener(new View.OnClickListener() {
            /**
             * This method handles the onClick event
             * @param view Createlist view
             */
            @Override
            public void onClick(View view) {
                //display DatePickerDialog with current date selected
                new DatePickerDialog(CreateList.this,
                        date,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        //initialize dbhandler
        dbHandler = new DBHandler(this, null);
    }

    /**
     * tHIS METHOD is called when a date is set in the DatePickerDialog
     */
    private void updateDueDate() {
        //create a format for the date in the calendar
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        //apply format to date in calander and set formatted date in dateEditText
        dateEditText.setText(simpleDateFormat.format(calendar.getTime()));
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
        getMenuInflater().inflate(R.menu.menu_create_list, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the add button in the action bar gets clicked
     * @param menuItem add list menu item
     */
    public void createList(MenuItem menuItem) {
    //get data input in editTexts and store it in Strings
        String name = nameEditText.getText().toString();
        String store = storeEditText.getText().toString();
        String date = dateEditText.getText().toString();

        //trim strings and see if they are equal to empty string
        if (name.trim().equals("") || store.trim().equals("") || date.trim().equals("")){
            //display toast
            Toast.makeText(this, "Please enter a name, store, and date!", Toast.LENGTH_LONG).show();
        } else {
            //add list to DB
            dbHandler.addShoppingList(name, store, date);
            //display Toast
            Toast.makeText(this, "Shopping list created!", Toast.LENGTH_LONG).show();

        }
    }
}