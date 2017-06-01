package com.wolff.wmoney.fragments.credit;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.wolff.wmoney.adapters.Adapter_CreditList;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WCredit;

import java.util.ArrayList;

/**
 * Created by wolff on 01.06.2017.
 */

public class Credit_list_fragment extends ListFragment {
    private ArrayList<WCredit> mCreditList;
    private Credit_list_fragment_listener listener;

    public interface Credit_list_fragment_listener{
        void onCreditItemClick(WCredit credit);
    }

    public Credit_list_fragment() {
    }
    public static Credit_list_fragment newInstance(){
        Credit_list_fragment fragment = new Credit_list_fragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCreditList = DataLab.get(getContext()).getWCreditList(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        mCreditList = DataLab.get(getContext()).getWCreditList(getContext());
        onActivityCreated(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListAdapter adapter = new Adapter_CreditList(getContext(),mCreditList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onCreditItemClick(mCreditList.get(position));
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Credit_list_fragment_listener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

}
