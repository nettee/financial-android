package me.nettee.financial.ui.asset;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MoneyAmountInputWatcher implements TextWatcher {

    public static final int INTEGER_PART_MAX = 7;
    public static final int DECIMAL_PART_MAX = 2;

    private final EditText mEditText;

    public MoneyAmountInputWatcher(EditText editText) {
        mEditText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void afterTextChanged(Editable editable) {

        int i = mEditText.getSelectionStart();// position of cursor
        String str = editable.toString();

        if (!str.contains(".")) {
            if (str.length() > INTEGER_PART_MAX) {
                editable.delete(i - 1, i);
            }
            return;
        }

        int k = str.indexOf('.');
        if (str.length() - 1 - k > DECIMAL_PART_MAX) {
            editable.delete(i - 1, i);
        }
    }
}
