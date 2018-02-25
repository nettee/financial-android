package me.nettee.financial.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.Money;

public class AccountDetailActivity extends Activity {

    public static final String EXTRA_ACCOUNT_OBJECT = "me.nettee.financial.Account";

    private static final int REQUEST_CODE_EDITED_ACCOUNT = 1;

    private Account mAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_account_detail);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAccount = (Account) getIntent().getSerializableExtra(EXTRA_ACCOUNT_OBJECT);

        ImageView accountCardImage = findViewById(R.id.account_card_image);
        TextView accountCardName = findViewById(R.id.account_card_name);
        TextView accountCardNameSplit = findViewById(R.id.account_card_name_split);
        TextView accountCardRemark = findViewById(R.id.account_card_remark);
        TextView accountCardAmount = findViewById(R.id.account_card_amount);

        accountCardImage.setImageResource(mAccount.getCandidateImageResource());
        accountCardName.setText(mAccount.getCandidateName());
        String remark = mAccount.getRemark();
        if (remark == null || remark.length() == 0) {
            accountCardNameSplit.setVisibility(View.INVISIBLE);
            accountCardRemark.setVisibility(View.INVISIBLE);
        } else {
            accountCardRemark.setText(remark);
        }
        accountCardAmount.setText(Money.formatWithoutYuan(mAccount.getDefaultAmount()));

        Button editButton = findViewById(R.id.account_card_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditAccountActivity.class);
                intent.putExtra(EditAccountActivity.EXTRA_EDIT_ACCOUNT_OBJECT, mAccount);
                startActivityForResult(intent, REQUEST_CODE_EDITED_ACCOUNT);
            }
        });
    }
}
