package me.nettee.financial.model.account;

import me.nettee.financial.model.Amount;

public abstract class CashCardAccount extends Account {

    private Amount mBalance;

    public Amount getBalance() {
        return mBalance;
    }

    public void setBalance(Amount balance) {
        mBalance = balance;
    }

    @Override
    public Amount getDefaultAmount() {
        return getBalance();
    }
}
