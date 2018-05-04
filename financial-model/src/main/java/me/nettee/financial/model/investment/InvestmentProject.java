package me.nettee.financial.model.investment;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.nettee.financial.model.Amount;

public abstract class InvestmentProject implements Serializable {

    public static final int MONETARY_FUND = 1;
    public static final int FIXED = 2;
    public static final int FUND = 3;
    public static final int STOCK = 4;
    public static final int OTHER = 5;

    private int mInvestmentPlatformType;

    public abstract int getType();

    public abstract String getCandidateName();

    public final int getCandidateImageResource() {
        try {
            Class<?> class_ = Class.forName("me.nettee.financial.model.ImageResource");
            Method method = class_.getMethod("getInvestmentProjectCandidateImageResource", InvestmentProject.class);
            return (int) (Integer) method.invoke(null, this);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return 0;
        }
    }

    public abstract String getName();

    public abstract Amount getPrinciple();

    public Amount getTotalAmount() {
        return getPrinciple();
    }

    public final int getInvestmentPlatformType() {
        return mInvestmentPlatformType;
    }

    public final void setInvestmentPlatformType(int investmentPlatformType) {
        mInvestmentPlatformType = investmentPlatformType;
    }

}
