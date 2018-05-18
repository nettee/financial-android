package me.nettee.financial.model.investment;

import java.io.Serializable;

public class InvestmentPlatform implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int GENERAL = 0;
    public static final int ANT_FORTUNE = 1;
    public static final int LUFAX = 2;
    public static final int TIANTIAN_FUND = 3;

    private final int mType;
    private final String mName;
    private final int mImageResource;

    InvestmentPlatform(int type, String name, int imageResource) {
        mType = type;
        mName = name;
        mImageResource = imageResource;
    }

    public int getType() {
        return mType;
    }

    public String getName() {
        return mName;
    }

    public int getImageResource() {
        return mImageResource;
    }

    @Override
    public String toString() {
        return mName;
    }
}
