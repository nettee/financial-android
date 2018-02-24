package me.nettee.financial.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

        Toolbar toolbar = findViewById(R.id.new_account_toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView accountAmountCaption = findViewById(R.id.account_amount_caption);
        accountAmountCaption.setText(R.string.account_balance);

        mRemark = findViewById(R.id.account_remark);
        mAccountBalance = findViewById(R.id.account_amount);

        mAccountBalance.addTextChangedListener(new MoneyAmountInputWatcher(mAccountBalance));

        Button saveButton = findViewById(R.id.button_save);
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
