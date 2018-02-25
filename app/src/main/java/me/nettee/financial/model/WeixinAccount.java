package me.nettee.financial.model;

import me.nettee.financial.R;

public class WeixinAccount extends Account {

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

    public Amount getBalance() {
        return mBalance;
    }

    public void setBalance(Amount balance) {
        mBalance = balance;
    }
}
