package me.nettee.financial.model.investment;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;

public class MonetaryFundInvestmentProject extends InvestmentProject {

    private Amount mAmount;

    @Override
    public int getType() {
        return MONETARY_FUND;
    }

    @Override
    public String getCandidateName() {
        return "货币基金";
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_account_investment;
    }

    public Amount getAmount() {
        return mAmount;
    }

    public void setAmount(Amount amount) {
        mAmount = amount;
    }
}
