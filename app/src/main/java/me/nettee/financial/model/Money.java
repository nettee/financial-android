package me.nettee.financial.model;

import android.content.Intent;

import java.text.DecimalFormat;

public class Money {

    public static String format(int amount) {
        String a = new DecimalFormat(",###").format(amount / 100);
        String y = String.format("¥%s.%02d", a, amount % 100);
        return y;
    }

    public static int from(String amountString) {
        if (amountString.length() == 0) {
            return 0;
        }
        if (!amountString.contains(".")) {
            int yuan = Integer.valueOf(amountString);
            return yuan * 100;
        }
        int i = amountString.indexOf('.');
        int yuan = Integer.valueOf(amountString.substring(0, i));
        int cent = Integer.valueOf(amountString.substring(i + 1, i + 3));
        return yuan * 100 + cent;
    }
}
