package com.wolff.wmoney.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wolff on 12.06.2017.
 */

public class WTransfer extends WBase implements Serializable{
        private static final long serialVersionUID = 1163051468057804386L;
        private WAccount mAccountFrom;
        private WAccount mAccountTo;
        private double mSummaFrom;
        private double mSummaTo;
        private Date mDateOper;

    public WAccount getAccountFrom() {
        return mAccountFrom;
    }

    public void setAccountFrom(WAccount accountFrom) {
        mAccountFrom = accountFrom;
    }

    public WAccount getAccountTo() {
        return mAccountTo;
    }

    public void setAccountTo(WAccount accountTo) {
        mAccountTo = accountTo;
    }

    public double getSummaFrom() {
        return mSummaFrom;
    }

    public void setSummaFrom(double summaFrom) {
        mSummaFrom = summaFrom;
    }

    public double getSummaTo() {
        return mSummaTo;
    }

    public void setSummaTo(double summaTo) {
        mSummaTo = summaTo;
    }

    public Date getDateOper() {
            return mDateOper;
        }

        public void setDateOper(Date dateOper) {
            mDateOper = dateOper;
        }
    }
