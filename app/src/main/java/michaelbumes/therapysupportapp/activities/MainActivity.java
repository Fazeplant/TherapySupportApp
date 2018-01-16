package michaelbumes.therapysupportapp.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavSwitchController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;
import com.ncapdevi.fragnav.tabhistory.FragNavTabHistoryController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.fragments.mainFragments.BaseFragment;
import michaelbumes.therapysupportapp.fragments.mainFragments.CalendarFragment;
import michaelbumes.therapysupportapp.fragments.mainFragments.DrugPlanFragment;
import michaelbumes.therapysupportapp.fragments.mainFragments.SettingsFragment;
import michaelbumes.therapysupportapp.fragments.mainFragments.TodayFragment;
import michaelbumes.therapysupportapp.utils.DatabaseInitializer;


public class MainActivity extends AppCompatActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener{
    private final int INDEX_TODAY = FragNavController.TAB1;
    private final int INDEX_DRUGPLAN = FragNavController.TAB2;
    private final int INDEX_BLANK = FragNavController.TAB3;
    private final int INDEX_CALENDAR = FragNavController.TAB4;
    private final int INDEX_SETTINGS = FragNavController.TAB5;
    private FragNavController mNavController;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BottomBar bottomBar = findViewById(R.id.bottomBar);
        final Button button = findViewById(R.id.button3);

        //Button zur Datenbank initialisierung
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseInitializer.populateAsync(AppDatabase.getAppDatabase(getApplicationContext()));
            }
        });


        final FloatingActionButton floatingActionButton = findViewById(R.id.floatingActionButton);

        boolean initial = savedInstanceState == null;
        if (initial) {
            bottomBar.selectTabAtPosition(INDEX_TODAY);
        }
        bottomBar.getTabAtPosition(2).setEnabled(false);

        //bottomBar.getTabAtPosition(0).setPaddingRelative(0,0,100,0);
        //bottomBar.getTabAtPosition(1).setPaddingRelative(0,0,100,0);
        //bottomBar.getTabAtPosition(2).setPaddingRelative(100,0,0,0);
        //bottomBar.getTabAtPosition(3).setPaddingRelative(100,0,0,0);

        //mNavTransactionOptions = FragNavTransactionOptions.newBuilder()

        //        .customAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right)
        //        .build();


        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container)

                .transactionListener(this)
                .rootFragmentListener(this, 5)
                .popStrategy(FragNavTabHistoryController.UNIQUE_TAB_HISTORY)

                //.defaultTransactionOptions(FragNavTransactionOptions.newBuilder().transition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).build())
                //.defaultTransactionOptions((FragNavTransactionOptions.newBuilder().customAnimations(R.anim.slide_in_from_right, R.anim.slide_out_to_left, R.anim.slide_in_from_left, R.anim.slide_out_to_right).build()))
                .switchController(new FragNavSwitchController() {
                    @Override
                    public void switchTab(int index, FragNavTransactionOptions mNavTransactionOptions) {
                        bottomBar.selectTabAtPosition(index);
                    }
                })
                .build();

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                switch (tabId) {
                    case R.id.bb_menu_today:
                        mNavController.switchTab(INDEX_TODAY);
                        break;
                    case R.id.bb_menu_drug_plan:
                        mNavController.switchTab(INDEX_DRUGPLAN);
                        break;
                    case R.id.bb_menu_calendar:
                        mNavController.switchTab(INDEX_CALENDAR);
                        break;
                    case R.id.bb_menu_settings:
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
            case INDEX_CALENDAR:
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

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

}
