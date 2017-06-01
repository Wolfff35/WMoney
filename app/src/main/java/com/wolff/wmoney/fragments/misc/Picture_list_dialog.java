package com.wolff.wmoney.fragments.misc;

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
import com.wolff.wmoney.adapters.Adapter_PictureList;

/**
 * Created by wolff on 31.05.2017.
 */

public class Picture_list_dialog extends DialogFragment {
    private TypedArray mPictureList;
    public static final String TAG_PICTURE_ID = "pict_id";
    private Context mContext;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mPictureList  = mContext.getResources().obtainTypedArray(R.array.account_pictures_array);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_list,null);
        ListView lvItemList = (ListView)view.findViewById(R.id.lvItemList);
        Adapter_PictureList adapter = new Adapter_PictureList(mContext);
        lvItemList.setAdapter(adapter);
        lvItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("DIALOG","Selected item "+mPictureList.getResourceId(position,-1));
                Intent intent = new Intent();
                intent.putExtra(TAG_PICTURE_ID,mPictureList.getResourceId(position,-1));
                getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK,intent);
                mPictureList.recycle();
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
