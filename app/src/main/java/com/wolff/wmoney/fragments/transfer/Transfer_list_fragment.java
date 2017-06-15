package com.wolff.wmoney.fragments.transfer;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.wolff.wmoney.adapters.Adapter_OperationList;
import com.wolff.wmoney.adapters.Adapter_TransferList;
import com.wolff.wmoney.fragments.operation.Operation_list_fragment;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WTransfer;

import java.util.ArrayList;

/**
 * Created by wolff on 12.06.2017.
 */

public class Transfer_list_fragment extends ListFragment {
    private ArrayList<WTransfer> mTransferList;
    private Transfer_list_fragment_listener listener;

    public interface Transfer_list_fragment_listener {
        void onTransferItemClick(WTransfer transfer);
    }

    public Transfer_list_fragment() {
    }
    public static Transfer_list_fragment newInstance(){
        Transfer_list_fragment fragment = new Transfer_list_fragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTransferList = DataLab.get(getContext()).getWTransferList(getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        mTransferList = DataLab.get(getContext()).getWTransferList(getContext());
        onActivityCreated(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListAdapter adapter = new Adapter_TransferList(getContext(), mTransferList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onTransferItemClick(mTransferList.get(position));
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Transfer_list_fragment_listener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

}

