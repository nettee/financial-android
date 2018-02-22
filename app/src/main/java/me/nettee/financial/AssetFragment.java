package me.nettee.financial;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AssetFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asset, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    }

}
