package com.wolff.wmoney.activities;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wolff.wmoney.R;
import com.wolff.wmoney.fragments.transfer.Transfer_item_fragment;
import com.wolff.wmoney.model.WTransfer;

/**
 * Created by wolff on 14.06.2017.
 */

public class Transfer_item_activity extends AppCompatActivity {
    private WTransfer mTransferItem;
    public static final String EXTRA_TRANSFER_ITEM = "TransferItem";

    public static Intent newIntent(Context context, WTransfer transfer){
        Intent intent = new Intent(context,Transfer_item_activity.class);
        intent.putExtra(EXTRA_TRANSFER_ITEM,transfer);
        return intent;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_activity);
        mTransferItem = (WTransfer) getIntent().getSerializableExtra(EXTRA_TRANSFER_ITEM);
        Transfer_item_fragment transfer_itemFragment = Transfer_item_fragment.newIntance(mTransferItem);

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.item_container, transfer_itemFragment);
        fragmentTransaction.commit();


    }

}
