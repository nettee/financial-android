package me.nettee.financial.model;

import me.nettee.financial.R;

public class CreditCardAccount extends Account {

    private int mAmount;

    public CreditCardAccount() {

    }

    @Override
    public int getType() {
        return CREDIT_CARD;
    }

    @Override
    public String getName() {
        return "信用卡";
    }

    @Override
    public int getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }

    @Override
    public int getImageId() {
        return R.drawable.ic_bank_card;
    }
}
