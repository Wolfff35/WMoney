package com.wolff.wmoney.activities;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wolff.wmoney.R;
import com.wolff.wmoney.fragments.operation.Operation_item_fragment;
import com.wolff.wmoney.localdb.DbSchema;
import com.wolff.wmoney.model.WOperation;

public class Operation_item_activity extends AppCompatActivity {
    private WOperation mCreditItem;
    private int mTypeOperaion;
    public static final String EXTRA_OPERATION_ITEM = "CreditItem";
    public static final String EXTRA_OPERATION_TYPE = "TypeItem";

    public static Intent newIntent(Context context, WOperation credit,int typeOperation){
        Intent intent = new Intent(context,Operation_item_activity.class);
        intent.putExtra(EXTRA_OPERATION_ITEM,credit);
        intent.putExtra(EXTRA_OPERATION_TYPE,typeOperation);
        return intent;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);
        mCreditItem = (WOperation)getIntent().getSerializableExtra(EXTRA_OPERATION_ITEM);
        mTypeOperaion = getIntent().getIntExtra(EXTRA_OPERATION_TYPE,0);
        Operation_item_fragment operation_itemFragment = Operation_item_fragment.newIntance(mCreditItem,mTypeOperaion);
       //android:label="@string/title_activity_credit_item_activity"

        if(mTypeOperaion== DbSchema.TYPE_OPERATION_CREDIT) {
            this.setTitle(getResources().getString(R.string.title_activity_credit_item));
        }else {
            this.setTitle(getResources().getString(R.string.title_activity_debit_item));
        }
        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.item_container, operation_itemFragment);
        fragmentTransaction.commit();


    }

}
