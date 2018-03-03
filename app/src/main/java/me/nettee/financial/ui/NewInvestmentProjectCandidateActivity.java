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

public class NewInvestmentProjectCandidateActivity extends Activity {

    private LinearLayout mCandidateInvestmentProjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_investment_project_candidate);

        Toolbar toolbar = findViewById(R.id.toolbar_new_investment_project_candidate);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());

        mCandidateInvestmentProjectList = findViewById(R.id.candidate_investment_project_list);
        LayoutInflater inflater = LayoutInflater.from(this);
        List<InvestmentProject> candidateInvestmentProjects = InvestmentProject.getCandidateInvestmentProjects();

        for (InvestmentProject candidateInvestmentProject : candidateInvestmentProjects) {

            View itemView = inflater.inflate(R.layout.list_item_candidate_investment_project, null);
            int itemHeight = (int) getResources().getDimension(R.dimen.height_item);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));

            itemView.<ImageView>findViewById(R.id.candidate_investment_project_list_item_image)
                    .setImageResource(candidateInvestmentProject.getCandidateImageResource());
            itemView.<TextView>findViewById(R.id.candidate_investment_project_list_item_name)
                    .setText(candidateInvestmentProject.getCandidateName());

            mCandidateInvestmentProjectList.addView(itemView);

            itemView.setOnClickListener(view -> {

            });

        }
    }
}
