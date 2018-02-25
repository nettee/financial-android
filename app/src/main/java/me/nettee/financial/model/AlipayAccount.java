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
    public String getCandidateName() {
        return "支付宝";
    }

    public int getDefaultAmount() {
        return mAmount;
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_alipay;
    }
}
