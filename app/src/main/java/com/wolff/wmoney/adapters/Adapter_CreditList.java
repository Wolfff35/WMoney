package com.wolff.wmoney.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wolff.wmoney.R;
import com.wolff.wmoney.Utils.DateUtils;
import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WCredit;

import java.util.ArrayList;

import static com.wolff.wmoney.R.id.tvAccountName;

/**
 * Created by wolff on 01.06.2017.
 */

public class Adapter_CreditList extends BaseAdapter{
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<WCredit> mCreditList;

    public Adapter_CreditList(Context context, ArrayList<WCredit> creditList){
        mContext=context;
        mCreditList=creditList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        //Log.e("ADAPTER","count = "+mCurrencyList.size());
        return mCreditList.size();
    }

    @Override
    public Object getItem(int position) {
        return mCreditList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            view=mInflater.inflate(R.layout.operation_list_item_adapter,parent,false);
        }
        WCredit credit = (WCredit) getItem(position);
        TextView tvCreditName = (TextView) view.findViewById(R.id.tvCreditName);
        TextView tvCreditCategory = (TextView) view.findViewById(R.id.tvCreditCategory);
        TextView tvCreditDateOper = (TextView) view.findViewById(R.id.tvCreditDateOper);
        TextView tvCreditSummaVal = (TextView) view.findViewById(R.id.tvCreditSummaVal);
        ImageView ivCreditPict = (ImageView)view.findViewById(R.id.ivCreditPict);
        //TODO fill
        //ivAccountPict.setImageResource(account.getIdPicture());
        tvCreditName.setText(credit.getName());
        tvCreditSummaVal.setText(String.format("%.2f",credit.getSummaVal())+" "+credit.getCurrency().getName());
        tvCreditCategory.setText(credit.getCategory().getName());
        DateUtils dateUtils = new DateUtils();
        tvCreditDateOper.setText(dateUtils.dateToString(credit.getDateOper(),DateUtils.DATE_FORMAT_VID));
        ivCreditPict.setImageResource(credit.getAccount().getIdPicture());
        return view;
    }}
