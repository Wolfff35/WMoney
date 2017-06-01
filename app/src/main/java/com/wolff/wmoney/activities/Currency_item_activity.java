package com.wolff.wmoney.activities;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wolff.wmoney.R;
import com.wolff.wmoney.fragments.currency.Currency_item_fragment;
import com.wolff.wmoney.model.WCurrency;

public class Currency_item_activity extends AppCompatActivity {

    private WCurrency mCurrencyItem;
    public static final String EXTRA_CURRENCY_ITEM = "CurrencyItem";

  public static Intent newIntent(Context context, WCurrency currency){
     Intent intent = new Intent(context,Currency_item_activity.class);
     intent.putExtra(EXTRA_CURRENCY_ITEM,currency);
      return intent;

  }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_item_activity);
        mCurrencyItem = (WCurrency)getIntent().getSerializableExtra(EXTRA_CURRENCY_ITEM);
        Currency_item_fragment currency_itemFragment = Currency_item_fragment.newIntance(mCurrencyItem);

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.currency_item_container, currency_itemFragment);
        fragmentTransaction.commit();


    }

}
