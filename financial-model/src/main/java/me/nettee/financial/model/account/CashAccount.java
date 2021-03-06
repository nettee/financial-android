package me.nettee.financial.model.account;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

import me.nettee.financial.model.Amount;
import me.nettee.financial.model.asset.Asset;

public final class CashAccount extends Account {

    private static final long serialVersionUID = 1L;

    private Amount mBalance = Amount.zero();

    public static CashAccount fromJson(JSONObject jsonObject) throws JSONException {
        CashAccount account = new CashAccount();
        account.setUuid(jsonObject.getString("uuid"));
        account.setBalance(Amount.valueOf(jsonObject.getString("balance")));
        account.setRemark(jsonObject.getString("remark"));
        return account;
    }

    @Override
    public JSONObject toJson() throws JSONException {
        JSONObject object = new JSONObject();
        object.put("uuid", getUuid());
        object.put("type", getType().toString());
        object.put("remark", getRemark());
        object.put("balance", getBalance().toString());
        return object;
    }

    @Override
    public AccountType getType() {
        return AccountType.CASH;
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
