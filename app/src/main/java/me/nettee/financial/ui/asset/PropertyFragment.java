package me.nettee.financial.ui.asset;

import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import me.nettee.financial.error.BadNetworkException;
import me.nettee.financial.R;
import me.nettee.financial.error.Errors;
import me.nettee.financial.model.ImageResource;
import me.nettee.financial.model.account.Account;
import me.nettee.financial.model.account.AccountLab;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.asset.Liability;
import me.nettee.financial.model.asset.Asset;
import me.nettee.financial.model.account.AlipayAccount;

public class PropertyFragment extends Fragment {

    private ImageView mAssetVisibility;
    private TextView mNetAssetContent;
    private TextView mTotalAssetContent;
    private TextView mTotalLiabilityContent;
    private ImageView mAccountListCollapse;
    private LinearLayout mAccountList;
    private List<TextView> mAccountAmounts = new ArrayList<>();

    private Amount mTotalAssets = Amount.zero();
    private Amount mTotalLiabilities = Amount.zero();
    private Amount mNetAssets = Amount.zero();

    private boolean mAssetVisible = true;
    private boolean mAccountListCollapsed = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_property, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        View assetsCard = view.findViewById(R.id.assets_card);
        mNetAssetContent = assetsCard.findViewById(R.id.assets_net_asset_content);
        mTotalAssetContent = assetsCard.findViewById(R.id.asset_total_asset_content);
        mTotalLiabilityContent = assetsCard.findViewById(R.id.assets_total_liability_content);

        mAccountList = view.findViewById(R.id.account_list);

        mAssetVisible = true;
        mAccountListCollapsed = false;

        mAssetVisibility = view.findViewById(R.id.assets_visibility);
        mAssetVisibility.setOnClickListener(v -> {
            mAssetVisible = !mAssetVisible;
            updateAssetVisibility();
        });

        mAccountListCollapse = view.findViewById(R.id.account_list_collapse);
        mAccountListCollapse.setOnClickListener(v -> {
            mAccountListCollapsed = !mAccountListCollapsed;
            updateAccountListStatus();
        });

        ImageView accountListAdd = view.findViewById(R.id.account_list_add);
        accountListAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AccountCandidateActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        new DownloadAccountsTask().execute();
    }

    private class DownloadAccountsTask extends AsyncTask<Void, Void, Pair<List<Account>, Throwable>> {

        @Override
        protected Pair<List<Account>, Throwable> doInBackground(Void... voids) {
            try {
                List<Account> accounts = AccountLab.getInstance(getContext()).getAccounts();
                return new Pair<>(accounts, null);
            } catch (BadNetworkException e) {
                return new Pair<>(null, e);
            }
        }

        @Override
        protected void onPostExecute(Pair<List<Account>, Throwable> result) {

            Throwable e = result.second;
            if (e != null) {
                String message = Errors.getErrorMessage(e);
                Log.e("TAG", message);
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                return;
            }

            List<Account> accounts = result.first;

            updateAccounts(accounts, LayoutInflater.from(getActivity()));

            List<Asset> assets = new ArrayList<>();
            List<Liability> liabilities = new ArrayList<>();
            for (Account account : accounts) {
                Optional<Asset> assetOptional = account.getAsset();
                if (assetOptional.isPresent()) {
                    assets.add(assetOptional.get());
                }
                Optional<Liability> liabilityOptional = account.getLiability();
                if (liabilityOptional.isPresent()) {
                    liabilities.add(liabilityOptional.get());
                }
            }

            mTotalAssets = Asset.sum(assets);
            mTotalLiabilities = Liability.sum(liabilities);
            mNetAssets = mTotalAssets.subUnsigned(mTotalLiabilities);

            updateAccountListStatus();
            updateAssetVisibility();
        }

    }

    private void updateAssetVisibility() {
        if (mAssetVisible) {
            mAssetVisibility.setImageResource(R.drawable.ic_visible);
            updateAmounts();
        } else {
            mAssetVisibility.setImageResource(R.drawable.ic_invisible);
            hideAmounts();
        }
    }

    private void updateAccountListStatus() {
        if (mAccountListCollapsed) {
            mAccountListCollapse.setImageResource(R.drawable.ic_status_collapsed);
            mAccountList.setVisibility(View.GONE);
        } else {
            mAccountListCollapse.setImageResource(R.drawable.ic_status_expanded);
            mAccountList.setVisibility(View.VISIBLE);
        }
    }

    private void updateAccounts(List<Account> accounts, LayoutInflater inflater) {

        mAccountList.removeAllViews();
        mAccountAmounts.clear();

        Deque<Account> accountDeque = new LinkedList<>();
        accountDeque.addAll(accounts);

        while (!accountDeque.isEmpty()) {

            Account account = accountDeque.pollFirst();

            int imageResource = ImageResource.getAccountDisplayImageResource(account);
            String name = account.getDisplayName();
            String remark = account.getDisplayRemark();
            Amount amount = account.getDefaultAmount();

            View itemView = inflater.inflate(R.layout.list_item_account, null);
            int itemHeight = (int) getResources().getDimension(R.dimen.height_item);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));

            itemView.<ImageView>findViewById(R.id.account_list_item_image).setImageResource(imageResource);
            itemView.<TextView>findViewById(R.id.account_list_item_name).setText(name);

            if (StringUtils.isNotEmpty(remark)) {
                TextView remarkView = itemView.findViewById(R.id.account_list_item_remark);
                remarkView.setVisibility(View.VISIBLE);
                remarkView.setText(remark);
            }

            if (account.getType() == Account.AccountType.INVESTMENT) {
                TextView badgeView = itemView.findViewById(R.id.account_list_item_badge);
                badgeView.setVisibility(View.VISIBLE);
            }

            TextView amountTextView = itemView.findViewById(R.id.account_list_item_amount);
            amountTextView.setText(amount.toYuanString());
            if (amount.isNegative()) {
                amountTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorLiability));
            }
            mAccountAmounts.add(amountTextView);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(getActivity(), AccountDetailActivity.class);
                intent.putExtra(AccountDetailActivity.EXTRA_ACCOUNT_OBJECT, account);
                startActivity(intent);
            });

            mAccountList.addView(itemView);

            if (account.getType() == Account.AccountType.ALIPAY) {
                AlipayAccount alipayAccount = (AlipayAccount) account;
                if (alipayAccount.isHuabeiOpen()) {
                    accountDeque.addFirst(alipayAccount.getHuabeiAccount());
                }
            }
        }
    }

    private void updateAmounts() {
        mNetAssetContent.setText(mNetAssets.toYuanString());
        mTotalAssetContent.setText(mTotalAssets.toYuanString());
        mTotalLiabilityContent.setText(mTotalLiabilities.toYuanString());
        for (TextView amountView : mAccountAmounts) {
            amountView.setVisibility(View.VISIBLE);
        }
    }

    private void hideAmounts() {
        mNetAssetContent.setText("******");
        mTotalAssetContent.setText("****");
        mTotalLiabilityContent.setText("****");
        for (TextView amountView : mAccountAmounts) {
            amountView.setVisibility(View.INVISIBLE);
        }
    }
}
