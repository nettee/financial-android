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
    public String getName() {
        return "微信钱包";
    }

    public int getAmount() {
        return mAmount;
    }

    @Override
    public int getImageId() {
        return R.drawable.ic_wxpay;
    }
}
