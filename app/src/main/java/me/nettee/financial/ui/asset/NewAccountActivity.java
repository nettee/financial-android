package me.nettee.financial.ui.asset;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import me.nettee.financial.R;
import me.nettee.financial.model.account.Account;
import me.nettee.financial.model.account.AccountLab;

public class NewAccountActivity extends NewSomeBaseActivity<Account> {

    public static final String EXTRA_CANDIDATE_ACCOUNT_OBJECT = "me.nettee.financial.extra_candidate_account_object";

    @Override
    public int getLayout() {
        return R.layout.activity_new_account;
    }

    @Override
    public int getToolbarId() {
        return R.id.new_account_toolbar;
    }

    @Override
    public Account getCandidate() {
        return (Account) getIntent().getSerializableExtra(EXTRA_CANDIDATE_ACCOUNT_OBJECT);
    }

    @Override
    public View getConstructedInputs() {
        return WriteAccounts.constructView(this, mCandidate);
    }

    @Override
    public View.OnClickListener getOnSaveListener() {

        return view -> {

            Account account = WriteAccounts.extractAccount(mCandidate.getType(), mInputs);

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
        };
    }
}
