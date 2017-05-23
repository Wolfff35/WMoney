package com.wolff.wmoney.model;


/**
 * Created by wolff on 23.05.2017.
 */

public class WAccount extends WBase {
    private int mIdPicture;
    private double mSumma;
    private WCurrency mCurrency;

    public int getIdPicture() {
        return mIdPicture;
    }

    public void setIdPicture(int idPicture) {
        mIdPicture = idPicture;
    }

    public double getSumma() {
        return mSumma;
    }

    public void setSumma(double summa) {
        mSumma = summa;
    }

    public WCurrency getCurrency() {
        return mCurrency;
    }

    public void setCurrency(WCurrency currency) {
        mCurrency = currency;
    }
}
