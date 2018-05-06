package me.nettee.financial.model.account;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

import me.nettee.financial.model.Amount;
import me.nettee.financial.model.CreditDate;
import me.nettee.financial.model.asset.Liability;

public final class CreditCardAccount extends BankCardAccount {

    private static final long serialVersionUID = 1L;

    private Amount mCreditLimit = Amount.zero();
    private CreditDate mBillDate = CreditDate.first();
    private CreditDate mPaymentDate = CreditDate.first();
    private Amount mArrears = Amount.zero();

    public static CreditCardAccount fromJson(JSONObject jsonObject) throws JSONException {
        CreditCardAccount account = new CreditCardAccount();
        account.setUuid(jsonObject.getString("uuid"));
        account.setRemark(jsonObject.getString("remark"));
        account.setBankCardNumber(jsonObject.getString("bankCardNumber"));
        account.setCreditLimit(Amount.valueOf(jsonObject.getString("creditLimit")));
        account.setBillDate(CreditDate.day(jsonObject.getInt("billDate")));
        account.setPaymentDate(CreditDate.day(jsonObject.getInt("paymentDate")));
        Amount arrears = Amount.valueOf(jsonObject.getString("arrears"));
        arrears.setSign(Amount.NEGATIVE);
        account.setArrears(arrears);
        return account;
    }

    @Override
    public Account.AccountType getType() {
        return Account.AccountType.CREDIT_CARD;
    }

    @Override
    public Amount getDefaultAmount() {
        return getArrears();
    }

    @Override
    public String getDefaultAmountCaption() {
        return "当前欠款";
    }

    @Override
    public Optional<Liability> getLiability() {
        return Optional.of(new Liability(mArrears));
    }

    public Amount getCreditLimit() {
        return mCreditLimit;
    }

    public void setCreditLimit(Amount creditLimit) {
        mCreditLimit = creditLimit;
    }

    public CreditDate getBillDate() {
        return mBillDate;
    }

    public void setBillDate(CreditDate billDate) {
        mBillDate = billDate;
    }

    public CreditDate getPaymentDate() {
        return mPaymentDate;
    }

    public void setPaymentDate(CreditDate paymentDate) {
        mPaymentDate = paymentDate;
    }

    public Amount getArrears() {
        return mArrears;
    }

    public void setArrears(Amount arrears) {
        mArrears = arrears;
    }

}
