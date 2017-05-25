package com.wolff.wmoney.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by wolff on 23.05.2017.
 */

public class WBase implements Serializable{
    private static final long serialVersionUID = 1263051468057804396L;

    private int mId;
    private String mName;
    private String mDescribe;
    private Date mDateCreation;

    public Date getDateCreation() {
        return mDateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        mDateCreation = dateCreation;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescribe() {
        return mDescribe;
    }

    public void setDescribe(String describe) {
        this.mDescribe = describe;
    }
}
