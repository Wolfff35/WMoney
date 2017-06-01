package com.wolff.wmoney.fragments.currency;


import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.wolff.wmoney.adapters.Adapter_CurrencyList;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WCurrency;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Currency_list_fragment extends ListFragment {
    private ArrayList<WCurrency> mCurrencyList;
    private Currency_list_fragment_listener listener;

    public interface Currency_list_fragment_listener{
        void onCurrencyItemClick(WCurrency currency);
    }

    public Currency_list_fragment() {
    }
    public static Currency_list_fragment newInstance(){
        Currency_list_fragment fragment = new Currency_list_fragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrencyList = DataLab.get(getContext()).getWCurrencyList();
    }

    @Override
    public void onResume() {
        super.onResume();
        mCurrencyList = DataLab.get(getContext()).getWCurrencyList();
        onActivityCreated(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListAdapter adapter = new Adapter_CurrencyList(getContext(),mCurrencyList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    listener.onCurrencyItemClick(mCurrencyList.get(position));
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Currency_list_fragment_listener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }
}
