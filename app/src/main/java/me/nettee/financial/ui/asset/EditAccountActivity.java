package me.nettee.financial.ui.asset;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.concurrent.ExecutionException;

import me.nettee.financial.R;
import me.nettee.financial.model.account.Account;
import me.nettee.financial.model.account.AccountLab;

public class EditAccountActivity extends Activity {

    public static final String EXTRA_EDIT_ACCOUNT_OBJECT = "me.nettee.financial.Account.edit";

    private Account mOldAccount;
    private View mAccountInputs;

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

        mAccountInputs = WriteAccounts.constructView(this, mOldAccount);

        WriteAccounts.fillAccount(mAccountInputs, mOldAccount);

        mSaveButton = findViewById(R.id.button_save);
        mDeleteButton = findViewById(R.id.button_delete);

        mSaveButton.setOnClickListener(view -> {

            Account newAccount = WriteAccounts.extractAccount(mOldAccount.getType(), mAccountInputs);

            if (newAccount == null) {
                Toast.makeText(getApplicationContext(), R.string.error_fail_edit_account, Toast.LENGTH_SHORT).show();
                return;
            }

            AccountLab.getInstance().modifyAccount(mOldAccount, newAccount);

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

                        DeleteAccountTask deleteAccountTask = new DeleteAccountTask();
                        deleteAccountTask.execute(mOldAccount);
                        try {
                            deleteAccountTask.get(); // Wait for the task to finish.
                            Toast.makeText(getApplicationContext(), R.string.message_success_deleted, Toast.LENGTH_SHORT).show();
                            setResult(AccountDetailActivity.RESULT_CODE_DELETED);
                            finish();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }

                    })
                    .setNegativeButton("取消", (dialogInterface, i) -> {
                        // Do nothing.
                    })
                    .create()
                    .show();
        });

    }

    private static class DeleteAccountTask extends AsyncTask<Account, Void, Void> {

        @Override
        protected Void doInBackground(Account... accounts) {
            for (Account account : accounts) {
                AccountLab.getInstance().deleteAccount(account);
            }
            return null;
        }
    }


}
