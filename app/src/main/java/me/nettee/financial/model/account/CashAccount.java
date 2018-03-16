package me.nettee.financial.model.account;

import java.util.Optional;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.asset.Asset;

public final class CashAccount extends Account {

    private static final long serialVersionUID = 1L;

    private Amount mBalance = Amount.zero();

    @Override
    public AccountType getType() {
        return AccountType.CASH;
    }

    @Override
    public String getCandidateName() {
        return "现金钱包";
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_wallet;
    }

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

    public void setBalance(Amount amount) {
        mBalance = amount;
    }
}
