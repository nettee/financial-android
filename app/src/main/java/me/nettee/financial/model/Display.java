package me.nettee.financial.model;

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
        }

        @Override
        public Display candidate() {
            return new CandidateAccountDisplay(mAccount);
        }

        public String getRemark() {
            return mRemark;
        }

        private void setRemark(String remark) {
            mRemark = remark;
        }

    }

    private static class CandidateAccountDisplay extends Display {

        private CandidateAccountDisplay(Account account) {
            setIcon(Display.getAccountCandidateImageResource(account));
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

    private static int getAccountDisplayImageResource(Account account) {
        if (account instanceof BankCardAccount) {
            return ((BankCardAccount) account).getBank().getImageResource();
        } else if (account instanceof InvestmentAccount) {
            return ((InvestmentAccount) account).getPlatform().getImageResource();
        } else {
            return getAccountCandidateImageResource(account);
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
