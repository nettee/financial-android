package me.nettee.financial.ui;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.AccountLab;
import me.nettee.financial.model.Money;

public class PropertyFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asset, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        LinearLayout accoutList = view.findViewById(R.id.account_list);

        LayoutInflater inflater = LayoutInflater.from(getActivity());

        List<Account> accounts = AccountLab.getInstance().getAccounts();

        for (final Account account : accounts) {
            View itemView = inflater.inflate(R.layout.account_list_item, null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180));

            ImageView accountIconImageView = itemView.findViewById(R.id.account_list_item_image);
            TextView accountNameTextView = itemView.findViewById(R.id.account_list_item_name);
            TextView accountAmountTextView = itemView.findViewById(R.id.account_list_item_amount);

            accountIconImageView.setImageResource(account.getImageId());
            accountNameTextView.setText(account.getName());
            accountAmountTextView.setText(Money.formatWithYuan(account.getAmount()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), AccountDetailActivity.class);
                    intent.putExtra(AccountDetailActivity.EXTRA_ACCOUNT_OBJECT, account);
                    startActivity(intent);
                }
            });

            accoutList.addView(itemView);
        }

        ImageView accountListAdd = view.findViewById(R.id.account_list_add);
        accountListAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}
