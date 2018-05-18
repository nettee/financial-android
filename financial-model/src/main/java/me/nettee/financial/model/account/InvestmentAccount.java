package me.nettee.financial.model.account;

import java.util.List;
import java.util.Optional;

import me.nettee.financial.model.Amount;
import me.nettee.financial.model.asset.Asset;
import me.nettee.financial.model.investment.InvestmentPlatform;
import me.nettee.financial.model.investment.InvestmentProject;

public final class InvestmentAccount extends Account {

    private static final long serialVersionUID = 1L;

    private InvestmentPlatform mPlatform;
    private List<InvestmentProject> mProjects;

    @Override
    public AccountType getType() {
        return AccountType.INVESTMENT;
    }

    @Override
    public Amount getDefaultAmount() {
        return Amount.zero();
    }

    @Override
    public String getDefaultAmountCaption() {
        return "账户总额";
    }

    @Override
    public Optional<Asset> getAsset() {
        return Optional.of(new Asset(Amount.zero()));
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
