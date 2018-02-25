package me.nettee.financial.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.AccountLab;
import me.nettee.financial.model.CandidateAccount;
import me.nettee.financial.model.CashAccount;
import me.nettee.financial.model.Money;

public class NewAccountActivity extends Activity {

    public static final String EXTRA_CANDIDATE_ACCOUNT_OBJECT = "me.nettee.financial.extra_candidate_account_object";

    public static final Map<Integer, Integer> accountInputsMap = new HashMap<Integer, Integer>() {
        {
            put(Account.CASH, R.layout.account_inputs_cash);
            put(Account.CREDIT_CARD, R.layout.account_inputs_credit_card);
            put(Account.DEBIT_CARD, R.layout.account_inputs_debit_card);
            put(Account.ALIPAY, R.layout.account_inputs_alipay);
            put(Account.WEIXIN, R.layout.account_inputs_weixin);
        }
    };

    private CandidateAccount mCandidateAccount;

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

        mCandidateAccount = (CandidateAccount) getIntent().getSerializableExtra(EXTRA_CANDIDATE_ACCOUNT_OBJECT);

        constructView(mCandidateAccount.getType());

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

    private void constructView(int accountType) {

        // Inflate two stubs.
        Integer accountInputsLayoutResource = accountInputsMap.get(accountType);
        if (accountInputsLayoutResource == null) {
            return;
        }

        Integer accountTitleBarLayoutResource;
        if (accountType == Account.CREDIT_CARD || accountType == Account.DEBIT_CARD) {
            accountTitleBarLayoutResource = R.layout.account_title_bar_bank_card;
        } else {
            accountTitleBarLayoutResource = R.layout.account_title_bar_ordinary;
        }

        ViewStub accountTitleBarStub = findViewById(R.id.account_title_bar_stub);
        accountTitleBarStub.setLayoutResource(accountTitleBarLayoutResource);
        View accountTitleBar = accountTitleBarStub.inflate();

        ViewStub accountInputsStub = findViewById(R.id.account_inputs_stub);
        accountInputsStub.setLayoutResource(accountInputsLayoutResource);
        mAccountInputs = accountInputsStub.inflate();

        // Fill in account title bar caption.
        accountTitleBar.<ImageView>findViewById(R.id.account_name_image)
                .setImageResource(mCandidateAccount.getCandidateImageResource());
        accountTitleBar.<TextView>findViewById(R.id.account_name_text)
                .setText(mCandidateAccount.getCandidateName());
    }

    private static Map<Integer, AccountExtractor> sAccountExtractorMap = new HashMap<Integer, AccountExtractor>() {
        {
            put(Account.CASH, new CashAccountExtractor());
        }
    };

    @FunctionalInterface
    public interface AccountExtractor {
        Account extract(View accountInputs);
    }

    public static class CashAccountExtractor implements AccountExtractor {

        @Override
        public Account extract(View accountInputs) {
            TextView accountRemark = accountInputs.findViewById(R.id.account_remark);
            TextView accountAmount = accountInputs.findViewById(R.id.account_amount);

            CashAccount account = new CashAccount();
            account.setBalanceAmount(Money.from(accountAmount.getText().toString()));
            account.setRemark(accountRemark.getText().toString());
            return account;
        }
    }

    public static class NullAccountExtractor implements AccountExtractor {

        @Override
        public Account extract(View accountInputs) {
            return null;
        }
    }

}
