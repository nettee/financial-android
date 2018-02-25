package me.nettee.financial.model;

import java.io.Serializable;
import java.util.UUID;

public abstract class Account implements Serializable {

    public static final int GENERAL = 0;
    public static final int CASH = 100;
    public static final int CREDIT_CARD = 200;
    public static final int DEBIT_CARD = 300;
    public static final int ALIPAY = 400;
    public static final int WEIXIN = 401;
    public static final int CASH_CARD = 500;
    public static final int INVESTMENT = 600;

    private final UUID mId;
    private String mRemark;

    public Account() {
        mId = UUID.randomUUID();
    }

    public final UUID getId() {
        return mId;
    }

    public abstract int getType();

    public abstract String getCandidateName();

    public final String getRemark() {
        return mRemark;
    }

    public final void setRemark(String remark) {
        mRemark = remark;
    }

    public abstract int getDefaultAmount();

    public abstract int getCandidateImageResource();

}
