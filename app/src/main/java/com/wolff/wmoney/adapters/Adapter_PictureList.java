package com.wolff.wmoney.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.wolff.wmoney.R;

/**
 * Created by wolff on 31.05.2017.
 */

public class Adapter_PictureList extends BaseAdapter{
    Context mContext;
    LayoutInflater mInflater;
    //private int[] mPictList_ = {R.drawable.pict_account_1,R.drawable.pict_account_2};
    private TypedArray mPictList;

    public Adapter_PictureList(Context context){
        mContext=context;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mPictList  = mContext.getResources().obtainTypedArray(R.array.account_pictures_array);
    }
    @Override
    public int getCount() {
        return mPictList.length();
    }

    @Override
    public Drawable getItem(int position) {
        return mPictList.getDrawable(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            view=mInflater.inflate(R.layout.picture_list_adapter,parent,false);
        }
        //WAccount account = (WAccount)getItem(position);
        ImageView ivPictureAdapter = (ImageView)view.findViewById(R.id.ivPictureAdapter);

        ivPictureAdapter.setImageDrawable(getItem(position));
        return view;
    }}
