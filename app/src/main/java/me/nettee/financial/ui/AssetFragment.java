package me.nettee.financial.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.AccountLab;
import me.nettee.financial.model.Money;

public class AssetFragment extends Fragment {

    private static Map<Integer, Integer> accoutTypeToImage = new HashMap<Integer, Integer>() {
        {
            put(Account.OTHER, R.drawable.account);
            put(Account.CASH, R.drawable.wallet);
            put(Account.BUS, R.drawable.bus);
            put(Account.CAMPUS_CARD, R.drawable.campus_card);
            put(Account.ALIPAY, R.drawable.alipay);
            put(Account.WXPAY, R.drawable.wxpay);
            put(Account.BANK_ICBC, R.drawable.bank_icbc);
        }
    };

    private RecyclerView mAccountRecyclerView;
    private AccountAdapter mAccountAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asset, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mAccountRecyclerView = view.findViewById(R.id.asset_account_recycler_view);
        mAccountRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAccountRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mAccountAdapter = new AccountAdapter();
        mAccountRecyclerView.setAdapter(mAccountAdapter);
    }

    private class AccountHolder extends RecyclerView.ViewHolder {

        private TextView mAccountNameTextView;
        private TextView mAccountAmountTextView;
        private ImageView mAccountIconImageView;

        public AccountHolder(View itemView) {
            super(itemView);
            mAccountNameTextView = itemView.findViewById(R.id.account_list_item_name);
            mAccountAmountTextView = itemView.findViewById(R.id.account_list_item_amount);
            mAccountIconImageView = itemView.findViewById(R.id.account_list_item_image);
        }

        public void bindAccount(Account account) {
            Integer imageId = accoutTypeToImage.get(account.getType());
            if (imageId == null) {
                imageId = R.drawable.account;
            }
            mAccountIconImageView.setImageResource(imageId);
            mAccountNameTextView.setText(account.getName());
            mAccountAmountTextView.setText(Money.format(account.getAmount()));
        }
    }

    private class AccountAdapter extends RecyclerView.Adapter<AccountHolder> {

        private List<Account> mAccountList;

        public AccountAdapter() {
            AccountLab accountLab = AccountLab.getInstance();
            mAccountList = accountLab.getAccounts();
        }

        @Override
        public AccountHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.account_list_item, parent, false);
            return new AccountHolder(view);
        }

        @Override
        public void onBindViewHolder(AccountHolder holder, int position) {
            Account account = mAccountList.get(position);
            holder.bindAccount(account);
        }

        @Override
        public int getItemCount() {
            return mAccountList.size();
        }
    }

}
