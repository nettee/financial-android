package me.nettee.financial.model;

public class InvestmentAccount extends Account {

    private String mName;
    private int mAmount;
    private int mImageId;

    public InvestmentAccount(String name, int amount, int imageId) {
        mName = name;
        mAmount = amount;
        mImageId = imageId;
    }

    @Override
    public int getType() {
        return INVESTMENT;
    }

    @Override
    public String getCandidateName() {
        return mName;
    }

    public int getDefaultAmount() {
        return mAmount;
    }

    @Override
    public int getCandidateImageResource() {
        return mImageId;
    }
}
