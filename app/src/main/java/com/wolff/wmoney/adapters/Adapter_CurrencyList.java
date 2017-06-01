package com.wolff.wmoney.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wolff.wmoney.R;
import com.wolff.wmoney.model.WCurrency;

import java.util.ArrayList;

/**
 * Created by wolff on 25.05.2017.
 */

public class Adapter_CurrencyList extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<WCurrency> mCurrencyList;

    public Adapter_CurrencyList(Context context, ArrayList<WCurrency> currencyList){
        mContext=context;
        mCurrencyList=currencyList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // Log.e("ADAPTER","CONSTRUCTOR");
    }
    @Override
    public int getCount() {
        //Log.e("ADAPTER","count = "+mCurrencyList.size());
        return mCurrencyList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCurrencyList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            view=mInflater.inflate(R.layout.currency_list_item_adapter,parent,false);
        }
        WCurrency curr = (WCurrency)getItem(position);
        TextView tvNameCurr = (TextView) view.findViewById(R.id.tvNameCurr);
        tvNameCurr.setText(curr.getName()+" / "+curr.getDescribe());
       // Log.e("ADAPTER","getView");
        return view;
    }
}
