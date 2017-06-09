package com.wolff.wmoney.fragments.category;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.wolff.wmoney.R;
import com.wolff.wmoney.fragments.Item_fragment;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WCategory;

/**
 * Created by wolff on 01.06.2017.
 */

public class Category_item_fragment extends Item_fragment {
    private static final String ARG_CATEGORY_ITEM = "WCategoryItem";
    private WCategory mCategoryItem;

    EditText edCategoryItem_Name;
    EditText edCategoryItem_Describe;
    ToggleButton tbIsCredit;

    public static Category_item_fragment newIntance(WCategory item){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY_ITEM,item);
        Category_item_fragment fragment = new Category_item_fragment();
        fragment.setArguments(args);
        return fragment;

    }
    public Category_item_fragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCategoryItem = (WCategory)getArguments().getSerializable(ARG_CATEGORY_ITEM);
        if(mCategoryItem==null){
            mCategoryItem = new WCategory();
            mIsNewItem=true;
            mIsEditable=true;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.category_item_fragment, container, false);
        edCategoryItem_Name = (EditText)view.findViewById(R.id.edCategoryItem_Name);
        edCategoryItem_Describe = (EditText)view.findViewById(R.id.edCategoryItem_Describe);
        tbIsCredit = (ToggleButton)view.findViewById(R.id.tbIsCredit);
        setFieldsVisibility();
        super.onCreateView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    public void fields_fillData() {
        super.fields_fillData();
        edCategoryItem_Name.setText(mCategoryItem.getName());
        edCategoryItem_Describe.setText(mCategoryItem.getDescribe());
        tbIsCredit.setChecked(mCategoryItem.isCredit());
    }

    @Override
    public void fields_setListeners() {
        super.fields_setListeners();
        edCategoryItem_Name.addTextChangedListener(textChangedListener);
        edCategoryItem_Describe.addTextChangedListener(textChangedListener);
        tbIsCredit.setOnCheckedChangeListener(onCheckedListener);
    }

    @Override
    public void setFieldsVisibility() {
        super.setFieldsVisibility();
         edCategoryItem_Name.setEnabled(mIsEditable);
         edCategoryItem_Describe.setEnabled(mIsEditable);
         tbIsCredit.setEnabled(mIsEditable);
     }

    @Override
    public void saveItem() {
        super.saveItem();
        if (!isFillingOk()) return;
        if(mIsNewItem){
            DataLab.get(getContext()).category_add(mCategoryItem);
        }else {
            DataLab.get(getContext()).category_update(mCategoryItem);
        }
        getActivity().finish();
    }

    @Override
    public boolean isFillingOk() {
        super.isFillingOk();
        if(mCategoryItem.getName().isEmpty()){
            Snackbar.make(getView(), "Не заполнено поле '"+getResources().getString(R.string.category_name_label)+"'", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        return true;
    }

    @Override
    public void deleteItem() {
        super.deleteItem();
        DataLab.get(getContext()).category_delete(mCategoryItem);
        getActivity().finish();
    }

    @Override
    public void updateItemFields() {
        super.updateItemFields();
        mCategoryItem.setName(edCategoryItem_Name.getText().toString());
        mCategoryItem.setDescribe(edCategoryItem_Describe.getText().toString());
        mCategoryItem.setCredit(tbIsCredit.isChecked());
    }

    //========================
    CompoundButton.OnCheckedChangeListener onCheckedListener = new CompoundButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mIsDataChanged=true;
            setOptionsMenuVisibility();

        }
    };
}
