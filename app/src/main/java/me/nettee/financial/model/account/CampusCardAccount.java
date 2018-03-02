package me.nettee.financial.model.account;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;

public class CampusCardAccount extends CashCardAccount {

    private static final long serialVersionUID = 1L;

    private Amount mBalance = Amount.zero();

    @Override
    public int getCashCardType() {
        return CAMPUS_CARD;
    }

    @Override
    public String getCandidateName() {
        return "校园卡";
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_campus_card;
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
