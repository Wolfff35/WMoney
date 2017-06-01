package com.wolff.wmoney.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wolff.wmoney.R;
import com.wolff.wmoney.model.WCategory;
import com.wolff.wmoney.model.WCurrency;

import java.util.ArrayList;

/**
 * Created by wolff on 01.06.2017.
 */

public class Adapter_CategoryList extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<WCategory> mCategoryList;

    public Adapter_CategoryList(Context context, ArrayList<WCategory> categoryList){
        mContext=context;
        mCategoryList=categoryList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mCategoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            view=mInflater.inflate(R.layout.category_list_item_adapter,parent,false);
        }
        WCategory category = (WCategory)getItem(position);
        TextView tvNameCategory = (TextView) view.findViewById(R.id.tvNameCategory);
        ImageView ivIsCredit = (ImageView) view.findViewById(R.id.ivIsCredit);
        tvNameCategory.setText(category.getName()+" / "+category.getDescribe());
        if(category.isCredit()){
            ivIsCredit.setImageResource(R.drawable.pict_minus);
        }else {
            ivIsCredit.setImageResource(R.drawable.pict_plus);
        }
        // Log.e("ADAPTER","getView");
        return view;
    }
}
