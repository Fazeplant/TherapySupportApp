package michaelbumes.therapysupportapp.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavSwitchController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;
import com.ncapdevi.fragnav.tabhistory.FragNavTabHistoryController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.fragments.BaseFragment;
import michaelbumes.therapysupportapp.fragments.CalendarFragment;
import michaelbumes.therapysupportapp.fragments.DrugPlanFragment;
import michaelbumes.therapysupportapp.fragments.SettingsFragment;
import michaelbumes.therapysupportapp.fragments.TodayFragment;


public class MainActivity extends AppCompatActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener{
    private final int INDEX_TODAY = FragNavController.TAB1;
    private final int INDEX_DRUGPLAN = FragNavController.TAB2;
    private final int INDEX_CLENDAR = FragNavController.TAB3;
    private final int INDEX_SETTINGS = FragNavController.TAB4;
    private FragNavController mNavController;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BottomBar bottomBar = findViewById(R.id.bottomBar);
        boolean initial = savedInstanceState == null;
        if (initial) {
            bottomBar.selectTabAtPosition(INDEX_CLENDAR);
        }
        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container)
                .transactionListener(this)
                .rootFragmentListener(this, 5)
                .popStrategy(FragNavTabHistoryController.UNIQUE_TAB_HISTORY)
                .switchController(new FragNavSwitchController() {
                    @Override
                    public void switchTab(int index, FragNavTransactionOptions transactionOptions) {
                        bottomBar.selectTabAtPosition(index);
                    }
                })
                .build();


        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.bb_menu_recents:
                        mNavController.switchTab(INDEX_TODAY);
                        break;
                    case R.id.bb_menu_favorites:
                        mNavController.switchTab(INDEX_DRUGPLAN);
                        break;
                    case R.id.bb_menu_nearby:
                        mNavController.switchTab(INDEX_CLENDAR);
                        break;
                    case R.id.bb_menu_friends:
                        mNavController.switchTab(INDEX_SETTINGS);
                        break;

                }
            }
        }, initial);

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
                mNavController.clearStack();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (!mNavController.popFragment()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mNavController != null) {
            mNavController.onSaveInstanceState(outState);
        }
    }

    @Override
    public void pushFragment(Fragment fragment) {
        if (mNavController != null) {
            mNavController.pushFragment(fragment);
        }
    }

    @Override
    public void onTabTransaction(Fragment fragment, int index) {
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }


    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        //do fragmentty stuff. Maybe change title, I'm not going to tell you how to live your life
        // If we have a backstack, show the back button
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }

    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case INDEX_TODAY:
                return TodayFragment.newInstance(0);
            case INDEX_DRUGPLAN:
                return DrugPlanFragment.newInstance(0);
            case INDEX_CLENDAR:
                return CalendarFragment.newInstance(0);
            case INDEX_SETTINGS:
                return SettingsFragment.newInstance(0);
        }
        throw new IllegalStateException("Need to send an index that we know");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mNavController.popFragment();
                break;
        }
        return true;
    }


}
