package me.nettee.financial.model.investment;

import java.io.Serializable;

import me.nettee.financial.model.Amount;

public abstract class InvestmentProject implements Serializable {

    public enum InvestmentProjectType {
        MONETARY_FUND,
        FIXED,
        FUND,
        STOCK,
        OTHER,
        ;
    }

    private InvestmentPlatform.Type mInvestmentPlatformType;

    public abstract InvestmentProjectType getType();

    public abstract String getName();

    public abstract Amount getPrinciple();

    public Amount getTotalAmount() {
        return getPrinciple();
    }

    public final InvestmentPlatform.Type getInvestmentPlatformType() {
        return mInvestmentPlatformType;
    }

    public final void setInvestmentPlatformType(InvestmentPlatform.Type investmentPlatformType) {
        mInvestmentPlatformType = investmentPlatformType;
    }

    public static CandidateInvestmentProject candidate(InvestmentProjectType type) {
        return new CandidateInvestmentProject(type);
    }

    private static final class CandidateInvestmentProject extends InvestmentProject implements Serializable {

        private static final long serialVersionUID = 1L;

        private final InvestmentProjectType mType;

        private CandidateInvestmentProject(InvestmentProjectType type) {
            mType = type;
        }

        @Override
        public InvestmentProjectType getType() {
            return mType;
        }

        @Override
        public String getName() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Amount getPrinciple() {
            throw new UnsupportedOperationException();
        }
    }

}
