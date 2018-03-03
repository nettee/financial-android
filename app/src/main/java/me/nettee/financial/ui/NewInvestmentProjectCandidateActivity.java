package me.nettee.financial.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.List;

import me.nettee.financial.R;
import me.nettee.financial.model.account.Account;
import me.nettee.financial.model.account.AccountLab;
import me.nettee.financial.model.investment.InvestmentProject;

public class NewInvestmentProjectCandidateActivity extends CandidateBaseActivity<InvestmentProject> {

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

        });
    }
}
