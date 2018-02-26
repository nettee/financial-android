package me.nettee.financial.model;

import android.util.Log;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;

public class Amount implements Serializable {

    int yuan;
    int cent;

    public static Amount zero() {
        Amount amount = new Amount();
        amount.yuan = 0;
        amount.cent = 0;
        return amount;
    }

    public static Amount integer(int yuan) {
        Amount amount = new Amount();
        amount.yuan = yuan;
        amount.cent = 0;
        return amount;
    }

    public static Amount decimal(int yuan, int cent) {
        Amount amount = new Amount();
        amount.yuan = yuan;
        amount.cent = cent;
        return amount;
    }

    public static Amount valueOf(String s) {
        if (s.length() == 0) {
            return Amount.integer(0);
        }
        if (!s.contains(".")) {
            int yuan = Integer.valueOf(s);
            return Amount.integer(yuan);
        }
        int i = s.indexOf('.');
        String s1 = s.substring(0, i);
        try {
            int yuan = new DecimalFormat().parse(s1).intValue();
            int cent = Integer.valueOf(s.substring(i + 1, i + 3));
            return Amount.decimal(yuan, cent);
        } catch (ParseException e) {
            Log.e("Amount", e.getMessage());
            return Amount.zero();
        }
    }

    @Override
    public String toString() {
        String a = new DecimalFormat(",###").format(yuan);
        String y = String.format("%s.%02d", a, cent);
        return y;
    }

    public String toYuanString() {
        return "¥" + toString();
    }
}
