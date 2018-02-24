package me.nettee.financial.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Toolbar;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.AccountLab;
import me.nettee.financial.model.CashAccount;
import me.nettee.financial.model.Money;

public class NewCashAccountActivity extends Activity {

    private EditText mRemark;
    private EditText mAccountBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cash_account);

        Toolbar toolbar = findViewById(R.id.toolbar_new_cash_account);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mRemark = findViewById(R.id.cash_account_remark);
        mAccountBalance = findViewById(R.id.account_balance);

        mAccountBalance.addTextChangedListener(new MoneyAmountInputWatcher(mAccountBalance));

        Button saveButton = findViewById(R.id.button_save_new_cash_account);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String remark = mRemark.getText().toString();
                String balance = mAccountBalance.getText().toString();
                newCashAccount(remark, balance);
            }
        });

    }

    private void newCashAccount(String remark, String balance) {
        int amount = Money.from(balance);
        Account account = new CashAccount(amount);
        account.setRemark(remark);

        AccountLab.getInstance().addAccount(account);

        Intent intent = new Intent(getApplicationContext(), AccountDetailActivity.class);
        intent.putExtra(AccountDetailActivity.EXTRA_ACCOUNT_OBJECT, account);
        startActivity(intent);
        setResult(RESULT_OK);
        finish();
    }
}
