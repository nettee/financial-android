package me.nettee.financial.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import me.nettee.financial.R;
import me.nettee.financial.model.account.Account;
import me.nettee.financial.model.account.AccountLab;

public class NewAccountActivity extends WriteAccountBaseActivity {

    public static final String EXTRA_CANDIDATE_ACCOUNT_OBJECT = "me.nettee.financial.extra_candidate_account_object";

    private Account mCandidateAccount;

    private Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        Toolbar toolbar = findViewById(R.id.new_account_toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());

        mCandidateAccount = (Account) getIntent().getSerializableExtra(EXTRA_CANDIDATE_ACCOUNT_OBJECT);

        constructView(mCandidateAccount.getType(),
                mCandidateAccount.getCandidateImageResource(),
                mCandidateAccount.getCandidateName());

        mSaveButton = findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(view -> {

            AccountExtractor extractor = sAccountExtractorMap
                    .getOrDefault(mCandidateAccount.getType(), new NullAccountExtractor());
            Account account = extractor.extract(mAccountInputs);

            if (account == null) {
                Toast.makeText(NewAccountActivity.this, R.string.error_fail_new_account, Toast.LENGTH_SHORT).show();
                return;
            }

            AccountLab.getInstance().addAccount(account);

            Intent intent = new Intent(getApplicationContext(), AccountDetailActivity.class);
            intent.putExtra(AccountDetailActivity.EXTRA_ACCOUNT_OBJECT, account);
            startActivity(intent);
            setResult(RESULT_OK);
            finish();
        });
    }
}
