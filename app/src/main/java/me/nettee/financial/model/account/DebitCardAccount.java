package me.nettee.financial.model.account;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

import me.nettee.financial.model.Amount;
import me.nettee.financial.model.asset.Asset;

public final class DebitCardAccount extends BankCardAccount {

    private static final long serialVersionUID = 1L;

    private Amount mBalance = Amount.zero();

    public static DebitCardAccount fromJson(JSONObject jsonObject) throws JSONException {
        DebitCardAccount account = new DebitCardAccount();
        account.setUuid(jsonObject.getString("uuid"));
        account.setRemark(jsonObject.getString("remark"));
        account.setBankCardNumber(jsonObject.getString("bankCardNumber"));
        account.setBalance(Amount.valueOf(jsonObject.getString("balance")));
        return account;
    }

    @Override
    public AccountType getType() {
        return AccountType.DEBIT_CARD;
    }

    @Override
    public String getCandidateName() {
        return "借记卡";
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

    public void setBalance(Amount balance) {
        mBalance = balance;
    }

}
