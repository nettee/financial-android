package me.nettee.financial.model.investment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.nettee.financial.R;
import me.nettee.financial.model.account.Account;

public abstract class InvestmentProject implements Serializable {

    public static final int CURRENT_DEPOSIT = 0;
    public static final int MONETARY_FUND = 1;
    public static final int FIXED = 2;
    public static final int FUND = 3;
    public static final int STOCK = 4;
    public static final int OTHER = 5;

    private static List<InvestmentProject> sCandidateInvestmentProjects = new ArrayList<InvestmentProject>() {
        private static final long serialVersionUID = 1L;
        {
            add(candidate(MONETARY_FUND, "货币基金", R.drawable.ic_account_investment));
        }
    };

    public abstract int getType();

    public abstract String getCandidateName();

    public abstract int getCandidateImageResource();

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
    }

}
