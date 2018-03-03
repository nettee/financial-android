package me.nettee.financial.model;

public class CurrentInvestmentProject extends InvestmentProject {

    private Amount mAmount;

    private CurrentInvestmentProject() {
        super(CURRENT);
    }

    public Amount getAmount() {
        return mAmount;
    }

    public void setAmount(Amount amount) {
        mAmount = amount;
    }
}
