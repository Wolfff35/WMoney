package com.wolff.wmoney.localdb;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WCategory;
import com.wolff.wmoney.model.WCurrency;

/**
 * Created by wolff on 23.05.2017.
 */

public class DbCursorWrapper extends CursorWrapper {

      public DbCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public WCurrency getWCurrency(){
        String s_id = getString(getColumnIndex(DbSchema.BaseColumns.ID));
        String s_name = getString(getColumnIndex(DbSchema.BaseColumns.NAME));
        String s_describe = getString(getColumnIndex(DbSchema.BaseColumns.DESCRIBE));
        WCurrency currency = new WCurrency();
        currency.setId(s_id);
        currency.setName(s_name);
        currency.setDescribe(s_describe);
        return currency;
     }
    public WCategory getWCategory(){
        String s_id = getString(getColumnIndex(DbSchema.BaseColumns.ID));
        String s_name = getString(getColumnIndex(DbSchema.BaseColumns.NAME));
        String s_describe = getString(getColumnIndex(DbSchema.BaseColumns.DESCRIBE));
        boolean isCred = (getInt(getColumnIndex(DbSchema.Table_Category.Cols.ISCREDIT))==1);
        WCategory category = new WCategory();
        category.setId(s_id);
        category.setName(s_name);
        category.setDescribe(s_describe);
        category.setCredit(isCred);
        return category;
    }
    public WAccount getWAccount(){
        String s_id = getString(getColumnIndex(DbSchema.BaseColumns.ID));
        String s_name = getString(getColumnIndex(DbSchema.BaseColumns.NAME));
        String s_describe = getString(getColumnIndex(DbSchema.BaseColumns.DESCRIBE));

        int id_pict = getInt(getColumnIndex(DbSchema.Table_Account.Cols.ID_PICTURE));
        int id_curr = getInt(getColumnIndex(DbSchema.Table_Account.Cols.ID_CURRENCY));
        double sum = getDouble(getColumnIndex(DbSchema.Table_Account.Cols.SUMMA));
        WAccount account = new WAccount();
        account.setId(s_id);
        account.setName(s_name);
        account.setDescribe(s_describe);
        account.setIdPicture(id_pict);
        account.setSumma(sum);
        //account.setCurrency(); TODO getting currency
        return account;
    }
}
