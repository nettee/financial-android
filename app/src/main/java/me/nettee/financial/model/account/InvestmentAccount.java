package me.nettee.financial.model.account;

import java.util.List;
import java.util.Optional;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.Asset;
import me.nettee.financial.model.InvestmentProjectLab;
import me.nettee.financial.model.investment.InvestmentPlatform;
import me.nettee.financial.model.investment.InvestmentProject;

public final class InvestmentAccount extends Account {

    private static final long serialVersionUID = 1L;

    private InvestmentPlatform mPlatform;
    private List<InvestmentProject> mProjects;

    @Override
    public int getType() {
        return INVESTMENT;
    }

    @Override
    public String getCandidateName() {
        return "投资账户";
    }

    @Override
    public int getCandidateImageResource() {
        return R.drawable.ic_account_investment;
    }

    @Override
    public String getDisplayName() {
        return getPlatform().getName();
    }

    @Override
    public int getDisplayImageResource() {
        return getPlatform().getImageResource();
    }

    @Override
    public Amount getDefaultAmount() {
        return getAllInvestmentProjectsSum();
    }

    @Override
    public String getDefaultAmountCaption() {
        return "账户总额";
    }

    public Amount getAllInvestmentProjectsSum() {
        List<InvestmentProject> investmentProjects = InvestmentProjectLab.getInstance().getInvestmentProjects(getPlatform().getType());
        Amount sum = Amount.zero();
        for (InvestmentProject investmentProject : investmentProjects) {
            sum = sum.add(investmentProject.getTotalAmount());
        }
        return sum;
    }

    @Override
    public Optional<Asset> getAsset() {
        return Optional.of(new Asset(getAllInvestmentProjectsSum()));
    }

    public InvestmentPlatform getPlatform() {
        return mPlatform;
    }

    public void setPlatform(InvestmentPlatform platform) {
        mPlatform = platform;
    }

    public List<InvestmentProject> getProjects() {
        return mProjects;
    }

    public void addProject(InvestmentProject project) {
        mProjects.add(project);
    }

}
