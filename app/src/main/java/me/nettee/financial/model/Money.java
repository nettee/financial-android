package me.nettee.financial.model;

import java.text.DecimalFormat;

public class Money {

    public static String format(int amount) {
        String a = new DecimalFormat(",###").format(amount / 100);
        String y = String.format("¥%s.%02d", a, amount % 100);
        return y;
    }
}
