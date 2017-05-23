package com.wolff.wmoney.model;

/**
 * Created by wolff on 23.05.2017.
 */

public class WBase {
    private String mId;
    private String mName;
    private String mDescribe;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
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
