package me.nettee.financial.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import me.nettee.financial.R;

public class NewDebitCardAccountActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_debit_card_account);

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
        accountNameText.setText(R.string.account_name_debit_card);

        TextView accountAmountCaption = findViewById(R.id.account_amount_caption);
        accountAmountCaption.setText(R.string.account_balance);
    }
}
