package me.nettee.financial.model.bill;

import me.nettee.financial.model.account.Account;

public class OutcomeEntry extends BookEntry {

    private Account sourceAccount;
    private OutcomeCategory category;

    @Override
    public Type getType() {
        return Type.OUTCOME;
    }

    public Account getSourceAccount() {
        return sourceAccount;
    }

    public void setSourceAccount(Account sourceAccount) {
        this.sourceAccount = sourceAccount;
    }

    public OutcomeCategory getCategory() {
        return category;
    }

    public void setCategory(OutcomeCategory category) {
        this.category = category;
    }
}
