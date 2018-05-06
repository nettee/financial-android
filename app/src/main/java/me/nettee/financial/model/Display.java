package me.nettee.financial.model;

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
            throw new AssertionError();
        }
    }

    public Display candidate() {
        throw new UnsupportedOperationException();
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

        private Account mAccount;
        private String mRemark;

        private AccountDisplay(Account account) {
            mAccount = account;
            setIcon(Display.getAccountDisplayImageResource(account));
            setName(Display.getAccountDisplayName(account));
            setRemark(Display.getAccountDisplayRemark(account));
        }

        @Override
        public Display candidate() {
            return new CandidateAccountDisplay(mAccount);
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
            setIcon(Display.getAccountCandidateImageResource(account));
            setName(Display.getAccountCandidateName(account));
        }
    }

    private static int getAccountCandidateImageResource(Account account) {
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

    private static String getAccountCandidateName(Account account) {
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

    private static int getAccountDisplayImageResource(Account account) {
        if (account instanceof BankCardAccount) {
            return ((BankCardAccount) account).getBank().getImageResource();
        } else if (account instanceof InvestmentAccount) {
            return ((InvestmentAccount) account).getPlatform().getImageResource();
        } else {
            return getAccountCandidateImageResource(account);
        }
    }

    private static String getAccountDisplayName(Account account) {
        if (account instanceof BankCardAccount) {
            return ((BankCardAccount) account).getBank().getName();
        } else if (account instanceof InvestmentAccount) {
            return ((InvestmentAccount) account).getPlatform().getName();
        } else {
            return getAccountCandidateName(account);
        }
    }

    private static String getAccountDisplayRemark(Account account) {
        if (account instanceof BankCardAccount) {
            BankCardAccount bankCardAccount = ((BankCardAccount) account);
            if (StringUtils.isEmpty(bankCardAccount.getBankCardNumber())) {
                return bankCardAccount.getBank().getName();
            } else {
                return String.format("%s %s", bankCardAccount.getBank().getName(), bankCardAccount.getBankCardNumberTail());
            }
        } else {
            return account.getRemark();
        }
    }

    private static int getInvestmentProjectCandidateImageResource(InvestmentProject investmentProject) {
        switch (investmentProject.getType()) {
            case InvestmentProject.MONETARY_FUND: return R.drawable.ic_account_investment;
            default: return R.drawable.ic_account_investment;
        }
    }

    private static int getBankImageResource(Bank bank) {
        return R.drawable.ic_bank_icbc;
    }

}
