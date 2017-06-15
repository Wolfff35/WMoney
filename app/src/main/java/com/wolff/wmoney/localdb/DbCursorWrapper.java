package com.wolff.wmoney.localdb;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;

import com.wolff.wmoney.Utils.DateUtils;
import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WCategory;
import com.wolff.wmoney.model.WOperation;
import com.wolff.wmoney.model.WCurrency;
import com.wolff.wmoney.model.WTransfer;

/**
 * Created by wolff on 23.05.2017.
 */

public class DbCursorWrapper extends CursorWrapper {

      public DbCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public WCurrency getWCurrency(){
        int s_id = getInt(getColumnIndex(DbSchema.BaseColumns.ID));
        String s_name = getString(getColumnIndex(DbSchema.BaseColumns.NAME));
        String s_describe = getString(getColumnIndex(DbSchema.BaseColumns.DESCRIBE));
        WCurrency currency = new WCurrency();
        currency.setId(s_id);
        currency.setName(s_name);
        currency.setDescribe(s_describe);
        currency.setDateCreation(new DateUtils().dateFromString(getString(getColumnIndex(DbSchema.BaseColumns.DATE_CREATION)),DateUtils.DATE_FORMAT_SAVE));
        return currency;
     }
    public WCategory getWCategory(int isCredit){
        //0 - all, 1 - credit,2 - debit
        int s_id = getInt(getColumnIndex(DbSchema.BaseColumns.ID));
        String s_name = getString(getColumnIndex(DbSchema.BaseColumns.NAME));
        String s_describe = getString(getColumnIndex(DbSchema.BaseColumns.DESCRIBE));
        boolean isCred = (getInt(getColumnIndex(DbSchema.Table_Category.Cols.ISCREDIT))==1);
        if((isCredit==0)|(isCred==(isCredit==1))){
            WCategory category = new WCategory();
            category.setId(s_id);
            category.setName(s_name);
            category.setDescribe(s_describe);
            category.setCredit(isCred);
            category.setDateCreation(new DateUtils().dateFromString(getString(getColumnIndex(DbSchema.BaseColumns.DATE_CREATION)),DateUtils.DATE_FORMAT_SAVE));
            return category;
        }
        return null;
    }
    public WAccount getWAccount(Context context){
        int s_id = getInt(getColumnIndex(DbSchema.BaseColumns.ID));
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
        account.setDateCreation(new DateUtils().dateFromString(getString(getColumnIndex(DbSchema.BaseColumns.DATE_CREATION)),DateUtils.DATE_FORMAT_SAVE));
        DataLab dataLab = DataLab.get(context);
        account.setCurrency(dataLab.fingCurrencyById(id_curr,DataLab.get(context).getWCurrencyList()));
        return account;
    }
    public WOperation getWOperation(Context context){
        int s_id = getInt(getColumnIndex(DbSchema.BaseColumns.ID));
        String s_name = getString(getColumnIndex(DbSchema.BaseColumns.NAME));
        String s_describe = getString(getColumnIndex(DbSchema.BaseColumns.DESCRIBE));

        int id_acc = getInt(getColumnIndex(DbSchema.Table_OperDebCred.Cols.ID_ACCOUNT));
        int id_cat = getInt(getColumnIndex(DbSchema.Table_OperDebCred.Cols.ID_CATEGORY));
        double sum = getDouble(getColumnIndex(DbSchema.Table_OperDebCred.Cols.SUMMA));
        String datOperS = getString(getColumnIndex(DbSchema.Table_OperDebCred.Cols.DATE_OPER));
        WOperation credit = new WOperation();
        credit.setId(s_id);
        credit.setName(s_name);
        credit.setDescribe(s_describe);
        credit.setSumma(sum);
        DateUtils dateUtils = new DateUtils();
        credit.setDateOper(dateUtils.dateFromString(datOperS,DateUtils.DATE_FORMAT_SAVE));
        credit.setDateCreation(new DateUtils().dateFromString(getString(getColumnIndex(DbSchema.BaseColumns.DATE_CREATION)),DateUtils.DATE_FORMAT_SAVE));
        DataLab dataLab = DataLab.get(context);
        credit.setAccount(dataLab.fingAccountById(id_acc,DataLab.get(context).getWAccountList(context)));
        credit.setCategory(dataLab.fingCategoryById(id_cat,DataLab.get(context).getWCategoryList(0)));
        return credit;
    }
    public WTransfer getWTransfer(Context context){
        int s_id = getInt(getColumnIndex(DbSchema.BaseColumns.ID));
        String s_name = getString(getColumnIndex(DbSchema.BaseColumns.NAME));
        String s_describe = getString(getColumnIndex(DbSchema.BaseColumns.DESCRIBE));

        int id_accFrom = getInt(getColumnIndex(DbSchema.Table_Transfer.Cols.ID_ACCOUNT_FROM));
        int id_accTo = getInt(getColumnIndex(DbSchema.Table_Transfer.Cols.ID_ACCOUNT_TO));
        double sum_from = getDouble(getColumnIndex(DbSchema.Table_Transfer.Cols.SUMMA_FROM));
        double sum_to = getDouble(getColumnIndex(DbSchema.Table_Transfer.Cols.SUMMA_TO));
        String datOperS = getString(getColumnIndex(DbSchema.Table_Transfer.Cols.DATE_OPER));
        WTransfer transfer = new WTransfer();
        transfer.setId(s_id);
        transfer.setName(s_name);
        transfer.setDescribe(s_describe);
        transfer.setSummaFrom(sum_from);
        transfer.setSummaTo(sum_to);
        DateUtils dateUtils = new DateUtils();
        transfer.setDateOper(dateUtils.dateFromString(datOperS,DateUtils.DATE_FORMAT_SAVE));
        transfer.setDateCreation(new DateUtils().dateFromString(getString(getColumnIndex(DbSchema.BaseColumns.DATE_CREATION)),DateUtils.DATE_FORMAT_SAVE));
        DataLab dataLab = DataLab.get(context);
        transfer.setAccountFrom(dataLab.fingAccountById(id_accFrom,DataLab.get(context).getWAccountList(context)));
        transfer.setAccountTo(dataLab.fingAccountById(id_accTo,DataLab.get(context).getWAccountList(context)));
        return transfer;
    }
}
