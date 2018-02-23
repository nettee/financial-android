package me.nettee.financial.model;

import me.nettee.financial.R;

public class CashAccount extends Account {

    private int mAmount;

    public CashAccount() {
        this(0);
    }

    public CashAccount(int amount) {
        mAmount = amount;
    }

    @Override
    public int getType() {
        return CASH;
    }

    @Override
    public String getName() {
        return "现金钱包";
    }

    @Override
    public int getAmount() {
        return mAmount;
    }

    @Override
    public int getImageId() {
        return R.drawable.ic_wallet;
    }
}
