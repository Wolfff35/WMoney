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
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WCurrency;

/**
 * A simple {@link Fragment} subclass.
 */
public class Currency_item_fragment extends Fragment {
    private static final String ARG_CURRENCY_ITEM = "WCurrItem";
    private WCurrency mCurrencyItem;

    private boolean mIsNewItem;
    private boolean mIsEditable;
    private boolean mIsDataChanged;

    private Menu mOptionsMenu;

    EditText edCurrencyItem_Name;
    EditText edCurrencyItem_Describe;
    public Currency_item_fragment() {
        // Required empty public constructor
    }

    public static Currency_item_fragment newIntance(WCurrency item){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CURRENCY_ITEM,item);
        Currency_item_fragment fragment = new Currency_item_fragment();
        fragment.setArguments(args);
        return fragment;

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
    setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.currency_item_fragment, container, false);
        edCurrencyItem_Name = (EditText)view.findViewById(R.id.edCurrencyItem_Name);
        edCurrencyItem_Describe = (EditText)view.findViewById(R.id.edCurrencyItem_Describe);

        edCurrencyItem_Name.setText(mCurrencyItem.getName());
        edCurrencyItem_Describe.setText(mCurrencyItem.getDescribe());

        edCurrencyItem_Name.addTextChangedListener(textChangedListener);
        edCurrencyItem_Describe.addTextChangedListener(textChangedListener);
        setFieldsVisibility();
    return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.mOptionsMenu = menu;
        inflater.inflate(R.menu.menu_item_actions,mOptionsMenu);
        super.onCreateOptionsMenu(mOptionsMenu, inflater);
        setOptionsMenuVisibility();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()){
            case R.id.action_save: {
                saveCurrencyItem();
                break;
            }
            case R.id.action_delete: {
                deleteCurrencyItem();
                break;
            }
            case R.id.action_edit: {
                mIsEditable=true;
                setOptionsMenuVisibility();
                setFieldsVisibility();
                break;
            }
             default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void setOptionsMenuVisibility(){
        if(mOptionsMenu!=null){
            MenuItem it_save = mOptionsMenu.findItem(R.id.action_save);
            MenuItem it_del = mOptionsMenu.findItem(R.id.action_delete);
            MenuItem it_edit = mOptionsMenu.findItem(R.id.action_edit);
            it_edit.setVisible(!mIsEditable);
            it_save.setVisible(mIsEditable&&mIsDataChanged);
            it_del.setVisible(!mIsNewItem);
        }
    }
    private void setFieldsVisibility(){
        edCurrencyItem_Name.setEnabled(mIsEditable);
        edCurrencyItem_Describe.setEnabled(mIsEditable);
    }
   private void saveCurrencyItem(){
       updateCurrency();
       if (!isFillingOk()) return;
       if(mIsNewItem){
            DataLab.get(getContext()).currency_add(mCurrencyItem);
        }else {
            DataLab.get(getContext()).currency_update(mCurrencyItem);
        }
        getActivity().finish();
   }
    private boolean isFillingOk() {
        if(mCurrencyItem.getName().isEmpty()){
            Snackbar.make(getView(), "Не заполнено поле '"+getResources().getString(R.string.currency_name_label)+"' ", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        return true;
    }
    private void deleteCurrencyItem(){
        DataLab.get(getContext()).currency_delete(mCurrencyItem);
       getActivity().finish();
   }
   private void updateCurrency(){
       mCurrencyItem.setName(edCurrencyItem_Name.getText().toString());
       mCurrencyItem.setDescribe(edCurrencyItem_Describe.getText().toString());
   }
    //==============================================================================================
    private TextWatcher textChangedListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            mIsDataChanged=true;
            setOptionsMenuVisibility();
        }
    };
}
