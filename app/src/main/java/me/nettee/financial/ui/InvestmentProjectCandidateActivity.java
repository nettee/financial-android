package me.nettee.financial.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.nettee.financial.R;
import me.nettee.financial.model.investment.InvestmentProject;

public class InvestmentProjectCandidateActivity extends SomeCandidateBaseActivity<InvestmentProject> {

    public static final String EXTRA_INVESTMENT_PLATFORM_TYPE = "me.nettee.financial.extra_investment_platform_type";

    private int REQUEST_CODE_CREATE_INVESTMENT_PROJECT_STATUS = 1;

    private int mInvestmentPlatformType;

    @Override
    public int getLayout() {
        return R.layout.activity_new_investment_project_candidate;
    }

    @Override
    public int getToolbarId() {
        return R.id.toolbar_new_investment_project_candidate;
    }

    @Override
    public int getCandidateListViewId() {
        return R.id.candidate_investment_project_list;
    }

    @Override
    public int getListItemLayout() {
        return R.layout.list_item_candidate_investment_project;
    }

    @Override
    public List getData() {
        mInvestmentPlatformType = (int) getIntent().getSerializableExtra(EXTRA_INVESTMENT_PLATFORM_TYPE);
        return InvestmentProject.getCandidateInvestmentProjects();
    }

    @Override
    public void initItemView(View itemView, InvestmentProject candidate) {

        itemView.<ImageView>findViewById(R.id.candidate_investment_project_list_item_image)
                .setImageResource(candidate.getCandidateImageResource());
        itemView.<TextView>findViewById(R.id.candidate_investment_project_list_item_name)
                .setText(candidate.getCandidateName());
    }

    @Override
    public void processItemView(View itemView, InvestmentProject candidate) {

        itemView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), NewInvestmentProjectActivity.class);
            intent.putExtra(NewInvestmentProjectActivity.EXTRA_INVESTMENT_PLATFORM_TYPE, mInvestmentPlatformType);
            intent.putExtra(NewInvestmentProjectActivity.EXTRA_CANDIDATE_INVESTMENT_PROJECT_OBJECT, candidate);
            startActivityForResult(intent, REQUEST_CODE_CREATE_INVESTMENT_PROJECT_STATUS);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_CREATE_INVESTMENT_PROJECT_STATUS) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }
}
