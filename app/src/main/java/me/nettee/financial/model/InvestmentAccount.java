package me.nettee.financial.model;

public final class InvestmentAccount extends Account {

    private static final long serialVersionUID = 1L;

    private InvestmentPlatform mPlatform;

    // TODO For test only.
    private Amount mAmount = Amount.zero();

    @Override
    public int getType() {
        return INVESTMENT;
    }

    @Override
    public String getCandidateName() {
        return getPlatform().getName();
    }

    @Override
    public int getCandidateImageResource() {
        return getPlatform().getImageResource();
    }

    public Amount getDefaultAmount() {
        return mAmount;
    }

    public InvestmentPlatform getPlatform() {
        return mPlatform;
    }

    public void setPlatform(InvestmentPlatform platform) {
        mPlatform = platform;
    }

    public Amount getAmount() {
        return mAmount;
    }

    public void setAmount(Amount amount) {
        mAmount = amount;
    }
}
