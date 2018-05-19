package me.nettee.financial.ui.bill;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.joda.time.LocalDate;

import java.util.Locale;

import me.nettee.financial.R;

public class BillFragment extends Fragment {

    private LinearLayout mBillListList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bill, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mBillListList = view.findViewById(R.id.bill_list_list);

        mBillListList.removeAllViews();

        LayoutInflater inflater = LayoutInflater.from(getActivity());

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        int month = now.getMonthOfYear();

        for (int m = month; m > 0; m--) {

            View billList = inflater.inflate(R.layout.title_bar_bill_list, null);
            int itemHeight = (int) getResources().getDimension(R.dimen.height_title_bar_category);
            billList.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));

            TextView billListTitle = billList.findViewById(R.id.bill_list_title);
            billListTitle.setText(String.format(Locale.CHINA, "%d年%d月", year, m));

            mBillListList.addView(billList);
        }
    }

}
