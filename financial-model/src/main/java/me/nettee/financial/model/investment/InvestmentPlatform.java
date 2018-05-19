package me.nettee.financial.model.investment;

import com.google.common.base.Preconditions;

import java.io.Serializable;

public class InvestmentPlatform implements Serializable {

    private static final long serialVersionUID = 1L;

    public enum Type {
        ANT_FORTUNE,
        LUFAX,
        TIANTIAN_FUND,
        GENERAL,
    }

    private final Type mType;
    private final String mName;

    private InvestmentPlatform(Type type, String name) {
        mType = type;
        mName = name;
    }

    static InvestmentPlatform predefined(Type type) {
        Preconditions.checkArgument(!type.equals(Type.GENERAL));
        String name = getPredefinedName(type);
        return new InvestmentPlatform(type, name);
    }

    static InvestmentPlatform general(String name) {
        return new InvestmentPlatform(Type.GENERAL, name);
    }

    private static String getPredefinedName(Type type) {
        switch (type) {
            case ANT_FORTUNE: return "蚂蚁财富";
            case LUFAX: return "陆金所";
            case TIANTIAN_FUND: return "天天基金";
            default: throw new IllegalArgumentException();
        }
    }

    public Type getType() {
        return mType;
    }

    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return mName;
    }
}
