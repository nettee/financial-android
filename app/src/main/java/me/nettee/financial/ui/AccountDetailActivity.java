package me.nettee.financial.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

import me.nettee.financial.R;
import me.nettee.financial.model.account.Account;

public class AccountDetailActivity extends Activity {

    public static final String EXTRA_ACCOUNT_OBJECT = "me.nettee.financial.Account";
    public static final String EXTRA_EDITED_ACCOUNT_OBJECT = "me.nettee.financial.extra_edited_account";

    private static final int REQUEST_CODE_EDIT_ACCOUNT = 1;

    public static final int RESULT_CODE_DELETED = 100;
    public static final int RESULT_CODE_EDITED = 101;

    private static final Map<Integer, Integer> sAccountTypeActionToolbarMap = new HashMap<Integer, Integer>() {
        {
            put(Account.INVESTMENT, R.layout.toolbar_account_detail_action_investment);
        }
    };

    private int getActionToolbarLayout(int accountType) {
        return sAccountTypeActionToolbarMap.getOrDefault(accountType, R.layout.toolbar_account_detail_action_blank);
    }

    private Account mAccount;

    ImageView mAccountCardImage;
    TextView mAccountCardName;
    TextView mAccountCardNameSplit;
    TextView mAccountCardRemark;
    TextView mAccountCardAmountCaption;
    TextView mAccountCardAmount;

    View mActionToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_account_detail);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());

        mAccount = (Account) getIntent().getSerializableExtra(EXTRA_ACCOUNT_OBJECT);

        mAccountCardImage = findViewById(R.id.account_card_image);
        mAccountCardName = findViewById(R.id.account_card_name);
        mAccountCardNameSplit = findViewById(R.id.account_card_name_split);
        mAccountCardRemark = findViewById(R.id.account_card_remark);
        mAccountCardAmountCaption = findViewById(R.id.account_card_amount_caption);
        mAccountCardAmount = findViewById(R.id.account_card_amount);

        ViewStub stub = findViewById(R.id.account_detail_action_toolbar_stub);
        stub.setLayoutResource(getActionToolbarLayout(mAccount.getType()));
        mActionToolbar = stub.inflate();

        Button editButton = findViewById(R.id.account_card_edit);
        editButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), EditAccountActivity.class);
            intent.putExtra(EditAccountActivity.EXTRA_EDIT_ACCOUNT_OBJECT, mAccount);
            startActivityForResult(intent, REQUEST_CODE_EDIT_ACCOUNT);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateView(mAccount);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_EDIT_ACCOUNT) {
            if (resultCode == RESULT_CODE_DELETED) {
                finish();
            } else if (resultCode == RESULT_CODE_EDITED) {
                mAccount = (Account) data.getSerializableExtra(EXTRA_EDITED_ACCOUNT_OBJECT);
                updateView(mAccount);
            }
        }
    }

    private void updateView(Account account) {
        Log.d("TAG", "Update view");
        mAccountCardImage.setImageResource(account.getDisplayImageResource());
        mAccountCardName.setText(account.getDisplayName());
        String remark = account.getDisplayRemark();
        if (StringUtils.isEmpty(remark)) {
            mAccountCardNameSplit.setVisibility(View.INVISIBLE);
            mAccountCardRemark.setVisibility(View.INVISIBLE);
        } else {
            mAccountCardRemark.setText(remark);
        }
        mAccountCardAmountCaption.setText(account.getDefaultAmountCaption());
        mAccountCardAmount.setText(account.getDefaultAmount().toString());
    }
}
