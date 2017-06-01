package com.wolff.wmoney.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wolff.wmoney.R;
import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WCurrency;

import java.util.ArrayList;

/**
 * Created by wolff on 31.05.2017.
 */

public class Adapter_AccountList extends BaseAdapter{
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<WAccount> mAccountList;

    public Adapter_AccountList(Context context, ArrayList<WAccount> accountList){
        mContext=context;
        mAccountList=accountList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        //Log.e("ADAPTER","count = "+mCurrencyList.size());
        return mAccountList.size();
    }

    @Override
    public Object getItem(int position) {
        return mAccountList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            view=mInflater.inflate(R.layout.account_list_item_adapter,parent,false);
        }
        WAccount account = (WAccount)getItem(position);
        TextView tvAccountName = (TextView) view.findViewById(R.id.tvAccountName);
        TextView tvAccountCost = (TextView) view.findViewById(R.id.tvAccountCost);
        ImageView ivAccountPict = (ImageView)view.findViewById(R.id.ivAccountPict);

        ivAccountPict.setImageResource(account.getIdPicture());
        tvAccountName.setText(account.getName());
        tvAccountCost.setText(String.format("%.2f",account.getSumma())+" "+account.getCurrency().getName());
        return view;
    }}
