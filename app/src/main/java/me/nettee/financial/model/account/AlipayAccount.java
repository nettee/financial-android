package me.nettee.financial.model.account;

import java.util.Optional;

import me.nettee.financial.R;
import me.nettee.financial.model.asset.Asset;
import me.nettee.financial.model.asset.Liability;

public final class AlipayAccount extends MobilePaymentAccount {

    private static final long serialVersionUID = 1L;

    private boolean mYuebaoOpen;
    private boolean mHuabeiOpen;

    private HuabeiAccount mHuabeiAccount;

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
    public Optional<Asset> getAsset() {
        return Optional.of(new Asset(getBalance()));
    }

    @Override
    public Optional<Liability> getLiability() {
        if (isHuabeiOpen()) {
            HuabeiAccount huabeiAccount = getHuabeiAccount();
            return Optional.of(new Liability(huabeiAccount.getArrears()));
        } else {
            return Optional.empty();
        }
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

    public HuabeiAccount getHuabeiAccount() {
        return mHuabeiAccount;
    }

    public void setHuabeiAccount(HuabeiAccount huabeiAccount) {
        mHuabeiAccount = huabeiAccount;
    }
}
