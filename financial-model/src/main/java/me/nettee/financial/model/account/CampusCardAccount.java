package me.nettee.financial.model.account;

public class CampusCardAccount extends CashCardAccount {

    private static final long serialVersionUID = 1L;

    @Override
    public AccountType getType() {
        return AccountType.CAMPUS_CARD;
    }

}
