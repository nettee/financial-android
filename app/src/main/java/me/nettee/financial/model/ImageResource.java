package me.nettee.financial.model;

import me.nettee.financial.R;
import me.nettee.financial.model.account.Account;

public class ImageResource {

    public static int getAccountCandidateImageResource(Account account) {
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

    public static int getBankImageResource(Bank bank) {
        return R.drawable.ic_bank_icbc;
    }
}
