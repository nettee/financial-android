package me.nettee.financial.ui;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import org.w3c.dom.Text;

import me.nettee.financial.R;

public class NewCreditCardAccountActivity extends Activity {

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

        ImageView accountNameImage = findViewById(R.id.account_name_image);
        TextView accountNameText = findViewById(R.id.account_name_text);
        accountNameImage.setImageResource(R.drawable.ic_bank_card);
        accountNameText.setText(R.string.account_name_credit_card);

        TextView accountAmountCaption = findViewById(R.id.account_amount_caption);
        accountAmountCaption.setText(R.string.current_arrears);

        Spinner billDateSpinner = findViewById(R.id.new_credit_card_account_bill_date_spinner);
        {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.bill_dates_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            billDateSpinner.setAdapter(adapter);
        }

        Spinner paymentDateSpinner = findViewById(R.id.new_credit_card_account_payment_date_spinner);
        {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.bill_dates_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            paymentDateSpinner.setAdapter(adapter);
        }
    }
}
