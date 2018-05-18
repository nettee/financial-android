package me.nettee.financial.model;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

import me.nettee.financial.R;
import me.nettee.financial.model.account.Account;
import me.nettee.financial.model.account.BankCardAccount;
import me.nettee.financial.model.account.InvestmentAccount;
import me.nettee.financial.model.investment.InvestmentProject;

public abstract class Display {

    private int mIcon;
    private String mName;

    public static Display of(Object object) {
        if (object instanceof Account) {
            return new AccountDisplay(((Account) object));
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static Display ofCandidate(Object object) {
        if (object instanceof Account) {
            return new CandidateAccountDisplay((Account) object);
        } else if (object instanceof InvestmentProject) {
            return new CandidateInvestmentProjectDisplay((InvestmentProject) object);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public int icon() {
        return mIcon;
    }

    public String name() {
        return mName;
    }

    public String remark() {
        throw new UnsupportedOperationException();
    }

    protected void setIcon(int icon) {
        mIcon = icon;
    }

    protected void setName(String name) {
        mName = name;
    }

    private static class AccountDisplay extends Display {

        private String mRemark;

        private AccountDisplay(Account account) {
            setIcon(Display.accountIcon(account));
            setName(Display.accountName(account));
            setRemark(Display.accountRemark(account));
        }

        @Override
        public String remark() {
            return mRemark;
        }

        private void setRemark(String remark) {
            mRemark = remark;
        }

    }

    private static class CandidateAccountDisplay extends Display {

        private CandidateAccountDisplay(Account account) {
            setIcon(candidateAccountIcon(account));
            setName(candidateAccountName(account));
        }
    }

    private static class CandidateInvestmentProjectDisplay extends Display {

        private CandidateInvestmentProjectDisplay(InvestmentProject investmentProject) {
            setIcon(candidateInvestmentProjectIcon(investmentProject));
            setName(candidateInvestmentProjectName(investmentProject));
        }
    }

    private static int candidateAccountIcon(Account account) {
        switch (account.getType()) {
            case CASH: return R.drawable.ic_wallet;
            case CREDIT_CARD: return R.drawable.ic_bank_card;
            case DEBIT_CARD: return R.drawable.ic_bank_card;
            case ALIPAY: return R.drawable.ic_alipay;
            case HUABEI: return R.drawable.ic_huabei;
            case WEIXIN: return R.drawable.ic_weixin;
            case CAMPUS_CARD: return R.drawable.ic_campus_card;
            case BUS_CARD: return R.drawable.ic_bus;
            case INVESTMENT: return R.drawable.ic_investment;
            case GENERAL: return R.drawable.ic_account;
            default: return R.drawable.ic_account;
        }
    }

    @NonNull
    private static String candidateAccountName(Account account) {
        switch (account.getType()) {
            case CASH: return "现金钱包";
            case CREDIT_CARD: return "信用卡";
            case DEBIT_CARD: return "借记卡";
            case ALIPAY: return "支付宝";
            case HUABEI: return "花呗";
            case WEIXIN: return "微信钱包";
            case CAMPUS_CARD: return "校园卡";
            case BUS_CARD: return "公交卡";
            case INVESTMENT: return "投资账户";
            case GENERAL: return "其他账户";
            default: return "账户";
        }
    }

    private static int accountIcon(Account account) {
        if (account.isBankCardAccount()) {
            return R.drawable.ic_bank_card;
        } else if (account.isInvestmentAccount()) {
            return ((InvestmentAccount) account).getPlatform().getImageResource();
        } else {
            return candidateAccountIcon(account);
        }
    }

    private static String accountName(Account account) {
        if (account.isBankCardAccount()) {
            return ((BankCardAccount) account).getBank().getName();
        } else if (account.isInvestmentAccount()) {
            return ((InvestmentAccount) account).getPlatform().getName();
        } else {
            return candidateAccountName(account);
        }
    }

    private static String accountRemark(Account account) {
        if (account.isBankCardAccount()) {
            String candidateName = candidateAccountName(account);
            BankCardAccount bankCardAccount = ((BankCardAccount) account);
            if (StringUtils.isEmpty(bankCardAccount.getBankCardNumber())) {
                return candidateName;
            } else {
                return String.format("%s %s", candidateName, bankCardAccount.getBankCardNumberTail());
            }
        } else {
            return account.getRemark();
        }
    }

    private static int candidateInvestmentProjectIcon(InvestmentProject investmentProject) {
        switch (investmentProject.getType()) {
            case MONETARY_FUND: return R.drawable.ic_investment_project_monetary_fund;
            case FIXED: return R.drawable.ic_investment_project_fixed;
            case FUND: return R.drawable.ic_investment_project_fund;
            case STOCK: return R.drawable.ic_investment_project_stock;
            case OTHER: return R.drawable.ic_investment_project_other;
            default: return 0;
        }
    }

    private static String candidateInvestmentProjectName(InvestmentProject investmentProject) {
        switch (investmentProject.getType()) {
            case MONETARY_FUND: return "货币基金";
            case FIXED: return "定期类";
            case FUND: return "基金类";
            case STOCK: return "股票";
            case OTHER: return "其他投资项目";
            default: return "其他投资项目";
        }
    }
}
