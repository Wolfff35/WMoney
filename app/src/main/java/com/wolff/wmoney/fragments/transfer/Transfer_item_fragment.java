package com.wolff.wmoney.fragments.transfer;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.wolff.wmoney.fragments.misc.DatePicker_dialog;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WTransfer;

import java.util.Date;

import static com.wolff.wmoney.fragments.account.Account_list_dialog.TAG_ACCOUNT_ID;
import static com.wolff.wmoney.fragments.misc.DatePicker_dialog.TAG_PERIOD_ID;

/**
 * Created by wolff on 14.06.2017.
 */

public class Transfer_item_fragment extends Item_fragment {
    public static final int DIALOG_REQUEST_ACCOUNT_FROM = 1;
    public static final int DIALOG_REQUEST_ACCOUNT_TO = 3;
    public static final int DIALOG_REQUEST_DATE = 2;

    private static final String ARG_TRANSFER_ITEM = "WTransItem";
    private WTransfer mTransferItem;

    EditText edTransfer_Name;
    EditText edTransfer_Describe;
    EditText edTransfer_SummaFrom;
    EditText edTransfer_SummaTo;
    EditText edTransfer_course;

    Button btnTransfer_AccountFrom;
    Button btnTransfer_AccountTo;
    ImageView ivAccountFromPicture;
    ImageView ivAccountToPicture;
    TextView tvTransfer_CurrencyFrom;
    TextView tvTransfer_CurrencyTo;
    Button btnTransfer_Period;
    TextInputLayout edTransfer_Name_layout;
    TextInputLayout edTransfer_SummaFrom_layout;
    TextInputLayout edTransfer_SummaTo_layout;

    public static Transfer_item_fragment newIntance(WTransfer item){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRANSFER_ITEM,item);
        Transfer_item_fragment fragment = new Transfer_item_fragment();
        fragment.setArguments(args);
        return fragment;

    }
    public Transfer_item_fragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTransferItem = (WTransfer)getArguments().getSerializable(ARG_TRANSFER_ITEM);
        if(mTransferItem ==null){
            mTransferItem = new WTransfer();
            mIsNewItem=true;
            mIsEditable=true;
            mTransferItem.setDateOper(new Date());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.transfer_item_fragment, container, false);
        edTransfer_Name = (EditText)view.findViewById(R.id.edTransfer_Name);
        edTransfer_Describe = (EditText)view.findViewById(R.id.edTransfer_describe);
        edTransfer_SummaFrom = (EditText)view.findViewById(R.id.edTransfer_summaFrom);
        edTransfer_SummaTo = (EditText)view.findViewById(R.id.edTransfer_summaTo);
        edTransfer_course = (EditText)view.findViewById(R.id.edTransfer_course);
        btnTransfer_AccountFrom = (Button)view.findViewById(R.id.imTransfer_accountFrom);
        btnTransfer_AccountTo = (Button)view.findViewById(R.id.imTransfer_accountTo);
        ivAccountFromPicture = (ImageView) view.findViewById(R.id.ivAccountFromPicture);
        ivAccountToPicture = (ImageView) view.findViewById(R.id.ivAccountToPicture);
        tvTransfer_CurrencyFrom = (TextView) view.findViewById(R.id.tvTransfer_currencyFrom);
        tvTransfer_CurrencyTo = (TextView) view.findViewById(R.id.tvTransfer_currencyTo);
        btnTransfer_Period = (Button) view.findViewById(R.id.imTransfer_Period);
        edTransfer_Name_layout = (TextInputLayout)view.findViewById(R.id.edTransfer_Name_layout);
        edTransfer_SummaFrom_layout = (TextInputLayout)view.findViewById(R.id.edTransfer_summaFrom_layout);
        edTransfer_SummaTo_layout = (TextInputLayout)view.findViewById(R.id.edTransfer_summaTo_layout);
        setFieldsVisibility();
        super.onCreateView(inflater,container,savedInstanceState);
        return view;
    }

    @Override
    public void fields_fillData() {
        super.fields_fillData();
        edTransfer_Name.setText(mTransferItem.getName());
        edTransfer_Describe.setText(mTransferItem.getDescribe());
        edTransfer_SummaFrom.setText(String.format("%.2f", mTransferItem.getSummaFrom()));
        edTransfer_SummaTo.setText(String.format("%.2f", mTransferItem.getSummaTo()));
        edTransfer_course.setText(setCource());
        if(mTransferItem.getAccountFrom()!=null){
            btnTransfer_AccountFrom.setText(mTransferItem.getAccountFrom().getName());
            ivAccountFromPicture.setImageResource(mTransferItem.getAccountFrom().getIdPicture());
            tvTransfer_CurrencyFrom.setText(mTransferItem.getAccountFrom().getCurrency().getName());
        }else {
            btnTransfer_AccountFrom.setText("");
        }
        if(mTransferItem.getAccountTo()!=null){
            btnTransfer_AccountTo.setText(mTransferItem.getAccountTo().getName());
            ivAccountToPicture.setImageResource(mTransferItem.getAccountTo().getIdPicture());
            tvTransfer_CurrencyTo.setText(mTransferItem.getAccountTo().getCurrency().getName());
        }else {
            btnTransfer_AccountTo.setText("");
        }
        DateUtils dateUtils = new DateUtils();
        btnTransfer_Period.setText(dateUtils.dateToString(mTransferItem.getDateOper(),DateUtils.DATE_FORMAT_VID));

    }

    @Override
    public void fields_setListeners() {
        super.fields_setListeners();
        btnTransfer_AccountFrom.setOnClickListener(onClick_Account_listener);
        btnTransfer_AccountTo.setOnClickListener(onClick_Account_listener);
        btnTransfer_Period.setOnClickListener(onClick_Period_listener);
        edTransfer_Name.addTextChangedListener(textChangedListener);
        edTransfer_Describe.addTextChangedListener(textChangedListener);
        edTransfer_SummaFrom.addTextChangedListener(textChangedListenerSumFrom);
        edTransfer_SummaTo.addTextChangedListener(textChangedListenerSumTo);
        edTransfer_Name.setOnFocusChangeListener(onFocusChanged_listener);
        edTransfer_course.addTextChangedListener(textChangedListenerCourse);
        edTransfer_SummaFrom.setOnFocusChangeListener(onFocusChanged_listener);
        edTransfer_SummaTo.setOnFocusChangeListener(onFocusChanged_listener);
    }

    @Override
    public void setFieldsVisibility() {
        super.setFieldsVisibility();
        edTransfer_Name.setEnabled(mIsEditable);
        edTransfer_Describe.setEnabled(mIsEditable);
        edTransfer_SummaFrom.setEnabled(mIsEditable);
        edTransfer_SummaTo.setEnabled(mIsEditable);
        btnTransfer_AccountFrom.setEnabled(mIsEditable);
        btnTransfer_AccountTo.setEnabled(mIsEditable);
        btnTransfer_Period.setEnabled(mIsEditable);
        setFieldsAccess();
    }
    private void setFieldsAccess(){
        boolean isAcc=!compareCurrencys();
        edTransfer_SummaTo.setEnabled(isAcc&&mIsEditable);
        //edTransfer_course.setEnabled(isAcc&&mIsEditable);
        edTransfer_course.setEnabled(false);

    }
    private boolean compareCurrencys() {
        if (mTransferItem.getAccountFrom() != null && mTransferItem.getAccountTo() != null) {
            if (mTransferItem.getAccountFrom().getCurrency().getId() == mTransferItem.getAccountTo().getCurrency().getId()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


    private String setCource(){
        double sumFrom,sumTo;
     try{
         sumFrom = Double.valueOf(edTransfer_SummaFrom.getText().toString());
     }catch (Exception e){
         sumFrom=0;
     }
     try{
         sumTo = Double.valueOf(edTransfer_SummaTo.getText().toString());
     }catch (Exception e){
         sumTo=0;
     }
     Log.e("COURSE","sumFrom = "+sumFrom+"; sumTo = "+sumTo);
     if(sumTo!=0){
        return String.format("%.5f", sumFrom/sumTo);
     }
     return "";
    }

    @Override
    public void saveItem() {
        super.saveItem();
        if (!isFillingOk()) return;
        if(mIsNewItem){
            DataLab.get(getContext()).transfer_add(mTransferItem);
        }else {
            DataLab.get(getContext()).transfer_update(mTransferItem);
        }
        getActivity().finish();
    }

    @Override
    public boolean isFillingOk() {
        super.isFillingOk();
        StringBuilder sb = new StringBuilder();
        boolean isOk=true;
        if(mTransferItem.getName().isEmpty()){
            isOk=false;
        }else {
            edTransfer_Name_layout.setErrorEnabled(false);
        }
        if(mTransferItem.getAccountFrom()==null){
            sb.append(getResources().getString(R.string.transfer_accountFrom_item)+",");
            isOk=false;
        }
        if(mTransferItem.getAccountTo()==null){
            sb.append(getResources().getString(R.string.transfer_accountTo_item));
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
        DataLab.get(getContext()).transfer_delete(mTransferItem);
        getActivity().finish();
    }

    @Override
    public void updateItemFields() {
        super.updateItemFields();
        mTransferItem.setName(edTransfer_Name.getText().toString());
        mTransferItem.setDescribe(edTransfer_Describe.getText().toString());
        if(edTransfer_SummaFrom.getText().length()>0){
            mTransferItem.setSummaFrom(Double.valueOf(edTransfer_SummaFrom.getText().toString()));
        }
        if(edTransfer_SummaTo.getText().length()>0){
            mTransferItem.setSummaTo(Double.valueOf(edTransfer_SummaTo.getText().toString()));
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        WAccount account;
        if(resultCode== Activity.RESULT_OK){
            switch (requestCode){
                case DIALOG_REQUEST_ACCOUNT_FROM:
                   // Log.e("DIALOG 1","DIALOG_REQUEST_ACCOUNT_FROM: resultCode = "+resultCode);
                    account = (WAccount) data.getSerializableExtra(TAG_ACCOUNT_ID);
                    mTransferItem.setAccountFrom(account);
                    btnTransfer_AccountFrom.setText(account.getName());
                    ivAccountFromPicture.setImageResource(account.getIdPicture());
                    tvTransfer_CurrencyFrom.setText(account.getCurrency().getName());
                    mIsDataChanged=true;
                    setOptionsMenuVisibility();
                    checkCurrencyAndSum();
                    break;
                case DIALOG_REQUEST_ACCOUNT_TO:
                   // Log.e("DIALOG 2","DIALOG_REQUEST_ACCOUNT_TO: resultCode = "+resultCode);
                    account = (WAccount) data.getSerializableExtra(TAG_ACCOUNT_ID);
                    mTransferItem.setAccountTo(account);
                    btnTransfer_AccountTo.setText(account.getName());
                    ivAccountToPicture.setImageResource(account.getIdPicture());
                    tvTransfer_CurrencyTo.setText(account.getCurrency().getName());
                    mIsDataChanged=true;
                    setOptionsMenuVisibility();
                    checkCurrencyAndSum();
                    break;
                case DIALOG_REQUEST_DATE:
                    DateUtils dateUtils = new DateUtils();
                    String ddate = data.getStringExtra(TAG_PERIOD_ID);
                    mTransferItem.setDateOper(dateUtils.dateFromString(ddate,DateUtils.DATE_FORMAT_VID));
                    btnTransfer_Period.setText(ddate);
                    mIsDataChanged = true;
                    setOptionsMenuVisibility();
                    break;
            }
        }
    }
private void checkCurrencyAndSum(){
    if(compareCurrencys()){
        edTransfer_SummaTo.setText(edTransfer_SummaFrom.getText());
    }
    //edTransfer_course.setText(setCource());
    setFieldsAccess();
}
    View.OnClickListener onClick_Account_listener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            DialogFragment dlgPict = new Account_list_dialog();
            if(v.getId()==R.id.imTransfer_accountFrom) {
                dlgPict.setTargetFragment(Transfer_item_fragment.this, DIALOG_REQUEST_ACCOUNT_FROM);
            }else {
                dlgPict.setTargetFragment(Transfer_item_fragment.this,DIALOG_REQUEST_ACCOUNT_TO);
           }
            dlgPict.show(getFragmentManager(),dlgPict.getClass().getName());

        }
    };
    View.OnClickListener onClick_Period_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DialogFragment dateDialog = new DatePicker_dialog();
            dateDialog.setTargetFragment(Transfer_item_fragment.this,DIALOG_REQUEST_DATE);
            dateDialog.show(getFragmentManager(),dateDialog.getClass().getName());
        }
    };
     View.OnFocusChangeListener onFocusChanged_listener = new View.OnFocusChangeListener(){

        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            Log.e("FOCUS",""+v.getId()+" = "+hasFocus);
            switch (v.getId()){
                case R.id.edTransfer_Name:
                    if(edTransfer_Name.length()<2){
                        edTransfer_Name_layout.setErrorEnabled(true);
                        edTransfer_Name_layout.setError("Не заполнено "+getResources().getString(R.string.transfer_name_label)+" (min 2 символа)");
                    }else {
                        edTransfer_Name_layout.setErrorEnabled(false);
                    }
                    break;
                case R.id.edTransfer_summaFrom:
                    if(edTransfer_SummaFrom.getText().toString().isEmpty()){
                        edTransfer_SummaFrom_layout.setErrorEnabled(true);
                        edTransfer_SummaFrom_layout.setError("Не заполнена "+getResources().getString(R.string.transfer_summaFrom_label));
                    }else {
                        edTransfer_SummaFrom_layout.setErrorEnabled(false);

                    }
                    break;
                case R.id.edTransfer_summaTo:
                    if(edTransfer_SummaTo.getText().toString().isEmpty()){
                        edTransfer_SummaTo_layout.setErrorEnabled(true);
                        edTransfer_SummaTo_layout.setError("Не заполнена "+getResources().getString(R.string.transfer_summaTo_label));
                    }else {
                        edTransfer_SummaTo_layout.setErrorEnabled(false);

                    }
                    break;
            }
        }
    };

    public TextWatcher textChangedListenerSumFrom = new TextWatcher() {
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
            if(compareCurrencys()){
                edTransfer_SummaTo.setText(s.toString());

            }
            edTransfer_course.setText(setCource());
        }
    };
    public TextWatcher textChangedListenerSumTo = new TextWatcher() {
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
            edTransfer_course.setText(setCource());
        }
    };
    public TextWatcher textChangedListenerCourse = new TextWatcher() {
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
            //double sumFrom = Double.valueOf(edTransfer_SummaFrom.getText().toString());
            //double course = Double.valueOf(edTransfer_course.getText().toString());
            //if(course!=0){
            //    edTransfer_SummaTo.setText(String.format("%.2f",sumFrom/course));
            //}else {
            //    edTransfer_SummaTo.setText("0.00");
            //}
        }
    };
}