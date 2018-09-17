package com.utopianblacklist.views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utopianblocklist.R;

import butterknife.BindView;

public class FragmentSearch extends Fragment {

    @BindView(R.id.ban_period)
    TextView txtBanPeriod;

    @BindView(R.id.ban_since)
    TextView txtBanSince;

    @BindView(R.id.ban_until)
    TextView txtBanUntil;

    @BindView(R.id.blacklist)
    TextView txtBlackList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,
                container, false);
        return view;
    }
}
