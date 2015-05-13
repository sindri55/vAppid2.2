package com.github.devnied.emvnfccard.activity;

import android.app.ListActivity;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by Ragnar on 13.5.2015.
 */
public class CreateInventoryActivity extends ListActivity {
    ArrayList<String> listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
     /*   listItems = new ArrayList<String>();
        //prices = new ArrayList<Integer>();
        //quantities = new ArrayList<Integer>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_inventory);

        //Setting up the adapter for the cart itself
        adapter = new ListViewRemovableAdapter(listItems, this);
        setListAdapter(adapter);

        //Set up the inventory adapter..
        inventoryItems = new ArrayList<String>();
        inventoryItems.add("Bindi 3000");
        inventoryItems.add("Slaufa 3500");
        inventoryItems.add("Jakki 7500");
        inventoryAdapter = new ListViewInventoryAdapter(inventoryItems, this);

        ListView lwInventory = (ListView) findViewById(R.id.inventory_list);
        lwInventory.setAdapter(inventoryAdapter);

        //Tengja filter
        EditText filter = (EditText) findViewById(R.id.edit_filter);
        filter.addTextChangedListener(searchTextWatcher);
        /*
        EditText qty = (EditText) findViewById(R.id.quantity);
        qty.setText("1"); */
    }
}
