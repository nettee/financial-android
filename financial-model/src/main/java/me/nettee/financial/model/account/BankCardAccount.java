package me.nettee.financial.model.account;

import org.apache.commons.lang3.StringUtils;

import me.nettee.financial.model.Bank;

public abstract class BankCardAccount extends Account {

    private static final long serialVersionUID = 1L;

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

}
