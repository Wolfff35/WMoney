package com.wolff.wmoney.model;


import java.io.Serializable;
import java.util.Date;

/**
 * Created by wolff on 23.05.2017.
 */

public class WAccount extends WBase implements Serializable {
    private static final long serialVersionUID = 1163051468057804396L;

    private int mIdPicture;
    private double mSumma;
    private WCurrency mCurrency;

    public WAccount(){
        //this.setDateCreation(new Date());
    }

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
