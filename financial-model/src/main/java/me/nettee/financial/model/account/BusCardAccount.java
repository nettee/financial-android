package me.nettee.financial.model.account;

public class BusCardAccount extends CashCardAccount {

    private static final long serialVersionUID = 1L;

    @Override
    public AccountType getType() {
        return AccountType.BUS_CARD;
    }

}
