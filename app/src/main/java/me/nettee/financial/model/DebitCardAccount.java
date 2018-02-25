package me.nettee.financial.model;

public class DebitCardAccount extends Account {

    private String mName;
    private int mAmount;
    private int mImageId;

    public DebitCardAccount(String name, int amount, int imageId) {
        mName = name;
        mAmount = amount;
        mImageId = imageId;
    }

    @Override
    public int getType() {
        return DEBIT_CARD;
    }

    @Override
    public String getName() {
        return mName;
    }

    public int getAmount() {
        return mAmount;
    }

    @Override
    public int getImageId() {
        return mImageId;
    }
}
