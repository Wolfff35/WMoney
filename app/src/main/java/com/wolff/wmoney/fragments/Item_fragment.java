package com.wolff.wmoney.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.wolff.wmoney.R;

/**
 * Created by wolff on 07.06.2017.
 */

public abstract class Item_fragment extends Fragment {
    public boolean mIsNewItem;
    public boolean mIsEditable;
    public boolean mIsDataChanged;

    private Menu mOptionsMenu;

    public Item_fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setHasOptionsMenu(true);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fields_fillData();
        fields_setListeners();
    return null;
    }

    public void fields_fillData(){}
    public void fields_setListeners(){}


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
                saveItem();
                break;
            }
            case R.id.action_delete: {
                deleteItem();
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
    public void setOptionsMenuVisibility(){
        if(mOptionsMenu!=null){
            MenuItem it_save = mOptionsMenu.findItem(R.id.action_save);
            MenuItem it_del = mOptionsMenu.findItem(R.id.action_delete);
            MenuItem it_edit = mOptionsMenu.findItem(R.id.action_edit);
            it_edit.setVisible(!mIsEditable);
            it_save.setVisible(mIsEditable&&mIsDataChanged);
            it_del.setVisible(!mIsNewItem);
        }
    }
    public void setFieldsVisibility(){
    }
    public void saveItem(){
        updateItemFields();
    }
    public boolean isFillingOk() {
        return true;
    }

    public void deleteItem(){
    }
    public void updateItemFields(){
     }
    //==============================================================================================
    public TextWatcher textChangedListener = new TextWatcher() {
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
            Log.e("textChangedListener 1","afterTextChanged 1");
        }
    };
}
