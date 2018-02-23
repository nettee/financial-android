package me.nettee.financial.model;

import java.util.UUID;

public class Account {

    public static final int OTHER = 0;
    public static final int CASH = 100;
    public static final int BUS = 101;
    public static final int CAMPUS_CARD = 102;
    public static final int ALIPAY = 300;
    public static final int WXPAY = 301;
    public static final int BANK_ICBC = 200;

    private final UUID mId;
    private String mName;
    private int mAmount;
    private int mType;

    public Account(String name, int amount, int type) {
        mId = UUID.randomUUID();
        mName = name;
        mAmount = amount;
        mType = type;
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }
}
