package com.wolff.wmoney.fragments.category;

import android.app.Fragment;
import android.os.Bundle;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.wolff.wmoney.R;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WCategory;

/**
 * Created by wolff on 01.06.2017.
 */

public class Category_item_fragment extends Fragment {
    private static final String ARG_CATEGORY_ITEM = "WCategoryItem";
    private WCategory mCategoryItem;

    private boolean mIsNewItem;
    private boolean mIsEditable;
    private boolean mIsDataChanged;

    private Menu mOptionsMenu;

    EditText edCategoryItem_Name;
    EditText edCategoryItem_Describe;
    ToggleButton tbIsCredit;
    public Category_item_fragment() {
        // Required empty public constructor
    }

    public static Category_item_fragment newIntance(WCategory item){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY_ITEM,item);
        Category_item_fragment fragment = new Category_item_fragment();
        fragment.setArguments(args);
        return fragment;

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
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.category_item_fragment, container, false);
        edCategoryItem_Name = (EditText)view.findViewById(R.id.edCategoryItem_Name);
        edCategoryItem_Describe = (EditText)view.findViewById(R.id.edCategoryItem_Describe);
        tbIsCredit = (ToggleButton)view.findViewById(R.id.tbIsCredit);

        edCategoryItem_Name.setText(mCategoryItem.getName());
        edCategoryItem_Describe.setText(mCategoryItem.getDescribe());
        tbIsCredit.setChecked(mCategoryItem.isCredit());

        edCategoryItem_Name.addTextChangedListener(textChangedListener);
        edCategoryItem_Describe.addTextChangedListener(textChangedListener);
        tbIsCredit.setOnCheckedChangeListener(onCheckedListener);
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
                saveCategoryItem();
                break;
            }
            case R.id.action_delete: {
                deleteCategoryItem();
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
        edCategoryItem_Name.setEnabled(mIsEditable);
        edCategoryItem_Describe.setEnabled(mIsEditable);
        tbIsCredit.setEnabled(mIsEditable);
    }
    private void saveCategoryItem(){
        updateCategory();
        if (!isFillingOk()) return;
        if(mIsNewItem){
            DataLab.get(getContext()).category_add(mCategoryItem);
        }else {
            DataLab.get(getContext()).category_update(mCategoryItem);
        }
        getActivity().finish();
    }
    private boolean isFillingOk() {
        if(mCategoryItem.getName().isEmpty()){
            Snackbar.make(getView(), "Не заполнено поле '"+getResources().getString(R.string.category_name_label)+"'", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return false;
        }
        return true;
    }

    private void deleteCategoryItem(){
        DataLab.get(getContext()).category_delete(mCategoryItem);
        getActivity().finish();
    }
    private void updateCategory(){
        mCategoryItem.setName(edCategoryItem_Name.getText().toString());
        mCategoryItem.setDescribe(edCategoryItem_Describe.getText().toString());
        mCategoryItem.setCredit(tbIsCredit.isChecked());
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
    CompoundButton.OnCheckedChangeListener onCheckedListener = new CompoundButton.OnCheckedChangeListener(){

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mIsDataChanged=true;
            setOptionsMenuVisibility();

        }
    };
}
