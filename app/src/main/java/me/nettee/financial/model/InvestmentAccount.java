package me.nettee.financial.model;

import me.nettee.financial.R;

public class InvestmentAccount extends Account {

    @Override
    public int getType() {
        return INVESTMENT;
    }

    @Override
    public String getCandidateName() {
        return "投资账户";
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_account;
    }

    public Amount getDefaultAmount() {
        return Amount.integer(0);
    }
}
