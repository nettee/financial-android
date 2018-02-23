package me.nettee.financial.model;

import java.util.ArrayList;
import java.util.List;

import me.nettee.financial.R;

public class AccountLab {

    private static AccountLab sAccountLab;

    private List<Account> mAccounts;

    private static List<CandidateAccount> sCandidateAccounts = new ArrayList<CandidateAccount>() {
        {
            add(new CandidateAccount("现金钱包", R.drawable.ic_wallet));
            add(new CandidateAccount("信用卡", R.drawable.ic_card));
            add(new CandidateAccount("借记卡", R.drawable.ic_card));
            add(new CandidateAccount("支付宝", R.drawable.ic_alipay));
            add(new CandidateAccount("微信钱包", R.drawable.ic_wxpay));
            add(new CandidateAccount("校园卡", R.drawable.ic_campus_card));
            add(new CandidateAccount("公交卡", R.drawable.ic_bus));
            add(new CandidateAccount("其他账户", R.drawable.ic_account));
        }
    };

    private AccountLab() {
        mAccounts = new ArrayList<>();
        mAccounts.add(new Account("现金钱包", 97600, Account.CASH));
        mAccounts.add(new Account("工商银行", 613477, Account.BANK_ICBC));
        mAccounts.add(new Account("支付宝", 1643191, Account.ALIPAY));
        mAccounts.add(new Account("微信钱包", 9260, Account.WXPAY));
        mAccounts.add(new Account("校园卡", 4920, Account.CAMPUS_CARD));
        mAccounts.add(new Account("公交卡", 6703, Account.BUS));
        mAccounts.add(new Account("花呗", 15043, Account.HUABEI));
        mAccounts.add(new Account("蚂蚁财富", 434000, Account.ANT_FORTUNE));
        mAccounts.add(new Account("陆金所", 100000, Account.LUFAX));
        mAccounts.add(new Account("天天基金", 200000, Account.TIANTIAN_FUND));
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

    public List<CandidateAccount> getCandidateAccounts() {
        return sCandidateAccounts;
    }
}
