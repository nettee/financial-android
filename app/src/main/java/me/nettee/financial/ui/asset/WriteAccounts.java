package me.nettee.financial.ui.asset;

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
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.CreditDate;
import me.nettee.financial.model.account.Account;
import me.nettee.financial.model.account.AlipayAccount;
import me.nettee.financial.model.account.BusCardAccount;
import me.nettee.financial.model.account.CampusCardAccount;
import me.nettee.financial.model.account.CashAccount;
import me.nettee.financial.model.account.CashCardAccount;
import me.nettee.financial.model.account.CreditCardAccount;
import me.nettee.financial.model.account.DebitCardAccount;
import me.nettee.financial.model.account.InvestmentAccount;
import me.nettee.financial.model.account.WeixinAccount;
import me.nettee.financial.model.investment.InvestmentPlatform;

import static android.view.View.GONE;

public abstract class WriteAccounts {

    private static final Map<Account.AccountType, Integer> sAccountInputsMap = new HashMap<Account.AccountType, Integer>() {
        private static final long serialVersionUID = 1L;
        {
            put(Account.AccountType.CASH, R.layout.inputs_account_cash);
            put(Account.AccountType.CREDIT_CARD, R.layout.inputs_account_credit_card);
            put(Account.AccountType.DEBIT_CARD, R.layout.inputs_account_debit_card);
            put(Account.AccountType.ALIPAY, R.layout.inputs_account_alipay);
            put(Account.AccountType.WEIXIN, R.layout.inputs_account_weixin);
            put(Account.AccountType.CAMPUS_CARD, R.layout.inputs_account_cash_card);
            put(Account.AccountType.BUS_CARD, R.layout.inputs_account_cash_card);
            put(Account.AccountType.INVESTMENT, R.layout.inputs_account_investment);
        }
    };

    private static final Map<Account.AccountType, InputsInitializer> sInputsInitializerMap = new HashMap<Account.AccountType, InputsInitializer>() {
        private static final long serialVersionUID = 1L;
        {
            put(Account.AccountType.CASH, new CashInputsInitializer());
            put(Account.AccountType.CREDIT_CARD, new CreditCardInputsInitializer());
            put(Account.AccountType.DEBIT_CARD, new DebitCardInputsInitializer());
            put(Account.AccountType.ALIPAY, new AlipayInputsInitializer());
            put(Account.AccountType.WEIXIN, new WeixinInputsInitializer());
            put(Account.AccountType.CAMPUS_CARD, new CashCardInputsInitializer());
            put(Account.AccountType.BUS_CARD, new CashCardInputsInitializer());
            put(Account.AccountType.INVESTMENT, new InvestmentInputsInitializer());
        }
    };

    private static final Map<Account.AccountType, AccountExtractor> sAccountExtractorMap = new HashMap<Account.AccountType, AccountExtractor>() {
        private static final long serialVersionUID = 1L;
        {
            put(Account.AccountType.CASH, new CashAccountExtractor());
            put(Account.AccountType.CREDIT_CARD, new CreditCardAccountExtractor());
            put(Account.AccountType.DEBIT_CARD, new DebitCardAccountExtractor());
            put(Account.AccountType.ALIPAY, new AlipayAccountExtractor());
            put(Account.AccountType.WEIXIN, new WeixinAccountExtractor());
            put(Account.AccountType.CAMPUS_CARD, new CampusCardAccountExtractor());
            put(Account.AccountType.BUS_CARD, new BusCardAccountExtractor());
            put(Account.AccountType.INVESTMENT, new InvestmentAccountExtractor());
        }
    };

    private static final Map<Account.AccountType, AccountFiller> sAccountFillerMap = new HashMap<Account.AccountType, AccountFiller>() {
        private static final long serialVersionUID = 1L;
        {
            put(Account.AccountType.CASH, new CashAccountFiller());
            put(Account.AccountType.CREDIT_CARD, new CreditCardFiller());
            put(Account.AccountType.DEBIT_CARD, new DebitCardAccountFiller());
            put(Account.AccountType.ALIPAY, new AlipayAccountFiller());
            put(Account.AccountType.WEIXIN, new WeixinAccountFiller());
            put(Account.AccountType.CAMPUS_CARD, new CashCardAccountFiller());
            put(Account.AccountType.BUS_CARD, new CashCardAccountFiller());
            put(Account.AccountType.INVESTMENT, new InvestmentAccountFiller());
        }
    };

    static View constructView(Activity activity, Account account) {

        constructTitleBar(activity, account);
        return constructInputs(activity, account);
    }

    private static void constructTitleBar(Activity activity, Account account) {
        Integer layoutResource;
        if (account.getType() == Account.AccountType.CREDIT_CARD || account.getType() == Account.AccountType.DEBIT_CARD) {
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
                .getOrDefault(account.getType(), R.layout.inputs_account_cash);

        ViewStub stub = activity.findViewById(R.id.account_inputs_stub);
        stub.setLayoutResource(layoutResource);
        View inputs = stub.inflate();

        initInputs(activity, inputs, account);

        return inputs;
    }

    private static void initInputs(Activity activity, View inputs, Account account) {

        InputsInitializer initializer = sInputsInitializerMap
                .getOrDefault(account.getType(), new NullInputsInitializer());
        initializer.init(activity, inputs);
    }

    static Account extractAccount(Account.AccountType accountType, View accountInputs) {
        AccountExtractor extractor = sAccountExtractorMap
                .getOrDefault(accountType, new NullAccountExtractor());
        return extractor.extract(accountInputs);
    }

    static void fillAccount(View accountInputs, Account account) {
        AccountFiller filler = sAccountFillerMap
                .getOrDefault(account.getType(), new NullAccountFiller());
        filler.fill(accountInputs, account);
    }

    @FunctionalInterface
    interface InputsInitializer {
        void init(Activity activity, View inputs);
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
            mRemark = accountInputs.findViewById(R.id.account_remark).findViewById(R.id.input_bar_text_content);
            mBalance = accountInputs.findViewById(R.id.account_balance).findViewById(R.id.input_bar_amount_content);
        }
    }

    static class CashInputsInitializer implements InputsInitializer {

        @Override
        public void init(Activity activity, View inputs) {
            inputs.findViewById(R.id.account_remark)
                    .<TextView>findViewById(R.id.input_bar_text_caption)
                    .setText(R.string.caption_remark);
            inputs.findViewById(R.id.account_remark)
                    .<EditText>findViewById(R.id.input_bar_text_content)
                    .setHint(R.string.hint_remark);
            inputs.findViewById(R.id.account_balance)
                    .<TextView>findViewById(R.id.input_bar_amount_caption)
                    .setText(R.string.caption_balance);
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
            mRemark = accountInputs.findViewById(R.id.account_remark)
                    .findViewById(R.id.input_bar_text_content);
            mBankCardNumber = accountInputs.findViewById(R.id.account_bank_card_number)
                    .findViewById(R.id.input_bar_bank_card_number_content);
            mCreditLimit = accountInputs.findViewById(R.id.account_credit_limit)
                    .findViewById(R.id.input_bar_amount_content);
            mBillDate = accountInputs.findViewById(R.id.account_bill_date)
                    .findViewById(R.id.input_bar_credit_date_spinner);
            mPaymentDate = accountInputs.findViewById(R.id.account_payment_date)
                    .findViewById(R.id.input_bar_credit_date_spinner);
            mCurrentArrears = accountInputs.findViewById(R.id.account_arrears)
                    .findViewById(R.id.input_bar_amount_content);
        }
    }

    static class CreditCardInputsInitializer implements InputsInitializer {

        private static String[] getCreditDates() {
            String[] creditDates = new String[28];
            for (int i = 0; i < creditDates.length; i++) {
                creditDates[i] = String.format("每月%d日", i + 1);
            }
            return creditDates;
        }

        @Override
        public void init(Activity activity, View inputs) {

            inputs.findViewById(R.id.account_remark)
                    .<TextView>findViewById(R.id.input_bar_text_caption)
                    .setText(R.string.caption_remark);
            inputs.findViewById(R.id.account_remark)
                    .<EditText>findViewById(R.id.input_bar_text_content)
                    .setHint(R.string.hint_remark);
            inputs.findViewById(R.id.account_bank_card_number)
                    .<TextView>findViewById(R.id.input_bar_bank_card_number_caption)
                    .setText(R.string.caption_bank_card_number);
            inputs.findViewById(R.id.account_bank_card_number)
                    .<EditText>findViewById(R.id.input_bar_bank_card_number_content)
                    .setHint(R.string.hint_bank_card_number);
            inputs.findViewById(R.id.account_credit_limit)
                    .<TextView>findViewById(R.id.input_bar_amount_caption)
                    .setText(R.string.caption_credit_limit);
            inputs.findViewById(R.id.account_bill_date)
                    .<TextView>findViewById(R.id.input_bar_credit_date_caption)
                    .setText(R.string.caption_bill_date);
            inputs.findViewById(R.id.account_payment_date)
                    .<TextView>findViewById(R.id.input_bar_credit_date_caption)
                    .setText(R.string.caption_payment_date);
            inputs.findViewById(R.id.account_arrears)
                    .<TextView>findViewById(R.id.input_bar_amount_caption)
                    .setText(R.string.caption_current_arrears);

            {
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(activity,
                        android.R.layout.simple_spinner_item,
                        getCreditDates());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                inputs.findViewById(R.id.account_bill_date)
                        .<Spinner>findViewById(R.id.input_bar_credit_date_spinner)
                        .setAdapter(adapter);
            }
            {
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(activity,
                        android.R.layout.simple_spinner_item,
                        getCreditDates());
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                inputs.findViewById(R.id.account_payment_date)
                        .<Spinner>findViewById(R.id.input_bar_credit_date_spinner)
                        .setAdapter(adapter);
            }
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
            account.setBillDate(CreditDate.day(mBillDate.getSelectedItemPosition() + 1));
            account.setPaymentDate(CreditDate.day(mPaymentDate.getSelectedItemPosition() + 1));
            Amount arrears = Amount.valueOf(mCurrentArrears.getText().toString());
            arrears.setSign(Amount.NEGATIVE);
            account.setArrears(arrears);
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
            mCurrentArrears.setText(creditCardAccount.getArrears().abs().toString());
        }
    }

    static class DebitCardInputsInitializer implements InputsInitializer {

        @Override
        public void init(Activity activity, View inputs) {

            inputs.findViewById(R.id.account_remark)
                    .<TextView>findViewById(R.id.input_bar_text_caption)
                    .setText(R.string.caption_remark);
            inputs.findViewById(R.id.account_remark)
                    .<EditText>findViewById(R.id.input_bar_text_content)
                    .setHint(R.string.hint_remark);

            inputs.findViewById(R.id.account_bank_card_number)
                    .<TextView>findViewById(R.id.input_bar_bank_card_number_caption)
                    .setText(R.string.caption_bank_card_number);
            inputs.findViewById(R.id.account_bank_card_number)
                    .<EditText>findViewById(R.id.input_bar_bank_card_number_content)
                    .setHint(R.string.hint_bank_card_number);

            inputs.findViewById(R.id.account_balance)
                    .<TextView>findViewById(R.id.input_bar_amount_caption)
                    .setText(R.string.caption_balance);
        }
    }

    static abstract class DebitCardAccountInout {

        protected EditText mRemark;
        protected EditText mBankCardNumber;
        protected EditText mBalance;

        public void pre(View accountInputs) {
            mRemark = accountInputs.findViewById(R.id.account_remark)
                    .findViewById(R.id.input_bar_text_content);
            mBankCardNumber = accountInputs.findViewById(R.id.account_bank_card_number)
                    .findViewById(R.id.input_bar_bank_card_number_content);
            mBalance = accountInputs.findViewById(R.id.account_balance)
                    .findViewById(R.id.input_bar_amount_content);
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

    static class CashCardInputsInitializer implements InputsInitializer {

        @Override
        public void init(Activity activity, View inputs) {
            inputs.findViewById(R.id.account_remark)
                    .<TextView>findViewById(R.id.input_bar_text_caption)
                    .setText(R.string.caption_remark);
            inputs.findViewById(R.id.account_remark)
                    .<EditText>findViewById(R.id.input_bar_text_content)
                    .setHint(R.string.hint_remark);
            inputs.findViewById(R.id.account_balance)
                    .<TextView>findViewById(R.id.input_bar_amount_caption)
                    .setText(R.string.caption_balance);
        }
    }

    static abstract class CashCardAccountInout {

        protected EditText mRemark;
        protected EditText mBalance;

        public void pre(View accountInputs) {
            mRemark = accountInputs.findViewById(R.id.account_remark).findViewById(R.id.input_bar_text_content);
            mBalance = accountInputs.findViewById(R.id.account_balance).findViewById(R.id.input_bar_amount_content);
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

    static abstract class AlipayAccountInout {

        protected EditText mRemark;
        protected EditText mBalance;
        protected Switch mYuebaoOpen;
        protected Switch mHuabeiOpen;

        public void pre(View inputs) {
            mRemark = inputs.findViewById(R.id.account_remark).findViewById(R.id.input_bar_text_content);
            mBalance = inputs.findViewById(R.id.account_balance).findViewById(R.id.input_bar_amount_content);
            mYuebaoOpen = inputs.findViewById(R.id.account_yuebao_open).findViewById(R.id.input_bar_toggle_switch);
            mHuabeiOpen = inputs.findViewById(R.id.account_huabei_open).findViewById(R.id.input_bar_toggle_switch);
        }
    }

    static class AlipayInputsInitializer implements InputsInitializer {

        @Override
        public void init(Activity activity, View inputs) {
            inputs.findViewById(R.id.account_remark)
                    .<TextView>findViewById(R.id.input_bar_text_caption)
                    .setText(R.string.caption_remark);
            inputs.findViewById(R.id.account_remark)
                    .<EditText>findViewById(R.id.input_bar_text_content)
                    .setHint(R.string.hint_remark);
            inputs.findViewById(R.id.account_balance)
                    .<TextView>findViewById(R.id.input_bar_amount_caption)
                    .setText(R.string.caption_balance);
            inputs.findViewById(R.id.account_yuebao_open)
                    .<TextView>findViewById(R.id.input_bar_toggle_caption)
                    .setText(R.string.caption_yuebao_open);
            inputs.findViewById(R.id.account_huabei_open)
                    .<TextView>findViewById(R.id.input_bar_toggle_caption)
                    .setText(R.string.caption_huabei_open);
        }
    }

    static class AlipayAccountExtractor extends AlipayAccountInout implements AccountExtractor {

        @Override
        public Account extract(View accountInputs) {
            pre(accountInputs);
            AlipayAccount account = new AlipayAccount();
            account.setRemark(mRemark.getText().toString());
            account.setBalance(Amount.valueOf(mBalance.getText().toString()));
            account.setYuebaoOpen(mYuebaoOpen.isChecked());
            account.setHuabeiOpen(mHuabeiOpen.isChecked());
            return account;
        }
    }

    static class AlipayAccountFiller extends AlipayAccountInout implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
            pre(accountInputs);
            AlipayAccount alipayAccount = (AlipayAccount) account;
            mRemark.setText(alipayAccount.getRemark());
            mBalance.setText(alipayAccount.getBalance().toString());
            mYuebaoOpen.setChecked(alipayAccount.isYuebaoOpen());
            mHuabeiOpen.setChecked(alipayAccount.isHuabeiOpen());
        }
    }

    static class WeixinInputsInitializer implements InputsInitializer {

        @Override
        public void init(Activity activity, View inputs) {
            inputs.findViewById(R.id.account_remark)
                    .<TextView>findViewById(R.id.input_bar_text_caption)
                    .setText(R.string.caption_remark);
            inputs.findViewById(R.id.account_remark)
                    .<EditText>findViewById(R.id.input_bar_text_content)
                    .setHint(R.string.hint_remark);
            inputs.findViewById(R.id.account_balance)
                    .<TextView>findViewById(R.id.input_bar_amount_caption)
                    .setText(R.string.caption_balance);
        }
    }

    static abstract class WeixinAccountInout {

        protected EditText mRemark;
        protected EditText mBalance;

        public void pre(View accountInputs) {
            mRemark = accountInputs.findViewById(R.id.account_remark).findViewById(R.id.input_bar_text_content);
            mBalance = accountInputs.findViewById(R.id.account_balance).findViewById(R.id.input_bar_amount_content);
        }
    }

    static class WeixinAccountExtractor extends WeixinAccountInout implements AccountExtractor {
        @Override
        public Account extract(View accountInputs) {
            pre(accountInputs);
            WeixinAccount account = new WeixinAccount();
            account.setRemark(mRemark.getText().toString());
            account.setBalance(Amount.valueOf(mBalance.getText().toString()));
            return account;
        }
    }

    static class WeixinAccountFiller extends WeixinAccountInout implements AccountFiller {

        @Override
        public void fill(View accountInputs, Account account) {
            pre(accountInputs);
            WeixinAccount weixinAccount = (WeixinAccount) account;
            mRemark.setText(weixinAccount.getRemark());
            mBalance.setText(weixinAccount.getBalance().toString());
        }
    }

    static abstract class InvestmentAccountInout {

        protected AutoCompleteTextView mPlatform;

        public void pre(View accountInputs) {
            mPlatform = accountInputs.findViewById(R.id.account_investment_platform)
                    .findViewById(R.id.input_bar_investment_platform_content);
        }
    }

    static class InvestmentInputsInitializer implements InputsInitializer {

        @Override
        public void init(Activity activity, View inputs) {
            AutoCompleteTextView investmentPlatform = inputs.findViewById(R.id.account_investment_platform)
                    .findViewById(R.id.input_bar_investment_platform_content);
            ImageView investmentPlatformImage = inputs.findViewById(R.id.account_investment_platform)
                    .findViewById(R.id.input_bar_investment_platform_image);
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

    static class NullInputsInitializer implements InputsInitializer {

        @Override
        public void init(Activity activity, View inputs) {
            // Do nothing.
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
