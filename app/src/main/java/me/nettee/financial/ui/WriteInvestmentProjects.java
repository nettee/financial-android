package me.nettee.financial.ui;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.nettee.financial.R;
import me.nettee.financial.model.investment.InvestmentProject;

public class WriteInvestmentProjects {

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

    private static abstract class InputsConstructor {

        abstract int getLayoutResource();

        View construct(Activity activity, InvestmentProject investmentProject) {
            ViewStub stub = activity.findViewById(R.id.investment_project_inputs_stub);
            stub.setLayoutResource(getLayoutResource());
            View inputs = stub.inflate();

            initInputs(inputs);

            return inputs;
        }

        abstract void initInputs(View inputs);

    }

    private static final class MonetaryFundInputsConstructor extends InputsConstructor {

        @Override
        int getLayoutResource() {
            return R.layout.inputs_investment_project_monetary_fund;
        }

        @Override
        void initInputs(View inputs) {

            View nameView = inputs.findViewById(R.id.investment_project_name);
            nameView.<TextView>findViewById(R.id.input_bar_date_caption).setText(R.string.caption_project_name);
            nameView.<EditText>findViewById(R.id.input_bar_text_content).setHint(R.string.hint_project_name);

            View principleView = inputs.findViewById(R.id.investment_project_principle);
            principleView.<TextView>findViewById(R.id.input_bar_amount_caption).setText(R.string.caption_principle);

            View annualYieldView = inputs.findViewById(R.id.investment_project_annual_yield);
            annualYieldView.<TextView>findViewById(R.id.input_bar_percent_caption).setText(R.string.caption_expected_annual_yield);

            View buyDateView = inputs.findViewById(R.id.investment_project_buy_date);
            buyDateView.<TextView>findViewById(R.id.input_bar_date_caption).setText("买入日期");
            buyDateView.<TextView>findViewById(R.id.input_bar_date_value).setText("2018-02-26");
            buyDateView.<TextView>findViewById(R.id.input_bar_date_value).setOnClickListener(view -> {
                Log.d("TAG", "clicked");
            });

            View valueDateView = inputs.findViewById(R.id.investment_project_value_date);
            valueDateView.<TextView>findViewById(R.id.input_bar_date_caption).setText("起息日期");
            valueDateView.<TextView>findViewById(R.id.input_bar_date_value).setText("2018-02-26");
        }
    }

}
