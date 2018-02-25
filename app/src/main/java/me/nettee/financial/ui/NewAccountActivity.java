package me.nettee.financial.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import me.nettee.financial.model.CreditCardAccount;
import me.nettee.financial.model.Money;

public class NewAccountActivity extends Activity {

    public static final String EXTRA_CANDIDATE_ACCOUNT_OBJECT = "me.nettee.financial.extra_candidate_account_object";

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

        mAccountInputs = constructView(this,
                mCandidateAccount.getType(),
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

    private static final Map<Integer, Integer> sAccountInputsMap = new HashMap<Integer, Integer>() {
        {
            put(Account.CASH, R.layout.account_inputs_cash);
            put(Account.CREDIT_CARD, R.layout.account_inputs_credit_card);
            put(Account.DEBIT_CARD, R.layout.account_inputs_debit_card);
            put(Account.ALIPAY, R.layout.account_inputs_alipay);
            put(Account.WEIXIN, R.layout.account_inputs_weixin);
        }
    };

    public static View constructView(Activity activity,
                                      int accountType,
                                      int candidateImageResource,
                                      String candidateName) {

        // Construct account title bar.
        {
            Integer layoutResource;
            if (accountType == Account.CREDIT_CARD || accountType == Account.DEBIT_CARD) {
                layoutResource = R.layout.account_title_bar_bank_card;
            } else {
                layoutResource = R.layout.account_title_bar_ordinary;
            }

            ViewStub stub = activity.findViewById(R.id.account_title_bar_stub);
            stub.setLayoutResource(layoutResource);
            View accountTitleBar = stub.inflate();

            accountTitleBar.<ImageView>findViewById(R.id.account_name_image)
                    .setImageResource(candidateImageResource);
            accountTitleBar.<TextView>findViewById(R.id.account_name_text)
                    .setText(candidateName);
        }

        // Construct account inputs.
        {
            Integer layoutResource = sAccountInputsMap
                    .getOrDefault(accountType, R.layout.account_inputs_cash);

            ViewStub stub = activity.findViewById(R.id.account_inputs_stub);
            stub.setLayoutResource(layoutResource);
            View accountInputs = stub.inflate();

            if (accountType == Account.CASH) {
                // Do nothing.
            } else if (accountType == Account.CREDIT_CARD) {
                accountInputs.findViewById(R.id.view_credit_limit)
                        .<TextView>findViewById(R.id.account_amount_caption)
                        .setText(R.string.caption_credit_limit);
                accountInputs.findViewById(R.id.view_current_arrears)
                        .<TextView>findViewById(R.id.account_amount_caption)
                        .setText(R.string.caption_current_arrears);
                accountInputs.findViewById(R.id.view_bill_date)
                        .<TextView>findViewById(R.id.account_credit_date_caption)
                        .setText(R.string.caption_bill_date);
                accountInputs.findViewById(R.id.view_payment_date)
                        .<TextView>findViewById(R.id.account_credit_date_caption)
                        .setText(R.string.caption_payment_date);
                {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                            R.array.bill_dates_array,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    accountInputs.findViewById(R.id.view_bill_date)
                            .<Spinner>findViewById(R.id.account_credit_date_spinner)
                            .setAdapter(adapter);
                }
                {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                                    R.array.bill_dates_array,
                                    android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    accountInputs.findViewById(R.id.view_payment_date)
                            .<Spinner>findViewById(R.id.account_credit_date_spinner)
                            .setAdapter(adapter);
                }
            }

            return accountInputs;
        }
    }

    private static Map<Integer, AccountExtractor> sAccountExtractorMap = new HashMap<Integer, AccountExtractor>() {
        {
            put(Account.CASH, new CashAccountExtractor());
            put(Account.CREDIT_CARD, new CreditCardAccountExtractor());
        }
    };

    @FunctionalInterface
    public interface AccountExtractor {
        Account extract(View accountInputs);
    }

    public static class CashAccountExtractor implements AccountExtractor {

        @Override
        public Account extract(View accountInputs) {
            EditText accountRemark = accountInputs.findViewById(R.id.account_remark);
            EditText accountAmount = accountInputs.findViewById(R.id.account_amount);

            CashAccount account = new CashAccount();
            account.setBalanceAmount(Money.from(accountAmount.getText().toString()));
            account.setRemark(accountRemark.getText().toString());
            return account;
        }
    }

    public static class CreditCardAccountExtractor implements AccountExtractor {

        @Override
        public Account extract(View accountInputs) {
            EditText remark = accountInputs.findViewById(R.id.account_remark);
            EditText bankCardNumber = accountInputs.findViewById(R.id.account_bank_card_number);
            EditText creditLimit = accountInputs.findViewById(R.id.view_credit_limit)
                    .findViewById(R.id.account_amount);
            Spinner billDate = accountInputs.findViewById(R.id.view_bill_date)
                    .findViewById(R.id.account_credit_date_spinner);
            Spinner paymentDate = accountInputs.findViewById(R.id.view_payment_date)
                    .findViewById(R.id.account_credit_date_spinner);
            EditText currentArrears = accountInputs.findViewById(R.id.view_current_arrears)
                    .findViewById(R.id.account_amount);

            CreditCardAccount account = new CreditCardAccount();
            account.setRemark(remark.getText().toString());
            account.setBankCardNumber(bankCardNumber.getText().toString());
            account.setCreditLimit(Money.from(creditLimit.getText().toString()));
            account.setBillDate(billDate.getSelectedItemPosition() + 1);
            account.setPaymentDate(paymentDate.getSelectedItemPosition() + 1);
            account.setCurrentArrears(Money.from(currentArrears.getText().toString()));
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
