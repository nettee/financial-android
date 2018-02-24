package me.nettee.financial.model;

import me.nettee.financial.ui.NewCashAccountActivity;
import me.nettee.financial.ui.NewCreditCardAccountActivity;
import me.nettee.financial.ui.NewDebitCardAccountActivity;

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
        } else if (mType == Account.DEBIT_CARD) {
            return NewDebitCardAccountActivity.class;
        } else {
            return null;
        }
    }
}
