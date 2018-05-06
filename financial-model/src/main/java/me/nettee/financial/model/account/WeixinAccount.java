package me.nettee.financial.model.account;

public final class WeixinAccount extends MobilePaymentAccount {

    private static final long serialVersionUID = 1L;

    @Override
    public AccountType getType() {
        return AccountType.WEIXIN;
    }

}
