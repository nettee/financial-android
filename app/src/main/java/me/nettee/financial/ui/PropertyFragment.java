package me.nettee.financial.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import me.nettee.financial.R;
import me.nettee.financial.model.account.Account;
import me.nettee.financial.model.account.AccountLab;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.Liability;
import me.nettee.financial.model.Asset;

public class PropertyFragment extends Fragment {

    private LinearLayout mAccountList;
    private LinearLayout mPropertyList;

    private boolean mAccountListCollapsed;
    private boolean mPropertyListCollapsed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_property, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mAccountList = view.findViewById(R.id.account_list);
        mPropertyList = view.findViewById(R.id.property_list);

        mAccountListCollapsed = false;
        mPropertyListCollapsed = false;

        ImageView accountListCollapse = view.findViewById(R.id.account_list_collapse);
        accountListCollapse.setOnClickListener(v -> {
            if (mAccountListCollapsed) {
                accountListCollapse.setImageResource(R.drawable.ic_status_expanded);
                mAccountList.setVisibility(View.VISIBLE);
            } else {
                accountListCollapse.setImageResource(R.drawable.ic_status_collapsed);
                mAccountList.setVisibility(View.GONE);
            }
            mAccountListCollapsed = !mAccountListCollapsed;
        });

        ImageView propertyListCollapse = view.findViewById(R.id.property_list_collapse);
        propertyListCollapse.setOnClickListener(v -> {
            if (mPropertyListCollapsed) {
                propertyListCollapse.setImageResource(R.drawable.ic_status_expanded);
                mPropertyList.setVisibility(View.VISIBLE);
            } else {
                propertyListCollapse.setImageResource(R.drawable.ic_status_collapsed);
                mPropertyList.setVisibility(View.GONE);
            }
            mPropertyListCollapsed = !mPropertyListCollapsed;
        });

        ImageView accountListAdd = view.findViewById(R.id.account_list_add);
        accountListAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NewAccountCandidateActivity.class);
            startActivity(intent);
        });

        ImageView propertyListSetting = view.findViewById(R.id.property_list_setting);
        propertyListSetting.setOnClickListener(v -> {
            // TODO
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        LayoutInflater inflater = LayoutInflater.from(getActivity());

        List<Account> accounts = AccountLab.getInstance().getAccounts();
        updateAccounts(accounts, inflater);

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

        updateAssets(assets, liabilities, inflater);

    }

    private void updateAccounts(List<Account> accounts, LayoutInflater inflater) {

        mAccountList.removeAllViews();

        for (final Account account : accounts) {

            int imageResource = account.getDisplayImageResource();
            String name = account.getDisplayName();
            String remark = account.getDisplayRemark();
            Amount amount = account.getDefaultAmount();

            Integer layoutId;
            if (StringUtils.isNotEmpty(remark)) {
                layoutId = R.layout.list_item_account_with_remark;
            } else {
                layoutId = R.layout.list_item_account;
            }

            View itemView = inflater.inflate(layoutId, null);
            int itemHeight = (int) getResources().getDimension(R.dimen.height_item);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));

            itemView.<ImageView>findViewById(R.id.account_list_item_image).setImageResource(imageResource);
            itemView.<TextView>findViewById(R.id.account_list_item_name).setText(name);
            TextView amountTextView = itemView.<TextView>findViewById(R.id.account_list_item_amount);
            amountTextView.setText(amount.toYuanString());

            if (amount.isNegative()) {
                amountTextView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorLiability));
            }

            if (StringUtils.isNotEmpty(remark)) {
                itemView.<TextView>findViewById(R.id.account_list_item_remark).setText(remark);
            }

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(getActivity(), AccountDetailActivity.class);
                intent.putExtra(AccountDetailActivity.EXTRA_ACCOUNT_OBJECT, account);
                startActivity(intent);
            });

            mAccountList.addView(itemView);
        }
    }

    private void updateAssets(List<Asset> assets, List<Liability> liabilities, LayoutInflater inflater) {

        Amount totalAssets = Asset.sum(assets);
        Amount totalLiabilities = Liability.sum(liabilities);
        Amount netAssets = totalAssets.subUnsigned(totalLiabilities);

        {
            View mTotalProperty = mPropertyList.findViewById(R.id.total_property);
            mTotalProperty.<ImageView>findViewById(R.id.property_list_item_image).setImageResource(R.drawable.ic_asset);
            mTotalProperty.<TextView>findViewById(R.id.property_list_item_name).setText(R.string.total_assets);
            mTotalProperty.<TextView>findViewById(R.id.property_list_item_amount).setText(totalAssets.toYuanString());

            View mTotalLiability = mPropertyList.findViewById(R.id.total_liability);
            mTotalLiability.<ImageView>findViewById(R.id.property_list_item_image).setImageResource(R.drawable.ic_liability);
            mTotalLiability.<TextView>findViewById(R.id.property_list_item_name).setText(R.string.total_liabilities);
            mTotalLiability.<TextView>findViewById(R.id.property_list_item_amount).setText(totalLiabilities.toYuanString());

            View mNetProperty = mPropertyList.findViewById(R.id.net_property);
            mNetProperty.<ImageView>findViewById(R.id.property_list_item_image).setImageResource(R.drawable.ic_net_asset);
            mNetProperty.<TextView>findViewById(R.id.property_list_item_name).setText(R.string.net_assets);
            mNetProperty.<TextView>findViewById(R.id.property_list_item_amount).setText(netAssets.toYuanString());

        }
    }
}
