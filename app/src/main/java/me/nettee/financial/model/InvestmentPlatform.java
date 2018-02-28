package me.nettee.financial.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.nettee.financial.R;

public class InvestmentPlatform implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int ANT_FORTUNE = 1;
    public static final int LUFAX = 2;
    public static final int TIANTIAN_FUND = 3;

    public static final InvestmentPlatform antFortune = new InvestmentPlatform(ANT_FORTUNE, "蚂蚁财富", R.drawable.ic_ant_fortune);
    public static final InvestmentPlatform lufax = new InvestmentPlatform(LUFAX, "陆金所", R.drawable.ic_lufax);
    public static final InvestmentPlatform tiantianFund = new InvestmentPlatform(TIANTIAN_FUND, "天天基金", R.drawable.ic_tiantian_fund);

    private static final List<InvestmentPlatform> platforms = new ArrayList<InvestmentPlatform>() {
        {
            add(antFortune);
            add(lufax);
            add(tiantianFund);
        }
    };

    public static final List<InvestmentPlatform> getPlatforms() {
        // Note: Do not use Arrays.asList here, otherwise the AutoCompleteTextView
        // will throw an exception.
        return platforms;
    }

    public static final InvestmentPlatform getPlatformOrDefault(String name) {
        return getPlatformByName(name, InvestmentPlatform.antFortune);
    }

    public static final InvestmentPlatform getPlatformByName(String name) {
        return getPlatformByName(name, null);
    }

    private static final InvestmentPlatform getPlatformByName(String name, InvestmentPlatform defaultPlatform) {
        for (InvestmentPlatform platform : platforms) {
            if (platform.getName().equals(name)) {
                return platform;
            }
        }
        return defaultPlatform;
    }


    private final int mType;
    private final String mName;
    private final int mImageResource;

    public InvestmentPlatform(int type, String name, int imageResource) {
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
