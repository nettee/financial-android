package me.nettee.financial.model;

import com.google.common.base.Preconditions;

import java.io.Serializable;

public class CreditDate implements Serializable {

    private final int day;

    private CreditDate(int day) {
        Preconditions.checkArgument(day >= 1 && day <= 28);
        this.day = day;
    }

    public static CreditDate first() {
        return day(1);
    }

    public static CreditDate day(int day) {
        if (day < 1 || day > 28) {
            throw new IllegalArgumentException();
        }
        return new CreditDate(day);
    }

    public int getDay() {
        return day;
    }

}
