package me.nettee.financial.model;

import me.nettee.financial.R;

public final class CreditCardAccount extends BankCardAccount {

    private int mCreditLimit;
    private int mBillDate;
    private int mPaymentDate;
    private int mCurrentArrears;

    public CreditCardAccount() {
    }

    @Override
    public int getType() {
        return CREDIT_CARD;
    }

    @Override
    public String getCandidateName() {
        return "信用卡";
    }

    public int getDefaultAmount() {
        return getCreditLimit();
    }

    public void setCreditLimit(int creditLimit) {
        mCreditLimit = creditLimit;
    }

    public int getCreditLimit() {
        return mCreditLimit;
    }

    public int getBillDate() {
        return mBillDate;
    }

    public void setBillDate(int billDate) {
        mBillDate = billDate;
    }

    public int getPaymentDate() {
        return mPaymentDate;
    }

    public void setPaymentDate(int paymentDate) {
        mPaymentDate = paymentDate;
    }

    public int getCurrentArrears() {
        return mCurrentArrears;
    }

    public void setCurrentArrears(int currentArrears) {
        mCurrentArrears = currentArrears;
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_bank_card;
    }
}
