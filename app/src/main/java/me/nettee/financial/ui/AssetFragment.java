package me.nettee.financial.ui;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.AccountLab;
import me.nettee.financial.model.Money;

public class AssetFragment extends Fragment {

    private static Map<Integer, Integer> accountTypeToImage = new HashMap<Integer, Integer>() {
        {
            put(Account.OTHER, R.drawable.ic_account);
            put(Account.CASH, R.drawable.ic_wallet);
            put(Account.BUS, R.drawable.ic_bus);
            put(Account.CAMPUS_CARD, R.drawable.ic_campus_card);
            put(Account.ALIPAY, R.drawable.ic_alipay);
            put(Account.WXPAY, R.drawable.ic_wxpay);
            put(Account.BANK_ICBC, R.drawable.ic_bank_icbc);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asset, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        LinearLayout accoutList = view.findViewById(R.id.account_list);

        LayoutInflater inflater = LayoutInflater.from(getActivity());

        List<Account> accounts = AccountLab.getInstance().getAccounts();

        for (Account account : accounts) {
            View itemView = inflater.inflate(R.layout.account_list_item, null);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 160));

            ImageView accountIconImageView = itemView.findViewById(R.id.account_list_item_image);
            TextView accountNameTextView = itemView.findViewById(R.id.account_list_item_name);
            TextView accountAmountTextView = itemView.findViewById(R.id.account_list_item_amount);

            Integer imageId = accountTypeToImage.get(account.getType());
            if (imageId == null) {
                imageId = R.drawable.ic_account;
            }
            accountIconImageView.setImageResource(imageId);
            accountNameTextView.setText(account.getName());
            accountAmountTextView.setText(Money.format(account.getAmount()));

            accoutList.addView(itemView);
        }
    }
}
