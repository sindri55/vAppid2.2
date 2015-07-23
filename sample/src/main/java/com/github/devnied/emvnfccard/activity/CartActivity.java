package com.github.devnied.emvnfccard.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.devnied.emvnfccard.R;
import com.github.devnied.emvnfccard.adapter.ListViewInventoryAdapter;
import com.github.devnied.emvnfccard.adapter.ListViewRemovableAdapter;
import com.github.devnied.emvnfccard.adapter.MenuDrawerAdapter;
import com.github.devnied.emvnfccard.fragment.AboutFragment;
import com.github.devnied.emvnfccard.fragment.BillingFragment;
import com.github.devnied.emvnfccard.fragment.CartFragment;
import com.github.devnied.emvnfccard.fragment.CreateInventoryFragment;
import com.github.devnied.emvnfccard.fragment.FundraiserFragment;
import com.github.devnied.emvnfccard.fragment.IRefreshable;
import com.github.devnied.emvnfccard.fragment.LogOutFragment;
import com.github.devnied.emvnfccard.fragment.SimplePayFragment;
import com.github.devnied.emvnfccard.utils.ConstantUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class CartActivity extends FragmentActivity implements AdapterView.OnItemClickListener{




    GridView gridView;




    ArrayList<Integer> prices;
    ArrayList<Integer> quantities; //1 to 1 correspondance with prices
    ArrayList<String> listItems;
    ArrayList<String> inventoryItems; //items for sale
    Integer currentTotal;
    /**
     * last selected Menu
     */
    private int mLastSelectedMenu = -1;
    /**
     * Reference for refreshable content
     */
    private WeakReference<IRefreshable> mRefreshableContent;
    /**
     * Action bar drawer toggle
     */
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    /**
     * Drawer layout
     */
    private DrawerLayout mDrawerLayout;

    /**
     * Menu adapter
     */
    private MenuDrawerAdapter mMenuAdapter;


    /**
     * ListView drawer
     */
    private ListView mDrawerListView;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    //ArrayAdapter<Integer> adapter;
    ListViewRemovableAdapter adapter;
    ListViewInventoryAdapter inventoryAdapter;
    public final static String EXTRA_CART_CONTENTS = "com.rbrjas.vappid.CART_CONTENTS";
    public final static String EXTRA_CART_QUANTITIES = "com.rbrjas.vappid.CART_QUANTITIES";
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Set up the inventory adapter..
        inventoryItems = new ArrayList<String>();
        inventoryItems.add("Mars     200kr");
        inventoryItems.add("Snickers 230kr");
        inventoryItems.add("7 Up     250kr");
        inventoryItems.add("Coca Cola 250kr");
        inventoryItems.add("Pepsi 250kr");
        inventoryItems.add("Montein Dew 250kr");
        inventoryItems.add("Cristal    Red      270kr");
        inventoryItems.add("Cristal    Blue      270kr");
        inventoryItems.add("Juice Orage 300kr");
        inventoryItems.add("Juice Apple 300kr");


        inventoryAdapter = new ListViewInventoryAdapter(inventoryItems, this);
        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(inventoryAdapter);





        /* Sidebar menu */
        // get ListView defined in activity_main.xml
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mMenuAdapter = new MenuDrawerAdapter(CartActivity.this);
        mDrawerListView.setAdapter(mMenuAdapter);
        mDrawerListView.setOnItemClickListener(this);

        // 2. App Icon
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // 2.1 create ActionBarDrawerToggle
        mActionBarDrawerToggle = new ActionBarDrawerToggle(/* */
                this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
                R.string.navigation_menu_open, /* "open drawer" description */
                R.string.navigation_menu_close /* "close drawer" description */
        );

        // 2.2 Set actionBarDrawerToggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayShowHomeEnabled(true);
        getActionBar().setDisplayUseLogoEnabled(false);
        getActionBar().setDisplayShowCustomEnabled(true);

        currentTotal = 0;
        listItems = new ArrayList<String>();
        prices = new ArrayList<Integer>();
        quantities = new ArrayList<Integer>();


        //Setting up the adapter for the cart itself
        /*
        adapter = new ListViewRemovableAdapter(listItems, this, prices, quantities);
        setListAdapter(adapter); */
        adapter = new ListViewRemovableAdapter(listItems, this, prices, quantities);
       /* ListView lwCartItems = (ListView) findViewById(R.id.cart_list);
        lwCartItems.setBackgroundColor(getResources().getColor(R.color.valitor_orange));
        lwCartItems.setAdapter(adapter);



        ListView lwInventory = (ListView) findViewById(R.id.inventory_list);
        lwCartItems.setBackgroundColor(getResources().getColor(R.color.valitor_lightorange));
        lwInventory.setAdapter(inventoryAdapter);
*/
        //Tengja filter
        EditText filter = (EditText) findViewById(R.id.edit_filter);
        filter.addTextChangedListener(searchTextWatcher);
        /*
        EditText qty = (EditText) findViewById(R.id.quantity);
        qty.setText("1"); */



    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this, SimplePayActivity.class);
        startActivity(intent);
        CartActivity.this.finish();
    }


    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (mLastSelectedMenu == ConstantUtils.ABOUT) {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content_frame);
            if (fragment != null) {
                BillingFragment billing = (BillingFragment) fragment.getChildFragmentManager().findFragmentById(R.id.about_inapp_content);
                if (billing != null) {
                    billing.onActivityResult(requestCode, resultCode, data);
                }
            }
        }
    }



    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        if (mLastSelectedMenu != position) {
            Fragment fragment = null;
            switch (position) {
                /*
                case ConstantUtils.CARDS_DETAILS:
                    fragment = new ViewPagerFragment();
                    refreshContent();
                    break; */
                case ConstantUtils.SIMPLEPAY:
                    fragment = new SimplePayFragment();
                    break;
                case ConstantUtils.CART:
                    fragment = new CartFragment();
                    break;
                case ConstantUtils.INVERTORY:
                    fragment = new CreateInventoryFragment();
                    break;
                case ConstantUtils.FUNDRAISER:
                    fragment = new FundraiserFragment();
                    break;
                case ConstantUtils.ABOUT:
                    fragment = new AboutFragment();
                    break;
                case ConstantUtils.LOGOUT:
                    fragment = new LogOutFragment();
                    break;
                default:
                    break;
            }
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
            mLastSelectedMenu = position;
        }
        mDrawerLayout.closeDrawer(mDrawerListView);
    }

    private void refreshContent() {
        if (mRefreshableContent != null && mRefreshableContent.get() != null) {
            mRefreshableContent.get().update();
        }
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            Crouton.cancelAllCroutons();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goPay(View view) {
        /*
        Intent intent = new Intent(this, ScanActivity.class);
            intent.putExtra("price", price);
            startActivity(intent);
         */
        String priceText = ((TextView) findViewById(R.id.text_total)).getText().toString();
        String[] prices = priceText.split(" ");
        String price = prices[prices.length - 1];
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra("price", price);
        startActivity(intent);
        CartActivity.this.finish();
    }

    public void quit(View view){
        Intent i = new Intent(this, SimplePayActivity.class);
        startActivity(i);
        CartActivity.this.finish();

    }

    public void addToCart(String item) {
        Log.d("hi2u", item);
        Integer cartIndex = containsString(item);
        Integer priceBeingAdded = 0;
        if (cartIndex == -1) {
            listItems.add(item + "x1");
            String[] parts = item.split(" ");
            priceBeingAdded = Integer.parseInt(parts[parts.length - 1]);
            prices.add(priceBeingAdded);
            quantities.add(1);
        }

        else {
            /* Thetta stak er til i index cartIndex */
            Integer quantity = quantities.get(cartIndex) + 1;
            quantities.set(cartIndex, quantity);
            String oldListItem = listItems.get(cartIndex);
            Integer quantityStartsAt = oldListItem.lastIndexOf("x");
            String newListItem = oldListItem.substring(0, (quantityStartsAt + 1)) + quantity.toString();
            listItems.set(cartIndex, newListItem);
            priceBeingAdded = prices.get(cartIndex);
        }

        TextView total = (TextView) findViewById(R.id.text_total);
        currentTotal += priceBeingAdded;
        total.setText("Total amount: " + currentTotal.toString());

        adapter.notifyDataSetChanged();
    }

    public void recalculateTotal() {
        currentTotal = 0;
        for (int i = 0; i < prices.size(); i++) {
            currentTotal += prices.get(i) * quantities.get(i);
        }

        TextView total = (TextView) findViewById(R.id.text_total);
        total.setText("Total amount: " + currentTotal.toString());
    }

    /* Ef listItems er med thetta item.. skilum index.. annars -1.. mix.. */
    private int containsString(String str) {
        for (int i = 0; i < listItems.size(); i++) {
            if (listItems.get(i).startsWith(str)) {
                return i;
            }
        }

        return -1;
    }

    private TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // ignore
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // ignore
        }

        @Override
        public void afterTextChanged(Editable s) {
            //Log.d(Constants.TAG, "*** Search value changed: " + s.toString());
            inventoryAdapter.getFilter().filter(s.toString());
        }
    };
}

