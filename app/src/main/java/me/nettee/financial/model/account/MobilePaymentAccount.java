package me.nettee.financial.model.account;

import java.util.Optional;

import me.nettee.financial.model.Amount;
import me.nettee.financial.model.asset.Asset;

public abstract class MobilePaymentAccount extends Account {

    private Amount mBalance = Amount.zero();

    @Override
    public Amount getDefaultAmount() {
        return getBalance();
    }

    @Override
    public String getDefaultAmountCaption() {
        return "账户余额";
    }

    @Override
    public Optional<Asset> getAsset() {
        return Optional.of(new Asset(mBalance));
    }

    public Amount getBalance() {
        return mBalance;
    }

    public void setBalance(Amount balance) {
        mBalance = balance;
    }
}
