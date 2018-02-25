package me.nettee.financial.ui;

import android.app.Activity;
import android.view.View;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import me.nettee.financial.R;
import me.nettee.financial.model.Account;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.CashAccount;
import me.nettee.financial.model.CreditCardAccount;
import me.nettee.financial.model.CreditDate;

public class NewEditAccounts {

    private static final Map<Integer, Integer> sAccountInputsMap = new HashMap<Integer, Integer>() {
        private static final long serialVersionUID = 1L;
        {
            put(Account.CASH, R.layout.account_inputs_cash);
            put(Account.CREDIT_CARD, R.layout.account_inputs_credit_card);
            put(Account.DEBIT_CARD, R.layout.account_inputs_debit_card);
            put(Account.ALIPAY, R.layout.account_inputs_alipay);
            put(Account.WEIXIN, R.layout.account_inputs_weixin);
        }
    };

    public static Map<Integer, AccountExtractor> sAccountExtractorMap = new HashMap<Integer, AccountExtractor>() {
        private static final long serialVersionUID = 1L;
        {
            put(Account.CASH, new CashAccountExtractor());
            put(Account.CREDIT_CARD, new CreditCardAccountExtractor());
        }
    };

    @FunctionalInterface
    public interface AccountExtractor {
        Account extract(View accountInputs);
    }

    public static abstract class CashAccountInout {

        protected EditText mRemark;
        protected EditText mBalanceAmount;

        public void pre(View accountInputs) {
            mRemark = accountInputs.findViewById(R.id.account_remark);
            mBalanceAmount = accountInputs.findViewById(R.id.account_amount);
        }
    }

    public static class CashAccountExtractor extends NewEditAccounts.CashAccountInout
            implements AccountExtractor {

        @Override
        public Account extract(View accountInputs) {
            pre(accountInputs);
            CashAccount account = new CashAccount();
            account.setBalance(Amount.valueOf(mBalanceAmount.getText().toString()));
            account.setRemark(mRemark.getText().toString());
            return account;
        }
    }

    public static abstract class CreditCardAccountInout {

        protected EditText mRemark;
        protected EditText mBankCardNumber;
        protected EditText mCreditLimit;
        protected Spinner mBillDate;
        protected Spinner mPaymentDate;
        protected EditText mCurrentArrears;

        public void pre(View accountInputs) {
            mRemark = accountInputs.findViewById(R.id.account_remark);
            mBankCardNumber = accountInputs.findViewById(R.id.account_bank_card_number);
            mCreditLimit = accountInputs.findViewById(R.id.view_credit_limit)
                    .findViewById(R.id.account_amount);
            mBillDate = accountInputs.findViewById(R.id.view_bill_date)
                    .findViewById(R.id.account_credit_date_spinner);
            mPaymentDate = accountInputs.findViewById(R.id.view_payment_date)
                    .findViewById(R.id.account_credit_date_spinner);
            mCurrentArrears = accountInputs.findViewById(R.id.view_current_arrears)
                    .findViewById(R.id.account_amount);
        }
    }


    public static class CreditCardAccountExtractor extends NewEditAccounts.CreditCardAccountInout
            implements AccountExtractor {

        @Override
        public Account extract(View accountInputs) {
            pre(accountInputs);
            CreditCardAccount account = new CreditCardAccount();
            account.setRemark(mRemark.getText().toString());
            account.setBankCardNumber(mBankCardNumber.getText().toString());
            account.setCreditLimit(Amount.valueOf(mCreditLimit.getText().toString()));
            account.setBillDate(CreditDate.fromSpinner(mBillDate));
            account.setPaymentDate(CreditDate.fromSpinner(mPaymentDate));
            account.setCurrentArrears(Amount.valueOf(mCurrentArrears.getText().toString()));
            return account;
        }
    }

    public static class NullAccountExtractor implements AccountExtractor {

        @Override
        public Account extract(View accountInputs) {
            return null;
        }
    }

    public static View constructView(Activity activity,
                                     int accountType,
                                     int candidateImageResource,
                                     String candidateName) {

        // Construct account title bar.
        {
            Integer layoutResource;
            if (accountType == Account.CREDIT_CARD || accountType == Account.DEBIT_CARD) {
                layoutResource = R.layout.account_title_bar_bank_card;
            } else {
                layoutResource = R.layout.account_title_bar_ordinary;
            }

            ViewStub stub = activity.findViewById(R.id.account_title_bar_stub);
            stub.setLayoutResource(layoutResource);
            View accountTitleBar = stub.inflate();

            accountTitleBar.<ImageView>findViewById(R.id.account_name_image)
                    .setImageResource(candidateImageResource);
            accountTitleBar.<TextView>findViewById(R.id.account_name_text)
                    .setText(candidateName);
        }

        // Construct account inputs.
        {
            Integer layoutResource = sAccountInputsMap
                    .getOrDefault(accountType, R.layout.account_inputs_cash);

            ViewStub stub = activity.findViewById(R.id.account_inputs_stub);
            stub.setLayoutResource(layoutResource);
            View accountInputs = stub.inflate();

            if (accountType == Account.CASH) {
                // Do nothing.
            } else if (accountType == Account.CREDIT_CARD) {
                accountInputs.findViewById(R.id.view_credit_limit)
                        .<TextView>findViewById(R.id.account_amount_caption)
                        .setText(R.string.caption_credit_limit);
                accountInputs.findViewById(R.id.view_current_arrears)
                        .<TextView>findViewById(R.id.account_amount_caption)
                        .setText(R.string.caption_current_arrears);
                accountInputs.findViewById(R.id.view_bill_date)
                        .<TextView>findViewById(R.id.account_credit_date_caption)
                        .setText(R.string.caption_bill_date);
                accountInputs.findViewById(R.id.view_payment_date)
                        .<TextView>findViewById(R.id.account_credit_date_caption)
                        .setText(R.string.caption_payment_date);
                {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                            R.array.bill_dates_array,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    accountInputs.findViewById(R.id.view_bill_date)
                            .<Spinner>findViewById(R.id.account_credit_date_spinner)
                            .setAdapter(adapter);
                }
                {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                            R.array.bill_dates_array,
                            android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    accountInputs.findViewById(R.id.view_payment_date)
                            .<Spinner>findViewById(R.id.account_credit_date_spinner)
                            .setAdapter(adapter);
                }
            }

            return accountInputs;
        }
    }




}
