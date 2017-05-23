package com.wolff.wmoney.model;

/**
 * Created by wolff on 23.05.2017.
 */

public class WCategory extends WBase {
    private boolean mIsCredit;

    public boolean isCredit() {
        return mIsCredit;
    }

    public void setCredit(boolean credit) {
        mIsCredit = credit;
    }

}
