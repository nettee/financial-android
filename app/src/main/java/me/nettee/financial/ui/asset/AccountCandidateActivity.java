package me.nettee.financial.ui.asset;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.nettee.financial.R;
import me.nettee.financial.model.account.Account;
import me.nettee.financial.model.account.AccountLab;

public class AccountCandidateActivity extends SomeCandidateBaseActivity<Account> {

    private static final int REQUEST_CODE_CREATE_ACCOUNT_STATUS = 1;

    @Override
    public int getLayout() {
        return R.layout.activity_new_account_candidate;
    }

    @Override
    public int getToolbarId() {
        return R.id.toolbar_new_account_candidate;
    }

    @Override
    public int getCandidateListViewId() {
        return R.id.candidate_account_list;
    }

    @Override
    public int getListItemLayout() {
        return R.layout.list_item_candidate_account;
    }

    @Override
    public List<Account> getData() {
        return AccountLab.getInstance().getCandidateAccounts();
    }

    @Override
    public void initItemView(View itemView, Account candidate) {

        itemView.<ImageView>findViewById(R.id.candidate_account_list_item_image)
                .setImageResource(candidate.getCandidateImageResource());
        itemView.<TextView>findViewById(R.id.candidate_account_list_item_name)
                .setText(candidate.getCandidateName());
    }

    @Override
    public void processItemView(View itemView, Account candidate) {

        itemView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), NewAccountActivity.class);
            intent.putExtra(NewAccountActivity.EXTRA_CANDIDATE_ACCOUNT_OBJECT, candidate);
            startActivityForResult(intent, REQUEST_CODE_CREATE_ACCOUNT_STATUS);
        });
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
