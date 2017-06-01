package com.wolff.wmoney.fragments.account;


import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.wolff.wmoney.adapters.Adapter_AccountList;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WAccount;

import java.util.ArrayList;


public class Account_list_fragment extends ListFragment {
    private ArrayList<WAccount> mAccountList;
    private Account_list_fragment_listener listener;

    public interface Account_list_fragment_listener{
        void onAccountItemClick(WAccount account);
    }

    public Account_list_fragment() {
    }
    public static Account_list_fragment newInstance(){
        Account_list_fragment fragment = new Account_list_fragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountList = DataLab.get(getContext()).getWAccountList(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        mAccountList = DataLab.get(getContext()).getWAccountList(getContext());
        onActivityCreated(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListAdapter adapter = new Adapter_AccountList(getContext(),mAccountList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onAccountItemClick(mAccountList.get(position));
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Account_list_fragment_listener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }
}
