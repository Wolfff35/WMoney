package com.wolff.wmoney.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wolff.wmoney.R;
import com.wolff.wmoney.fragments.category.Category_list_fragment;
import com.wolff.wmoney.fragments.operation.Operation_list_fragment;
import com.wolff.wmoney.fragments.misc.Logo_fragment;
import com.wolff.wmoney.fragments.account.Account_list_fragment;
import com.wolff.wmoney.fragments.currency.Currency_list_fragment;
import com.wolff.wmoney.fragments.transfer.Transfer_list_fragment;
import com.wolff.wmoney.localdb.DbSchema;
import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WCategory;
import com.wolff.wmoney.model.WOperation;
import com.wolff.wmoney.model.WCurrency;
import com.wolff.wmoney.model.WTransfer;
import com.wolff.wmoney.test_data.Test_data;


public class Main_activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        Currency_list_fragment.Currency_list_fragment_listener,
        Account_list_fragment.Account_list_fragment_listener,
        Category_list_fragment.Category_list_fragment_listener,
        Operation_list_fragment.Operation_list_fragment_listener,
        Transfer_list_fragment.Transfer_list_fragment_listener{
    FloatingActionButton fab;
    private Fragment mCurrentFragment;
    private int mTypeItemToAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewItem();
             }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mCurrentFragment = new Logo_fragment();
        displayFragment();
         //writeTEstData();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayFragment();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id){
            case R.id.nav_categories:
                mCurrentFragment = Category_list_fragment.newInstance();
                mTypeItemToAdd=1;
                break;
            case R.id.nav_currencyes:
                mCurrentFragment = Currency_list_fragment.newInstance();
                mTypeItemToAdd=2;
                break;
            case R.id.nav_accounts:
                mCurrentFragment = Account_list_fragment.newInstance();
                mTypeItemToAdd=3;
                break;
            case R.id.nav_credit:
                mCurrentFragment = Operation_list_fragment.newInstance(DbSchema.TYPE_OPERATION_CREDIT);
                mTypeItemToAdd=4;
                break;
             case R.id.nav_debit:
                mCurrentFragment = Operation_list_fragment.newInstance(DbSchema.TYPE_OPERATION_DEBIT);
                 mTypeItemToAdd=5;
                 break;
            case R.id.nav_trans:
                //getApplication().setTheme(R.style.AppTheme);
                mCurrentFragment = Transfer_list_fragment.newInstance();
                mTypeItemToAdd=6;
                break;
            case R.id.nav_settings:
                //getApplication().setTheme(R.style.AppThemeLight);
                mTypeItemToAdd=0;
                break;
             default:
                Log.e("DEFAULT CASE","NOTHING");
                break;
        }
        displayFragment();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

     //=============================================================================================
    private void displayFragment() {
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_main, mCurrentFragment);
        fragmentTransaction.commit();
        if(mTypeItemToAdd>0){
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.INVISIBLE);
        }
    }
    private void writeTEstData(){
        Test_data test_data = new Test_data();
        test_data.fillTestData(getApplicationContext());
        Log.e("TEST DATA","Success");
    }
    private void addNewItem(){
        Intent intent = null;
        switch (mTypeItemToAdd){
            case 1:
                intent = Category_item_activity.newIntent(getApplicationContext(),null);
                break;
            case 2:
                intent = Currency_item_activity.newIntent(getApplicationContext(),null);
                break;
            case 3:
                intent = Account_item_activity.newIntent(getApplicationContext(),null);
                break;
            case 4:
                intent = Operation_item_activity.newIntent(getApplicationContext(),null,DbSchema.TYPE_OPERATION_CREDIT);
                break;
            case 5:
                intent = Operation_item_activity.newIntent(getApplicationContext(),null,DbSchema.TYPE_OPERATION_DEBIT);
                break;
            case 6:
                intent = Transfer_item_activity.newIntent(getApplicationContext(),null);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
    @Override
    public void onCurrencyItemClick(WCurrency currency) {
        Intent intent = Currency_item_activity.newIntent(getApplicationContext(),currency);
        startActivity(intent);
    }

    @Override
    public void onAccountItemClick(WAccount account) {
     //Log.e("ACCOUNT"," ITEM CLICK "+account.getName());
        Intent intent = Account_item_activity.newIntent(getApplicationContext(),account);
        startActivity(intent);
    }

    @Override
    public void onCategoryItemClick(WCategory category) {
        Intent intent = Category_item_activity.newIntent(getApplicationContext(),category);
        startActivity(intent);
        //Log.e("CATEGORY"," ITEM CLICK "+category.getName());

    }

    @Override
    public void onOperationItemClick(WOperation operation, int typeOperation) {
        Intent intent = Operation_item_activity.newIntent(getApplicationContext(),operation,typeOperation);
        startActivity(intent);
        //Log.e("CATEGORY"," ITEM CLICK "+operation.getName());

    }

    @Override
    public void onTransferItemClick(WTransfer transfer) {
        Intent intent = Transfer_item_activity.newIntent(getApplicationContext(),transfer);
        startActivity(intent);
        Log.e("TRANSFER"," ITEM CLICK "+transfer.getName());

    }
}
