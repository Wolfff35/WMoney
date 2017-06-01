package com.wolff.wmoney.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wolff on 01.06.2017.
 */

public class WCredit extends WBase implements Serializable {
    private static final long serialVersionUID = 1263051678907804396L;
    private WAccount mAccount;
    private WCurrency mCurrency;
    private WCategory mCategory;
    private double mSumma;
    private double mSummaVal;
    private Date mDateOper;

    public WAccount getAccount() {
        return mAccount;
    }

    public void setAccount(WAccount account) {
        mAccount = account;
    }

    public WCurrency getCurrency() {
        return mCurrency;
    }

    public void setCurrency(WCurrency currency) {
        mCurrency = currency;
    }

    public WCategory getCategory() {
        return mCategory;
    }

    public void setCategory(WCategory category) {
        mCategory = category;
    }

    public double getSumma() {
        return mSumma;
    }

    public void setSumma(double summa) {
        mSumma = summa;
    }

    public double getSummaVal() {
        return mSummaVal;
    }

    public void setSummaVal(double summaVal) {
        mSummaVal = summaVal;
    }

     public Date getDateOper() {
        return mDateOper;
    }

    public void setDateOper(Date dateOper) {
        mDateOper = dateOper;
    }
}
