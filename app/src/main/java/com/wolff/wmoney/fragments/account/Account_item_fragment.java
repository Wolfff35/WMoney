package com.wolff.wmoney.fragments.account;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.wolff.wmoney.R;
import com.wolff.wmoney.fragments.currency.Currency_list_dialog;
import com.wolff.wmoney.fragments.misc.Picture_list_dialog;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WCurrency;

import static com.wolff.wmoney.fragments.currency.Currency_list_dialog.TAG_CURRENCY_ID;

/**
 * Created by wolff on 31.05.2017.
 */

public class Account_item_fragment extends Fragment{

    public static final int DIALOG_REQUEST_PICTURE = 1;
    public static final int DIALOG_REQUEST_CURRENCY = 2;


    private static final String ARG_ACCOUNT_ITEM = "WAccItem";
    private WAccount mAccountItem;

    private boolean mIsNewItem;
    private boolean mIsEditable;
    private boolean mIsDataChanged;

    private Menu mOptionsMenu;

    EditText edAccountItem_Name;
    EditText edAccountItem_Describe;
    EditText edAccountItem_Summa;
    Button imAccountItem_Currency;
    ImageButton imAccountItem_Picture;
    public Account_item_fragment() {
        // Required empty public constructor
    }

    public static Account_item_fragment newIntance(WAccount item){
        Bundle args = new Bundle();
        args.putSerializable(ARG_ACCOUNT_ITEM,item);
        Account_item_fragment fragment = new Account_item_fragment();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAccountItem = (WAccount)getArguments().getSerializable(ARG_ACCOUNT_ITEM);
        if(mAccountItem==null){
            mAccountItem = new WAccount();
            mIsNewItem=true;
            mIsEditable=true;
            mAccountItem.setIdPicture(R.drawable.pict_account_1);
        }
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.account_item_fragment, container, false);
        edAccountItem_Name = (EditText)view.findViewById(R.id.edAccountItem_Name);
        edAccountItem_Describe = (EditText)view.findViewById(R.id.edAccountItem_Describe);
        edAccountItem_Summa = (EditText)view.findViewById(R.id.edAccountItem_Summa);
        imAccountItem_Currency = (Button)view.findViewById(R.id.imAccountItem_Currency);
        imAccountItem_Picture = (ImageButton)view.findViewById(R.id.imAccountItem_Picture);

        edAccountItem_Name.setText(mAccountItem.getName());
        edAccountItem_Describe.setText(mAccountItem.getDescribe());
        edAccountItem_Summa.setText(String.format("%.2f",mAccountItem.getSumma()));
        try {
            imAccountItem_Currency.setText(mAccountItem.getCurrency().getName());
        }catch (Exception e){}
        imAccountItem_Picture.setImageResource(mAccountItem.getIdPicture());

        edAccountItem_Name.addTextChangedListener(textChangedListener);
        edAccountItem_Describe.addTextChangedListener(textChangedListener);
        edAccountItem_Summa.addTextChangedListener(textChangedListener);
        imAccountItem_Currency.setOnClickListener(onClick_Currency_listener);
        imAccountItem_Picture.setOnClickListener(onClick_Picture_Listener);
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
                saveAccountItem();
                break;
            }
            case R.id.action_delete: {
                deleteAccountItem();
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
        edAccountItem_Name.setEnabled(mIsEditable);
        edAccountItem_Describe.setEnabled(mIsEditable);
        edAccountItem_Summa.setEnabled(mIsEditable);
        imAccountItem_Currency.setEnabled(mIsEditable);
        imAccountItem_Picture.setEnabled(mIsEditable);
    }
    private void saveAccountItem(){
        updateAccount();
        if (!isFillingOk()) return;
        if(mIsNewItem){
            DataLab.get(getContext()).account_add(mAccountItem);
        }else {
            DataLab.get(getContext()).account_update(mAccountItem);
        }
        getActivity().finish();
    }

    private boolean isFillingOk() {
        StringBuilder sb = new StringBuilder();
        boolean isOk=true;
        if(mAccountItem.getName().isEmpty()){
            sb.append(getResources().getString(R.string.account_name_label)+", ");
            isOk=false;
        }
        if(mAccountItem.getCurrency()==null){
            sb.append(getResources().getString(R.string.currency_name_label)+",");
            isOk=false;
        }
         if(mAccountItem.getIdPicture()==0){
             sb.append("пиктограмма");
             isOk=false;
         }
        if(!isOk){
            Snackbar.make(getView(), "Не заполнены необходимые поля: "+sb.toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        return true;
    }

    private void deleteAccountItem(){
        DataLab.get(getContext()).account_delete(mAccountItem);
        getActivity().finish();
    }
    private void updateAccount(){
        mAccountItem.setName(edAccountItem_Name.getText().toString());
        mAccountItem.setDescribe(edAccountItem_Describe.getText().toString());
        mAccountItem.setSumma(Double.valueOf(edAccountItem_Summa.getText().toString()));
      }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case DIALOG_REQUEST_PICTURE:
                    int idPict = data.getIntExtra(Picture_list_dialog.TAG_PICTURE_ID,-1);
                    //Log.e("RESULT","idPict = "+idPict);
                    mAccountItem.setIdPicture(idPict);
                    imAccountItem_Picture.setImageResource(idPict);
                    mIsDataChanged=true;
                    setOptionsMenuVisibility();
                    break;
                case DIALOG_REQUEST_CURRENCY:
                    WCurrency currency = (WCurrency) data.getSerializableExtra(TAG_CURRENCY_ID);
                    if(currency!=null) {
                        //Log.e("RESULT", "Currency = " + currency.getName());
                        mAccountItem.setCurrency(currency);
                        imAccountItem_Currency.setText(currency.getName());
                        mIsDataChanged = true;
                        setOptionsMenuVisibility();
                    }else {
                        Log.e("RESULT"," NO CURRENCY");
                    }
                    break;
            }
        }
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
//---------------------------
    View.OnClickListener onClick_Picture_Listener = new View.OnClickListener(){

    @Override
    public void onClick(View v) {
        DialogFragment dlgPict = new Picture_list_dialog();
        dlgPict.setTargetFragment(Account_item_fragment.this,DIALOG_REQUEST_PICTURE);
        dlgPict.show(getFragmentManager(),dlgPict.getClass().getName());
    }
};
    View.OnClickListener onClick_Currency_listener = new View.OnClickListener(){

    @Override
    public void onClick(View v) {
        DialogFragment dlgPict = new Currency_list_dialog();
        dlgPict.setTargetFragment(Account_item_fragment.this,DIALOG_REQUEST_CURRENCY);
        dlgPict.show(getFragmentManager(),dlgPict.getClass().getName());

    }
};
}
