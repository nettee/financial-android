package me.nettee.financial.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.CashAccount;
import me.nettee.financial.model.CreditCardAccount;
import me.nettee.financial.model.Money;

public class EditAccountActivity extends Activity {

    public static final String EXTRA_EDIT_ACCOUNT_OBJECT = "me.nettee.financial.Account.edit";

    private Account mOldAccount;

    private View mAccountInputs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        Toolbar toolbar = findViewById(R.id.edit_account_toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());

        mOldAccount = (Account) getIntent().getSerializableExtra(EXTRA_EDIT_ACCOUNT_OBJECT);

        mAccountInputs = NewAccountActivity.constructView(this,
                mOldAccount.getType(),
                mOldAccount.getCandidateImageResource(),
                mOldAccount.getCandidateName());

        Log.d("TAG", String.format("Old account: amount = %d, remark = %s",
                mOldAccount.getDefaultAmount(),
                mOldAccount.getRemark()));

        AccountFiller filler = sAccountFillerMap
                .getOrDefault(mOldAccount.getType(), new NullAccountFiller());
        filler.fill(mAccountInputs, mOldAccount);

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

    public static class CashAccountFiller implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
            TextView accountRemark = accountInputs.findViewById(R.id.account_remark);
            TextView accountAmount = accountInputs.findViewById(R.id.account_amount);

            CashAccount cashAccount = (CashAccount) account;
            accountRemark.setText(cashAccount.getRemark());
            accountAmount.setText(Money.formatWithoutYuan(cashAccount.getDefaultAmount()));
        }
    }

    public static class CreditCardFiller implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
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

            CreditCardAccount creditCardAccount = (CreditCardAccount) account;
            remark.setText(creditCardAccount.getRemark());
            bankCardNumber.setText(creditCardAccount.getBankCardNumber());
            creditLimit.setText(Money.formatWithoutYuan(creditCardAccount.getCreditLimit()));
            billDate.setSelection(creditCardAccount.getBillDate() - 1);
            paymentDate.setSelection(creditCardAccount.getPaymentDate() - 1);
            currentArrears.setText(Money.formatWithoutYuan(creditCardAccount.getCurrentArrears()));
        }
    }

    public static class NullAccountFiller implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
            // Do nothing.
        }
    }
}
