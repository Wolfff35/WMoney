package com.wolff.wmoney.fragments.currency;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wolff.wmoney.R;
import com.wolff.wmoney.adapters.Adapter_CurrencyList;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WCurrency;

import java.util.ArrayList;

/**
 * Created by wolff on 01.06.2017.
 */

public class Currency_list_dialog extends DialogFragment {
    private ArrayList<WCurrency> mCurrencyList;
    public static final String TAG_CURRENCY_ID = "curr_id";
    private Context mContext;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mCurrencyList  = DataLab.get(mContext).getWCurrencyList();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_list,null);
        ListView lvItemList = (ListView)view.findViewById(R.id.lvItemList);
        Adapter_CurrencyList adapter = new Adapter_CurrencyList(mContext,mCurrencyList);
        lvItemList.setAdapter(adapter);
        lvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("DIALOG","Selected item curr "+mCurrencyList.get(position));
                Intent intent = new Intent();
                intent.putExtra(TAG_CURRENCY_ID,mCurrencyList.get(position));
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
