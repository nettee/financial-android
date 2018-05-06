package me.nettee.financial.model;

import me.nettee.financial.R;
import me.nettee.financial.model.investment.InvestmentProject;

public class ImageResource {

    public static int getInvestmentProjectCandidateImageResource(InvestmentProject investmentProject) {
        switch (investmentProject.getType()) {
            case InvestmentProject.MONETARY_FUND: return R.drawable.ic_account_investment;
            default: return R.drawable.ic_account_investment;
        }
    }

    public static int getBankImageResource(Bank bank) {
        return R.drawable.ic_bank_icbc;
    }
}
