package me.nettee.financial.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toolbar;

import me.nettee.financial.R;

public class NewCashAccountActivity extends Activity {

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

        EditText accoutBalance = findViewById(R.id.account_balance);
        accoutBalance.addTextChangedListener(new MoneyAmountInputWatcher(accoutBalance));
    }
}
