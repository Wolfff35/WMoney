package com.wolff.wmoney.activities;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.wolff.wmoney.fragments.Fragment_Logo;
import com.wolff.wmoney.fragments.Fragment_currency_list;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WCurrency;
import com.wolff.wmoney.test_data.Test_data;

import java.util.ArrayList;

public class Activity_Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,Fragment_currency_list.Currency_list_fragment_listener{
    private ArrayList<WCurrency> mCurrencyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Fragment_Logo fragment_logo = new Fragment_Logo();
        displayFragment(fragment_logo);
       // writeTEstData();
        readData();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        //item.setChecked(true);
        switch (id){
            case R.id.nav_categories:
                break;
            case R.id.nav_credit:
                break;
            case R.id.nav_currencyes:
                Fragment_currency_list fragment_currency_list = Fragment_currency_list.newInstance(mCurrencyList);
                displayFragment(fragment_currency_list);
                break;
            case R.id.nav_debit:
                break;
            case R.id.nav_accounts:
                break;
            case R.id.nav_settings:
                break;
            case R.id.nav_trans:
                break;
            default:
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //=============================================================================================
    private void displayFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container_main, fragment);
        fragmentTransaction.commit();
        //if (fragment.getClass().getSimpleName().equalsIgnoreCase("Fragment_ListWItem")) {
        //    fab.setVisibility(View.VISIBLE);
        //} else {
        //    fab.setVisibility(View.INVISIBLE);
        //}
    }
    private void readData(){
        DataLab dataLab = DataLab.get(getApplicationContext());
        mCurrencyList = dataLab.getWCurrencyList();
        Log.e("read data","currency list size = "+mCurrencyList.size());
    }
    private void writeTEstData(){
        Test_data test_data = new Test_data();
        test_data.fillTestData(getApplicationContext());
    }

    @Override
    public void onCurrencyItemClick(int idCurrency) {
        Log.e("==onCurrencyItemClick","==onCurrencyItemClick");
    }
}
