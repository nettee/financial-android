package me.nettee.financial.model.bill;

import me.nettee.financial.model.account.Account;

public class TransferEntry extends BookEntry {

    private Account fromAccount;
    private Account toAccount;

    @Override
    public Type getType() {
        return Type.TRANSFER;
    }

    public Account getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(Account fromAccount) {
        this.fromAccount = fromAccount;
    }

    public Account getToAccount() {
        return toAccount;
    }

    public void setToAccount(Account toAccount) {
        this.toAccount = toAccount;
    }
}
