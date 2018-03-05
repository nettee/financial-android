package me.nettee.financial.model.investment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.nettee.financial.R;

public abstract class InvestmentProject implements Serializable {

    public static final int MONETARY_FUND = 1;
    public static final int FIXED = 2;
    public static final int FUND = 3;
    public static final int STOCK = 4;
    public static final int OTHER = 5;

    private static List<InvestmentProject> sCandidateInvestmentProjects = new ArrayList<InvestmentProject>() {
        private static final long serialVersionUID = 1L;
        {
            add(candidate(MONETARY_FUND, "货币基金", R.drawable.ic_investment_project_monetary_fund));
            add(candidate(FIXED, "定期类", R.drawable.ic_investment_project_fixed));
            add(candidate(FUND, "基金", R.drawable.ic_investment_project_fund));
            add(candidate(STOCK, "股票", R.drawable.ic_investment_project_stock));
            add(candidate(OTHER, "其他投资项目", R.drawable.ic_investment_project_other));
        }
    };

    public abstract int getType();

    public abstract String getCandidateName();

    public abstract int getCandidateImageResource();

    public abstract String getName();

    private static CandidateInvestmentProject candidate(int type, String candidateName, int candidateImageResource) {
        return new CandidateInvestmentProject(type, candidateName, candidateImageResource);
    }

    public static List<InvestmentProject> getCandidateInvestmentProjects() {
        return sCandidateInvestmentProjects;
    }

    private static final class CandidateInvestmentProject extends InvestmentProject implements Serializable {

        private int mType;
        private String mCandidateName;
        private int mCandidateImageResource;

        private CandidateInvestmentProject(int type, String candidateName, int candidateImageResource) {
            mType = type;
            mCandidateName = candidateName;
            mCandidateImageResource = candidateImageResource;
        }

        @Override
        public int getType() {
            return mType;
        }

        @Override
        public String getCandidateName() {
            return mCandidateName;
        }

        @Override
        public int getCandidateImageResource() {
            return mCandidateImageResource;
        }

        @Override
        public String getName() {
            throw new UnsupportedOperationException();
        }
    }

}
