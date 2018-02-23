package me.nettee.financial.model;

public abstract class CashCardAccount extends Account {

    public static final int BUS_CARD = 0;
    public static final int CAMPUS_CARD = 1;

    private int mAmount;

    public CashCardAccount() {
        this(0);
    }

    public CashCardAccount(int amount) {
        mAmount = amount;
    }

    @Override
    public int getType() {
        return CASH_CARD;
    }

    public abstract int getCashCardType();

    @Override
    public abstract String getName();

    @Override
    public int getAmount() {
        return mAmount;
    }

    @Override
    public abstract int getImageId();
}
