package me.nettee.financial.ui.asset;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toolbar;

import me.nettee.financial.R;

public abstract class NewSomeBaseActivity<T> extends Activity {

    protected T mCandidate;
    protected View mInputs;

    public abstract int getLayout();

    public abstract int getToolbarId();

    public abstract T getCandidate();

    public abstract View getConstructedInputs();

    public abstract View.OnClickListener getOnSaveListener();

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        Toolbar toolbar = findViewById(getToolbarId());
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());

        mCandidate = getCandidate();
        mInputs = getConstructedInputs();

        findViewById(R.id.button_save).setOnClickListener(getOnSaveListener());
    }
}
