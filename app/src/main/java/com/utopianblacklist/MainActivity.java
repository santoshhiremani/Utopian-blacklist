package com.utopianblacklist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.utopianblocklist.R;
import com.utopianblacklist.views.FragmentBannedList;
import com.utopianblacklist.views.FragmentSearch;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment = null;
    private FragmentTransaction fragmentTransaction;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new FragmentSearch();
                    switchFragment(fragment);
                    return true;
                case R.id.navigation_dashboard:
                    fragment = new FragmentBannedList();
                    switchFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    /**
     * @param fragment
     */
    private void switchFragment(Fragment fragment) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // for showing the default fragment in the FrameLayout
        FragmentSearch importFragment = new FragmentSearch();
        switchFragment(importFragment);
    }

}
