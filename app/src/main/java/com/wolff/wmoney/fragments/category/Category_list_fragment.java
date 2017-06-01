package com.wolff.wmoney.fragments.category;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;

import com.wolff.wmoney.adapters.Adapter_CategoryList;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WCategory;

import java.util.ArrayList;

/**
 * Created by wolff on 01.06.2017.
 */

public class Category_list_fragment extends ListFragment{
    private ArrayList<WCategory> mCategoryList;
    private Category_list_fragment_listener listener;

    public interface Category_list_fragment_listener{
        void onCategoryItemClick(WCategory category);
    }

    public Category_list_fragment() {
    }
    public static Category_list_fragment newInstance(){
        Category_list_fragment fragment = new Category_list_fragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryList = DataLab.get(getContext()).getWCategoryList(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCategoryList = DataLab.get(getContext()).getWCategoryList(0);
        onActivityCreated(null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListAdapter adapter = new Adapter_CategoryList(getContext(),mCategoryList);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onCategoryItemClick(mCategoryList.get(position));
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (Category_list_fragment_listener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }

}
