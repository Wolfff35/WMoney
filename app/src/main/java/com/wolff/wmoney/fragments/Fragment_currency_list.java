package com.wolff.wmoney.fragments;


import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.wolff.wmoney.R;
import com.wolff.wmoney.adapters.Adapter_CurrencyList;
import com.wolff.wmoney.model.WCurrency;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_currency_list extends Fragment {
    public static final String ARG_CURR_LIST = "CurrencyList";
    private ArrayList<WCurrency> mCurrencyList;

    private ListView lvCurrency;

    private Currency_list_fragment_listener listener;

    public interface Currency_list_fragment_listener{
        void onCurrencyItemClick(int idCurrency);
    }

    public Fragment_currency_list() {
    }
    public static Fragment_currency_list newInstance(ArrayList<WCurrency>currencyList){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CURR_LIST,currencyList);
        Fragment_currency_list fragment = new Fragment_currency_list();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrencyList = (ArrayList<WCurrency>)getArguments().getSerializable(ARG_CURR_LIST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_currency_list, container, false);
        lvCurrency = (ListView)v.findViewById(R.id.lvCurrency);
        Adapter_CurrencyList adapter = new Adapter_CurrencyList(getContext(),mCurrencyList);
        lvCurrency.setAdapter(adapter);
        lvCurrency.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("onItemClick","position = "+position+"; "+mCurrencyList.get(position).getName());
                    listener.onCurrencyItemClick(mCurrencyList.get(position).getId());
                Log.e("ITEM CLICK",""+mCurrencyList.get(position).getName());
            }
        });
        return v;
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
