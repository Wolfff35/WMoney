package com.wolff.wmoney.localdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WCategory;
import com.wolff.wmoney.model.WCurrency;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wolff on 23.05.2017.
 */

public class DataLab {
    private static DataLab sDataLab;

    private ArrayList<WCurrency> mWCurrencyList;
    private ArrayList<WCategory> mWCategoryDebitList;
    private ArrayList<WCategory> mWCategoryCreditList;
    private ArrayList<WAccount> mWAccountList;

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
        return new DbCursorWrapper(cursor);
    }
    public ArrayList<WCurrency> getWCurrencyList(){
        DbCursorWrapper cursorWrapper = queryWCurrency();
        mWCurrencyList = new ArrayList<>();
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                mWCurrencyList.add(cursorWrapper.getWCurrency());
                Log.e("getWCurrencyList","added = "+cursorWrapper.getWCurrency().getName());
                cursorWrapper.moveToNext();
            }
            cursorWrapper.close();
    return mWCurrencyList;
    }
    public void addWCurrencyToDb(WCurrency currency){
        ContentValues values = getContentValues_WCurrency(currency);
        mDatabase.insert(DbSchema.Table_Currency.TABLE_NAME,null,values);
    }
    private static ContentValues getContentValues_WCurrency(WCurrency currency){
        ContentValues values = new ContentValues();
        //values.put(DbSchema.BaseColumns.ID,currency.getId());
        values.put(DbSchema.BaseColumns.NAME,currency.getName());
        values.put(DbSchema.BaseColumns.DESCRIBE,currency.getDescribe());
        values.put(DbSchema.BaseColumns.DATE_CREATION,new Date().getTime());
        return values;
    }
    public WCurrency fingCurrencyById(double idCurr){
        for (WCurrency item:mWCurrencyList) {
            if(item.getId()==idCurr){
                return item;
            }
        }
        return null;
    }
}
