package me.nettee.financial.model;

import me.nettee.financial.R;

public class BusCardAccount extends CashCardAccount {

    public BusCardAccount() {
        this(0);
    }

    public BusCardAccount(int amount) {
        super(amount);
    }

    @Override
    public int getCashCardType() {
        return BUS_CARD;
    }

    @Override
    public String getName() {
        return "公交卡";
    }

    @Override
    public int getImageId() {
        return R.drawable.ic_bus;
    }
}
