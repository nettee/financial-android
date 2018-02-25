package me.nettee.financial.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Toolbar;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;

public class NewAccountActivity extends Activity {

    public static final String EXTRA_ACCOUNT_TYPE = "me.nettee.financial.extra_account_type";

    private int mAccountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        Toolbar toolbar = findViewById(R.id.new_account_toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAccountType = getIntent().getIntExtra(EXTRA_ACCOUNT_TYPE, Account.GENERAL);

        Integer accountInputsLayoutResource = null;
        if (mAccountType == Account.CASH) {
            accountInputsLayoutResource = R.layout.account_inputs_cash;
        } else if (mAccountType == Account.CREDIT_CARD) {
            accountInputsLayoutResource = R.layout.account_inputs_credit_card;
        } else if (mAccountType == Account.DEBIT_CARD) {
            accountInputsLayoutResource = R.layout.account_inputs_debit_card;
        }

        ViewStub stub = findViewById(R.id.account_inputs_stub);
        if (accountInputsLayoutResource != null) {
            stub.setLayoutResource(accountInputsLayoutResource);
            stub.inflate();
        }
    }
}
