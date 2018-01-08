package michaelbumes.therapysupportapp;

import android.app.FragmentManager;
import android.app.Fragment;
import android.app.FragmentTransaction;
//import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {


            switch (item.getItemId()) {
                case R.id.navigation_today:
                    TodayFragment todayFragment = new TodayFragment();
                    FragmentTransaction fragmentTransactionToday = getFragmentManager().beginTransaction();
                    fragmentTransactionToday.replace(R.id.content, todayFragment);
                    fragmentTransactionToday.commit();
                    return true;
                case R.id.navigation_drug_plan:
                    DrugPlanFragment drugPlanFragment = new DrugPlanFragment();
                    FragmentTransaction drugPlanfragmentTransaction = getFragmentManager().beginTransaction();
                    drugPlanfragmentTransaction.replace(R.id.content, drugPlanFragment);
                    drugPlanfragmentTransaction.commit();
                    return true;
                case R.id.navigation_calendar:
                    CalendarFragment calendarFragment = new CalendarFragment();
                    FragmentTransaction calendarFragmentTransaction = getFragmentManager().beginTransaction();
                    calendarFragmentTransaction.replace(R.id.content, calendarFragment);
                    calendarFragmentTransaction.commit();
                    return true;
                case R.id.navigation_settings:
                    SettingsFragment settingsFragment = new SettingsFragment();
                    FragmentTransaction settingsFragmentTransaction = getFragmentManager().beginTransaction();
                    settingsFragmentTransaction.replace(R.id.content, settingsFragment);
                    settingsFragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationViewEx navigation = (BottomNavigationViewEx) findViewById(R.id.navigation);
        navigation.enableShiftingMode(false);
        //navigation.enableAnimation(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        TodayFragment todayFragment = new TodayFragment();
        FragmentTransaction fragmentTransactionToday = getFragmentManager().beginTransaction();
        fragmentTransactionToday.replace(R.id.content, todayFragment);
        fragmentTransactionToday.commit();
    }

}
