package me.nettee.financial.model;

import me.nettee.financial.R;

public class AlipayAccount extends Account {

    private Amount mBalance = Amount.zero();

    @Override
    public int getType() {
        return ALIPAY;
    }

    @Override
    public String getCandidateName() {
        return "支付宝";
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_alipay;
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
