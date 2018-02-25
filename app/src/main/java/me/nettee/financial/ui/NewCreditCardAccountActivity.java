package me.nettee.financial.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import me.nettee.financial.R;
import me.nettee.financial.model.CreditCardAccount;
import me.nettee.financial.model.Money;

public class NewCreditCardAccountActivity extends Activity {

    private EditText mRemark;
    private EditText mBankCardNumber;
    private EditText mCreditLimit;
    private Spinner mBillDate;
    private Spinner mPaymentDate;
    private EditText mCurrentArrears;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_credit_card_account);

        Toolbar toolbar = findViewById(R.id.new_account_toolbar);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        this.<ImageView>findViewById(R.id.account_name_image)
                .setImageResource(R.drawable.ic_bank_card);
        this.<TextView>findViewById(R.id.account_name_text)
                .setText(R.string.account_name_credit_card);

        findViewById(R.id.view_credit_limit)
                .<TextView>findViewById(R.id.account_amount_caption)
                .setText(R.string.caption_credit_limit);

        findViewById(R.id.view_current_arrears)
                .<TextView>findViewById(R.id.account_amount_caption)
                .setText(R.string.caption_current_arrears);

        findViewById(R.id.view_bill_date)
                .<TextView>findViewById(R.id.account_credit_date_caption)
                .setText(R.string.caption_bill_date);

        findViewById(R.id.view_payment_date)
                .<TextView>findViewById(R.id.account_credit_date_caption)
                .setText(R.string.caption_payment_date);

        mRemark = findViewById(R.id.account_remark);
        mBankCardNumber = findViewById(R.id.account_bank_card_number);
        mCreditLimit = findViewById(R.id.view_credit_limit).findViewById(R.id.account_amount);
        mBillDate = findViewById(R.id.view_bill_date).findViewById(R.id.account_credit_date);
        mPaymentDate = findViewById(R.id.view_payment_date).findViewById(R.id.account_credit_date);
        mCurrentArrears = findViewById(R.id.view_current_arrears).findViewById(R.id.account_amount);

        {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.bill_dates_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mBillDate.setAdapter(adapter);
        }

        {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.bill_dates_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mPaymentDate.setAdapter(adapter);
        }

        Button saveButton = findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreditCardAccount account = new CreditCardAccount();
                account.setRemark(mRemark.getText().toString());
                account.setBankCardNumber(mBankCardNumber.getText().toString());
                account.setCreditLimit(Money.from(mCreditLimit.getText().toString()));
                account.setBillDate(getBillDate());
                account.setPaymentDate(getPaymentDate());
            }
        });
    }

    private int getBillDate() {
        int i = mBillDate.getSelectedItemPosition();
        return i + 1;
    }

    private int getPaymentDate() {
        int i = mPaymentDate.getSelectedItemPosition();
        return i + 1;
    }
}
