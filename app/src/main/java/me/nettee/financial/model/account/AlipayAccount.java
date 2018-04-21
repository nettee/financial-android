package me.nettee.financial.model.account;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.CreditDate;
import me.nettee.financial.model.asset.Asset;
import me.nettee.financial.model.asset.Liability;

public final class AlipayAccount extends MobilePaymentAccount {

    private static final long serialVersionUID = 1L;

    private boolean mYuebaoOpen;
    private boolean mHuabeiOpen;

    private HuabeiAccount mHuabeiAccount;

    public static AlipayAccount fromJson(JSONObject jsonObject) throws JSONException {
        AlipayAccount account = new AlipayAccount();
        account.setUuid(jsonObject.getString("uuid"));
        account.setRemark(jsonObject.getString("remark"));
        account.setBalance(Amount.valueOf(jsonObject.getString("balance")));
        account.setYuebaoOpen(false);
        if (jsonObject.has("huabei")) {
            JSONObject huabeiObject = jsonObject.getJSONObject("huabei");
            HuabeiAccount huabeiAccount = new HuabeiAccount();
            huabeiAccount.setCreditLimit(Amount.valueOf(huabeiObject.getString("creditLimit")));
            huabeiAccount.setBillDate(CreditDate.day(huabeiObject.getInt("billDate")));
            huabeiAccount.setPaymentDate(CreditDate.day(huabeiObject.getInt("paymentDate")));
            Amount arrears = Amount.valueOf(huabeiObject.getString("arrears"));
            arrears.setSign(Amount.NEGATIVE);
            huabeiAccount.setArrears(arrears);
            account.setHuabeiOpen(true);
            account.setHuabeiAccount(huabeiAccount);
        }
        return account;
    }

    @Override
    public AccountType getType() {
        return AccountType.ALIPAY;
    }

    @Override
    public String getCandidateName() {
        return "支付宝";
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
