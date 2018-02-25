package me.nettee.financial.model;

import me.nettee.financial.R;

public class AlipayAccount extends Account {

    private int mAmount;

    public AlipayAccount() {
        this(0);
    }

    public AlipayAccount(int amount) {
        mAmount = amount;
    }

    @Override
    public int getType() {
        return ALIPAY;
    }

    @Override
    public String getName() {
        return "支付宝";
    }

    public int getAmount() {
        return mAmount;
    }

    @Override
    public int getImageId() {
        return R.drawable.ic_alipay;
    }
}
