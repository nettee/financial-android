package me.nettee.financial.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.CandidateAccount;

public class NewAccountActivity extends Activity {

    public static final String EXTRA_CANDIDATE_ACCOUNT_OBJECT = "me.nettee.financial.extra_candidate_account_object";

    private static final Map<Integer, Integer> accountInputsMap = new HashMap<Integer, Integer>() {
        {
            put(Account.CASH, R.layout.account_inputs_cash);
            put(Account.CREDIT_CARD, R.layout.account_inputs_credit_card);
            put(Account.DEBIT_CARD, R.layout.account_inputs_debit_card);
            put(Account.ALIPAY, R.layout.account_inputs_alipay);
            put(Account.WEIXIN, R.layout.account_inputs_weixin);
        }
    };

    private CandidateAccount mCandidateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        Toolbar toolbar = findViewById(R.id.new_account_toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mCandidateAccount = (CandidateAccount) getIntent().getSerializableExtra(EXTRA_CANDIDATE_ACCOUNT_OBJECT);

        setView();
    }

    private void setView() {

        // Inflate two stubs.
        Integer accountInputsLayoutResource = accountInputsMap.get(mCandidateAccount.getType());
        if (accountInputsLayoutResource == null) {
            return;
        }

        Integer accountTitleBarLayoutResource = null;
        if (mCandidateAccount.getType() == Account.CREDIT_CARD
                || mCandidateAccount.getType() == Account.DEBIT_CARD) {
            accountTitleBarLayoutResource = R.layout.account_title_bar_bank_card;
        } else {
            accountTitleBarLayoutResource = R.layout.account_title_bar_ordinary;
        }

        ViewStub accountTitleBarStub = findViewById(R.id.account_title_bar_stub);
        accountTitleBarStub.setLayoutResource(accountTitleBarLayoutResource);
        View accountTitleBar = accountTitleBarStub.inflate();

        ViewStub accountInputsStub = findViewById(R.id.account_inputs_stub);
        accountInputsStub.setLayoutResource(accountInputsLayoutResource);
        accountInputsStub.inflate();

        // Fill in account information.
        accountTitleBar.<ImageView>findViewById(R.id.account_name_image)
                .setImageResource(mCandidateAccount.getImageId());
        accountTitleBar.<TextView>findViewById(R.id.account_name_text)
                .setText(mCandidateAccount.getName());
    }

}
