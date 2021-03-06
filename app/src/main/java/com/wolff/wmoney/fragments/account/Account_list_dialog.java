package com.wolff.wmoney.fragments.account;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wolff.wmoney.R;
import com.wolff.wmoney.adapters.Adapter_AccountList;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WCurrency;

import java.util.ArrayList;

/**
 * Created by wolff on 31.05.2017.
 */

public class Account_list_dialog extends DialogFragment {
    private ArrayList<WAccount> mAccountList;
    public static final String TAG_ACCOUNT_ID = "acc_id";
    private Context mContext;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        DataLab dataLab = DataLab.get(mContext);
        mAccountList = dataLab.getWAccountList(mContext);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_list,null);
        ListView lvItemList = (ListView)view.findViewById(R.id.lvItemList);
        Adapter_AccountList adapter = new Adapter_AccountList(mContext, mAccountList);
        lvItemList.setAdapter(adapter);
        lvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("DIALOG","Selected item curr "+mAccountList.get(position));
                Intent intent = new Intent();
                intent.putExtra(TAG_ACCOUNT_ID,mAccountList.get(position));
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
                dismiss();
            }
        });
        builder.setView(view);
       return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
