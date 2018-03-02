package me.nettee.financial.model.account;

import java.util.Optional;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.Asset;

public final class DebitCardAccount extends BankCardAccount {

    private static final long serialVersionUID = 1L;

    private Amount mBalance = Amount.zero();

    @Override
    public int getType() {
        return DEBIT_CARD;
    }

    @Override
    public String getCandidateName() {
        return "借记卡";
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_bank_card;
    }

    @Override
    public Amount getDefaultAmount() {
        return getBalance();
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
