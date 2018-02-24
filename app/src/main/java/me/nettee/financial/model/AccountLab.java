package me.nettee.financial.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.nettee.financial.R;

public class AccountLab {

    private static AccountLab sAccountLab;

    private List<Account> mAccounts;

    private Comparator<Account> mAccountComparator = new Comparator<Account>() {
        @Override
        public int compare(Account one, Account another) {
            if (one.getType() < another.getType()) {
                return -1;
            } else if (one.getType() > another.getType()) {
                return 1;
            } else {
                return 0;
            }
        }
    };

    private static List<CandidateAccount> sCandidateAccounts = new ArrayList<CandidateAccount>() {
        {
            add(new CandidateAccount(Account.CASH, "现金钱包", R.drawable.ic_wallet));
            add(new CandidateAccount(Account.CREDIT_CARD, "信用卡", R.drawable.ic_bank_card));
            add(new CandidateAccount(Account.DEBIT_CARD, "借记卡", R.drawable.ic_bank_card));
            add(new CandidateAccount(Account.ALIPAY, "支付宝", R.drawable.ic_alipay));
            add(new CandidateAccount(Account.WEIXIN, "微信钱包", R.drawable.ic_wxpay));
            add(new CandidateAccount(Account.CASH_CARD, "校园卡", R.drawable.ic_campus_card));
            add(new CandidateAccount(Account.CASH_CARD, "公交卡", R.drawable.ic_bus));
//            add(new CandidateAccount(Account.CASH, "其他账户", R.drawable.ic_account));
        }
    };

    private AccountLab() {
        mAccounts = new ArrayList<>();
        CashAccount cashAccount = new CashAccount(97600);
        cashAccount.setRemark("钱包A");
        mAccounts.add(cashAccount);
        mAccounts.add(new BankCardAccount("工商银行", 613477, R.drawable.ic_bank_icbc));
        mAccounts.add(new AlipayAccount(1643191));
        mAccounts.add(new WeixinAccount(9260));
        mAccounts.add(new CampusCardAccount(4920));
        mAccounts.add(new BusCardAccount(6703));
//        mAccounts.add(new Account("花呗", 15043, Account.HUABEI, R.drawable.ic_huabei));
        mAccounts.add(new InvestmentAccount("蚂蚁财富", 434000, R.drawable.ic_ant_fortune));
        mAccounts.add(new InvestmentAccount("陆金所", 100000, R.drawable.ic_lufax));
        mAccounts.add(new InvestmentAccount("天天基金", 200000, R.drawable.ic_tiantian_fund));
//        mAccounts.add(new Account("账户Z", 0, Account.OTHER));
    }

    public static AccountLab getInstance() {
        if (sAccountLab == null) {
            sAccountLab = new AccountLab();
        }
        return sAccountLab;
    }

    public void addAccount(Account account) {
        mAccounts.add(account);
        Collections.sort(mAccounts, mAccountComparator);
    }

    public List<Account> getAccounts() {
        return mAccounts;
    }

    public List<CandidateAccount> getCandidateAccounts() {
        return sCandidateAccounts;
    }
}
