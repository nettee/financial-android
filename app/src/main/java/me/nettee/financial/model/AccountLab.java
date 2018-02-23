package me.nettee.financial.model;

import java.util.ArrayList;
import java.util.List;

public class AccountLab {

    private static AccountLab sAccountLab;

    private List<Account> mAccounts;

    private AccountLab() {
        mAccounts = new ArrayList<>();
        mAccounts.add(new Account("现金钱包", 97600, Account.CASH));
        mAccounts.add(new Account("工商银行", 613477, Account.BANK_ICBC));
        mAccounts.add(new Account("支付宝", 1643191, Account.ALIPAY));
        mAccounts.add(new Account("微信钱包", 9260, Account.WXPAY));
        mAccounts.add(new Account("校园卡", 4920, Account.CAMPUS_CARD));
        mAccounts.add(new Account("公交卡", 6703, Account.BUS));
//        mAccounts.add(new Account("账户Z", 0, Account.OTHER));
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
