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
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class ViewList extends AppCompatActivity {


    //delcare a bundle and a long used to get and store the data sent from
    //the main activity
    Bundle bundle;
    long id;

    //declare dbhandler
    DBHandler dbHandler;

    //declare intent
    Intent intent;

    //declare a shoppinglistitems cursor adapter
    ShoppingListItems shoppingListItemsAdapter;

    //declare listview
    ListView itemListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //initialize the bundle
        bundle = this.getIntent().getExtras();

        //use bundle to get id and store it in id field
        id = bundle.getLong("_id");

        //initialize dbhandler

        dbHandler = new DBHandler(this, null);

        //call getshoppinglistname method and store its return in the string
        String shoppingListName = dbHandler.getShoppingListName((int) id);

        //set the title of the viewlist activity to the shoppinglist name
        this.setTitle(shoppingListName);

        itemListView = (ListView) findViewById(R.id.itemListView);

        //initialize shoppinglistitems cursor adapter
        shoppingListItemsAdapter = new ShoppingListItems(this, dbHandler.getShoppingListItems((int) id), 0);

        itemListView.setAdapter(shoppingListItemsAdapter);

        //register an onitemclicklistener to the listview
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /**
             * This method gets called when an item in the listview is clicked
             * @param parent item list view
             * @param view viewlist activity view
             * @param position position of clicked item
             * @param id database id of clicked item
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //call method that updates the clicked items item_has to true
                //if it's false
                updateItem(id);

                //initialize intent for viewitem activity
                intent = new Intent(ViewList.this, ViewItem.class);

                //put the database id in the intent
                intent.putExtra("_id", id);

                //put the database id in the intent
                intent.putExtra("_list_id", ViewList.this.id);


                //start the viewitem activity
                startActivity(intent);
            }
        });

        //set the subtitle to the totalcost of the shoppinglist
        toolbar.setSubtitle("Total Cost: $" + dbHandler.getShoppingListTotalCost((int) id));

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
        getMenuInflater().inflate(R.menu.menu_view_list, menu);
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
                intent.putExtra("_id", id);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method gets called when the add floating action button is clicked
     * it starts the additem activity
     * @param view Viewlist View
     */
    public void openAddItem(View view) {
        // initialize an intent for the add item activity and start it
        intent = new Intent(this, AddItem.class);
        //put the database id in the intent
        intent.putExtra("_id", id);
        startActivity(intent);
    }

    /**
     * this method updates the clicked items item_has to true
     * if it's false
     * @param id database id of the clicked item
     */
    public void updateItem(long id) {
        //checking if clicked item is unpurchased
        if (dbHandler.isItemUnpurchased((int) id) == 1) {
            //make clicked item purchased
            dbHandler.updateItem((int) id);
            //refresh listview with updated data
            shoppingListItemsAdapter.swapCursor(dbHandler.getShoppingListItems((int) this.id));
            shoppingListItemsAdapter.notifyDataSetChanged();

            //display toast
            Toast.makeText(this, "Item purchased!", Toast.LENGTH_LONG).show();
        }
    }
    public void deleteList(MenuItem menuItem) {
        //delete shoppinglist from db
        dbHandler.deleteShoppingList((int) id);

        //display list deleted toast
        Toast.makeText(this, "List Deleted!", Toast.LENGTH_LONG).show();
    }
}