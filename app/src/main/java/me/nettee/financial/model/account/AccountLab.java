package me.nettee.financial.model.account;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.CreditDate;
import me.nettee.financial.model.investment.InvestmentPlatform;
import me.nettee.financial.model.investment.InvestmentPlatformLab;

public class AccountLab {

    private static AccountLab sAccountLab;

    private static List<Account> sCandidateAccounts = new ArrayList<Account>() {
        private static final long serialVersionUID = 1L;
        {
            add(Account.candidate(Account.AccountType.CASH,"现金钱包"));
            add(Account.candidate(Account.AccountType.CREDIT_CARD,"信用卡"));
            add(Account.candidate(Account.AccountType.DEBIT_CARD,"借记卡"));
            add(Account.candidate(Account.AccountType.ALIPAY,"支付宝"));
            add(Account.candidate(Account.AccountType.WEIXIN,"微信钱包"));
            add(Account.candidate(Account.AccountType.CAMPUS_CARD,"校园卡"));
            add(Account.candidate(Account.AccountType.BUS_CARD,"公交卡"));
            add(Account.candidate(Account.AccountType.INVESTMENT,"投资账户"));
        }
    };

    private List<Account> mAccounts;

    private AccountLab() {
        mAccounts = new ArrayList<>();
        CashAccount cashAccount = new CashAccount();
        cashAccount.setBalance(Amount.integer(976));
        cashAccount.setRemark("钱包A");
        mAccounts.add(cashAccount);
        CreditCardAccount creditCardAccount = new CreditCardAccount();
        creditCardAccount.setArrears(Amount.integer(1234).neg());
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
        InvestmentAccount investmentAccount1 = new InvestmentAccount();
        investmentAccount1.setPlatform(InvestmentPlatformLab.getPlatformByName("蚂蚁财富"));
        mAccounts.add(investmentAccount1);
        InvestmentAccount investmentAccount2 = new InvestmentAccount();
        investmentAccount2.setPlatform(InvestmentPlatformLab.getPlatformByName("陆金所"));
        mAccounts.add(investmentAccount2);
        InvestmentAccount investmentAccount3 = new InvestmentAccount();
        investmentAccount3.setPlatform(InvestmentPlatformLab.getPlatformByName("天天基金"));
        mAccounts.add(investmentAccount3);
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
        mAccounts.add(newAccount);
    }

    public void deleteAccount(Account account) {
        mAccounts.remove(account);
    }



    public List<Account> getAccounts() {
        return mAccounts;
    }

    public List<Account> getCandidateAccounts() {
        return sCandidateAccounts;
    }
}
