package me.nettee.financial.model.account;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;

public final class AlipayAccount extends MobilePaymentAccount {

    private static final long serialVersionUID = 1L;

    private boolean mYuebaoOpen;
    private boolean mHuabeiOpen;

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

    public boolean isYuebaoOpen() {
        return mYuebaoOpen;
    }

    public void setYuebaoOpen(boolean yuebaoOpen) {
        mYuebaoOpen = yuebaoOpen;
    }

    public boolean isHuabeiOpen() {
        return mHuabeiOpen;
    }

    public void setHuabeiOpen(boolean huabeiOpen) {
        mHuabeiOpen = huabeiOpen;
    }
}
