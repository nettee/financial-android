package me.nettee.financial.model;

import me.nettee.financial.R;

public class BusCardAccount extends CashCardAccount {

    private Amount mBalance = Amount.zero();

    @Override
    public int getCashCardType() {
        return BUS_CARD;
    }

    @Override
    public String getCandidateName() {
        return "公交卡";
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_bus;
    }

    @Override
    public Amount getDefaultAmount() {
        return getBalance();
    }

    public Amount getBalance() {
        return mBalance;
    }

    public void setBalance(Amount balance) {
        mBalance = balance;
    }
}
