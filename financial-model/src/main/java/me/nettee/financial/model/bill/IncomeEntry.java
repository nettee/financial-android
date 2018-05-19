package me.nettee.financial.model.bill;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import me.nettee.financial.model.Amount;
import me.nettee.financial.model.account.Account;
import me.nettee.financial.model.account.DebitCardAccount;

public class IncomeEntry extends BookEntry {

    private Account sinkAccount;
    private IncomeCategory category;

    public static IncomeEntry getDefault() {
        IncomeEntry incomeEntry = new IncomeEntry();
        incomeEntry.setAmount(Amount.zero());
        incomeEntry.setSinkAccount(new DebitCardAccount());
        incomeEntry.setCreatedTime(DateTime.now());
        incomeEntry.setLastModifiedTime(DateTime.now());
        incomeEntry.setDate(new LocalDate(2018, 5, 17));
        incomeEntry.setRemark("");
        return incomeEntry;
    }

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
