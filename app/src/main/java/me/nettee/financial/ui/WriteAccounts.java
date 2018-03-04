package me.nettee.financial.ui;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.CreditDate;
import me.nettee.financial.model.investment.InvestmentPlatform;
import me.nettee.financial.model.account.Account;
import me.nettee.financial.model.account.AlipayAccount;
import me.nettee.financial.model.account.BusCardAccount;
import me.nettee.financial.model.account.CampusCardAccount;
import me.nettee.financial.model.account.CashAccount;
import me.nettee.financial.model.account.CashCardAccount;
import me.nettee.financial.model.account.CreditCardAccount;
import me.nettee.financial.model.account.DebitCardAccount;
import me.nettee.financial.model.account.InvestmentAccount;
import me.nettee.financial.model.account.MobilePaymentAccount;
import me.nettee.financial.model.account.WeixinAccount;

import static android.view.View.GONE;

public abstract class WriteAccounts {

    private static final Map<Integer, Integer> sAccountInputsMap = new HashMap<Integer, Integer>() {
        private static final long serialVersionUID = 1L;
        {
            put(Account.CASH, R.layout.account_inputs_cash);
            put(Account.CREDIT_CARD, R.layout.account_inputs_credit_card);
            put(Account.DEBIT_CARD, R.layout.account_inputs_debit_card);
            put(Account.ALIPAY, R.layout.account_inputs_alipay);
            put(Account.WEIXIN, R.layout.account_inputs_weixin);
            put(Account.CAMPUS_CARD, R.layout.account_inputs_cash_card);
            put(Account.BUS_CARD, R.layout.account_inputs_cash_card);
            put(Account.INVESTMENT, R.layout.account_inputs_investment);
        }
    };

    protected static Map<Integer, AccountExtractor> sAccountExtractorMap = new HashMap<Integer, AccountExtractor>() {
        private static final long serialVersionUID = 1L;
        {
            put(Account.CASH, new CashAccountExtractor());
            put(Account.CREDIT_CARD, new CreditCardAccountExtractor());
            put(Account.DEBIT_CARD, new DebitCardAccountExtractor());
            put(Account.ALIPAY, new AlipayAccountExtractor());
            put(Account.WEIXIN, new WeixinAccountExtractor());
            put(Account.CAMPUS_CARD, new CampusCardAccountExtractor());
            put(Account.BUS_CARD, new BusCardAccountExtractor());
            put(Account.INVESTMENT, new InvestmentAccountExtractor());
        }
    };

    protected static Map<Integer, AccountFiller> sAccountFillerMap = new HashMap<Integer, AccountFiller>() {
        private static final long serialVersionUID = 1L;
        {
            put(Account.CASH, new CashAccountFiller());
            put(Account.CREDIT_CARD, new CreditCardFiller());
            put(Account.DEBIT_CARD, new DebitCardAccountFiller());
            put(Account.ALIPAY, new MobilePaymentAccountFiller());
            put(Account.WEIXIN, new MobilePaymentAccountFiller());
            put(Account.CAMPUS_CARD, new CashCardAccountFiller());
            put(Account.BUS_CARD, new CashCardAccountFiller());
            put(Account.INVESTMENT, new InvestmentAccountFiller());
        }
    };

    public static View constructView(Activity activity, Account account) {

        constructTitleBar(activity, account);
        return constructInputs(activity, account);
    }

    private static void constructTitleBar(Activity activity, Account account) {
        Integer layoutResource;
        if (account.getType() == Account.CREDIT_CARD || account.getType() == Account.DEBIT_CARD) {
            layoutResource = R.layout.title_bar_account_write_bank_card;
        } else {
            layoutResource = R.layout.title_bar_account_write_ordinary;
        }

        ViewStub stub = activity.findViewById(R.id.account_title_bar_stub);
        stub.setLayoutResource(layoutResource);
        View titleBar = stub.inflate();

        titleBar.<ImageView>findViewById(R.id.account_name_image)
                .setImageResource(account.getCandidateImageResource());
        titleBar.<TextView>findViewById(R.id.account_name_text)
                .setText(account.getCandidateName());
    }

    private static View constructInputs(Activity activity, Account account) {
        Integer layoutResource = sAccountInputsMap
                .getOrDefault(account.getType(), R.layout.account_inputs_cash);

        ViewStub stub = activity.findViewById(R.id.account_inputs_stub);
        stub.setLayoutResource(layoutResource);
        View inputs = stub.inflate();

        initInputs(activity, inputs, account);

        return inputs;
    }

    private static void initInputs(Activity activity, View inputs, Account account) {
        if (account.getType() == Account.CASH) {
            // Do nothing.
        } else if (account.getType() == Account.CREDIT_CARD) {
            inputs.findViewById(R.id.view_credit_limit)
                    .<TextView>findViewById(R.id.input_bar_amount_caption)
                    .setText(R.string.caption_credit_limit);
            inputs.findViewById(R.id.view_current_arrears)
                    .<TextView>findViewById(R.id.input_bar_amount_caption)
                    .setText(R.string.caption_current_arrears);
            inputs.findViewById(R.id.view_bill_date)
                    .<TextView>findViewById(R.id.account_credit_date_caption)
                    .setText(R.string.caption_bill_date);
            inputs.findViewById(R.id.view_payment_date)
                    .<TextView>findViewById(R.id.account_credit_date_caption)
                    .setText(R.string.caption_payment_date);
            {
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                        R.array.bill_dates_array,
                        android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                inputs.findViewById(R.id.view_bill_date)
                        .<Spinner>findViewById(R.id.account_credit_date_spinner)
                        .setAdapter(adapter);
            }
            {
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(activity,
                        R.array.bill_dates_array,
                        android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                inputs.findViewById(R.id.view_payment_date)
                        .<Spinner>findViewById(R.id.account_credit_date_spinner)
                        .setAdapter(adapter);
            }
        } else if (account.getType() == Account.INVESTMENT) {
            AutoCompleteTextView investmentPlatform = inputs.findViewById(R.id.account_investment_platform);
            ImageView investmentPlatformImage = inputs.findViewById(R.id.account_investment_platform_image);
            InvestmentPlatformAdapter adapter = new InvestmentPlatformAdapter(activity, InvestmentPlatform.getPlatforms());
            investmentPlatform.setAdapter(adapter);
            investmentPlatform.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    // Do nothing.
                }

                @Override
                public void onTextChanged(CharSequence text, int i, int i1, int i2) {
                    InvestmentPlatform platform = InvestmentPlatform.getPlatformByName(text.toString());
                    if (platform != null) {
                        investmentPlatformImage.setVisibility(View.VISIBLE);
                        investmentPlatformImage.setImageResource(platform.getImageResource());
                    } else {
                        investmentPlatformImage.setVisibility(GONE);
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    // Do nothing.
                }
            });
        }
    }

    public static Account extractAccount(int accountType, View accountInputs) {
        AccountExtractor extractor = sAccountExtractorMap
                .getOrDefault(accountType, new NullAccountExtractor());
        Account account = extractor.extract(accountInputs);
        return account;
    }

    public static void fillAccount(View accountInputs, Account account) {
        AccountFiller filler = sAccountFillerMap
                .getOrDefault(account.getType(), new NullAccountFiller());
        filler.fill(accountInputs, account);
    }

    @FunctionalInterface
    interface AccountExtractor {
        Account extract(View accountInputs);
    }

    @FunctionalInterface
    interface AccountFiller {
        void fill(View accountInputs, Account account);
    }

    static abstract class CashAccountInout {

        protected EditText mRemark;
        protected EditText mBalance;

        public void pre(View accountInputs) {
            mRemark = accountInputs.findViewById(R.id.account_remark);
            mBalance = accountInputs.findViewById(R.id.account_amount);
        }
    }

    static class CashAccountExtractor extends CashAccountInout implements AccountExtractor {

        @Override
        public Account extract(View accountInputs) {
            pre(accountInputs);
            CashAccount account = new CashAccount();
            account.setBalance(Amount.valueOf(mBalance.getText().toString()));
            account.setRemark(mRemark.getText().toString());
            return account;
        }
    }

    static class CashAccountFiller extends CashAccountInout implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
            pre(accountInputs);
            CashAccount cashAccount = (CashAccount) account;
            mRemark.setText(cashAccount.getRemark());
            mBalance.setText(cashAccount.getBalance().toString());
        }
    }

    static abstract class CreditCardAccountInout {

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

    static class CreditCardAccountExtractor extends CreditCardAccountInout implements AccountExtractor {
        @Override
        public Account extract(View accountInputs) {
            pre(accountInputs);
            CreditCardAccount account = new CreditCardAccount();
            account.setRemark(mRemark.getText().toString());
            account.setBankCardNumber(mBankCardNumber.getText().toString());
            account.setCreditLimit(Amount.valueOf(mCreditLimit.getText().toString()));
            account.setBillDate(CreditDate.fromSpinner(mBillDate));
            account.setPaymentDate(CreditDate.fromSpinner(mPaymentDate));
            Amount arrears = Amount.valueOf(mCurrentArrears.getText().toString());
            arrears.setSign(Amount.NEGATIVE);
            account.setCurrentArrears(arrears);
            return account;
        }

    }

    static class CreditCardFiller extends CreditCardAccountInout implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
            pre(accountInputs);
            CreditCardAccount creditCardAccount = (CreditCardAccount) account;
            mRemark.setText(creditCardAccount.getRemark());
            mBankCardNumber.setText(creditCardAccount.getBankCardNumber());
            mCreditLimit.setText(creditCardAccount.getCreditLimit().toString());
            mBillDate.setSelection(creditCardAccount.getBillDate().toPosition());
            mPaymentDate.setSelection(creditCardAccount.getPaymentDate().toPosition());
            mCurrentArrears.setText(creditCardAccount.getCurrentArrears().toString());
        }
    }

    static abstract class DebitCardAccountInout {

        protected EditText mRemark;
        protected EditText mBankCardNumber;
        protected EditText mBalance;

        public void pre(View accountInputs) {
            mRemark = accountInputs.findViewById(R.id.account_remark);
            mBankCardNumber = accountInputs.findViewById(R.id.account_bank_card_number);
            mBalance = accountInputs.findViewById(R.id.view_balance)
                    .findViewById(R.id.account_amount);
        }

    }

    static class DebitCardAccountExtractor extends DebitCardAccountInout implements AccountExtractor {
        @Override
        public Account extract(View accountInputs) {
            pre(accountInputs);
            DebitCardAccount account = new DebitCardAccount();
            account.setRemark(mRemark.getText().toString());
            account.setBankCardNumber(mBankCardNumber.getText().toString());
            account.setBalance(Amount.valueOf(mBalance.getText().toString()));
            return account;
        }

    }

    static class DebitCardAccountFiller extends DebitCardAccountInout implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
            pre(accountInputs);
            DebitCardAccount debitCardAccount = (DebitCardAccount) account;
            mRemark.setText(debitCardAccount.getRemark());
            mBankCardNumber.setText(debitCardAccount.getBankCardNumber());
            mBalance.setText(debitCardAccount.getBalance().toString());
        }
    }

    static abstract class CashCardAccountInout {

        protected EditText mRemark;
        protected EditText mBalance;

        public void pre(View accountInputs) {
            mRemark = accountInputs.findViewById(R.id.account_remark);
            mBalance = accountInputs.findViewById(R.id.account_amount);
        }
    }

    static class CampusCardAccountExtractor extends CashCardAccountInout implements AccountExtractor {

        @Override
        public Account extract(View accountInputs) {
            pre(accountInputs);
            CashCardAccount account = new CampusCardAccount();
            account.setRemark(mRemark.getText().toString());
            account.setBalance(Amount.valueOf(mBalance.getText().toString()));
            return account;
        }
    }

    static class BusCardAccountExtractor extends CashCardAccountInout implements AccountExtractor {

        @Override
        public Account extract(View accountInputs) {
            pre(accountInputs);
            CashCardAccount account = new BusCardAccount();
            account.setRemark(mRemark.getText().toString());
            account.setBalance(Amount.valueOf(mBalance.getText().toString()));
            return account;
        }
    }

    static class CashCardAccountFiller extends CashCardAccountInout implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
            pre(accountInputs);
            CashCardAccount cashCardAccount = (CashCardAccount) account;
            mRemark.setText(cashCardAccount.getRemark());
            mBalance.setText(cashCardAccount.getBalance().toString());
        }
    }

    static abstract class MobilePaymentAccountInout {

        protected EditText mRemark;
        protected EditText mBalance;

        public void pre(View accountInputs) {
            mRemark = accountInputs.findViewById(R.id.account_remark);
            mBalance = accountInputs.findViewById(R.id.account_amount);
        }
    }

    static class AlipayAccountExtractor extends MobilePaymentAccountInout implements AccountExtractor {

        @Override
        public Account extract(View accountInputs) {
            pre(accountInputs);
            AlipayAccount account = new AlipayAccount();
            account.setRemark(mRemark.getText().toString());
            account.setBalance(Amount.valueOf(mBalance.getText().toString()));
            return account;
        }
    }

    static class WeixinAccountExtractor extends MobilePaymentAccountInout implements AccountExtractor {

        @Override
        public Account extract(View accountInputs) {
            pre(accountInputs);
            WeixinAccount account = new WeixinAccount();
            account.setRemark(mRemark.getText().toString());
            account.setBalance(Amount.valueOf(mBalance.getText().toString()));
            return account;
        }
    }

    static class MobilePaymentAccountFiller extends MobilePaymentAccountInout implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
            pre(accountInputs);
            MobilePaymentAccount mobilePaymentAccount = (MobilePaymentAccount) account;
            mRemark.setText(mobilePaymentAccount.getRemark());
            mBalance.setText(mobilePaymentAccount.getBalance().toString());
        }
    }

    static abstract class InvestmentAccountInout {

        protected AutoCompleteTextView mPlatform;
        public void pre(View accountInputs) {
            mPlatform = accountInputs.findViewById(R.id.account_investment_platform);
        }

    }

    static class InvestmentAccountExtractor extends InvestmentAccountInout implements AccountExtractor {
        @Override
        public Account extract(View accountInputs) {
            pre(accountInputs);
            InvestmentAccount account = new InvestmentAccount();
            account.setPlatform(InvestmentPlatform.getPlatformOrGeneral(mPlatform.getText().toString()));
            return account;
        }
    }

    static class InvestmentAccountFiller extends InvestmentAccountInout implements AccountFiller {
        @Override
        public void fill(View accountInputs, Account account) {
            pre(accountInputs);
            InvestmentAccount investmentAccount = (InvestmentAccount) account;
            mPlatform.setText(investmentAccount.getPlatform().getName());
        }
    }

    static class InvestmentPlatformAdapter extends ArrayAdapter<InvestmentPlatform> {

        private List<InvestmentPlatform> mPlatforms;

        public InvestmentPlatformAdapter(@NonNull Context context, List<InvestmentPlatform> platforms) {
            super(context, 0, platforms);
            // Note: copy a list here, otherwise no suggestions for the second time.
            mPlatforms = new ArrayList<>(platforms);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            InvestmentPlatform platform = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.dropdown_item_investment_platform, parent, false);
            }

            convertView.<ImageView>findViewById(R.id.investment_platform_image)
                    .setImageResource(platform.getImageResource());
            convertView.<TextView>findViewById(R.id.investment_platform_text)
                    .setText(platform.getName());

            return convertView;
        }
        @NonNull
        @Override
        public Filter getFilter() {
            return new Filter() {

                @Override
                public CharSequence convertResultToString(Object resultValue) {
                    return ((InvestmentPlatform) resultValue).getName();
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    List<InvestmentPlatform> suggestions = mPlatforms.stream()
                            .filter(platform -> platform.getName().contains(constraint))
                            .collect(Collectors.toList());
                    FilterResults filterResults = new FilterResults();
                    filterResults.values = suggestions;
                    filterResults.count = suggestions.size();
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                    if (filterResults == null || filterResults.count <= 0) {
                        return;
                    }

                    ArrayList<InvestmentPlatform> values = (ArrayList<InvestmentPlatform>) filterResults.values;

                    clear();
                    for (InvestmentPlatform platform : values) {
                        add(platform);
                    }
                    notifyDataSetChanged();
                }
            };
        }

    }

    static class NullAccountExtractor implements AccountExtractor {
        @Override
        public Account extract(View accountInputs) {
            return null;
        }

    }

    static class NullAccountFiller implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
            // Do nothing.
        }
    }


}
