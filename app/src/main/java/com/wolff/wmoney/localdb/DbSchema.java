package com.wolff.wmoney.localdb;

/**
 * Created by wolff on 23.05.2017.
 */

public class DbSchema {
    public static final int TYPE_OPERATION_DEBIT = 2;
    public static final int TYPE_OPERATION_CREDIT = 1;


    //==========================================================================================
    public static final String DATABASE_NAME = "wmoney.db";

    public static final String CREATE_CURRENCY_TABLE = "CREATE TABLE "+Table_Currency.TABLE_NAME+" ("+
            BaseColumns.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            BaseColumns.NAME+" TEXT, "+
            BaseColumns.DATE_CREATION+" TEXT, "+
            BaseColumns.DESCRIBE+ ")";

    public static final String CREATE_CATEGORY_TABLE = "CREATE TABLE "+Table_Category.TABLE_NAME+" ("+
            BaseColumns.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            BaseColumns.NAME+" TEXT, "+
            BaseColumns.DATE_CREATION+" TEXT, "+
            BaseColumns.DESCRIBE+" TEXT, "+
            Table_Category.Cols.ISCREDIT+" INTEGER) ";

    public static final String CREATE_ACCOUNTS_TABLE = "CREATE TABLE "+Table_Account.TABLE_NAME+" ("+
            BaseColumns.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            BaseColumns.NAME+" TEXT, "+
            BaseColumns.DATE_CREATION+" TEXT, "+
            BaseColumns.DESCRIBE+" TEXT, "+
            Table_Account.Cols.ID_PICTURE+" INTEGER, "+
            Table_Account.Cols.ID_CURRENCY+" INTEGER, "+
            Table_Account.Cols.SUMMA+" REAL, FOREIGN KEY ("+Table_Account.Cols.ID_CURRENCY+") REFERENCES "+Table_Currency.TABLE_NAME+"("+BaseColumns.ID+"))";

    private static final String OPER_TABLE = " ("+
            BaseColumns.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            BaseColumns.NAME+" TEXT, "+
            BaseColumns.DATE_CREATION+" TEXT, "+
            BaseColumns.DESCRIBE+" TEXT, "+
            Table_OperDebCred.Cols.ID_ACCOUNT+" INTEGER, "+
            Table_OperDebCred.Cols.ID_CATEGORY+" INTEGER, "+
            Table_OperDebCred.Cols.SUMMA+" REAL, "+
            Table_OperDebCred.Cols.DATE_OPER+" TEXT, "+
                    "FOREIGN KEY ("+ Table_OperDebCred.Cols.ID_CATEGORY+") REFERENCES "+Table_Category.TABLE_NAME+"("+BaseColumns.ID+"), "+
                    "FOREIGN KEY ("+ Table_OperDebCred.Cols.ID_ACCOUNT+") REFERENCES "+Table_Account.TABLE_NAME+"("+BaseColumns.ID+"))";

    public static final String CREATE_DEBIT_TABLE = "CREATE TABLE "+Table_Debit.TABLE_NAME+OPER_TABLE;
    public static final String CREATE_CREDIT_TABLE = "CREATE TABLE "+Table_Credit.TABLE_NAME+OPER_TABLE;


    private static final String TRANSFER_TABLE = " ("+
            BaseColumns.ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            BaseColumns.NAME+" TEXT, "+
            BaseColumns.DATE_CREATION+" TEXT, "+
            BaseColumns.DESCRIBE+" TEXT, "+
            Table_Transfer.Cols.ID_ACCOUNT_FROM+" INTEGER, "+
            Table_Transfer.Cols.ID_ACCOUNT_TO+" INTEGER, "+
            Table_Transfer.Cols.SUMMA_FROM+" REAL, "+
            Table_Transfer.Cols.SUMMA_TO+" REAL, "+
            Table_Transfer.Cols.DATE_OPER+" TEXT, "+
            "FOREIGN KEY ("+ Table_Transfer.Cols.ID_ACCOUNT_FROM+") REFERENCES "+Table_Account.TABLE_NAME+"("+BaseColumns.ID+")," +
            "FOREIGN KEY ("+ Table_Transfer.Cols.ID_ACCOUNT_TO+") REFERENCES "+Table_Account.TABLE_NAME+"("+BaseColumns.ID+"))";

    public static final String CREATE_TRANSFER_TABLE = "CREATE TABLE "+Table_Transfer.TABLE_NAME+TRANSFER_TABLE;


    //==================================================================================================
    public static final class BaseColumns{
        public static final String ID = "_id";
        public static final String NAME = "_name";
        public static final String DESCRIBE = "_describe";
        public static final String DATE_CREATION = "_dateCreation";
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
    public static final class Table_OperDebCred {

        public static final class Cols {
            public static final String  ID_ACCOUNT= "_idAccount";
            public static final String ID_CATEGORY= "_idCategory";
            public static final String SUMMA = "_summa";

            public static final String DATE_OPER = "_date";
        }
    }
    public static final class Table_Transfer {
        public static final String TABLE_NAME = "table_transfer";

        public static final class Cols {
            public static final String  ID_ACCOUNT_FROM= "_idAccount_from";
            public static final String  ID_ACCOUNT_TO= "_idAccount_to";
            public static final String SUMMA_FROM = "_summa_from";
            public static final String SUMMA_TO = "_summa_to";

            public static final String DATE_OPER = "_date";
        }
    }
}
