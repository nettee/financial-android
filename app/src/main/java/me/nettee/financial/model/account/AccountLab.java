package me.nettee.financial.model.account;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.CreditDate;
import me.nettee.financial.model.investment.InvestmentPlatform;

public class AccountLab {

    private static AccountLab sAccountLab;

    private final List<Account> mAccounts;

    private Comparator<Account> mAccountComparator = (one, another) -> {
        if (one.getType() < another.getType()) {
            return -1;
        } else if (one.getType() > another.getType()) {
            return 1;
        } else {
            return 0;
        }
    };

    private static List<Account> sCandidateAccounts = new ArrayList<Account>() {
        private static final long serialVersionUID = 1L;
        {
            add(Account.candidate(Account.CASH, "现金钱包", R.drawable.ic_wallet));
            add(Account.candidate(Account.CREDIT_CARD, "信用卡", R.drawable.ic_bank_card));
            add(Account.candidate(Account.DEBIT_CARD, "借记卡", R.drawable.ic_bank_card));
            add(Account.candidate(Account.ALIPAY, "支付宝", R.drawable.ic_alipay));
            add(Account.candidate(Account.WEIXIN, "微信钱包", R.drawable.ic_wxpay));
            add(Account.candidate(Account.CAMPUS_CARD, "校园卡", R.drawable.ic_campus_card));
            add(Account.candidate(Account.BUS_CARD, "公交卡", R.drawable.ic_bus));
            add(Account.candidate(Account.INVESTMENT, "投资账户", R.drawable.ic_account_investment));
//            add(Account.candidate(Account.CASH, "其他账户", R.drawable.ic_account));
        }
    };

    private AccountLab() {
        mAccounts = new ArrayList<>();
        CashAccount cashAccount = new CashAccount();
        cashAccount.setBalance(Amount.integer(976));
        cashAccount.setRemark("钱包A");
        mAccounts.add(cashAccount);
        CreditCardAccount creditCardAccount = new CreditCardAccount();
        Amount arrears = Amount.integer(1234);
        arrears.setSign(Amount.NEGATIVE);
        creditCardAccount.setArrears(arrears);
        mAccounts.add(creditCardAccount);
        DebitCardAccount debitCardAccount = new DebitCardAccount();
        debitCardAccount.setBankCardNumber("669395");
        debitCardAccount.setBalance(Amount.decimal(6134, 77));
        mAccounts.add(debitCardAccount);
        AlipayAccount alipayAccount = new AlipayAccount();
        alipayAccount.setBalance(Amount.decimal(16431, 91));
        alipayAccount.setHuabeiOpen(true);
        HuabeiAccount huabeiAccount = new HuabeiAccount();
        huabeiAccount.setCreditLimit(Amount.integer(2000));
        huabeiAccount.setBillDate(CreditDate.day(1));
        huabeiAccount.setPaymentDate(CreditDate.day(9));
        huabeiAccount.setArrears(Amount.decimal(-413, 43));
        alipayAccount.setHuabeiAccount(huabeiAccount);
        mAccounts.add(alipayAccount);
        WeixinAccount weixinAccount = new WeixinAccount();
        weixinAccount.setBalance(Amount.decimal(92, 60));
        mAccounts.add(weixinAccount);
        CampusCardAccount campusCardAccount = new CampusCardAccount();
        campusCardAccount.setBalance(Amount.decimal(49, 20));
        mAccounts.add(campusCardAccount);
        BusCardAccount busCardAccount = new BusCardAccount();
        busCardAccount.setBalance(Amount.decimal(67, 3));
        mAccounts.add(busCardAccount);
//        mAccounts.add(new Account("花呗", 15043, Account.HUABEI, R.drawable.ic_huabei));
        InvestmentAccount investmentAccount1 = new InvestmentAccount();
        investmentAccount1.setPlatform(InvestmentPlatform.getPlatformByName("蚂蚁财富"));
        mAccounts.add(investmentAccount1);
        InvestmentAccount investmentAccount2 = new InvestmentAccount();
        investmentAccount2.setPlatform(InvestmentPlatform.getPlatformByName("陆金所"));
        mAccounts.add(investmentAccount2);
        InvestmentAccount investmentAccount3 = new InvestmentAccount();
        investmentAccount3.setPlatform(InvestmentPlatform.getPlatformByName("天天基金"));
        mAccounts.add(investmentAccount3);
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

    public void deleteAccount(Account account) {
        mAccounts.removeIf(acc -> acc.getId().equals(account.getId()));
    }

    public List<Account> getAccounts() {
        return mAccounts;
    }

    public List<Account> getCandidateAccounts() {
        return sCandidateAccounts;
    }
}
