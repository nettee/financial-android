package me.nettee.financial.model;

import java.util.ArrayList;
import java.util.List;

import me.nettee.financial.model.investment.InvestmentProject;
import me.nettee.financial.model.investment.MonetaryFundInvestmentProject;

public class InvestmentProjectLab {

    private static InvestmentProjectLab sInvestmentProjectLab;

    private final List<InvestmentProject> mInvestmentProjects;

    private InvestmentProjectLab() {
        mInvestmentProjects = new ArrayList<>();
        MonetaryFundInvestmentProject monetaryFundInvestmentProject = new MonetaryFundInvestmentProject();
        monetaryFundInvestmentProject.setName("超值基金");
        monetaryFundInvestmentProject.setPrinciple(Amount.integer(10000));
        mInvestmentProjects.add(monetaryFundInvestmentProject);
        MonetaryFundInvestmentProject monetaryFundInvestmentProject2 = new MonetaryFundInvestmentProject();
        monetaryFundInvestmentProject2.setName("超值基金2");
        monetaryFundInvestmentProject2.setPrinciple(Amount.integer(20000));
        mInvestmentProjects.add(monetaryFundInvestmentProject2);
        MonetaryFundInvestmentProject monetaryFundInvestmentProject3 = new MonetaryFundInvestmentProject();
        monetaryFundInvestmentProject3.setName("超值基金3");
        monetaryFundInvestmentProject3.setPrinciple(Amount.integer(30000));
        mInvestmentProjects.add(monetaryFundInvestmentProject3);
        MonetaryFundInvestmentProject monetaryFundInvestmentProject4 = new MonetaryFundInvestmentProject();
        monetaryFundInvestmentProject4.setName("超值基金4");
        monetaryFundInvestmentProject4.setPrinciple(Amount.integer(40000));
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

    public void addInvestmentProject(InvestmentProject investmentProject) {
        mInvestmentProjects.add(investmentProject);
    }
}