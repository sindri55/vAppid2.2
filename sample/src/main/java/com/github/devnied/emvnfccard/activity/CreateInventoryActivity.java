package com.github.devnied.emvnfccard.activity;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import com.github.devnied.emvnfccard.R;
import com.github.devnied.emvnfccard.adapter.ListViewCreateInventoryAdapter;
import com.github.devnied.emvnfccard.adapter.ListViewInventoryAdapter;
import com.github.devnied.emvnfccard.adapter.ListViewRemovableAdapter;

import java.util.ArrayList;

/**
 * Created by Ragnar on 13.5.2015.
 */
public class CreateInventoryActivity extends ListActivity {
    ArrayList<String> listItems;
    ListViewCreateInventoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        listItems = new ArrayList<String>();
        //prices = new ArrayList<Integer>();
        //quantities = new ArrayList<Integer>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_inventory);

        //Setting up the adapter for the listview.. (list)..
        adapter = new ListViewCreateInventoryAdapter(listItems, this);
        setListAdapter(adapter);
    }

    public void addToList(View view) {
        EditText name = (EditText) findViewById(R.id.edit_name);
        EditText price = (EditText) findViewById(R.id.edit_price);
        listItems.add(name.getText().toString() + " " + price.getText().toString());
        adapter.notifyDataSetChanged();
    }
}
