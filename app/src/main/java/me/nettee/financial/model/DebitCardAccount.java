package me.nettee.financial.model;

import me.nettee.financial.R;

public class DebitCardAccount extends Account {

    private Amount mBalance = Amount.zero();

    @Override
    public int getType() {
        return DEBIT_CARD;
    }

    @Override
    public String getCandidateName() {
        return "借记卡";
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_bank_card;
    }

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
