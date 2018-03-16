package me.nettee.financial.ui.asset;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import me.nettee.financial.R;
import me.nettee.financial.model.investment.InvestmentProjectLab;
import me.nettee.financial.model.account.Account;
import me.nettee.financial.model.account.InvestmentAccount;
import me.nettee.financial.model.investment.InvestmentPlatform;
import me.nettee.financial.model.investment.InvestmentProject;
import me.nettee.financial.model.investment.MonetaryFundInvestmentProject;

public class AccountDetailActivity extends Activity {

    public static final String EXTRA_ACCOUNT_OBJECT = "me.nettee.financial.Account";
    public static final String EXTRA_EDITED_ACCOUNT_OBJECT = "me.nettee.financial.extra_edited_account";

    private static final int REQUEST_CODE_EDIT_ACCOUNT = 1;

    public static final int RESULT_CODE_DELETED = 100;
    public static final int RESULT_CODE_EDITED = 101;

    private static final Map<Account.AccountType, Integer> sAccountTypeActionToolbarMap = new HashMap<Account.AccountType, Integer>() {
        {
            put(Account.AccountType.ALIPAY, R.layout.toolbar_account_detail_action_alipay);
            put(Account.AccountType.WEIXIN, R.layout.toolbar_account_detail_action_weixin);
            put(Account.AccountType.INVESTMENT, R.layout.toolbar_account_detail_action_investment);
        }
    };

    private Optional<Integer> getActionToolbarLayout(Account.AccountType accountType) {
        if (sAccountTypeActionToolbarMap.containsKey(accountType)) {
            return Optional.of(sAccountTypeActionToolbarMap.get(accountType));
        } else {
            return Optional.empty();
        }
    }

    private Account mAccount;

    ImageView mAccountCardImage;
    TextView mAccountCardName;
    TextView mAccountCardNameSplit;
    TextView mAccountCardRemark;
    TextView mAccountCardAmountCaption;
    TextView mAccountCardAmount;

    View mAccountDetailBody;
    View mActionToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_account_detail);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());

        mAccount = (Account) getIntent().getSerializableExtra(EXTRA_ACCOUNT_OBJECT);

        mAccountCardImage = findViewById(R.id.account_card_image);
        mAccountCardName = findViewById(R.id.account_card_name);
        mAccountCardNameSplit = findViewById(R.id.account_card_name_split);
        mAccountCardRemark = findViewById(R.id.account_card_remark);
        mAccountCardAmountCaption = findViewById(R.id.account_card_amount_caption);
        mAccountCardAmount = findViewById(R.id.account_card_amount);

        if (mAccount.getType() == Account.AccountType.INVESTMENT) {
            ViewStub stub = findViewById(R.id.account_detail_body_stub);
            stub.setLayoutResource(R.layout.account_detail_body_investment);
            mAccountDetailBody = stub.inflate();
        }

        constructActionToolbar();

        Button editButton = findViewById(R.id.account_card_edit);
        editButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), EditAccountActivity.class);
            intent.putExtra(EditAccountActivity.EXTRA_EDIT_ACCOUNT_OBJECT, mAccount);
            startActivityForResult(intent, REQUEST_CODE_EDIT_ACCOUNT);
        });
    }

    private void constructActionToolbar() {
        Optional<Integer> actionToolbarLayoutOptional = getActionToolbarLayout(mAccount.getType());
        if (!actionToolbarLayoutOptional.isPresent()) {
            return;
        }

        ViewStub stub = findViewById(R.id.account_detail_action_toolbar_stub);
        stub.setLayoutResource(actionToolbarLayoutOptional.get());
        mActionToolbar = stub.inflate();

        if (mAccount.getType() == Account.AccountType.ALIPAY) {
            mActionToolbar.findViewById(R.id.button_open_app).setOnClickListener(view -> {
                PackageManager packageManager = getApplicationContext().getPackageManager();
                Intent intent = packageManager.getLaunchIntentForPackage("com.eg.android.AlipayGphone");
                startActivity(intent);
            });

            mActionToolbar.findViewById(R.id.button_open_scan_code).setOnClickListener(view -> {
                Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            });

            mActionToolbar.findViewById(R.id.button_open_payment_code).setOnClickListener(view -> {
                Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=20000056");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            });

        } else if (mAccount.getType() == Account.AccountType.WEIXIN) {
            mActionToolbar.findViewById(R.id.button_open_app).setOnClickListener(view -> {
                Uri uri = Uri.parse("weixin://");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            });

            mActionToolbar.findViewById(R.id.button_open_scan_code).setOnClickListener(view -> {
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
                startActivity(intent);
            });

        } else if (mAccount.getType() == Account.AccountType.INVESTMENT) {
            InvestmentPlatform investmentPlatform = ((InvestmentAccount) mAccount).getPlatform();
            View newInvestmentProjectButton = mActionToolbar.findViewById(R.id.button_new_investment_project);
            newInvestmentProjectButton.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), InvestmentProjectCandidateActivity.class);
                intent.putExtra(InvestmentProjectCandidateActivity.EXTRA_INVESTMENT_PLATFORM_TYPE, investmentPlatform.getType());
                startActivity(intent);
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_EDIT_ACCOUNT) {
            if (resultCode == RESULT_CODE_DELETED) {
                finish();
            } else if (resultCode == RESULT_CODE_EDITED) {
                mAccount = (Account) data.getSerializableExtra(EXTRA_EDITED_ACCOUNT_OBJECT);
                updateView();
            }
        }
    }

    private void updateView() {
        mAccountCardImage.setImageResource(mAccount.getDisplayImageResource());
        mAccountCardName.setText(mAccount.getDisplayName());
        String remark = mAccount.getDisplayRemark();
        if (StringUtils.isEmpty(remark)) {
            mAccountCardNameSplit.setVisibility(View.INVISIBLE);
            mAccountCardRemark.setVisibility(View.INVISIBLE);
        } else {
            mAccountCardRemark.setText(remark);
        }
        mAccountCardAmountCaption.setText(mAccount.getDefaultAmountCaption());
        mAccountCardAmount.setText(mAccount.getDefaultAmount().toString());

        if (mAccount.getType() == Account.AccountType.INVESTMENT) {

            InvestmentPlatform investmentPlatform = ((InvestmentAccount) mAccount).getPlatform();

            LayoutInflater inflater = LayoutInflater.from(this);
            List<InvestmentProject> investmentProjects = InvestmentProjectLab.getInstance().getInvestmentProjects(investmentPlatform.getType());

            LinearLayout investmentProjectList = mAccountDetailBody.findViewById(R.id.account_detail_investment_project_list);

            investmentProjectList.removeAllViews();

            for (InvestmentProject investmentProject : investmentProjects) {

                MonetaryFundInvestmentProject monetaryFundInvestmentProject = (MonetaryFundInvestmentProject) investmentProject;

                View itemView = inflater.inflate(R.layout.investment_project_card, null);
                int itemHeight = (int) getResources().getDimension(R.dimen.investment_project_card_height);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight);
                layoutParams.setMargins(0,0, 0, (int) getResources().getDimension(R.dimen.margin_vertical));
                itemView.setLayoutParams(layoutParams);

                itemView.<TextView>findViewById(R.id.investment_project_card_name)
                        .setText(monetaryFundInvestmentProject.getName());
                itemView.<TextView>findViewById(R.id.investment_project_card_principle)
                        .setText(monetaryFundInvestmentProject.getPrinciple().toString());
                itemView.<TextView>findViewById(R.id.investment_project_card_annual_yield)
                        .setText(monetaryFundInvestmentProject.getAnnualYield().toString());
                LocalDate buyDate = monetaryFundInvestmentProject.getBuyDate();
                int daysDiff = Days.daysBetween(buyDate, LocalDate.now()).getDays();
                itemView.<TextView>findViewById(R.id.investment_project_card_time)
                        .setText(String.format("已买入%d天", daysDiff));

                investmentProjectList.addView(itemView);
            }
        }
    }
}
