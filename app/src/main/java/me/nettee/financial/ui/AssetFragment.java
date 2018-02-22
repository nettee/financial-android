package me.nettee.financial.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.AccountLab;

public class AssetFragment extends Fragment {

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

        public AccountHolder(View itemView) {
            super(itemView);
            mAccountNameTextView = itemView.findViewById(R.id.account_list_item_name);
            mAccountAmountTextView = itemView.findViewById(R.id.account_list_item_amount);
        }

        public void bindAccount(Account account) {
            mAccountNameTextView.setText(account.getName());
            int amount = account.getAmount();
            mAccountAmountTextView.setText(String.format(getString(R.string.money_amount_template), amount / 100, amount % 100));
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
