package me.nettee.financial.ui.asset;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.List;

import me.nettee.financial.R;
import me.nettee.financial.model.Display;
import me.nettee.financial.model.account.Account;
import me.nettee.financial.model.account.AccountLab;

public class AccountCandidateActivity extends Activity {

    private static final int REQUEST_CODE_CREATE_ACCOUNT_STATUS = 1;

    public int getLayout() {
        return R.layout.activity_new_account_candidate;
    }

    public int getToolbarId() {
        return R.id.toolbar_new_account_candidate;
    }

    public int getCandidateListViewId() {
        return R.id.candidate_account_list;
    }

    public int getListItemLayout() {
        return R.layout.list_item_candidate_account;
    }

    private class DownloadCandidatesTask extends AsyncTask<Void, Void, List<Account>> {

        @Override
        protected List<Account> doInBackground(Void... voids) {
            List<Account> candidates = AccountLab.getInstance().getCandidateAccounts();
            return candidates;
        }

        @Override
        protected void onPostExecute(List<Account> accounts) {

            if (accounts == null) {
                return;
            }

            LinearLayout candidateListView = findViewById(getCandidateListViewId());
            LayoutInflater inflater = LayoutInflater.from(AccountCandidateActivity.this);

            for (Account candidate : accounts) {

                View itemView = inflater.inflate(getListItemLayout(), null);
                int itemHeight = (int) getResources().getDimension(R.dimen.height_item);
                itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));

                itemView.<ImageView>findViewById(R.id.candidate_account_list_item_image)
                        .setImageResource(Display.ofCandidate(candidate).icon());
                itemView.<TextView>findViewById(R.id.candidate_account_list_item_name)
                        .setText(Display.ofCandidate(candidate).name());

                candidateListView.addView(itemView);

                itemView.setOnClickListener(view -> {
                    Intent intent = new Intent(getApplicationContext(), NewAccountActivity.class);
                    intent.putExtra(NewAccountActivity.EXTRA_CANDIDATE_ACCOUNT_OBJECT, candidate);
                    startActivityForResult(intent, REQUEST_CODE_CREATE_ACCOUNT_STATUS);
                });
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayout());

        Toolbar toolbar = findViewById(getToolbarId());
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());

        new DownloadCandidatesTask().execute();
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
