package me.nettee.financial.ui.asset;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.HashMap;
import java.util.Map;

import me.nettee.financial.R;
import me.nettee.financial.model.Amount;
import me.nettee.financial.model.Percent;
import me.nettee.financial.model.investment.InvestmentProject;
import me.nettee.financial.model.investment.MonetaryFundInvestmentProject;

public class WriteInvestmentProjects {

    private static Map<Integer, InvestmentProjectExtractor> sExtractorMap = new HashMap<Integer, InvestmentProjectExtractor>() {
        private static final long serialVersionUID = 1L;
        {
            put(InvestmentProject.MONETARY_FUND, new MonetaryFundInvestmentProjectExtractor());
        }
    };

    public static View constructView(Activity activity, InvestmentProject investmentProject) {

        constructTitleBar(activity, investmentProject);
        return constructInputs(activity, investmentProject);
    }

    private static void constructTitleBar(Activity activity, InvestmentProject investmentProject) {

        int layoutResource = R.layout.title_bar_investment_project_write;

        ViewStub stub = activity.findViewById(R.id.investment_project_title_bar_stub);
        stub.setLayoutResource(layoutResource);
        View titleBar = stub.inflate();

        titleBar.<ImageView>findViewById(R.id.investment_project_name_image)
                .setImageResource(investmentProject.getCandidateImageResource());
        titleBar.<TextView>findViewById(R.id.investment_project_name_text)
                .setText(investmentProject.getCandidateName());
    }

    private static View constructInputs(Activity activity, InvestmentProject investmentProject) {

        InputsConstructor constructor = new MonetaryFundInputsConstructor();

        return constructor.construct(activity, investmentProject);
    }

    public static InvestmentProject extractInvestmentProject(int investmentProjectType, View inputs) {
        InvestmentProjectExtractor extractor = sExtractorMap.getOrDefault(investmentProjectType, new NullExtractor());
        return extractor.extract(inputs);
    }

    private static abstract class InputsConstructor {

        abstract int getLayoutResource();

        View construct(Activity activity, InvestmentProject investmentProject) {
            ViewStub stub = activity.findViewById(R.id.investment_project_inputs_stub);
            stub.setLayoutResource(getLayoutResource());
            View inputs = stub.inflate();

            initInputs(activity, inputs);

            return inputs;
        }

        abstract void initInputs(Activity activity, View inputs);

    }

    @FunctionalInterface
    private interface InvestmentProjectExtractor {
        InvestmentProject extract(View inputs);
    }

    private static final class MonetaryFundInputsConstructor extends InputsConstructor {

        static final String DATE_PATTERN = "yyyy-MM-dd";

        @Override
        int getLayoutResource() {
            return R.layout.inputs_investment_project_monetary_fund;
        }

        @Override
        void initInputs(Activity activity, View inputs) {

            View nameView = inputs.findViewById(R.id.investment_project_name);
            nameView.<TextView>findViewById(R.id.input_bar_text_multiline_caption).setText(R.string.caption_project_name);
            nameView.<EditText>findViewById(R.id.input_bar_text_content).setHint(R.string.hint_project_name);

            View principleView = inputs.findViewById(R.id.investment_project_principle);
            principleView.<TextView>findViewById(R.id.input_bar_amount_caption).setText(R.string.caption_principle);

            View annualYieldView = inputs.findViewById(R.id.investment_project_annual_yield);
            annualYieldView.<TextView>findViewById(R.id.input_bar_percent_caption).setText(R.string.caption_expected_annual_yield);

            View buyDateView = inputs.findViewById(R.id.investment_project_buy_date);
            buyDateView.<TextView>findViewById(R.id.input_bar_date_caption).setText(R.string.caption_buy_date);
            TextView buyDateTextView = buyDateView.findViewById(R.id.input_bar_date_value);
            buyDateTextView.setText(LocalDate.now().toString(DATE_PATTERN));

            View valueDateView = inputs.findViewById(R.id.investment_project_value_date);
            valueDateView.<TextView>findViewById(R.id.input_bar_date_caption).setText(R.string.caption_value_date);
            TextView valueDateTextView = valueDateView.findViewById(R.id.input_bar_date_value);
            valueDateTextView.setText(MonetaryFundInvestmentProject.getValueDateFromBuyDate(LocalDate.now()).toString(DATE_PATTERN));

            buyDateTextView.setOnClickListener(view -> {
                LocalDate date0 = new LocalDate(buyDateTextView.getText().toString());
                int y = date0.getYear();
                int m = date0.getMonthOfYear();
                int d = date0.getDayOfMonth();
                DatePickerDialog.OnDateSetListener listener1 = (datePicker, year, month, dayOfMonth) -> {
                    LocalDate newDate = new LocalDate(year, month + 1, dayOfMonth);
                    buyDateTextView.setText(newDate.toString(DATE_PATTERN));
                    valueDateTextView.setText(MonetaryFundInvestmentProject.getValueDateFromBuyDate(newDate).toString(DATE_PATTERN));
                };
                new DatePickerDialog(activity, listener1, y, m - 1, d).show();
            });

            valueDateTextView.setOnClickListener(view -> {
                LocalDate date0 = new LocalDate(valueDateTextView.getText().toString());
                int y = date0.getYear();
                int m = date0.getMonthOfYear();
                int d = date0.getDayOfMonth();
                DatePickerDialog.OnDateSetListener listener = (datePicker, year, month, dayOfMonth) -> {
                    LocalDate newDate = new LocalDate(year, month + 1, dayOfMonth);
                    valueDateTextView.setText(newDate.toString(DATE_PATTERN));
                };
                new DatePickerDialog(activity, listener, y, m - 1, d).show();
            });

            View postscriptView = inputs.findViewById(R.id.investment_project_postscript);
            postscriptView.<TextView>findViewById(R.id.input_bar_text_multiline_caption).setText(R.string.caption_postscript);
            postscriptView.<EditText>findViewById(R.id.input_bar_text_multiline_content).setHint(R.string.hint_postscript);
        }

    }

    private static class MonetaryFundInvestmentProjectExtractor implements InvestmentProjectExtractor {

        private EditText mName;
        private EditText mPrinciple;
        private EditText mAnnualYield;
        private TextView mBuyDate;
        private TextView mValueDate;
        private EditText mPostscript;

        @Override
        public InvestmentProject extract(View inputs) {
            mName = inputs.findViewById(R.id.investment_project_name).findViewById(R.id.input_bar_text_content);
            mPrinciple = inputs.findViewById(R.id.investment_project_principle).findViewById(R.id.input_bar_amount_content);
            mAnnualYield = inputs.findViewById(R.id.investment_project_annual_yield).findViewById(R.id.input_bar_percent_content);
            mBuyDate = inputs.findViewById(R.id.investment_project_buy_date).findViewById(R.id.input_bar_date_value);
            mValueDate = inputs.findViewById(R.id.investment_project_value_date).findViewById(R.id.input_bar_date_value);
            mPostscript = inputs.findViewById(R.id.investment_project_postscript).findViewById(R.id.input_bar_text_multiline_content);

            MonetaryFundInvestmentProject monetaryFund = new MonetaryFundInvestmentProject();
            monetaryFund.setName(mName.getText().toString());
            monetaryFund.setPrinciple(Amount.valueOf(mPrinciple.getText().toString()));
            monetaryFund.setAnnualYield(Percent.valueOf(mAnnualYield.getText().toString()));
            monetaryFund.setBuyDate(new LocalDate(mBuyDate.getText().toString()));
            monetaryFund.setValueDate(new LocalDate(mValueDate.getText().toString()));
            monetaryFund.setPostscript(mPostscript.getText().toString());
            return monetaryFund;
        }
    }

    private static class NullExtractor implements InvestmentProjectExtractor {

        @Override
        public InvestmentProject extract(View inputs) {
            return null;
        }
    }

}
