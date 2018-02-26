package me.nettee.financial.model;

import org.apache.commons.lang3.StringUtils;

public abstract class BankCardAccount extends Account {

    private String mBankCardNumber;

    public final Bank getBank() {
        return Bank.icbc();
    }

    public final String getBankCardNumber() {
        return mBankCardNumber;
    }

    public final String getBankCardNumberTail() {
        String bankCardNumber = getBankCardNumber();
        if (StringUtils.isEmpty(bankCardNumber)) {
            return bankCardNumber;
        } else if (bankCardNumber.length() <= 4) {
            return bankCardNumber;
        } else {
            return bankCardNumber.substring(bankCardNumber.length() - 4);
        }
    }

    public final void setBankCardNumber(String bankCardNumber) {
        mBankCardNumber = bankCardNumber;
    }

    @Override
    public String getDisplayName() {
        return getBank().getName();
    }

    @Override
    public int getDisplayImageResource() {
        return getBank().getImageResource();
    }

    @Override
    public final String getDisplayRemark() {
        if (StringUtils.isEmpty(mBankCardNumber)) {
            return getCandidateName();
        } else {
            return String.format("%s %s", getCandidateName(), getBankCardNumberTail());
        }
    }
}
