package me.nettee.financial.model.account;

import java.util.Optional;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.CreditDate;
import me.nettee.financial.model.asset.Liability;

public final class CreditCardAccount extends BankCardAccount {

    private static final long serialVersionUID = 1L;

    private Amount mCreditLimit = Amount.zero();
    private CreditDate mBillDate = CreditDate.first();
    private CreditDate mPaymentDate = CreditDate.first();
    private Amount mArrears = Amount.zero();

    @Override
    public AccountType getType() {
        return AccountType.CREDIT_CARD;
    }

    @Override
    public String getCandidateName() {
        return "信用卡";
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
