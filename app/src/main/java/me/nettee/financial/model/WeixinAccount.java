package me.nettee.financial.model;

import me.nettee.financial.R;

public class WeixinAccount extends Account {

    private int mAmount;

    public WeixinAccount() {
        this(0);
    }

    public WeixinAccount(int amount) {
        mAmount = amount;
    }

    @Override
    public int getType() {
        return WEIXIN;
    }

    @Override
    public String getCandidateName() {
        return "微信钱包";
    }

    public int getDefaultAmount() {
        return mAmount;
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_wxpay;
    }
}
