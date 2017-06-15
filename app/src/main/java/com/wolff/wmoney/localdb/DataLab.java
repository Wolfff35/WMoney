package com.wolff.wmoney.localdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wolff.wmoney.Utils.DateUtils;
import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WCategory;
import com.wolff.wmoney.model.WOperation;
import com.wolff.wmoney.model.WCurrency;
import com.wolff.wmoney.model.WTransfer;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wolff on 23.05.2017.
 */

public class DataLab {
    private static DataLab sDataLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    private DataLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new DbHelper(mContext).getWritableDatabase();
    }
    public static DataLab get(Context context){
        if(sDataLab==null){
            sDataLab = new DataLab(context);
        }
        return sDataLab;
    }
    //==============================================================================================
    private DbCursorWrapper queryWCurrency(){
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = mDatabase.query(DbSchema.Table_Currency.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);
       // Log.e("QUERY","=== QUERY");
        return new DbCursorWrapper(cursor);
    }
    public ArrayList<WCurrency> getWCurrencyList(){
        DbCursorWrapper cursorWrapper = queryWCurrency();
        ArrayList<WCurrency> currencyList = new ArrayList<>();
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                currencyList.add(cursorWrapper.getWCurrency());
                cursorWrapper.moveToNext();
            }
            cursorWrapper.close();
    return currencyList;
    }
    private static ContentValues getContentValues_WCurrency(WCurrency currency){
        ContentValues values = new ContentValues();
        //values.put(DbSchema.BaseColumns.ID,currency.getId());
        values.put(DbSchema.BaseColumns.NAME,currency.getName());
        values.put(DbSchema.BaseColumns.DESCRIBE,currency.getDescribe());
        if(currency.getDateCreation()==null) {
            values.put(DbSchema.BaseColumns.DATE_CREATION, new DateUtils().dateToString(new Date(),DateUtils.DATE_FORMAT_SAVE));
        }
        return values;
    }
    public WCurrency fingCurrencyById(double idCurr,ArrayList<WCurrency> currencyList){

        for (WCurrency item:currencyList) {
            if(item.getId()==idCurr){
                return item;
            }
        }
        return null;
    }
    public void currency_add(WCurrency currency){
        ContentValues values = getContentValues_WCurrency(currency);
        mDatabase.insert(DbSchema.Table_Currency.TABLE_NAME,null,values);
        Log.e("add currency","Success");
    }
    public void currency_update(WCurrency currency){
        ContentValues values = getContentValues_WCurrency(currency);
        mDatabase.update(
                DbSchema.Table_Currency.TABLE_NAME,
                values,
                DbSchema.BaseColumns.ID+" = ?",
                new String[]{String.valueOf(currency.getId())}
        );
        Log.e("update currency"," Success");
    }
    public void currency_delete(WCurrency currency){
        mDatabase.delete(
                DbSchema.Table_Currency.TABLE_NAME,
                DbSchema.BaseColumns.ID+" =?",
                new String[]{String.valueOf(currency.getId())}
        );
        Log.e("delete currency","Success");
    }
    //==============================================================================================
    private DbCursorWrapper queryWAccount(){
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        Cursor cursor = mDatabase.query(DbSchema.Table_Account.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);
        //Log.e("QUERY","=== QUERY");
        return new DbCursorWrapper(cursor);
    }
    public ArrayList<WAccount> getWAccountList(Context context){
        DbCursorWrapper cursorWrapper = queryWAccount();
        ArrayList<WAccount>accountList = new ArrayList<>();
        cursorWrapper.moveToFirst();
        while (!cursorWrapper.isAfterLast()) {
            accountList.add(cursorWrapper.getWAccount(context));
            cursorWrapper.moveToNext();
        }
        cursorWrapper.close();
        return accountList;
    }
    private static ContentValues getContentValues_WAccount(WAccount account){
        ContentValues values = new ContentValues();
        //values.put(DbSchema.BaseColumns.ID,currency.getId());
        values.put(DbSchema.BaseColumns.NAME,account.getName());
        values.put(DbSchema.BaseColumns.DESCRIBE,account.getDescribe());
        values.put(DbSchema.Table_Account.Cols.ID_CURRENCY,account.getCurrency().getId());
        values.put(DbSchema.Table_Account.Cols.ID_PICTURE,account.getIdPicture());
        values.put(DbSchema.Table_Account.Cols.SUMMA,account.getSumma());
        if(account.getDateCreation()==null) {
            values.put(DbSchema.BaseColumns.DATE_CREATION, new DateUtils().dateToString(new Date(),DateUtils.DATE_FORMAT_SAVE));
        }
        return values;
    }
    public WAccount fingAccountById(double idAcc,ArrayList<WAccount>accountList){
        for (WAccount item:accountList) {
            if(item.getId()==idAcc){
                return item;
            }
        }
        return null;
    }
    public void account_add(WAccount account){
        ContentValues values = getContentValues_WAccount(account);
        mDatabase.insert(DbSchema.Table_Account.TABLE_NAME,null,values);
        Log.e("add account","Success");
    }
    public void account_update(WAccount account){
        ContentValues values = getContentValues_WAccount(account);
        mDatabase.update(
                DbSchema.Table_Account.TABLE_NAME,
                values,
                DbSchema.BaseColumns.ID+" = ?",
                new String[]{String.valueOf(account.getId())}
        );
        Log.e("update account"," Success");
    }
    public void account_delete(WAccount account){
        mDatabase.delete(
                DbSchema.Table_Account.TABLE_NAME,
                DbSchema.BaseColumns.ID+" =?",
                new String[]{String.valueOf(account.getId())}
        );
        Log.e("delete account","Success");
    }
//==================================================================================================
private DbCursorWrapper queryWCategory(int isCredit){
    //0 - all, 1 - credit,2 - debit
    String selection;
    String[] selectionArgs;
    String[] columns = null;
    String groupBy = null;
    String having = null;
    String orderBy = null;
    if(isCredit==0) {
        selection = null;
        selectionArgs = null;
        Log.e("SELECTION","category = all");
    }else if (isCredit==1){
        selection = DbSchema.Table_Category.Cols.ISCREDIT+" = ?";
        selectionArgs = new String[]{"1"};
        Log.e("SELECTION","category = CREDIT");
    }else if (isCredit==2){
        selection = DbSchema.Table_Category.Cols.ISCREDIT+" = ?";
        selectionArgs = new String[]{"0"};
        Log.e("SELECTION","category = DEBIT");
    }else {
        selection = null;
        selectionArgs = null;
        Log.e("SELECTION","category = all");
    }

    Cursor cursor = mDatabase.query(DbSchema.Table_Category.TABLE_NAME,
            columns,
            selection,
            selectionArgs,
            groupBy,
            having,
            orderBy);
    return new DbCursorWrapper(cursor);
}
    public ArrayList<WCategory> getWCategoryList(int isCredit){
        DbCursorWrapper cursorWrapper = queryWCategory(isCredit);
        ArrayList<WCategory> categoryList = new ArrayList<>();
        cursorWrapper.moveToFirst();
        while (!cursorWrapper.isAfterLast()) {
            categoryList.add(cursorWrapper.getWCategory(isCredit));
            cursorWrapper.moveToNext();
        }
        cursorWrapper.close();
        return categoryList;
    }
    private static ContentValues getContentValues_WCategory(WCategory category){
        ContentValues values = new ContentValues();
        values.put(DbSchema.BaseColumns.NAME,category.getName());
        values.put(DbSchema.BaseColumns.DESCRIBE,category.getDescribe());
        values.put(DbSchema.Table_Category.Cols.ISCREDIT,((category.isCredit()?1:0)));
        if(category.getDateCreation()==null) {
            values.put(DbSchema.BaseColumns.DATE_CREATION, new DateUtils().dateToString(new Date(),DateUtils.DATE_FORMAT_SAVE));
        }
        return values;
    }
    public WCategory fingCategoryById(double idCategory,ArrayList<WCategory> categoryList){

        for (WCategory item:categoryList) {
            if(item.getId()==idCategory){
                return item;
            }
        }
        return null;
    }
    public void category_add(WCategory category){
        ContentValues values = getContentValues_WCategory(category);
        mDatabase.insert(DbSchema.Table_Category.TABLE_NAME,null,values);
        Log.e("add category","Success");
    }
    public void category_update(WCategory category){
        ContentValues values = getContentValues_WCategory(category);
        mDatabase.update(
                DbSchema.Table_Category.TABLE_NAME,
                values,
                DbSchema.BaseColumns.ID+" = ?",
                new String[]{String.valueOf(category.getId())}
        );
        Log.e("update category"," Success");
    }
    public void category_delete(WCategory category){
        mDatabase.delete(
                DbSchema.Table_Category.TABLE_NAME,
                DbSchema.BaseColumns.ID+" =?",
                new String[]{String.valueOf(category.getId())}
        );
        Log.e("delete category","Success");
    }
    //==================================================================================================
    private DbCursorWrapper queryWOperation(int typeOperation){
       String selection=null;
        String[] selectionArgs = null;
        String[] columns = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String table = getOperationTableNameByType(typeOperation);
         Cursor cursor = mDatabase.query(table,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);
        return new DbCursorWrapper(cursor);
    }
    public ArrayList<WOperation> getWOperationList(Context context, int typeOperation){
        DbCursorWrapper cursorWrapper = queryWOperation(typeOperation);
        ArrayList<WOperation> creditList = new ArrayList<>();
        cursorWrapper.moveToFirst();
        while (!cursorWrapper.isAfterLast()) {
            creditList.add(cursorWrapper.getWOperation(context));
            cursorWrapper.moveToNext();
        }
        cursorWrapper.close();
        return creditList;
    }
    private static ContentValues getContentValues_WOperation(WOperation credit){
        DateUtils dateUtils = new DateUtils();
        ContentValues values = new ContentValues();
        values.put(DbSchema.BaseColumns.NAME,credit.getName());
        values.put(DbSchema.BaseColumns.DESCRIBE,credit.getDescribe());
        WAccount lAccount = credit.getAccount();
        if(lAccount!=null) {
            values.put(DbSchema.Table_OperDebCred.Cols.ID_ACCOUNT, lAccount.getId());
            //WCurrency lCurrency = credit.getAccount().getCurrency();
            //if(lCurrency!=null) {
            //    values.put(DbSchema.Table_OperDebCred.Cols.ID_CURRENCY, lCurrency.getId());
            //}
        }
          WCategory lCategory = credit.getCategory();
        if(lCategory!=null) {
            values.put(DbSchema.Table_OperDebCred.Cols.ID_CATEGORY, lCategory.getId());
        }
        values.put(DbSchema.Table_OperDebCred.Cols.SUMMA,credit.getSumma());
        values.put(DbSchema.Table_OperDebCred.Cols.DATE_OPER,dateUtils.dateToString(credit.getDateOper(),DateUtils.DATE_FORMAT_SAVE));

        if(credit.getDateCreation()==null) {
            values.put(DbSchema.BaseColumns.DATE_CREATION,dateUtils.dateToString(new Date(),DateUtils.DATE_FORMAT_SAVE));
        }
        return values;
    }
    public WOperation fingCreditById(double idCredit, ArrayList<WOperation> creditList){

        for (WOperation item:creditList) {
            if(item.getId()==idCredit){
                return item;
            }
        }
        return null;
    }
    public void operation_add(WOperation oper,int typeOperation){
        ContentValues values = getContentValues_WOperation(oper);
        String table = getOperationTableNameByType(typeOperation);
        mDatabase.insert(table,null,values);
        Log.e("add oper","Success");
    }
    private String getOperationTableNameByType(int type){
        if(type==DbSchema.TYPE_OPERATION_CREDIT){
            return DbSchema.Table_Credit.TABLE_NAME;
        }else {
            return DbSchema.Table_Debit.TABLE_NAME;
        }
    }

    public void operation_update(WOperation oper,int typeOperation){
        ContentValues values = getContentValues_WOperation(oper);
        String table = getOperationTableNameByType(typeOperation);
        mDatabase.update(
                table,
                values,
                DbSchema.BaseColumns.ID+" = ?",
                new String[]{String.valueOf(oper.getId())}
        );
        Log.e("update oper"," Success");
    }
    public void operation_delete(WOperation oper, int typeOperation){
        String table = getOperationTableNameByType(typeOperation);
        mDatabase.delete(
                table,
                DbSchema.BaseColumns.ID+" =?",
                new String[]{String.valueOf(oper.getId())}
        );
        Log.e("delete oper","Success");
    }

    //==================================================================================================
    private DbCursorWrapper queryWTransfer(){
        String selection=null;
        String[] selectionArgs = null;
        String[] columns = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;
        String table = DbSchema.Table_Transfer.TABLE_NAME;
        Cursor cursor = mDatabase.query(table,
                columns,
                selection,
                selectionArgs,
                groupBy,
                having,
                orderBy);
        return new DbCursorWrapper(cursor);
    }
    public ArrayList<WTransfer> getWTransferList(Context context){
        DbCursorWrapper cursorWrapper = queryWTransfer();
        ArrayList<WTransfer> transferList = new ArrayList<>();
        cursorWrapper.moveToFirst();
        while (!cursorWrapper.isAfterLast()) {
            transferList.add(cursorWrapper.getWTransfer(context));
            cursorWrapper.moveToNext();
        }
        cursorWrapper.close();
        return transferList;
    }
    private static ContentValues getContentValues_WTransfer(WTransfer transfer){
        DateUtils dateUtils = new DateUtils();
        ContentValues values = new ContentValues();
        values.put(DbSchema.BaseColumns.NAME,transfer.getName());
        values.put(DbSchema.BaseColumns.DESCRIBE,transfer.getDescribe());
        WAccount lAccountFrom = transfer.getAccountFrom();
        if(lAccountFrom!=null) {
            values.put(DbSchema.Table_Transfer.Cols.ID_ACCOUNT_FROM, lAccountFrom.getId());
          }
        WAccount lAccountTo = transfer.getAccountTo();
        if(lAccountTo!=null) {
            values.put(DbSchema.Table_Transfer.Cols.ID_ACCOUNT_TO, lAccountTo.getId());
        }
        values.put(DbSchema.Table_Transfer.Cols.SUMMA_FROM,transfer.getSummaFrom());
        values.put(DbSchema.Table_Transfer.Cols.SUMMA_TO,transfer.getSummaTo());
        values.put(DbSchema.Table_Transfer.Cols.DATE_OPER,dateUtils.dateToString(transfer.getDateOper(),DateUtils.DATE_FORMAT_SAVE));

        if(transfer.getDateCreation()==null) {
            values.put(DbSchema.BaseColumns.DATE_CREATION,dateUtils.dateToString(new Date(),DateUtils.DATE_FORMAT_SAVE));
        }
        return values;
    }
     public void transfer_add(WTransfer transfer){
        ContentValues values = getContentValues_WTransfer(transfer);
        String table = DbSchema.Table_Transfer.TABLE_NAME;
        mDatabase.insert(table,null,values);
        Log.e("add transfer","Success");
    }
     public void transfer_update(WTransfer transfer){
        ContentValues values = getContentValues_WTransfer(transfer);
        String table = DbSchema.Table_Transfer.TABLE_NAME;
        mDatabase.update(
                table,
                values,
                DbSchema.BaseColumns.ID+" = ?",
                new String[]{String.valueOf(transfer.getId())}
        );
        Log.e("update transfer"," Success");
    }
    public void transfer_delete(WTransfer transfer){
        String table = DbSchema.Table_Transfer.TABLE_NAME;
        mDatabase.delete(
                table,
                DbSchema.BaseColumns.ID+" =?",
                new String[]{String.valueOf(transfer.getId())}
        );
        Log.e("delete transfer","Success");
    }



}
