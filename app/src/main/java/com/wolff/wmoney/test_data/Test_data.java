package com.wolff.wmoney.test_data;

import android.content.Context;

import com.wolff.wmoney.R;
import com.wolff.wmoney.localdb.DataLab;
import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WCategory;
import com.wolff.wmoney.model.WCredit;
import com.wolff.wmoney.model.WCurrency;

import java.util.Date;

/**
 * Created by wolff on 23.05.2017.
 */

public class Test_data {
    public void fillTestData(Context context){
        DataLab dataLab = DataLab.get(context);
        WCredit credit1 = new WCredit();
        credit1.setName("расход 1");
        credit1.setCategory(dataLab.fingCategoryById(1,dataLab.getWCategoryList(0)));
        credit1.setCurrency(dataLab.fingCurrencyById(1,dataLab.getWCurrencyList()));
        credit1.setSumma(1000);
        credit1.setAccount(dataLab.fingAccountById(1,dataLab.getWAccountList(context)));
        credit1.setDateOper(new Date());
        credit1.setSummaVal(1000);
        credit1.setDateCreation(new Date());

        WCredit credit2 = new WCredit();
        credit2.setName("расход 2");
        credit2.setCategory(dataLab.fingCategoryById(1,dataLab.getWCategoryList(0)));
        credit2.setCurrency(dataLab.fingCurrencyById(1,dataLab.getWCurrencyList()));
        credit2.setSumma(567);
        credit2.setAccount(dataLab.fingAccountById(1,dataLab.getWAccountList(context)));
        credit2.setDateOper(new Date());
        credit2.setSummaVal(8985);
        credit2.setDateCreation(new Date());


        //    WCurrency curr = new WCurrency();
    //    curr.setName("UAH");
    //    curr.setDescribe("Гривна");
    //    dataLab.currency_add(curr);
    //    WCurrency curr2 = new WCurrency();
    //    curr2.setName("USD");
    //    curr2.setDescribe("Доллар");
    //    dataLab.currency_add(curr2);

 /*       WAccount acc1 = new WAccount();
        acc1.setIdPicture(R.drawable.pict_account_1);
        acc1.setSumma(12345);
        acc1.setCurrency(dataLab.fingCurrencyById(1,DataLab.get(context).getWCurrencyList()));
        acc1.setName("Кошелек 1");
        acc1.setDescribe("Карман");
        acc1.setDateCreation(new Date());
        dataLab.account_add(acc1);
        WAccount acc2 = new WAccount();
        acc2.setIdPicture(R.drawable.pict_account_1);
        acc2.setSumma(100000);
        acc2.setCurrency(dataLab.fingCurrencyById(1,DataLab.get(context).getWCurrencyList()));
        acc2.setName("Кошелек 2");
        acc2.setDescribe("Бановская карта");
        acc2.setDateCreation(new Date());
        dataLab.account_add(acc2);
        */
  /*      WCategory category1 = new WCategory();
        category1.setName("Category 1");
        category1.setDescribe("Desc category 1");
        dataLab.category_add(category1);
        WCategory category2 = new WCategory();
        category2.setName("Category 2");
        category2.setDescribe("Desc category 2");
        category2.setCredit(true);
        dataLab.category_add(category2);
        WCategory category3 = new WCategory();
        category3.setName("Category 3");
        category3.setDescribe("Desc category 3");
        dataLab.category_add(category3);
*/
    }

}
