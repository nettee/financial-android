package me.nettee.financial.ui.bill;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.Locale;

import me.nettee.financial.R;
import me.nettee.financial.model.Display;
import me.nettee.financial.model.bill.IncomeEntry;

public class NewBookEntryIncomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_book_entry_income, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fillBookEntry(IncomeEntry.getDefault(), view);
    }

    private void fillBookEntry(IncomeEntry entry, View view) {

        Display sinkAccountDisplay = Display.of(entry.getSinkAccount());
        View sinkAccountAndAmountView = view.findViewById(R.id.income_entry_sink_account_and_amount);
        sinkAccountAndAmountView.<ImageView>findViewById(R.id.entry_account_image)
                .setImageResource(sinkAccountDisplay.icon());
        sinkAccountAndAmountView.<TextView>findViewById(R.id.entry_account_name)
                .setText(sinkAccountDisplay.name());
        sinkAccountAndAmountView.<TextView>findViewById(R.id.entry_account_remark)
                .setText(sinkAccountDisplay.remark());
        sinkAccountAndAmountView.<EditText>findViewById(R.id.entry_amount_content)
                .setText(entry.getAmount().toString());

        LocalDate date = entry.getDate();
        String dateString = String.format(Locale.CHINA, "%d月%d日",
                date.getMonthOfYear(), date.getDayOfMonth());

        View dateAndRemarkView = view.findViewById(R.id.income_entry_date_and_remark);
        dateAndRemarkView.<TextView>findViewById(R.id.entry_date_display)
                .setText(dateString);
        dateAndRemarkView.<EditText>findViewById(R.id.entry_remark_content)
                .setText(entry.getRemark());

    }

}
