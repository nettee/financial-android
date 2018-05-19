package me.nettee.financial.model.investment;

import java.util.ArrayList;
import java.util.List;

import me.nettee.financial.R;

public class InvestmentPlatformLab {

    private static final List<InvestmentPlatform> platforms = new ArrayList<InvestmentPlatform>() {
        private static final long serialVersionUID = 1L;
        {
            add(InvestmentPlatform.predefined(InvestmentPlatform.Type.ANT_FORTUNE));
            add(InvestmentPlatform.predefined(InvestmentPlatform.Type.LUFAX));
            add(InvestmentPlatform.predefined(InvestmentPlatform.Type.TIANTIAN_FUND));
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
        return getPlatformByName(name, InvestmentPlatform.general(name));
    }

    public static InvestmentPlatform getPlatformByName(String name) {
        return getPlatformByName(name, null);
    }

}
