package com.github.devnied.emvnfccard.activity;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.github.devnied.emvnfccard.R;
import com.github.devnied.emvnfccard.adapter.ListViewCreateInventoryAdapter;
import com.github.devnied.emvnfccard.adapter.ListViewInventoryAdapter;
import com.github.devnied.emvnfccard.adapter.ListViewRemovableAdapter;

import java.util.ArrayList;

/**
 * Created by Ragnar on 13.5.2015.
 */
public class CreateInventoryActivity extends Activity {
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
        ListView lwCreateInventoryList = (ListView) findViewById(R.id.create_inventory_list);
        lwCreateInventoryList.setAdapter(adapter);
        //setListAdapter(adapter);
    }

    public void addToList(View view) {
        EditText name = (EditText) findViewById(R.id.edit_name);
        EditText price = (EditText) findViewById(R.id.edit_price);
        listItems.add(name.getText().toString() + " " + price.getText().toString());
        adapter.notifyDataSetChanged();
        price.setText("");
        name.setText("");
    }

    public void saveList(View view) {
        /*
        Intent intent = new Intent(this, SimplePayActivity.class);
        startActivity(intent); */
        Context context = getApplicationContext();
        CharSequence text = "Listi vistaður.";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        final Context outerContext = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i=new Intent(outerContext, SimplePayActivity.class);
                startActivity(i);
            }
        }, 1500);
    }
}
