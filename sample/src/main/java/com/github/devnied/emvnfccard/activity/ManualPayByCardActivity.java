package com.github.devnied.emvnfccard.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;

import com.github.devnied.emvnfccard.R;
import com.github.devnied.emvnfccard.adapter.MenuDrawerAdapter;
import com.github.devnied.emvnfccard.fragment.AboutFragment;
import com.github.devnied.emvnfccard.fragment.BillingFragment;
import com.github.devnied.emvnfccard.fragment.CartFragment;
import com.github.devnied.emvnfccard.fragment.CreateInventoryFragment;
import com.github.devnied.emvnfccard.fragment.FundraiserFragment;
import com.github.devnied.emvnfccard.fragment.IRefreshable;
import com.github.devnied.emvnfccard.fragment.LogOutFragment;
import com.github.devnied.emvnfccard.fragment.SimplePayFragment;
import com.github.devnied.emvnfccard.fragment.ViewPagerFragment;
import com.github.devnied.emvnfccard.utils.ConstantUtils;

import java.lang.ref.WeakReference;

import de.keyboardsurfer.android.widget.crouton.Crouton;


public class ManualPayByCardActivity extends FragmentActivity implements AdapterView.OnItemClickListener {//extends Activity{
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
    /* END DRAWER */


    private ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_pay_by_card);
        progress = new ProgressDialog(this);

        /* Sidebar menu */
        // get ListView defined in activity_main.xml
        mDrawerListView = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        mMenuAdapter = new MenuDrawerAdapter(this);
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
    }

    @Override
    public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id) {
        if (mLastSelectedMenu != position) {
            Fragment fragment = null;
            switch (position) {
                case ConstantUtils.CARDS_DETAILS:
                    fragment = new ViewPagerFragment();
                    refreshContent();
                    break;
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



    public void backToPaymentPage(View view) {
        finish();
    }

    public void onRadioButtonClicked(View view) {
        LinearLayout ccForm  = (LinearLayout) findViewById(R.id.form_cc);
        LinearLayout dcForm = (LinearLayout) findViewById(R.id.form_dc);
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_credit:
                if (checked)
                    //Display credit form
                    dcForm.setVisibility(View.GONE);
                    ccForm.setVisibility(View.VISIBLE);
                    break;
            case R.id.radio_debit:
                if (checked)
                    ccForm.setVisibility(View.GONE);
                    dcForm.setVisibility(View.VISIBLE);
                    //Display debit form
                    break;
        }
    }

    public void cccpayed(View view) {
        progress.setMessage("Greiðsla í vinnslu...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                Intent intent = new Intent(ManualPayByCardActivity.this, testActivity.class);
                startActivity(intent);


            }
        }, 3000);


    }



}
