package me.nettee.financial.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
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
import me.nettee.financial.model.CashAccount;
import me.nettee.financial.model.CreditCardAccount;
import me.nettee.financial.model.Money;

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

        mAccountInputs = NewEditAccounts.constructView(this,
                mOldAccount.getType(),
                mOldAccount.getCandidateImageResource(),
                mOldAccount.getCandidateName());

        Log.d("TAG", String.format("Old account: amount = %d, remark = %s",
                mOldAccount.getDefaultAmount(),
                mOldAccount.getRemark()));

        AccountFiller filler = sAccountFillerMap
                .getOrDefault(mOldAccount.getType(), new NullAccountFiller());
        filler.fill(mAccountInputs, mOldAccount);

        mSaveButton = findViewById(R.id.button_save);
        mDeleteButton = findViewById(R.id.button_delete);

        mSaveButton.setOnClickListener(view -> {
            NewEditAccounts.AccountExtractor extractor = NewEditAccounts.sAccountExtractorMap
                    .getOrDefault(mOldAccount.getType(), new NewEditAccounts.NullAccountExtractor());
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

    private static Map<Integer, AccountFiller> sAccountFillerMap = new HashMap<Integer, AccountFiller>() {
        {
            put(Account.CASH, new CashAccountFiller());
            put(Account.CREDIT_CARD, new CreditCardFiller());
        }
    };

    @FunctionalInterface
    public interface AccountFiller {
        void fill(View accountInputs, Account account);
    }

    public static class CashAccountFiller extends NewEditAccounts.CashAccountInout
            implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
            pre(accountInputs);
            CashAccount cashAccount = (CashAccount) account;
            mRemark.setText(cashAccount.getRemark());
            mBalanceAmount.setText(Money.formatWithoutYuan(cashAccount.getDefaultAmount()));
        }
    }

    public static class CreditCardFiller extends NewEditAccounts.CreditCardAccountInout
            implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
            pre(accountInputs);
            CreditCardAccount creditCardAccount = (CreditCardAccount) account;
            mRemark.setText(creditCardAccount.getRemark());
            mBankCardNumber.setText(creditCardAccount.getBankCardNumber());
            mCreditLimit.setText(Money.formatWithoutYuan(creditCardAccount.getCreditLimit()));
            mBillDate.setSelection(creditCardAccount.getBillDate() - 1);
            mPaymentDate.setSelection(creditCardAccount.getPaymentDate() - 1);
            mCurrentArrears.setText(Money.formatWithoutYuan(creditCardAccount.getCurrentArrears()));
        }
    }

    public static class NullAccountFiller implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
            // Do nothing.
        }
    }
}
