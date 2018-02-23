package me.nettee.financial.ui;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.HashMap;
import java.util.Map;

import me.nettee.financial.R;

public class MainActivity extends Activity {

    private Map<Integer, Fragment> mFragmentMap = new HashMap<>();
    private Fragment mCurrentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation);
        bnve.enableAnimation(false);
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setIconSize(20, 20);

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

        mCurrentFragment = new AssetFragment();
        mFragmentMap.put(R.id.menu_asset, mCurrentFragment);
        mFragmentMap.put(R.id.menu_bill, new BillFragment());
        mFragmentMap.put(R.id.menu_statement, new StatementFragment());
        mFragmentMap.put(R.id.menu_me, new MeFragment());

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

        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
        mCurrentFragment = fragment;
    }
}
