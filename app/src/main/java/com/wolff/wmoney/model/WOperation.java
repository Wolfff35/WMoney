package com.wolff.wmoney.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wolff on 01.06.2017.
 */

public class WOperation extends WBase implements Serializable {
    private static final long serialVersionUID = 1263051678907804396L;
    private WAccount mAccount;
    private WCategory mCategory;
    private double mSumma;
    private Date mDateOper;
    private boolean isCredit;

    public boolean isCredit() {
        return isCredit;
    }

    public void setCredit(boolean credit) {
        isCredit = credit;
    }

    public WAccount getAccount() {
        return mAccount;
    }

    public void setAccount(WAccount account) {
        mAccount = account;
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

     public Date getDateOper() {
        return mDateOper;
    }

    public void setDateOper(Date dateOper) {
        mDateOper = dateOper;
    }
}
