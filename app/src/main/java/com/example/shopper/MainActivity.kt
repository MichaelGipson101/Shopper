package com.example.shopper;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //declare intent
    Intent intent;

    //declare a DBHandler
    DBHandler dbHandler;

    //declare a shoppinglists cursoradapter
    CursorAdapter shoppingListsCursorAdapter;

    //declare a listview
    ListView shopperListView;
    /**
     * this method intitializes the action bar and view of the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Initialize the view and action bar of the main activity
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize dbhandler
        dbHandler = new DBHandler(this, null);
        //initialize cursoradapter
        shoppingListsCursorAdapter = new ShoppingLists(this, dbHandler.getShoppingList(), 0);
        //initialize listview
        shopperListView = (ListView) findViewById(R.id.shopperListView);

        //set shoppinglists cursoradapter on listview
        shopperListView.setAdapter(shoppingListsCursorAdapter);

        //set onitemclicklistener
        shopperListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * this method gets called when an item in the listview is clicked
             * @param adapterView shopperlistview
             * @param view mainactivity view
             * @param position [position of the clicked item
             * @param id database of the clicked item
             */
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //initialize intent for the viewlist activity
                intent = new Intent(MainActivity.this, ViewList.class);

                //put the database id in the intent
                intent.putExtra("_id", id);

                //start the viewlist activity
                startActivity(intent);
            }
        });
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
     * this method gets called when the add floating action button is clicked/
     * it starts the create list activity.
     * @param view
     */
    public void openCreateList(View view) {
        // initialize an intent for the main activity and start it
        intent = new Intent(this, CreateList.class);
        startActivity(intent);
    }
}