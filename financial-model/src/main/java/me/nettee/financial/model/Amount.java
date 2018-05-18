package me.nettee.financial.model;

import com.google.common.base.Preconditions;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

public class Amount implements Comparable<Amount>, Serializable {

    private final boolean positive;
    private final int yuan;
    private final int cent;

    private Amount() {
        this(true, 0, 0);
    }

    private Amount(boolean positive, int yuan, int cent) {
        Preconditions.checkArgument(yuan >= 0);
        Preconditions.checkArgument(!(yuan == 0 && !positive));
        Preconditions.checkArgument(cent >= 0 && cent < 100);
        this.positive = positive;
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
        return new Amount(yuan >= 0, Math.abs(yuan), cent);
    }

    private static Amount fromCents(int cents) {
        boolean positive = cents >= 0;
        cents = Math.abs(cents);
        return new Amount(positive, cents / 100, cents % 100);
    }

    private int toCents() {
        int c = yuan * 100 + cent;
        return positive ? c : -c;
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
        return new Amount(true, this.yuan, this.cent);
    }

    public Amount neg() {
        return new Amount(!this.positive, this.yuan, this.cent);
    }

    public Amount add(Amount that) {
        int c1 = this.toCents();
        int c2 = that.toCents();
        return Amount.fromCents(c1 + c2);
    }

    public Amount sub(Amount that) {
        int c1 = this.toCents();
        int c2 = that.toCents();
        return Amount.fromCents(c1 - c2);
    }

    public boolean isPositive() {
        return positive;
    }

    public boolean isNegative() {
        return !positive;
    }

    private String format(boolean withYuan) {
        String a = new DecimalFormat(",###").format(yuan);
        return String.format(Locale.CHINA, "%s%s%s.%02d", isNegative() ? "-" : "", withYuan ? "¥" : "", a, cent);
    }

    @Override
    public int compareTo(Amount that) {
        return this.toCents() - that.toCents();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Amount) {
            Amount that = (Amount) o;
            return this.compareTo(that) == 0;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return format(false);
    }

    public String toYuanString() {
        return format(true);
    }
}
