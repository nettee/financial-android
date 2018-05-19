package me.nettee.financial.model.investment;

import org.joda.time.LocalDate;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.Percent;

public class InvestmentProjectLab {

    private static InvestmentProjectLab sInvestmentProjectLab;

    private static List<InvestmentProject> sCandidateInvestmentProjects = new ArrayList<InvestmentProject>() {
        private static final long serialVersionUID = 1L;
        {
            InvestmentProject.InvestmentProjectType[] types = {
                    InvestmentProject.InvestmentProjectType.MONETARY_FUND,
                    InvestmentProject.InvestmentProjectType.FIXED,
                    InvestmentProject.InvestmentProjectType.FUND,
                    InvestmentProject.InvestmentProjectType.STOCK,
                    InvestmentProject.InvestmentProjectType.OTHER,
            };
            for (InvestmentProject.InvestmentProjectType type : types) {
                add(InvestmentProject.candidate(type));
            }
        }
    };

    private final List<InvestmentProject> mInvestmentProjects;

    private InvestmentProjectLab() {
        mInvestmentProjects = new ArrayList<>();
        MonetaryFundInvestmentProject monetaryFundInvestmentProject1 = new MonetaryFundInvestmentProject();
        monetaryFundInvestmentProject1.setInvestmentPlatformType(InvestmentPlatform.Type.ANT_FORTUNE);
        monetaryFundInvestmentProject1.setName("超值基金");
        monetaryFundInvestmentProject1.setPrinciple(Amount.integer(10000));
        monetaryFundInvestmentProject1.setAnnualYield(Percent.valueOf(4.5521));
        monetaryFundInvestmentProject1.setBuyDate(new LocalDate("2018-02-01"));
        mInvestmentProjects.add(monetaryFundInvestmentProject1);
        MonetaryFundInvestmentProject ljb = new MonetaryFundInvestmentProject();
        ljb.setInvestmentPlatformType(InvestmentPlatform.Type.LUFAX);
        ljb.setName("陆金宝T+1");
        ljb.setPrinciple(Amount.integer(1000));
        ljb.setAnnualYield(Percent.valueOf(4.38));
        ljb.setBuyDate(new LocalDate("2018-02-10"));
        ljb.setValueDate(new LocalDate("2018-02-13"));
        mInvestmentProjects.add(ljb);
        MonetaryFundInvestmentProject nfttl = new MonetaryFundInvestmentProject();
        nfttl.setInvestmentPlatformType(InvestmentPlatform.Type.TIANTIAN_FUND);
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

    public List<InvestmentProject> getInvestmentProjects(InvestmentPlatform.Type investmentPlatformType) {
        List<InvestmentProject> results = new ArrayList<>();
        for (InvestmentProject investmentProject : mInvestmentProjects) {
            if (investmentProject.getInvestmentPlatformType().equals(investmentPlatformType)) {
                results.add(investmentProject);
            }
        }
        return results;
    }

    public static List<InvestmentProject> getCandidateInvestmentProjects() {
        return sCandidateInvestmentProjects;
    }

    public void addInvestmentProject(InvestmentProject investmentProject) {
        mInvestmentProjects.add(investmentProject);
    }

}
