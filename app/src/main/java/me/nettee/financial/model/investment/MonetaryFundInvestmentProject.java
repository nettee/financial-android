package me.nettee.financial.model.investment;

import java.util.Date;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.Percent;

public class MonetaryFundInvestmentProject extends InvestmentProject {

    private String mName;
    private Amount mPrinciple;
    private Percent mAnnualYield;
    private Date mBuyDate;
    private Date mValueDate;
    private String mPostscript;

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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Amount getPrinciple() {
        return mPrinciple;
    }

    public void setPrinciple(Amount principle) {
        mPrinciple = principle;
    }

    public Percent getAnnualYield() {
        return mAnnualYield;
    }

    public void setAnnualYield(Percent annualYield) {
        mAnnualYield = annualYield;
    }

    public Date getBuyDate() {
        return mBuyDate;
    }

    public void setBuyDate(Date buyDate) {
        mBuyDate = buyDate;
    }

    public Date getValueDate() {
        return mValueDate;
    }

    public void setValueDate(Date valueDate) {
        mValueDate = valueDate;
    }

    public String getPostscript() {
        return mPostscript;
    }

    public void setPostscript(String postscript) {
        mPostscript = postscript;
    }
}
