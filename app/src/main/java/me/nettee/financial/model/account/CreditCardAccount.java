package me.nettee.financial.model.account;

import java.util.Optional;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.Asset;
import me.nettee.financial.model.CreditDate;
import me.nettee.financial.model.Liability;

public final class CreditCardAccount extends BankCardAccount {

    private static final long serialVersionUID = 1L;

    private Amount mCreditLimit = Amount.zero();
    private CreditDate mBillDate = CreditDate.first();
    private CreditDate mPaymentDate = CreditDate.first();
    private Amount mCurrentArrears = Amount.zero();

    @Override
    public int getType() {
        return CREDIT_CARD;
    }

    @Override
    public String getCandidateName() {
        return "信用卡";
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_bank_card;
    }

    @Override
    public Amount getDefaultAmount() {
        return getCurrentArrears();
    }

    @Override
    public Optional<Liability> getLiability() {
        return Optional.of(new Liability(mCurrentArrears));
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

    public Amount getCurrentArrears() {
        return mCurrentArrears;
    }

    public void setCurrentArrears(Amount currentArrears) {
        mCurrentArrears = currentArrears;
    }

}
