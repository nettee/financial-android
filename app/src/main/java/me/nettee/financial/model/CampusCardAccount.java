package me.nettee.financial.model;

import me.nettee.financial.R;

public class CampusCardAccount extends CashCardAccount {

    public CampusCardAccount() {
        this(0);
    }

    public CampusCardAccount(int amount) {
        super(amount);
    }

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
}
