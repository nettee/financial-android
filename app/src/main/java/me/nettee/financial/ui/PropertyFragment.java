package me.nettee.financial.ui;

import android.app.Fragment;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.AccountLab;
import me.nettee.financial.model.Bank;
import me.nettee.financial.model.BankCardAccount;

public class PropertyFragment extends Fragment {

    private LinearLayout mAccountList;

    private boolean mAccountListCollapsed;
    private boolean mPropertyListCollapsed;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_property, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

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
            } else {
                propertyListCollapse.setImageResource(R.drawable.ic_status_collapsed);
            }
            mPropertyListCollapsed = !mPropertyListCollapsed;
        });

        ImageView accountListAdd = view.findViewById(R.id.account_list_add);
        accountListAdd.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), NewAccountCandidateActivity.class);
            startActivity(intent);
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        mAccountList = getActivity().findViewById(R.id.account_list);
        mAccountList.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        List<Account> accounts = AccountLab.getInstance().getAccounts();

        for (final Account account : accounts) {

            int imageResource = account.getDisplayImageResource();
            String name = account.getDisplayName();
            String remark = account.getDisplayRemark();
            String amount = account.getDefaultAmount().toYuanString();

            Integer layoutId;
            if (StringUtils.isNotEmpty(remark)) {
                layoutId = R.layout.account_list_item_with_remark;
            } else {
                layoutId = R.layout.account_list_item;
            }

            View itemView = inflater.inflate(layoutId, null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180));

            itemView.<ImageView>findViewById(R.id.account_list_item_image).setImageResource(imageResource);
            itemView.<TextView>findViewById(R.id.account_list_item_name).setText(name);
            itemView.<TextView>findViewById(R.id.account_list_item_amount).setText(amount);

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
}
