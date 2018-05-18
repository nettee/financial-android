package me.nettee.financial.model;

public class Bank {

    public enum BankType {
        ICBC,
    }

    private final BankType type;
    private final String name;

    private Bank(BankType type, String name) {
        this.type = type;
        this.name = name;
    }

    public static Bank icbc() {
        return new Bank(BankType.ICBC, "工商银行");
    }

    public BankType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}
