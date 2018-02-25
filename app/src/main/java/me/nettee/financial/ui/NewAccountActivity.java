package me.nettee.financial.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.AccountLab;

public class NewAccountActivity extends Activity {

    public static final String EXTRA_CANDIDATE_ACCOUNT_OBJECT = "me.nettee.financial.extra_candidate_account_object";

    private Account mCandidateAccount;

    private View mAccountInputs;
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

        mAccountInputs = NewEditAccounts.constructView(this,
                mCandidateAccount.getType(),
                mCandidateAccount.getCandidateImageResource(),
                mCandidateAccount.getCandidateName());

        mSaveButton = findViewById(R.id.button_save);
        mSaveButton.setOnClickListener(view -> {

            NewEditAccounts.AccountExtractor extractor = NewEditAccounts.sAccountExtractorMap
                    .getOrDefault(mCandidateAccount.getType(), new NewEditAccounts.NullAccountExtractor());
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
