package com.wolff.wmoney.fragments.category;

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
import com.wolff.wmoney.adapters.Adapter_CategoryList;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WCategory;

import java.util.ArrayList;


/**
 * Created by wolff on 06.06.2017.
 */

public class Category_list_dialog extends DialogFragment {
    private ArrayList<WCategory> mCategoryList;
    public static final String TAG_CATEGORY_ID = "cat_id";
    public static final String TYPE_CATEGORY = "type_category";
    private Context mContext;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        DataLab dataLab = DataLab.get(mContext);

        mCategoryList = dataLab.getWCategoryList(getArguments().getInt(TYPE_CATEGORY));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_list,null);
        ListView lvItemList = (ListView)view.findViewById(R.id.lvItemList);
        Adapter_CategoryList adapter = new Adapter_CategoryList(mContext, mCategoryList);
        lvItemList.setAdapter(adapter);
        lvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("DIALOG","Selected item cat "+mCategoryList.get(position));
                Intent intent = new Intent();
                intent.putExtra(TAG_CATEGORY_ID,mCategoryList.get(position));
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

/*    private Context mContext;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_list,null);
        ListView lvItemList = (ListView)view.findViewById(R.id.lvItemList);
        Adapter_CategoryList adapter = new Adapter_CategoryList(mContext, DataLab.get(mContext).getWCategoryList(0));
        lvItemList.setAdapter(adapter);
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}*/

