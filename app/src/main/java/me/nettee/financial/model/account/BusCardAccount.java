package me.nettee.financial.model.account;

import me.nettee.financial.R;

public class BusCardAccount extends CashCardAccount {

    private static final long serialVersionUID = 1L;

    @Override
    public AccountType getType() {
        return AccountType.BUS_CARD;
    }

    @Override
    public String getCandidateName() {
        return "公交卡";
    }

}
