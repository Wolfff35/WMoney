package com.wolff.wmoney.localdb;

import android.provider.BaseColumns;

/**
 * Created by wolff on 23.05.2017.
 */

public class DbSchema {
    public static final String DATABASE_NAME = "wmoney.db";

    public static final String CREATE_CURRENCY_TABLE = "CREATE TABLE "+Table_Currency.TABLE_NAME+" ("+
            BaseColumns.ID+" INTEGER PRIMARY KEY, "+
            BaseColumns.NAME+" TEXT,"+
            BaseColumns.DESCRIBE+ "TEXT )";

    public static final String CREATE_CATEGORY_TABLE = "CREATE TABLE "+Table_Category.TABLE_NAME+" ("+
            BaseColumns.ID+" INTEGER PRIMARY KEY, "+
            BaseColumns.NAME+" TEXT, "+
            BaseColumns.DESCRIBE+" TEXT, "+
            Table_Category.Cols.ISCREDIT+" INTEGER)";

    public static final String CREATE_ACCOUNTS_TABLE = "CREATE TABLE "+Table_Account.TABLE_NAME+" ("+
            BaseColumns.ID+" INTEGER PRIMARY KEY, "+
            BaseColumns.NAME+" TEXT, "+
            BaseColumns.DESCRIBE+" TEXT, "+
            Table_Account.Cols.ID_PICTURE+" INTEGER, "+
            Table_Account.Cols.ID_CURRENCY+" INTEGER, "+
            Table_Account.Cols.SUMMA+" REAL, FOREIGN KEY ("+Table_Account.Cols.ID_CURRENCY+") REFERENCES "+Table_Currency.TABLE_NAME+"("+BaseColumns.ID+"))";

    private static final String OPER_TABLE = " ("+
            BaseColumns.ID+" INTEGER PRIMARY KEY, "+
            BaseColumns.NAME+" TEXT, "+
            BaseColumns.DESCRIBE+" TEXT, "+
            Table_Oper.Cols.ID_ACCOUNT+" INTEGER, "+
            Table_Oper.Cols.ID_CURRENCY+" INTEGER, "+
            Table_Oper.Cols.ID_CATEGORY+" INTEGER, "+
            Table_Oper.Cols.SUMMA+" REAL, "+
            Table_Oper.Cols.DATE_OPER+" INTEGER, "+
            Table_Oper.Cols.DATE_OPER_STR+" TEXT, " +
                    "FOREIGN KEY ("+Table_Oper.Cols.ID_CATEGORY+") REFERENCES "+Table_Category.TABLE_NAME+"("+BaseColumns.ID+"), "+
                    "FOREIGN KEY ("+Table_Oper.Cols.ID_CURRENCY+") REFERENCES "+Table_Currency.TABLE_NAME+"("+BaseColumns.ID+"), " +
                    "FOREIGN KEY ("+Table_Oper.Cols.ID_ACCOUNT+") REFERENCES "+Table_Account.TABLE_NAME+"("+BaseColumns.ID+"))";

    public static final String CREATE_DEBIT_TABLE = "CREATE TABLE "+Table_Debit.TABLE_NAME+OPER_TABLE;
    public static final String CREATE_CREDIT_TABLE = "CREATE TABLE "+Table_Credit.TABLE_NAME+OPER_TABLE;

    //==================================================================================================
    public static final class BaseColumns{
        public static final String ID = "_id";
        public static final String NAME = "_name";
        public static final String DESCRIBE = "_describe";
}
     public static final class Table_Currency{

        public static final String TABLE_NAME = "table_currency";

    }
    public static final class Table_Category{

        public static final String TABLE_NAME = "table_category";

        public static final class Cols{
            public static final String ISCREDIT = "_isCredit";

        }
    }
    public static final class Table_Account{

        public static final String TABLE_NAME = "table_account";

        public static final class Cols{
            public static final String ID_PICTURE = "_idPicture";
            public static final String ID_CURRENCY = "_idCurrency";
            public static final String SUMMA = "_summa";
        }
    }
    public static final class Table_Debit{

        public static final String TABLE_NAME = "table_debit";

    }
    public static final class Table_Credit{

        public static final String TABLE_NAME = "table_credit";

    }
    public static final class Table_Oper{

        public static final class Cols {
            public static final String  ID_ACCOUNT= "_idAccount";
            public static final String ID_CURRENCY= "_idCurrency";
            public static final String ID_CATEGORY= "_idCategory";
            public static final String SUMMA = "_summa";
            public static final String DATE_OPER = "_date";
            public static final String DATE_OPER_STR = "_dateStr";
        }
    }

}
