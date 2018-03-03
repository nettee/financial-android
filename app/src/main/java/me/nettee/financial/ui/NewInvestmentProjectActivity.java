package me.nettee.financial.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import me.nettee.financial.R;
import me.nettee.financial.model.investment.InvestmentProject;

public class NewInvestmentProjectActivity extends NewSomeBaseActivity<InvestmentProject> {

    public static final String EXTRA_CANDIDATE_INVESTMENT_PROJECT_OBJECT = "me.nettee.financial.extra_candidate_investment_project";

    private InvestmentProject mCandidateInvestmentProject;

    @Override
    public int getLayout() {
        return R.layout.activity_new_investment_project;
    }

    @Override
    public int getToolbarId() {
        return R.id.new_investment_project_toolbar;
    }

    @Override
    public InvestmentProject getCandidate() {
        return (InvestmentProject) getIntent().getSerializableExtra(EXTRA_CANDIDATE_INVESTMENT_PROJECT_OBJECT);
    }

    @Override
    public View getConstructedInputs() {
        return null;
    }

    @Override
    public View.OnClickListener getOnSaveListener() {
        return view -> {

        };
    }
}
