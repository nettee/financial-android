package me.nettee.financial.model.bill;

import me.nettee.financial.model.account.Account;

public class IncomeEntry extends BookEntry {

    private Account sinkAccount;
    private IncomeCategory category;

    @Override
    public Type getType() {
        return Type.INCOME;
    }

    public Account getSinkAccount() {
        return sinkAccount;
    }

    public void setSinkAccount(Account sinkAccount) {
        this.sinkAccount = sinkAccount;
    }

    public IncomeCategory getCategory() {
        return category;
    }

    public void setCategory(IncomeCategory category) {
        this.category = category;
    }
}
