package com.wolff.wmoney.activities;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wolff.wmoney.R;
import com.wolff.wmoney.fragments.operation.Operation_item_fragment;
import com.wolff.wmoney.model.WOperation;

public class Credit_item_activity extends AppCompatActivity {
    private WOperation mCreditItem;
    public static final String EXTRA_CREDIT_ITEM = "CreditItem";

    public static Intent newIntent(Context context, WOperation credit){
        Intent intent = new Intent(context,Credit_item_activity.class);
        intent.putExtra(EXTRA_CREDIT_ITEM,credit);
        return intent;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);
        mCreditItem = (WOperation)getIntent().getSerializableExtra(EXTRA_CREDIT_ITEM);
        Operation_item_fragment credit_itemFragment = Operation_item_fragment.newIntance(mCreditItem);

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.item_container, credit_itemFragment);
        fragmentTransaction.commit();


    }

}
