package me.nettee.financial;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {

    private Map<Integer, Integer> mFragmentTitleMap = new HashMap<>();
    private Map<Integer, Fragment> mFragmentMap = new HashMap<>();
    private Fragment mCurrentFragment;

    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);

        final BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);

        bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switchFragment(item.getItemId());
                return true; // should be true here
            }
        });

        initFragments();

    }

    private void initFragments() {

        mFragmentTitleMap.put(R.id.menu_asset, R.string.nav_asset);
        mFragmentTitleMap.put(R.id.menu_bill, R.string.nav_bill);
        mFragmentTitleMap.put(R.id.menu_statement, R.string.nav_statement);
        mFragmentTitleMap.put(R.id.menu_me, R.string.nav_me);

        mCurrentFragment = new AssetFragment();
        mFragmentMap.put(R.id.menu_asset, mCurrentFragment);
        mFragmentMap.put(R.id.menu_bill, new BillFragment());
        mFragmentMap.put(R.id.menu_statement, new StatementFragment());
        mFragmentMap.put(R.id.menu_me, new MeFragment());

        mToolbarTitle.setText(R.string.nav_asset);

        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mCurrentFragment)
                .commit();
    }

    private void switchFragment(int itemId) {
        Fragment fragment = mFragmentMap.get(itemId);
        if (fragment == null) {
            Toast.makeText(getApplicationContext(), "Error: cannot find fragment", Toast.LENGTH_SHORT).show();
        }

        if (fragment == mCurrentFragment) {
            return;
        }

        int stringId = mFragmentTitleMap.get(itemId);
        mToolbarTitle.setText(stringId);

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        mCurrentFragment = fragment;
    }
}
