package com.wolff.wmoney.fragments.operation;


import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.wolff.wmoney.R;
import com.wolff.wmoney.Utils.DateUtils;
import com.wolff.wmoney.fragments.Item_fragment;
import com.wolff.wmoney.fragments.account.Account_list_dialog;
import com.wolff.wmoney.fragments.category.Category_list_dialog;
import com.wolff.wmoney.fragments.misc.DatePicker_dialog;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WCategory;
import com.wolff.wmoney.model.WOperation;

import java.util.Date;

import static com.wolff.wmoney.fragments.account.Account_list_dialog.TAG_ACCOUNT_ID;
import static com.wolff.wmoney.fragments.category.Category_list_dialog.TAG_CATEGORY_ID;
import static com.wolff.wmoney.fragments.category.Category_list_dialog.TYPE_CATEGORY;
import static com.wolff.wmoney.fragments.misc.DatePicker_dialog.TAG_PERIOD_ID;

/**
 * A simple {@link Fragment} subclass.
 */
public class Operation_item_fragment extends Item_fragment {
    public static final int DIALOG_REQUEST_ACCOUNT = 1;
    public static final int DIALOG_REQUEST_DATE = 2;
    public static final int DIALOG_REQUEST_CATEGORY = 3;

    private static final String ARG_OPERATION_ITEM = "WCredItem";
    private WOperation mOperationItem;

    EditText edOperation_Name;
    EditText edOperation_Describe;
    EditText edOperation_Summa;
    Button btnOperation_Account;
    ImageView ivAccountPicture;
    TextView tvOperation_Currency;
    Button btnOperation_Period;
    Button btnOperation_Category;
    TextInputLayout edOperation_Name_layout;
    TextInputLayout edOperation_Summa_layout;

    public static Operation_item_fragment newIntance(WOperation item){
        Bundle args = new Bundle();
        args.putSerializable(ARG_OPERATION_ITEM,item);
        Operation_item_fragment fragment = new Operation_item_fragment();
        fragment.setArguments(args);
        return fragment;

    }
    public Operation_item_fragment() {
        super();
     }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOperationItem = (WOperation)getArguments().getSerializable(ARG_OPERATION_ITEM);
        if(mOperationItem ==null){
            mOperationItem = new WOperation();
            mIsNewItem=true;
            mIsEditable=true;
            mOperationItem.setDateOper(new Date());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.operation_item_fragment, container, false);
        edOperation_Name = (EditText)view.findViewById(R.id.edOperation_Name);
        edOperation_Describe = (EditText)view.findViewById(R.id.edOperation_describe);
        edOperation_Summa = (EditText)view.findViewById(R.id.edOperation_summa);
        btnOperation_Account = (Button)view.findViewById(R.id.imOperation_account);
        ivAccountPicture = (ImageView) view.findViewById(R.id.ivAccountPicture);
        tvOperation_Currency = (TextView) view.findViewById(R.id.tvOperation_currency);
        btnOperation_Period = (Button) view.findViewById(R.id.imOperation_Period);
        btnOperation_Category = (Button) view.findViewById(R.id.imOperation_category);
        edOperation_Name_layout = (TextInputLayout)view.findViewById(R.id.edOperation_Name_layout);
        edOperation_Summa_layout = (TextInputLayout)view.findViewById(R.id.edOperation_summa_layout);
         setFieldsVisibility();
        super.onCreateView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    public void fields_fillData() {
        super.fields_fillData();
        edOperation_Name.setText(mOperationItem.getName());
        edOperation_Describe.setText(mOperationItem.getDescribe());
        edOperation_Summa.setText(String.format("%.2f", mOperationItem.getSumma()));
        if(mOperationItem.getCategory()!=null) {
            btnOperation_Category.setText(mOperationItem.getCategory().getName());
        }else {
            btnOperation_Category.setText("");
        }
        if(mOperationItem.getAccount()!=null){
            btnOperation_Account.setText(mOperationItem.getAccount().getName());
            ivAccountPicture.setImageResource(mOperationItem.getAccount().getIdPicture());
            tvOperation_Currency.setText(mOperationItem.getAccount().getCurrency().getName());
        }else {
            btnOperation_Account.setText("");
        }
        DateUtils dateUtils = new DateUtils();
        btnOperation_Period.setText(dateUtils.dateToString(mOperationItem.getDateOper(),DateUtils.DATE_FORMAT_VID));

    }

    @Override
    public void fields_setListeners() {
        super.fields_setListeners();
        btnOperation_Account.setOnClickListener(onClick_Account_listener);
        btnOperation_Period.setOnClickListener(onClick_Period_listener);
        btnOperation_Category.setOnClickListener(onClick_Category_listener);
        edOperation_Name.addTextChangedListener(textChangedListener);
        edOperation_Describe.addTextChangedListener(textChangedListener);
        edOperation_Summa.addTextChangedListener(textChangedListener);
        edOperation_Name.setOnFocusChangeListener(onFocusChanged_listener);
        edOperation_Summa.setOnFocusChangeListener(onFocusChanged_listener);
    }

    @Override
    public void setFieldsVisibility() {
        super.setFieldsVisibility();
          edOperation_Name.setEnabled(mIsEditable);
          edOperation_Describe.setEnabled(mIsEditable);
          edOperation_Summa.setEnabled(mIsEditable);
          btnOperation_Account.setEnabled(mIsEditable);
          btnOperation_Period.setEnabled(mIsEditable);
          btnOperation_Category.setEnabled(mIsEditable);
    }

    @Override
    public void saveItem() {
        super.saveItem();
        if (!isFillingOk()) return;
        if(mIsNewItem){
            DataLab.get(getContext()).credit_add(mOperationItem);
        }else {
            DataLab.get(getContext()).credit_update(mOperationItem);
        }
        getActivity().finish();
    }

    @Override
    public boolean isFillingOk() {
        super.isFillingOk();
        StringBuilder sb = new StringBuilder();
        boolean isOk=true;
        if(mOperationItem.getName().isEmpty()){
            //sb.append(getResources().getString(R.string.credit_name_label)+", ");
            isOk=false;
        }else {
            edOperation_Name_layout.setErrorEnabled(false);
        }
        if(mOperationItem.getAccount()==null){
            sb.append(getResources().getString(R.string.account_name_label)+",");
            isOk=false;
        }
        if(mOperationItem.getCategory()==null){
            sb.append(getResources().getString(R.string.category_name_label));
            isOk=false;
        }
        if(!isOk){
            Snackbar.make(getView(), "Не заполнены необходимые поля: "+sb.toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        return true;
    }

    @Override
    public void deleteItem() {
        super.deleteItem();
        DataLab.get(getContext()).credit_delete(mOperationItem);
        getActivity().finish();
    }

    @Override
    public void updateItemFields() {
        super.updateItemFields();
        mOperationItem.setName(edOperation_Name.getText().toString());
        mOperationItem.setDescribe(edOperation_Describe.getText().toString());
        if(edOperation_Summa.getText().length()>0){
            mOperationItem.setSumma(Double.valueOf(edOperation_Summa.getText().toString()));
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case DIALOG_REQUEST_ACCOUNT:
                    WAccount account = (WAccount) data.getSerializableExtra(TAG_ACCOUNT_ID);
                    mOperationItem.setAccount(account);
                    btnOperation_Account.setText(account.getName());
                    ivAccountPicture.setImageResource(account.getIdPicture());
                    tvOperation_Currency.setText(account.getCurrency().getName());
                    mIsDataChanged=true;
                    setOptionsMenuVisibility();
                    break;
                case DIALOG_REQUEST_DATE:
                    DateUtils dateUtils = new DateUtils();
                    String ddate = data.getStringExtra(TAG_PERIOD_ID);
                    mOperationItem.setDateOper(dateUtils.dateFromString(ddate,DateUtils.DATE_FORMAT_VID));
                    btnOperation_Period.setText(ddate);
                    mIsDataChanged = true;
                    setOptionsMenuVisibility();
                    break;
                case DIALOG_REQUEST_CATEGORY:
                    WCategory category = (WCategory) data.getSerializableExtra(TAG_CATEGORY_ID);
                    mOperationItem.setCategory(category);
                    btnOperation_Category.setText(category.getName());
                    mIsDataChanged=true;
                    setOptionsMenuVisibility();
                    break;
            }
        }
    }
    View.OnClickListener onClick_Account_listener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            DialogFragment dlgPict = new Account_list_dialog();
            dlgPict.setTargetFragment(Operation_item_fragment.this,DIALOG_REQUEST_ACCOUNT);
            dlgPict.show(getFragmentManager(),dlgPict.getClass().getName());

        }
    };
    View.OnClickListener onClick_Period_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment dateDialog = new DatePicker_dialog();
            dateDialog.setTargetFragment(Operation_item_fragment.this,DIALOG_REQUEST_DATE);
            dateDialog.show(getFragmentManager(),dateDialog.getClass().getName());
        }
    };
    View.OnClickListener onClick_Category_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment dialog = new Category_list_dialog();
            dialog.setTargetFragment(Operation_item_fragment.this,DIALOG_REQUEST_CATEGORY);
            Bundle args = new Bundle();
            args.putInt(TYPE_CATEGORY,1);
            dialog.setArguments(args);
            dialog.show(getFragmentManager(),dialog.getClass().getName());
        }
    };
    View.OnFocusChangeListener onFocusChanged_listener = new View.OnFocusChangeListener(){

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            Log.e("FOCUS",""+v.getId()+" = "+hasFocus);
            switch (v.getId()){
                case R.id.edOperation_Name:
                    if(edOperation_Name.length()<2){
                        edOperation_Name_layout.setErrorEnabled(true);
                        edOperation_Name_layout.setError("Не заполнено "+getResources().getString(R.string.operation_name_label)+" (min 2 символа)");
                    }else {
                        edOperation_Name_layout.setErrorEnabled(false);
                    }
                    break;
                case R.id.edOperation_summa:
                    if(edOperation_Summa.getText().toString().isEmpty()){
                        edOperation_Summa_layout.setErrorEnabled(true);
                        edOperation_Summa_layout.setError("Не заполнена "+getResources().getString(R.string.operation_summa_label));
                    }else {
                        edOperation_Summa_layout.setErrorEnabled(false);

                    }
                    break;
            }
        }
    };
}