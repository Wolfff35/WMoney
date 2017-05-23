package com.wolff.wmoney.localdb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.wolff.wmoney.model.WAccount;
import com.wolff.wmoney.model.WCategory;
import com.wolff.wmoney.model.WCurrency;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by wolff on 23.05.2017.
 */

public class DataLab {
    private static DataLab sDataLab;

    private ArrayList<WCurrency> mWCurrencyList;
    private ArrayList<WCategory> mWCategoryList;
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
        try {
            cursorWrapper.moveToFirst();
            while (!cursorWrapper.isAfterLast()) {
                mWCurrencyList.add(cursorWrapper.getWCurrency());
                cursorWrapper.moveToNext();
            }
        }catch (Exception e){

        }finally {
            cursorWrapper.close();
        }
    return mWCurrencyList;
    }
}
