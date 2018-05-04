package me.nettee.financial.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;

public class Amount implements Serializable {

    public static final int POSITIVE = 0;
    public static final int NEGATIVE = 1;

    int sign;
    int yuan;
    int cent;

    private Amount() {
        this(POSITIVE, 0, 0);
    }

    private Amount(int yuan, int cent) {
        this(POSITIVE, yuan, cent);
    }

    private Amount(int sign, int yuan, int cent) {
        this.sign = sign;
        this.yuan = yuan;
        this.cent = cent;
    }

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

    private static Amount fromCents(int cents) {
        int sign = cents >= 0 ? POSITIVE : NEGATIVE;
        cents = Math.abs(cents);
        int yuan = cents / 100;
        int cent = cents % 100;
        return new Amount(sign, yuan, cent);
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
            return Amount.zero();
        }
    }

    public Amount abs() {
        return new Amount(POSITIVE, this.yuan, this.cent);
    }

    public Amount neg() {
        int sign = this.sign == POSITIVE ? NEGATIVE : POSITIVE;
        return new Amount(sign, this.yuan, this.cent);
    }

    public Amount add(Amount that) {
        int c1 = this.toCents();
        int c2 = that.toCents();
        int c = c1 + c2;
        return Amount.fromCents(c);
    }

    public Amount sub(Amount that) {
        int c1 = this.toCents();
        int c2 = that.toCents();
        int c = c1 - c2;
        return Amount.fromCents(c);
    }

    public Amount subUnsigned(Amount that) {
        int c1 = this.toCents();
        int c2 = that.toCents();
        int c = Math.abs(c1) - Math.abs(c2);
        return Amount.fromCents(c);
    }

    private int toCents() {
        int c = yuan * 100 + cent;
        if (isNegative()) {
            c = -c;
        }
        return c;
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
