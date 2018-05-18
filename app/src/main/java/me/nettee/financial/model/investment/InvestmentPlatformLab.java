package me.nettee.financial.model.investment;

import java.util.ArrayList;
import java.util.List;

import me.nettee.financial.R;

public class InvestmentPlatformLab {

    private static final List<InvestmentPlatform> platforms = new ArrayList<InvestmentPlatform>() {
        private static final long serialVersionUID = 1L;
        {
            add(new InvestmentPlatform(InvestmentPlatform.ANT_FORTUNE, "蚂蚁财富", R.drawable.ic_ant_fortune));
            add(new InvestmentPlatform(InvestmentPlatform.LUFAX, "陆金所", R.drawable.ic_lufax));
            add(new InvestmentPlatform(InvestmentPlatform.TIANTIAN_FUND, "天天基金", R.drawable.ic_tiantian_fund));
        }
    };

    public static List<InvestmentPlatform> getPlatforms() {
        // Note: Do not use Arrays.asList here, otherwise the AutoCompleteTextView
        // will throw an exception.
        return new ArrayList<>(platforms);
    }

    public static InvestmentPlatform getPlatformByName(String name, InvestmentPlatform defaultPlatform) {
        for (InvestmentPlatform platform : platforms) {
            if (platform.getName().equals(name)) {
                return platform;
            }
        }
        return defaultPlatform;
    }

    public static InvestmentPlatform getPlatformOrGeneral(String name) {
        InvestmentPlatform generalPlatform = new InvestmentPlatform(InvestmentPlatform.GENERAL, name, R.drawable.ic_investment);
        return getPlatformByName(name, generalPlatform);
    }

    public static InvestmentPlatform getPlatformByName(String name) {
        return getPlatformByName(name, null);
    }

}
