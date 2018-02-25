package me.nettee.financial.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.HashMap;
import java.util.Map;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.CashAccount;
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

        constructView(mOldAccount.getType());

        Log.d("TAG", String.format("Old account: amount = %d, remark = %s",
                mOldAccount.getDefaultAmount(),
                mOldAccount.getRemark()));

        AccountFiller filler = sAccountFillerMap
                .getOrDefault(mOldAccount.getType(), new NullAccountFiller());
        filler.fill(mAccountInputs, mOldAccount);

    }

    // TODO copied
    private void constructView(int accountType) {

        // Inflate two stubs.
        Integer accountInputsLayoutResource = NewAccountActivity.accountInputsMap.get(accountType);
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

        // Fill in account information.
        accountTitleBar.<ImageView>findViewById(R.id.account_name_image)
                .setImageResource(mOldAccount.getCandidateImageResource());
        accountTitleBar.<TextView>findViewById(R.id.account_name_text)
                .setText(mOldAccount.getCandidateName());
    }

    private static Map<Integer, AccountFiller> sAccountFillerMap = new HashMap<Integer, AccountFiller>() {
        {
            put(Account.CASH, new CashAccountFiller());
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

    public static class NullAccountFiller implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
            // Do nothing.
        }
    }
}
