package com.wolff.wmoney.activities;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wolff.wmoney.R;
import com.wolff.wmoney.fragments.account.Account_item_fragment;
import com.wolff.wmoney.model.WAccount;

public class Account_item_activity extends AppCompatActivity {
    private WAccount mAccountItem;
    public static final String EXTRA_ACCOUNT_ITEM = "AccountItem";

    public static Intent newIntent(Context context, WAccount account){
        Intent intent = new Intent(context,Account_item_activity.class);
        intent.putExtra(EXTRA_ACCOUNT_ITEM,account);
        return intent;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);
        mAccountItem = (WAccount)getIntent().getSerializableExtra(EXTRA_ACCOUNT_ITEM);
        Account_item_fragment account_itemFragment = Account_item_fragment.newIntance(mAccountItem);

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.item_container, account_itemFragment);
        fragmentTransaction.commit();


    }

}
