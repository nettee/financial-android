package me.nettee.financial.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.List;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.AccountLab;

public class NewAccountCandidateActivity extends Activity {

    private static final int REQUEST_CODE_CREATE_ACCOUNT_STATUS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account_candidate);

        Toolbar toolbar = findViewById(R.id.toolbar_new_account);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayout candidateAccountList = findViewById(R.id.candidate_account_list);
        LayoutInflater inflater = LayoutInflater.from(this);
        List<Account> candidateAccounts = AccountLab.getInstance().getCandidateAccounts();

        for (final Account candidateAccount : candidateAccounts) {
            View itemView = inflater.inflate(R.layout.candidate_account_list_item, null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180));

            ImageView candidateAccountIconImageView = itemView.findViewById(R.id.candidate_account_list_item_image);
            TextView candidateAccountNameTextView = itemView.findViewById(R.id.candidate_account_list_item_name);

            candidateAccountIconImageView.setImageResource(candidateAccount.getCandidateImageResource());
            candidateAccountNameTextView.setText(candidateAccount.getCandidateName());

            candidateAccountList.addView(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), NewAccountActivity.class);
                    intent.putExtra(NewAccountActivity.EXTRA_CANDIDATE_ACCOUNT_OBJECT, candidateAccount);
                    startActivityForResult(intent, REQUEST_CODE_CREATE_ACCOUNT_STATUS);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_CREATE_ACCOUNT_STATUS) {
            if (resultCode == RESULT_OK) {
                finish();
            }
        }
    }
}