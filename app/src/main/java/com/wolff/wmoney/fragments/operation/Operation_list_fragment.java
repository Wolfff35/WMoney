package com.wolff.wmoney.fragments.operation;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.wolff.wmoney.adapters.Adapter_OperationList;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WOperation;

import java.util.ArrayList;

/**
 * Created by wolff on 01.06.2017.
 */

public class Operation_list_fragment extends ListFragment {
   public static final String TYPE_OPERATION_ARG = "typeOper";
    private int mTypeOperation;
    private ArrayList<WOperation> mOperationList;
    private Operation_list_fragment_listener listener;

    public interface Operation_list_fragment_listener {
        void onOperationItemClick(WOperation operation, int typeOperation);
    }

    public Operation_list_fragment() {
    }
    public static Operation_list_fragment newInstance(int typeOperation){
        Bundle args = new Bundle();
        args.putInt(TYPE_OPERATION_ARG,typeOperation);
        Operation_list_fragment fragment = new Operation_list_fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTypeOperation = getArguments().getInt(TYPE_OPERATION_ARG);
            mOperationList = DataLab.get(getContext()).getWOperationList(getContext(),mTypeOperation);
       // Log.e("CREATE FRAGMENT"," = "+ mOperationList.size());
    }

    @Override
    public void onResume() {
        super.onResume();
        mOperationList = DataLab.get(getContext()).getWOperationList(getContext(),mTypeOperation);
        onActivityCreated(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListAdapter adapter = new Adapter_OperationList(getContext(), mOperationList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onOperationItemClick(mOperationList.get(position),mTypeOperation);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Operation_list_fragment_listener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

}
