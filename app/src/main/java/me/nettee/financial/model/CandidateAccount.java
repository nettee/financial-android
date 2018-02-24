package me.nettee.financial.model;

import android.content.Context;
import android.content.Intent;

import me.nettee.financial.ui.NewCashAccountActivity;
import me.nettee.financial.ui.NewCreditCardAccountActivity;

public class CandidateAccount {

    private int mType;
    private String mName;
    private int mImageId;

    public CandidateAccount(int type, String name, int imageId) {
        mType = type;
        mName = name;
        mImageId = imageId;
    }

    public String getName() {
        return mName;
    }

    public int getImageId() {
        return mImageId;
    }

    public Class getActivityClass() {
        if (mType == Account.CASH) {
            return NewCashAccountActivity.class;
        } else if (mType == Account.CREDIT_CARD) {
            return NewCreditCardAccountActivity.class;
        } else {
            return null;
        }
    }
}
