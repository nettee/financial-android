package me.nettee.financial.model.account;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;

public final class AlipayAccount extends MobilePaymentAccount {

    private static final long serialVersionUID = 1L;

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

}
