package me.nettee.financial.model.investment;

import org.joda.time.DateTimeConstants;
import org.joda.time.LocalDate;

import me.nettee.financial.model.Amount;
import me.nettee.financial.model.Percent;

import static me.nettee.financial.model.investment.InvestmentProject.InvestmentProjectType.MONETARY_FUND;

public class MonetaryFundInvestmentProject extends InvestmentProject {

    private String mName;
    private Amount mPrinciple;
    private Percent mAnnualYield;
    private LocalDate mBuyDate;
    private LocalDate mValueDate;
    private String mPostscript;

    @Override
    public InvestmentProjectType getType() {
        return MONETARY_FUND;
    }

    public static LocalDate getValueDateFromBuyDate(LocalDate buyDate) {
        switch (buyDate.getDayOfWeek()) {
            case DateTimeConstants.MONDAY:
            case DateTimeConstants.TUESDAY:
            case DateTimeConstants.WEDNESDAY:
            case DateTimeConstants.THURSDAY:
                return buyDate.plusDays(2);
            case DateTimeConstants.FRIDAY:
                return buyDate.plusDays(4);
            case DateTimeConstants.SATURDAY:
                return buyDate.plusDays(3);
            case DateTimeConstants.SUNDAY:
                return buyDate.plusDays(2);
            default:
                return buyDate.plusDays(2);
        }
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

    public LocalDate getBuyDate() {
        return mBuyDate;
    }

    public void setBuyDate(LocalDate buyDate) {
        mBuyDate = buyDate;
    }

    public LocalDate getValueDate() {
        return mValueDate;
    }

    public void setValueDate(LocalDate valueDate) {
        mValueDate = valueDate;
    }

    public String getPostscript() {
        return mPostscript;
    }

    public void setPostscript(String postscript) {
        mPostscript = postscript;
    }
}
