package me.nettee.financial.model;

public abstract class BankCardAccount extends Account {

    private String mBankCardNumber;

    public final String getBankCardNumber() {
        return mBankCardNumber;
    }

    public final void setBankCardNumber(String bankCardNumber) {
        mBankCardNumber = bankCardNumber;
    }
}
