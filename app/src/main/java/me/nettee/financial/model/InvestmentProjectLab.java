package me.nettee.financial.model;

import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import me.nettee.financial.R;
import me.nettee.financial.model.investment.InvestmentPlatform;
import me.nettee.financial.model.investment.InvestmentProject;
import me.nettee.financial.model.investment.MonetaryFundInvestmentProject;

public class InvestmentProjectLab {

    private static InvestmentProjectLab sInvestmentProjectLab;

    private static List<InvestmentProject> sCandidateInvestmentProjects = new ArrayList<InvestmentProject>() {
        private static final long serialVersionUID = 1L;
        {
            add(candidate(InvestmentProject.MONETARY_FUND, "货币基金", R.drawable.ic_investment_project_monetary_fund));
            add(candidate(InvestmentProject.FIXED, "定期类", R.drawable.ic_investment_project_fixed));
            add(candidate(InvestmentProject.FUND, "基金类", R.drawable.ic_investment_project_fund));
//            add(candidate(InvestmentProject.STOCK, "股票", R.drawable.ic_investment_project_stock));
//            add(candidate(InvestmentProject.OTHER, "其他投资项目", R.drawable.ic_investment_project_other));
        }
    };

    private final List<InvestmentProject> mInvestmentProjects;

    private InvestmentProjectLab() {
        mInvestmentProjects = new ArrayList<>();
        MonetaryFundInvestmentProject monetaryFundInvestmentProject1 = new MonetaryFundInvestmentProject();
        monetaryFundInvestmentProject1.setInvestmentPlatformType(InvestmentPlatform.ANT_FORTUNE);
        monetaryFundInvestmentProject1.setName("超值基金");
        monetaryFundInvestmentProject1.setPrinciple(Amount.integer(10000));
        monetaryFundInvestmentProject1.setAnnualYield(Percent.valueOf(4.5521));
        monetaryFundInvestmentProject1.setBuyDate(new LocalDate("2018-02-01"));
        mInvestmentProjects.add(monetaryFundInvestmentProject1);
        MonetaryFundInvestmentProject ljb = new MonetaryFundInvestmentProject();
        ljb.setInvestmentPlatformType(InvestmentPlatform.LUFAX);
        ljb.setName("陆金宝T+1");
        ljb.setPrinciple(Amount.integer(1000));
        ljb.setAnnualYield(Percent.valueOf(4.38));
        ljb.setBuyDate(new LocalDate("2018-02-10"));
        ljb.setValueDate(new LocalDate("2018-02-13"));
        mInvestmentProjects.add(ljb);
        MonetaryFundInvestmentProject nfttl = new MonetaryFundInvestmentProject();
        nfttl.setInvestmentPlatformType(InvestmentPlatform.TIANTIAN_FUND);
        nfttl.setName("南方天天利货币B");
        nfttl.setPrinciple(Amount.integer(2000));
        nfttl.setAnnualYield(Percent.valueOf(4.7290));
        nfttl.setBuyDate(new LocalDate("2018-02-21"));
        nfttl.setValueDate(new LocalDate("2018-02-23"));
        mInvestmentProjects.add(nfttl);
    }

    public static InvestmentProjectLab getInstance() {
        if (sInvestmentProjectLab == null) {
            sInvestmentProjectLab = new InvestmentProjectLab();
        }
        return sInvestmentProjectLab;
    }

    public List<InvestmentProject> getInvestmentProjects() {
        return mInvestmentProjects;
    }

    public List<InvestmentProject> getInvestmentProjects(int investmentPlatformType) {
        List<InvestmentProject> results = new ArrayList<>();
        for (InvestmentProject investmentProject : mInvestmentProjects) {
            if (investmentProject.getInvestmentPlatformType() == investmentPlatformType) {
                results.add(investmentProject);
            }
        }
        return results;
    }

    private static CandidateInvestmentProject candidate(int type, String candidateName, int candidateImageResource) {
        return new CandidateInvestmentProject(type, candidateName, candidateImageResource);
    }

    public static List<InvestmentProject> getCandidateInvestmentProjects() {
        return sCandidateInvestmentProjects;
    }

    public void addInvestmentProject(InvestmentProject investmentProject) {
        mInvestmentProjects.add(investmentProject);
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

        @Override
        public Amount getPrinciple() {
            throw new UnsupportedOperationException();
        }
    }
}
