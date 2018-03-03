package me.nettee.financial.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import java.util.List;

import me.nettee.financial.R;

public abstract class SomeCandidateBaseActivity<C> extends Activity {

    public abstract int getLayout();

    public abstract int getToolbarId();

    public abstract int getCandidateListViewId();

    public abstract int getListItemLayout();

    public abstract List<C> getData();

    public abstract void initItemView(View itemView, C candidate);

    public abstract void processItemView(View itemView, C candidate);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayout());

        Toolbar toolbar = findViewById(getToolbarId());
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());

        LinearLayout candidateListView = findViewById(getCandidateListViewId());
        LayoutInflater inflater = LayoutInflater.from(this);
        List<C> candidates = getData();

        for (C candidate : candidates) {

            View itemView = inflater.inflate(getListItemLayout(), null);
            int itemHeight = (int) getResources().getDimension(R.dimen.height_item);
            itemView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight));

            initItemView(itemView, candidate);

            candidateListView.addView(itemView);

            processItemView(itemView, candidate);
        }
    }
}
