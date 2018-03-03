package me.nettee.financial.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toolbar;

import me.nettee.financial.R;
import me.nettee.financial.model.investment.InvestmentProject;

public class NewInvestmentProjectActivity extends Activity {

    public static final String EXTRA_CANDIDATE_INVESTMENT_PROJECT_OBJECT = "me.nettee.financial.extra_candidate_investment_project";

    private InvestmentProject mCandidateInvestmentProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_investment_project);

        Toolbar toolbar = findViewById(R.id.new_investment_project_toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());

        mCandidateInvestmentProject = (InvestmentProject) getIntent().getSerializableExtra(EXTRA_CANDIDATE_INVESTMENT_PROJECT_OBJECT);
    }
}
