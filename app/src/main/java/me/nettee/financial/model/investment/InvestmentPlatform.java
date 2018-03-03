package me.nettee.financial.model.investment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.nettee.financial.R;

public class InvestmentPlatform implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int GENERAL = 0;
    public static final int ANT_FORTUNE = 1;
    public static final int LUFAX = 2;
    public static final int TIANTIAN_FUND = 3;

    private final int mType;
    private final String mName;
    private final int mImageResource;

    private InvestmentPlatform(int type, String name, int imageResource) {
        mType = type;
        mName = name;
        mImageResource = imageResource;
    }

    private static final List<InvestmentPlatform> platforms = new ArrayList<InvestmentPlatform>() {
        private static final long serialVersionUID = 1L;
        {
            add(new InvestmentPlatform(ANT_FORTUNE, "蚂蚁财富", R.drawable.ic_ant_fortune));
            add(new InvestmentPlatform(LUFAX, "陆金所", R.drawable.ic_lufax));
            add(new InvestmentPlatform(TIANTIAN_FUND, "天天基金", R.drawable.ic_tiantian_fund));
        }
    };

    public static List<InvestmentPlatform> getPlatforms() {
        // Note: Do not use Arrays.asList here, otherwise the AutoCompleteTextView
        // will throw an exception.
        return platforms;
    }

    public static InvestmentPlatform getPlatformOrGeneral(String name) {
        InvestmentPlatform generalPlatform = new InvestmentPlatform(GENERAL, name, R.drawable.ic_investment);
        return getPlatformByName(name, generalPlatform);
    }

    public static InvestmentPlatform getPlatformByName(String name) {
        return getPlatformByName(name, null);
    }

    private static InvestmentPlatform getPlatformByName(String name, InvestmentPlatform defaultPlatform) {
        for (InvestmentPlatform platform : platforms) {
            if (platform.getName().equals(name)) {
                return platform;
            }
        }
        return defaultPlatform;
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
