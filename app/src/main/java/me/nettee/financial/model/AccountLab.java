package me.nettee.financial.model;

import java.util.ArrayList;
import java.util.List;

public class AccountLab {

    private static AccountLab sAccountLab;

    private List<Account> mAccounts;

    private AccountLab() {
        mAccounts = new ArrayList<>();
        mAccounts.add(new Account("现金钱包", 97600));
        mAccounts.add(new Account("支付宝", 1643191));
        for (int i = 0; i < 98; i++) {
            mAccounts.add(new Account("账户 " + (i + 3), 0));
        }
    }

    public static AccountLab getInstance() {
        if (sAccountLab == null) {
            sAccountLab = new AccountLab();
        }
        return sAccountLab;
    }

    public List<Account> getAccounts() {
        return mAccounts;
    }
}
