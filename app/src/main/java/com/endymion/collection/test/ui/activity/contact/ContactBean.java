package com.endymion.collection.test.ui.activity.contact;

/**
 * Created by Jim Lee on 2018/11/23.
 */
public class ContactBean {
    String displayName;
    String phoneNum;
    String sortKey;
    long photoId;
    String lookUpKey;

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public void setPhotoId(long photoId) {
        this.photoId = photoId;
    }

    public void setLookUpKey(String lookUpKey) {
        this.lookUpKey = lookUpKey;
    }
}
