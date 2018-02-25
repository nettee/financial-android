package me.nettee.financial.model;

import me.nettee.financial.R;

public class CashAccount extends Account {

    private int mBalanceAmount;

    @Override
    public int getType() {
        return CASH;
    }

    @Override
    public String getCandidateName() {
        return "现金钱包";
    }

    @Override
    public int getDefaultAmount() {
        return mBalanceAmount;
    }

    public void setBalanceAmount(int amount) {
        mBalanceAmount = amount;
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_wallet;
    }
}
