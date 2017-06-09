package com.wolff.wmoney.fragments.currency;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.wolff.wmoney.R;
import com.wolff.wmoney.fragments.Item_fragment;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WCurrency;

/**
 * A simple {@link Fragment} subclass.
 */
public class Currency_item_fragment extends Item_fragment {
    private static final String ARG_CURRENCY_ITEM = "WCurrItem";
    private WCurrency mCurrencyItem;

    EditText edCurrencyItem_Name;
    EditText edCurrencyItem_Describe;

    public static Currency_item_fragment newIntance(WCurrency item){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CURRENCY_ITEM,item);
        Currency_item_fragment fragment = new Currency_item_fragment();
        fragment.setArguments(args);
        return fragment;

    }

    public Currency_item_fragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCurrencyItem = (WCurrency)getArguments().getSerializable(ARG_CURRENCY_ITEM);
        if(mCurrencyItem==null){
            mCurrencyItem = new WCurrency();
            mIsNewItem=true;
            mIsEditable=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.currency_item_fragment, container, false);
        edCurrencyItem_Name = (EditText)view.findViewById(R.id.edCurrencyItem_Name);
        edCurrencyItem_Describe = (EditText)view.findViewById(R.id.edCurrencyItem_Describe);
        super.onCreateView(inflater,container,savedInstanceState);

        setFieldsVisibility();
        return view;
    }

    @Override
    public void fields_fillData() {
        super.fields_fillData();
        edCurrencyItem_Name.setText(mCurrencyItem.getName());
        edCurrencyItem_Describe.setText(mCurrencyItem.getDescribe());
    }

    @Override
    public void fields_setListeners() {
        super.fields_setListeners();
        edCurrencyItem_Name.addTextChangedListener(textChangedListener);
        edCurrencyItem_Describe.addTextChangedListener(textChangedListener);
    }

    @Override
    public void setFieldsVisibility() {
        super.setFieldsVisibility();
        edCurrencyItem_Name.setEnabled(mIsEditable);
        edCurrencyItem_Describe.setEnabled(mIsEditable);
    }

    @Override
    public void saveItem() {
        super.saveItem();
        if (!isFillingOk()) return;
        if(mIsNewItem){
            DataLab.get(getContext()).currency_add(mCurrencyItem);
        }else {
            DataLab.get(getContext()).currency_update(mCurrencyItem);
        }
        getActivity().finish();
    }

    @Override
    public boolean isFillingOk() {
        super.isFillingOk();
        if(mCurrencyItem.getName().isEmpty()){
            Snackbar.make(getView(), "Не заполнено поле '"+getResources().getString(R.string.currency_name_label)+"' ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        return true;

    }

    @Override
    public void deleteItem() {
        super.deleteItem();
        DataLab.get(getContext()).currency_delete(mCurrencyItem);
        getActivity().finish();
    }

    @Override
    public void updateItemFields() {
        super.updateItemFields();
        mCurrencyItem.setName(edCurrencyItem_Name.getText().toString());
        mCurrencyItem.setDescribe(edCurrencyItem_Describe.getText().toString());
    }
}
