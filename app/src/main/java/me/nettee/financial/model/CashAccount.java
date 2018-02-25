package me.nettee.financial.model;

import me.nettee.financial.R;

public class CashAccount extends Account {

    private Amount mBalance = Amount.zero();

    @Override
    public int getType() {
        return CASH;
    }

    @Override
    public String getCandidateName() {
        return "现金钱包";
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_wallet;
    }

    @Override
    public Amount getDefaultAmount() {
        return getBalance();
    }

    public Amount getBalance() {
        return mBalance;
    }

    public void setBalance(Amount amount) {
        mBalance = amount;
    }
}
