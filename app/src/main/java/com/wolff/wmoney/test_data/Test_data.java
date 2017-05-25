package com.wolff.wmoney.test_data;

import android.content.Context;

import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WCategory;
import com.wolff.wmoney.model.WCurrency;

/**
 * Created by wolff on 23.05.2017.
 */

public class Test_data {
    public void fillTestData(Context context){
        DataLab dataLab = DataLab.get(context);
        WCurrency curr = new WCurrency();
        curr.setName("UAH");
        curr.setDescribe("Гривна");
        dataLab.addWCurrencyToDb(curr);
        WCurrency curr2 = new WCurrency();
        curr2.setName("USD");
        curr2.setDescribe("Доллар");
        dataLab.addWCurrencyToDb(curr2);

 //       WCategory category1 = new WCategory();
 //       category1.setName("Category 1");
 //       category1.setDescribe("Desc category 1");
 //       dataLab.addCategory(category1);
    }

}
