package me.nettee.financial.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toolbar;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;

public class EditAccountActivity extends Activity {

    public static final String EXTRA_EDIT_ACCOUNT_OBJECT = "me.nettee.financial.Account.edit";

    private Account mOldAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        Toolbar toolbar = findViewById(R.id.edit_account_toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mOldAccount = (Account) getIntent().getSerializableExtra(EXTRA_EDIT_ACCOUNT_OBJECT);

        Integer accountInputsLayoutResource = null;
        if (mOldAccount.getType() == Account.CASH) {
            accountInputsLayoutResource = R.layout.account_inputs_cash;
        } else if (mOldAccount.getType() == Account.CREDIT_CARD) {
            accountInputsLayoutResource = R.layout.account_inputs_credit_card;
        } else if (mOldAccount.getType() == Account.DEBIT_CARD) {
            accountInputsLayoutResource = R.layout.account_inputs_debit_card;
        }

        ViewStub stub = findViewById(R.id.account_inputs_container);
        if (accountInputsLayoutResource != null) {
            stub.setLayoutResource(accountInputsLayoutResource);
            stub.inflate();
        }

    }
}
