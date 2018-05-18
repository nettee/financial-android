package me.nettee.financial.model.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import me.nettee.financial.model.Amount;
import me.nettee.financial.model.CreditDate;
import me.nettee.financial.model.investment.InvestmentPlatformLab;

public class AccountLab {

    private static AccountLab sAccountLab;

    private static List<Account> sCandidateAccounts = new ArrayList<Account>() {
        private static final long serialVersionUID = 1L;
        {
            Account.AccountType[] types = {
                    Account.AccountType.CASH,
                    Account.AccountType.CREDIT_CARD,
                    Account.AccountType.DEBIT_CARD,
                    Account.AccountType.ALIPAY,
                    Account.AccountType.WEIXIN,
                    Account.AccountType.CAMPUS_CARD,
                    Account.AccountType.BUS_CARD,
                    Account.AccountType.INVESTMENT,
            };
            for (Account.AccountType type : types) {
                add(Account.candidate(type));
            }
        }
    };

    private TreeSet<Account> mAccounts;

    private AccountLab() {
        mAccounts = new TreeSet<>();
        fillTestAccounts();
    }

    private void fillTestAccounts() {
        CashAccount cashAccount = new CashAccount();
        cashAccount.setBalance(Amount.integer(976));
        cashAccount.setRemark("钱包A");
        addAccount(cashAccount);
        CreditCardAccount creditCardAccount = new CreditCardAccount();
        creditCardAccount.setArrears(Amount.integer(1234).neg());
        addAccount(creditCardAccount);
        DebitCardAccount debitCardAccount = new DebitCardAccount();
        debitCardAccount.setBankCardNumber("669395");
        debitCardAccount.setBalance(Amount.decimal(6134, 77));
        addAccount(debitCardAccount);
        AlipayAccount alipayAccount = new AlipayAccount();
        alipayAccount.setBalance(Amount.decimal(16431, 91));
        alipayAccount.setHuabeiOpen(true);
        HuabeiAccount huabeiAccount = new HuabeiAccount();
        huabeiAccount.setCreditLimit(Amount.integer(2000));
        huabeiAccount.setBillDate(CreditDate.day(1));
        huabeiAccount.setPaymentDate(CreditDate.day(9));
        huabeiAccount.setArrears(Amount.decimal(-413, 43));
        alipayAccount.setHuabeiAccount(huabeiAccount);
        addAccount(alipayAccount);
        WeixinAccount weixinAccount = new WeixinAccount();
        weixinAccount.setBalance(Amount.decimal(92, 60));
        addAccount(weixinAccount);
        CampusCardAccount campusCardAccount = new CampusCardAccount();
        campusCardAccount.setBalance(Amount.decimal(49, 20));
        addAccount(campusCardAccount);
        BusCardAccount busCardAccount = new BusCardAccount();
        busCardAccount.setBalance(Amount.decimal(67, 3));
        addAccount(busCardAccount);
        InvestmentAccount investmentAccount1 = new InvestmentAccount();
        investmentAccount1.setPlatform(InvestmentPlatformLab.getPlatformByName("蚂蚁财富"));
        addAccount(investmentAccount1);
        InvestmentAccount investmentAccount2 = new InvestmentAccount();
        investmentAccount2.setPlatform(InvestmentPlatformLab.getPlatformByName("陆金所"));
        addAccount(investmentAccount2);
        InvestmentAccount investmentAccount3 = new InvestmentAccount();
        investmentAccount3.setPlatform(InvestmentPlatformLab.getPlatformByName("天天基金"));
        addAccount(investmentAccount3);
    }

    // Singleton pattern
    public static AccountLab getInstance() {
        if (sAccountLab == null) {
            sAccountLab = new AccountLab();
        }
        return sAccountLab;
    }

    public void addAccount(Account account) {
        mAccounts.add(account);
    }

    public void modifyAccount(Account oldAccount, Account newAccount) {
        mAccounts.remove(oldAccount);
        addAccount(newAccount);
    }

    public void deleteAccount(Account account) {
        mAccounts.remove(account);
    }

    public Collection<Account> getAccounts() {
        return mAccounts;
    }

    public List<Account> getCandidateAccounts() {
        return sCandidateAccounts;
    }
}
