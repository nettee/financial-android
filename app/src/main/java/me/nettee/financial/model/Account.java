package me.nettee.financial.model;

import java.util.UUID;

public abstract class Account {

    public static final int CASH = 100;
    public static final int CASH_CARD = 101;
    public static final int BANK_CARD = 200;
    public static final int ALIPAY = 300;
    public static final int WEIXIN = 301;
    public static final int INVESTMENT = 600;

    private final UUID mId;

    public Account() {
        mId = UUID.randomUUID();
    }

    public final UUID getId() {
        return mId;
    }

    public abstract int getType();

    public abstract String getName();

    public abstract int getAmount();

    public abstract int getImageId();

}
