package me.nettee.financial.ui.asset;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import me.nettee.financial.R;
import me.nettee.financial.model.investment.InvestmentPlatform;
import me.nettee.financial.model.investment.InvestmentProjectLab;
import me.nettee.financial.model.investment.InvestmentProject;

public class NewInvestmentProjectActivity extends NewSomeBaseActivity<InvestmentProject> {

    public static final String EXTRA_INVESTMENT_PLATFORM_TYPE = "me.nettee.financial.extra_investment_platform_type";
    public static final String EXTRA_CANDIDATE_INVESTMENT_PROJECT_OBJECT = "me.nettee.financial.extra_candidate_investment_project";

    private InvestmentPlatform.Type mInvestmentPlatformType;

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
        mInvestmentPlatformType = (InvestmentPlatform.Type) getIntent().getSerializableExtra(EXTRA_INVESTMENT_PLATFORM_TYPE);
        Log.d("TAG", "investment platform type = " + mInvestmentPlatformType);
        InvestmentProject candidate = (InvestmentProject) getIntent().getSerializableExtra(EXTRA_CANDIDATE_INVESTMENT_PROJECT_OBJECT);
        return candidate;
    }

    @Override
    public View getConstructedInputs() {
        return WriteInvestmentProjects.constructView(this, mCandidate);
    }

    @Override
    public View.OnClickListener getOnSaveListener() {

        return view -> {

            InvestmentProject investmentProject = WriteInvestmentProjects.extractInvestmentProject(mCandidate.getType(), mInputs);
            investmentProject.setInvestmentPlatformType(mInvestmentPlatformType);

            if (investmentProject == null) {
                Toast.makeText(NewInvestmentProjectActivity.this, "添加投资项目失败", Toast.LENGTH_SHORT).show();
                return;
            }

            InvestmentProjectLab.getInstance().addInvestmentProject(investmentProject);

            setResult(RESULT_OK);
            finish();
        };
    }
}
