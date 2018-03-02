package me.nettee.financial.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.AccountLab;

public class EditAccountActivity extends WriteAccountBaseActivity {

    public static final String EXTRA_EDIT_ACCOUNT_OBJECT = "me.nettee.financial.Account.edit";

    private Account mOldAccount;

    private Button mSaveButton;
    private Button mDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        Toolbar toolbar = findViewById(R.id.edit_account_toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());

        mOldAccount = (Account) getIntent().getSerializableExtra(EXTRA_EDIT_ACCOUNT_OBJECT);

        constructView(mOldAccount.getType(),
                mOldAccount.getCandidateImageResource(),
                mOldAccount.getCandidateName());

        Log.d("TAG", String.format("Old account: amount = %s, remark = %s",
                mOldAccount.getDefaultAmount().toString(),
                mOldAccount.getRemark()));

        AccountFiller filler = sAccountFillerMap
                .getOrDefault(mOldAccount.getType(), new NullAccountFiller());
        filler.fill(mAccountInputs, mOldAccount);

        mSaveButton = findViewById(R.id.button_save);
        mDeleteButton = findViewById(R.id.button_delete);

        mSaveButton.setOnClickListener(view -> {
            AccountExtractor extractor = sAccountExtractorMap
                    .getOrDefault(mOldAccount.getType(), new NullAccountExtractor());
            Account newAccount = extractor.extract(mAccountInputs);

            if (newAccount == null) {
                Toast.makeText(getApplicationContext(), R.string.error_fail_edit_account, Toast.LENGTH_SHORT).show();
                return;
            }

            AccountLab accountLab = AccountLab.getInstance();
            accountLab.deleteAccount(mOldAccount);
            accountLab.addAccount(newAccount);

            Intent intent = new Intent();
            intent.putExtra(AccountDetailActivity.EXTRA_EDITED_ACCOUNT_OBJECT, newAccount);
            setResult(AccountDetailActivity.RESULT_CODE_EDITED, intent);
            finish();
        });

        mDeleteButton.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setCancelable(true)
                    .setMessage("确定要删除该账户吗？")
                    .setPositiveButton("删除", (dialogInterface, i) -> {
                        AccountLab.getInstance().deleteAccount(mOldAccount);

                        Toast.makeText(getApplicationContext(), R.string.message_success_deleted, Toast.LENGTH_SHORT).show();
                        setResult(AccountDetailActivity.RESULT_CODE_DELETED);
                        finish();
                    })
                    .setNegativeButton("取消", (dialogInterface, i) -> {
                        // Do nothing.
                    })
                    .create()
                    .show();
        });

    }




}
