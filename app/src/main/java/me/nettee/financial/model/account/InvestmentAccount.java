package me.nettee.financial.model.account;

import java.util.Optional;

import me.nettee.financial.model.Amount;
import me.nettee.financial.model.Asset;
import me.nettee.financial.model.InvestmentPlatform;

public final class InvestmentAccount extends Account {

    private static final long serialVersionUID = 1L;

    private InvestmentPlatform mPlatform;

    // TODO For test only.
    private Amount mAmount = Amount.zero();

    @Override
    public int getType() {
        return INVESTMENT;
    }

    @Override
    public String getCandidateName() {
        return getPlatform().getName();
    }

    @Override
    public int getCandidateImageResource() {
        return getPlatform().getImageResource();
    }

    @Override
    public Amount getDefaultAmount() {
        return mAmount;
    }

    @Override
    public String getDefaultAmountCaption() {
        return "账户总额";
    }

    @Override
    public Optional<Asset> getAsset() {
        return Optional.of(new Asset(mAmount));
    }

    public InvestmentPlatform getPlatform() {
        return mPlatform;
    }

    public void setPlatform(InvestmentPlatform platform) {
        mPlatform = platform;
    }

    public Amount getAmount() {
        return mAmount;
    }

    public void setAmount(Amount amount) {
        mAmount = amount;
    }
}
