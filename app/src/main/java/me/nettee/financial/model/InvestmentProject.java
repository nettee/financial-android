package me.nettee.financial.model;

public class InvestmentProject {

    public static final int CURRENT = 0;
    public static final int FIXED = 1;
    public static final int FUND = 2;
    public static final int STOCK = 3;
    public static final int OTHER = 4;

    private final int mType;

    protected InvestmentProject(int type) {
        mType = type;
    }

    public int getType() {
        return mType;
    }

    public static InvestmentProject newCurrent() {
        return new InvestmentProject(CURRENT);
    }

    public static InvestmentProject newFixed() {
        return new InvestmentProject(FIXED);
    }

    public static InvestmentProject newFund() {
        return new InvestmentProject(FUND);
    }

    public static InvestmentProject newStock() {
        return new InvestmentProject(STOCK);
    }

    public static InvestmentProject newOther() {
        return new InvestmentProject(OTHER);
    }

}
