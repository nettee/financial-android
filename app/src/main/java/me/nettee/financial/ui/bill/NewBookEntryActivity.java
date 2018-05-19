package me.nettee.financial.ui.bill;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.util.SparseArray;
import android.widget.Toast;
import android.widget.Toolbar;

import me.nettee.financial.R;

public class NewBookEntryActivity extends Activity {

    private Fragment mCurrentFragment;
    private SparseArray<Fragment> mFragmentSparseArray = new SparseArray<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_book_entry);

        Toolbar toolbar = findViewById(R.id.toolbar_new_book_entry);
        setActionBar(toolbar);
        getActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(view -> finish());

        initFragments();

        TabLayout tabLayout = findViewById(R.id.tab_layout_book_entry_type);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // Do nothing.
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // Do nothing.
            }
        });
    }

    private void initFragments() {
        mCurrentFragment = new NewBookEntryIncomeFragment();
        mFragmentSparseArray.put(0, mCurrentFragment);
        mFragmentSparseArray.put(1, new NewBookEntryOutcomeFragment());
        mFragmentSparseArray.put(2, new NewBookEntryTransferFragment());

        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mCurrentFragment)
                .commit();
    }

    private void switchFragment(int tabPosition) {
        Fragment fragment = mFragmentSparseArray.get(tabPosition);
        if (fragment == null) {
            Toast.makeText(getApplicationContext(), "Error: cannot find fragment", Toast.LENGTH_SHORT).show();
        }

        if (fragment == mCurrentFragment) {
            return;
        }

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        mCurrentFragment = fragment;
    }
}
