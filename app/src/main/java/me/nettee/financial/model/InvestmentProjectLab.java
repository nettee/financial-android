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
        MonetaryFundInvestmentProject monetaryFundInvestmentProject2 = new MonetaryFundInvestmentProject();
        monetaryFundInvestmentProject2.setInvestmentPlatformType(InvestmentPlatform.LUFAX);
        monetaryFundInvestmentProject2.setName("超值基金2");
        monetaryFundInvestmentProject2.setPrinciple(Amount.integer(20000));
        monetaryFundInvestmentProject2.setAnnualYield(Percent.valueOf(4.5522));
        monetaryFundInvestmentProject2.setBuyDate(new LocalDate("2018-02-02"));
        mInvestmentProjects.add(monetaryFundInvestmentProject2);
        MonetaryFundInvestmentProject monetaryFundInvestmentProject3 = new MonetaryFundInvestmentProject();
        monetaryFundInvestmentProject3.setInvestmentPlatformType(InvestmentPlatform.TIANTIAN_FUND);
        monetaryFundInvestmentProject3.setName("超值基金3");
        monetaryFundInvestmentProject3.setPrinciple(Amount.integer(30000));
        monetaryFundInvestmentProject3.setAnnualYield(Percent.valueOf(4.5523));
        monetaryFundInvestmentProject3.setBuyDate(new LocalDate("2018-02-03"));
        mInvestmentProjects.add(monetaryFundInvestmentProject3);
        MonetaryFundInvestmentProject monetaryFundInvestmentProject4 = new MonetaryFundInvestmentProject();
        monetaryFundInvestmentProject4.setInvestmentPlatformType(InvestmentPlatform.ANT_FORTUNE);
        monetaryFundInvestmentProject4.setName("超值基金4");
        monetaryFundInvestmentProject4.setPrinciple(Amount.integer(40000));
        monetaryFundInvestmentProject4.setAnnualYield(Percent.valueOf(4.5524));
        monetaryFundInvestmentProject4.setBuyDate(new LocalDate("2018-02-04"));
        mInvestmentProjects.add(monetaryFundInvestmentProject4);
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
