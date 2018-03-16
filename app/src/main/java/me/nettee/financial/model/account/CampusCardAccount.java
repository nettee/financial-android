package me.nettee.financial.model.account;

import me.nettee.financial.R;

public class CampusCardAccount extends CashCardAccount {

    private static final long serialVersionUID = 1L;

    @Override
    public AccountType getType() {
        return AccountType.CAMPUS_CARD;
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
