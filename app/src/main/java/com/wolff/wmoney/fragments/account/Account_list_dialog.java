package com.wolff.wmoney.fragments.account;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.wolff.wmoney.R;
import com.wolff.wmoney.adapters.Adapter_AccountList;
import com.wolff.wmoney.localdb.DataLab;

/**
 * Created by wolff on 31.05.2017.
 */

public class Account_list_dialog extends DialogFragment {
    private Context mContext;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_list,null);
        ListView lvItemList = (ListView)view.findViewById(R.id.lvItemList);
        Adapter_AccountList adapter = new Adapter_AccountList(mContext, DataLab.get(mContext).getWAccountList(mContext));
        lvItemList.setAdapter(adapter);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}
