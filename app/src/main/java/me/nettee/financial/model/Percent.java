package me.nettee.financial.model;

import org.apache.commons.lang3.StringUtils;

import java.text.DecimalFormat;
import java.text.ParseException;

public class Percent {

    private final double mValue;

    private Percent(double value) {
        mValue = value;
    }

    public static Percent zero() {
        return new Percent(0.0);
    }

    public static Percent integer(int i) {
        return new Percent((double) i);
    }

    public static Percent valueOf(double d) {
        return new Percent(d);
    }

    public static Percent valueOf(String s) {
        if (StringUtils.isEmpty(s)) {
            return zero();
        }
        try {
            double d = new DecimalFormat().parse(s).doubleValue();
            return new Percent(d);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public double getValue() {
        return mValue;
    }

    @Override
    public String toString() {
        return String.format("%.4f%%", mValue);
    }
}
