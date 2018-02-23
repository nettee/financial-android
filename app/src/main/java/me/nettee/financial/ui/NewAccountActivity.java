package me.nettee.financial.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.List;

import me.nettee.financial.R;
import me.nettee.financial.model.AccountLab;
import me.nettee.financial.model.CandidateAccount;

public class NewAccountActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

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
        List<CandidateAccount> candidateAccounts = AccountLab.getInstance().getCandidateAccounts();

        for (CandidateAccount candidateAccount : candidateAccounts) {
            View itemView = inflater.inflate(R.layout.candidate_account_list_item, null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180));

            ImageView candidateAccountIconImageView = itemView.findViewById(R.id.candidate_account_list_item_image);
            TextView candidateAccountNameTextView = itemView.findViewById(R.id.candidate_account_list_item_name);

            candidateAccountIconImageView.setImageResource(candidateAccount.getImageId());
            candidateAccountNameTextView.setText(candidateAccount.getName());

            candidateAccountList.addView(itemView);
        }

    }

}
