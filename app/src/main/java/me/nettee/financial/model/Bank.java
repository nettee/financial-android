package me.nettee.financial.model;

import me.nettee.financial.R;

public class Bank {

    public static final int ICBC = 1;

    private final String mName;
    private final int mImageResource;

    private Bank(String name, int imageResource) {
        mName = name;
        mImageResource = imageResource;
    }

    public static Bank icbc() {
        return new Bank("工商银行", R.drawable.ic_bank_icbc);
    }

    public String getName() {
        return mName;
    }

    public int getImageResource() {
        return mImageResource;
    }
}
