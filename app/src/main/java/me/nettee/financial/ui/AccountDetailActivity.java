package me.nettee.financial.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import me.nettee.financial.R;

public class AccountDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_detail);

        Toolbar toolbar = findViewById(R.id.toolbar_account_detail);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
