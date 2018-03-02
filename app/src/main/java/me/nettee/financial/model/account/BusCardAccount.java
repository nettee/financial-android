package me.nettee.financial.model.account;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;

public class BusCardAccount extends CashCardAccount {

    private static final long serialVersionUID = 1L;

    @Override
    public int getType() {
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

}
