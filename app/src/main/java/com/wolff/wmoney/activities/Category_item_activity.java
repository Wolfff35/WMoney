package com.wolff.wmoney.activities;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.wolff.wmoney.R;
import com.wolff.wmoney.fragments.category.Category_item_fragment;
import com.wolff.wmoney.model.WCategory;

public class Category_item_activity extends AppCompatActivity {

    private WCategory mCategoryItem;
    public static final String EXTRA_CATEGORY_ITEM = "CategoryItem";

    public static Intent newIntent(Context context, WCategory category){
        Intent intent = new Intent(context,Category_item_activity.class);
        intent.putExtra(EXTRA_CATEGORY_ITEM,category);
        return intent;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_item_activity);
        mCategoryItem = (WCategory)getIntent().getSerializableExtra(EXTRA_CATEGORY_ITEM);
        Category_item_fragment category_itemFragment = Category_item_fragment.newIntance(mCategoryItem);

        FragmentTransaction fragmentTransaction;
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.category_item_container, category_itemFragment);
        fragmentTransaction.commit();


    }

}
