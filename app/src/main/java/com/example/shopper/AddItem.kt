package com.example.shopper;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddItem extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    //delcare a bundle and a long used to get and store the data sent from
    //the viewlist activity
    Bundle bundle;
    long id;

    //declare dbhandler
    DBHandler dbHandler;

    //declare intent
    Intent intent;

    //declare edittexts
    EditText nameEditText;
    EditText priceEditText;

    //declare spinner
    Spinner quantitySpinner;

    //declare a string to store quantity
    String quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //initialize the bundle
        bundle = this.getIntent().getExtras();

        //use bundle to get id and store it in id field
        id = bundle.getLong("_id");

        //initialize dbhandler

        dbHandler = new DBHandler(this, null);

        //initialize edittexts
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        priceEditText = (EditText) findViewById(R.id.priceEditText);

        //initialize spinner
        quantitySpinner = (Spinner) findViewById(R.id.quantitySpinner);

        //initialize arrayadapter with values in quantities string-array
        //and stylize it with style defined by simple spinner item
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quantities, android.R.layout.simple_spinner_item);

        //further stylize the arrayadapter
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        //set the arrayadapter on the spinner
        quantitySpinner.setAdapter(adapter);

        //register an onitemselectedlistener to spinner
        quantitySpinner.setOnItemSelectedListener(this);

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
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
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
            case R.id.action_view_list :
                // initialize an intent for the add item activity and start it
                intent = new Intent(this, ViewList.class);
                //put the database id in the intent
                intent.putExtra("_id", id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addItem(MenuItem item) {
        //get data input into edittexts and store it in strings
        String name = nameEditText.getText().toString();
        String price = priceEditText.getText().toString();

        //trim strings and see if they're equal to empty strings
        if (name.trim().equals("") || price.trim().equals("") || quantity.trim().equals("")) {
            //display toast\
            Toast.makeText(this, "Please enter a name, price, and quantity",
                    Toast.LENGTH_LONG).show();
        } else {
            //else add item into db
            dbHandler.addItemToList(name, Double.parseDouble(price), Integer.parseInt(quantity), (int) id);
            Toast.makeText(this, "Item added!",
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method gets called when an item in the spinner is selected
     * @param parent Spinner adapterview
     * @param view add item view
     * @param position position of item that was selected in the spinner
     * @param id database id of the selected item in the spinner
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        quantity = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}