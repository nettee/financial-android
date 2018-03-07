package me.nettee.financial.model;

import android.widget.Spinner;

import java.io.Serializable;

public class CreditDate implements Serializable {

    int dateCount;

    public static CreditDate first() {
        CreditDate creditDate = new CreditDate();
        creditDate.dateCount = 1;
        return creditDate;
    }

    public static CreditDate day(int day) {
        CreditDate creditDate = new CreditDate();
        creditDate.dateCount = day;
        return creditDate;
    }

    public static CreditDate fromSpinner(Spinner spinner) {
        CreditDate creditDate = new CreditDate();
        creditDate.dateCount = spinner.getSelectedItemPosition() + 1;
        return creditDate;
    }

    public int toPosition() {
        return dateCount - 1;
    }
}
