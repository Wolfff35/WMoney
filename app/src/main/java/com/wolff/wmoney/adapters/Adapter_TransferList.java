package com.wolff.wmoney.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wolff.wmoney.R;
import com.wolff.wmoney.Utils.DateUtils;
import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WCategory;
import com.wolff.wmoney.model.WCurrency;
import com.wolff.wmoney.model.WOperation;
import com.wolff.wmoney.model.WTransfer;

import java.util.ArrayList;

/**
 * Created by wolff on 12.06.2017.
 */

public class Adapter_TransferList extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    ArrayList<WTransfer> mTransferList;

    public Adapter_TransferList(Context context, ArrayList<WTransfer> transferList){
        mContext=context;
        mTransferList=transferList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mTransferList.size();
    }

    @Override
    public Object getItem(int position) {
        return mTransferList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view==null){
            view=mInflater.inflate(R.layout.transfer_list_item_adapter,parent,false);
        }
        WTransfer transfer = (WTransfer) getItem(position);

        ImageView ivTransferPictFrom = (ImageView)view.findViewById(R.id.ivTransferPictFrom);
        ImageView ivTransferPictTo = (ImageView) view.findViewById(R.id.ivTransferPictTo);

        TextView tvTransferName = (TextView) view.findViewById(R.id.tvTransferName);
        TextView tvTrDateOper = (TextView) view.findViewById(R.id.tvTransferDateOper);
        TextView tvTransferSummaFrom = (TextView) view.findViewById(R.id.tvTransferSummaFrom);
        TextView tvTransferSummaTo = (TextView) view.findViewById(R.id.tvTransferSummaTo);
        //TODO fill
        ivTransferPictFrom.setImageResource(transfer.getAccountFrom().getIdPicture());
        ivTransferPictTo.setImageResource(transfer.getAccountTo().getIdPicture());
        DateUtils dateUtils = new DateUtils();
        tvTrDateOper.setText(dateUtils.dateToString(transfer.getDateOper(),DateUtils.DATE_FORMAT_VID));
        tvTransferName.setText(transfer.getName());
        tvTransferSummaFrom.setText(String.format("%.2f", -transfer.getSummaFrom()));
        tvTransferSummaTo.setText(String.format("%.2f", transfer.getSummaTo()));
        Log.e("SUMMA TO",""+transfer.getSummaTo());
        //ivAccountPict.setImageResource(account.getIdPicture());
        //WAccount lAccount = credit.getAccount();
        //WCurrency lCurrency=null;
        //if(lAccount!=null) {
        //    lCurrency  = lAccount.getCurrency();
        //    ivCreditPict.setImageResource(lAccount.getIdPicture());
        //}
        //if(lCurrency!=null) {
        //    tvCreditSumma.setText(String.format("%.2f", credit.getSumma()) + " " + lCurrency.getName());
        //}
        //WCategory lCategory = credit.getCategory();
        //if(lCategory!=null) {
        //    tvCreditCategory.setText(lCategory.getName());
        //}
        //DateUtils dateUtils = new DateUtils();
        //tvCreditDateOper.setText(dateUtils.dateToString(transfer.getDateOper(),DateUtils.DATE_FORMAT_VID));
        // WAccount lAccount = credit.getAccount();
        return view;
    }}
