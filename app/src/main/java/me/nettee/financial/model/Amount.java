package me.nettee.financial.model;

import android.util.Log;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;

public class Amount implements Serializable {

    public static final int POSITIVE = 0;
    public static final int NEGATIVE = 1;

    int sign = POSITIVE;
    int yuan;
    int cent;

    public static Amount zero() {
        return integer(0);
    }

    public static Amount integer(int yuan) {
        return decimal(yuan, 0);
    }

    public static Amount decimal(int yuan, int cent) {
        Amount amount = new Amount();
        amount.sign = yuan >= 0 ? POSITIVE : NEGATIVE;
        amount.yuan = Math.abs(yuan);
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

    public boolean isPositive() {
        return sign == POSITIVE;
    }

    public boolean isNegative() {
        return sign == NEGATIVE;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    private String format(boolean withYuan) {
        String a = new DecimalFormat(",###").format(yuan);
        return String.format("%s%s%s.%02d", isNegative() ? "-" : "", withYuan ? "¥" : "", a, cent);
    }

    @Override
    public String toString() {
        return format(false);
    }

    public String toYuanString() {
        return format(true);
    }
}
