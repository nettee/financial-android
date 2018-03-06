package me.nettee.financial.model;

import org.joda.time.LocalDate;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import me.nettee.financial.model.investment.InvestmentPlatform;
import me.nettee.financial.model.investment.InvestmentProject;
import me.nettee.financial.model.investment.MonetaryFundInvestmentProject;

public class InvestmentProjectLab {

    private static InvestmentProjectLab sInvestmentProjectLab;

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

    public void addInvestmentProject(InvestmentProject investmentProject) {
        mInvestmentProjects.add(investmentProject);
    }
}
