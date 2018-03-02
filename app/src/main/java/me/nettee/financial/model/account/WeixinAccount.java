package me.nettee.financial.model.account;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;

public final class WeixinAccount extends MobilePaymentAccount {

    private static final long serialVersionUID = 1L;

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

}
