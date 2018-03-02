package me.nettee.financial.model.account;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;

public final class WeixinAccount extends Account {

    private static final long serialVersionUID = 1L;

    private Amount mBalance = Amount.zero();

    @Override
    public int getType() {
        return WEIXIN;
    }

    @Override
    public String getCandidateName() {
        return "微信钱包";
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_wxpay;
    }

    @Override
    public Amount getDefaultAmount() {
        return getBalance();
    }

    @Override
    public String getDefaultAmountCaption() {
        return "账户余额";
    }

    public Amount getBalance() {
        return mBalance;
    }

    public void setBalance(Amount balance) {
        mBalance = balance;
    }
}
