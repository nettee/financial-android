package me.nettee.financial.model;

import me.nettee.financial.R;

public final class CreditCardAccount extends BankCardAccount {

    private int mCreditLimit;
    private int mBillDate;
    private int mPaymentDate;

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

    public int getAmount() {
        return mCreditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        mCreditLimit = creditLimit;
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

    @Override
    public int getImageId() {
        return R.drawable.ic_bank_card;
    }
}
