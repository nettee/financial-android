package me.nettee.financial.model;

import java.util.UUID;

public class Account {

    private final UUID mId;
    private String mName;
    private int mAmount;

    public Account(String name, int amount) {
        mId = UUID.randomUUID();
        mName = name;
        mAmount = amount;
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }
}
