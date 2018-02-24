package michaelbumes.therapysupportapp.activities;

import android.Manifest;
import android.animation.Animator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavSwitchController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;
import com.ncapdevi.fragnav.tabhistory.FragNavTabHistoryController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.fragments.AddMedicineFragment;
import michaelbumes.therapysupportapp.fragments.BaseFragment;
import michaelbumes.therapysupportapp.fragments.CalendarFragment;
import michaelbumes.therapysupportapp.fragments.DrugPlanFragment;
import michaelbumes.therapysupportapp.fragments.FoodFragment;
import michaelbumes.therapysupportapp.fragments.MoodFragment;
import michaelbumes.therapysupportapp.fragments.NoteFragment;
import michaelbumes.therapysupportapp.fragments.SettingsFragment;
import michaelbumes.therapysupportapp.fragments.TodayFragment;


public class MainActivity extends AppCompatActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {
    private final int INDEX_TODAY = FragNavController.TAB1;
    private final int INDEX_DRUGPLAN = FragNavController.TAB2;
    private final int INDEX_BLANK = FragNavController.TAB3;
    private final int INDEX_CALENDAR = FragNavController.TAB4;
    private final int INDEX_SETTINGS = FragNavController.TAB5;

    public final int ART_ID_NOTE = 3;
    public final int ART_ID_MOOD = 1;
    public final int ART_ID_FOOD = 2;

    private FragNavController mNavController;
    FloatingActionButton floatingActionButton, fabNote, fabMood, fabFood;
    Animation fabOpen, fabClose, rotateForward, rotateBackwards;
    Boolean isOpen = false;
    int instanceInt = 0;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BottomBar bottomBar = findViewById(R.id.bottomBar);

        verifyPermissions();

        floatingActionButton = findViewById(R.id.floatingActionButton);
        fabNote = findViewById(R.id.fab_note);
        fabMood = findViewById(R.id.fab_mood);
        fabFood = findViewById(R.id.fab_food);

        fabOpen = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this,R.anim.fab_close);

        rotateForward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotateBackwards = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
            }
        });
        fabNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
                Toast.makeText(MainActivity.this, "Note fab Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                String value = intent.getStringExtra("key");
                Intent myIntent = new Intent(MainActivity.this, michaelbumes.therapysupportapp.activities.NoteActivity.class);
                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);

            }
        });

        fabMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
                Toast.makeText(MainActivity.this, "Mood fab Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                String value = intent.getStringExtra("key");
                Intent myIntent = new Intent(MainActivity.this, michaelbumes.therapysupportapp.activities.MoodActivity.class);
                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);

            }
        });

        fabFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
                Toast.makeText(MainActivity.this, "Food fab Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                String value = intent.getStringExtra("key");
                Intent myIntent = new Intent(MainActivity.this, michaelbumes.therapysupportapp.activities.FoodActivity.class);
                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
            }
        });


        boolean initial = savedInstanceState == null;
        if (initial) {
            bottomBar.selectTabAtPosition(INDEX_TODAY);
        }
        bottomBar.getTabAtPosition(2).setEnabled(false);


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

        //Unterte Leiste initialisieren
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

    private void animateFab(){
        if (isOpen){
            floatingActionButton.startAnimation(rotateBackwards);
            fabNote.startAnimation(fabClose);
            fabMood.startAnimation(fabClose);
            fabFood.startAnimation(fabClose);
            fabNote.setClickable(false);
            fabMood.setClickable(false);
            fabFood.setClickable(false);
            isOpen=false;

        }
        else {
            floatingActionButton.startAnimation(rotateForward);
            fabNote.startAnimation(fabOpen);
            fabMood.startAnimation(fabOpen);
            fabFood.startAnimation(fabOpen);
            fabNote.setClickable(true);
            fabMood.setClickable(true);
            fabFood.setClickable(true);
            isOpen = true;
        }
    }

    private void verifyPermissions(){
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[0]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[1]) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this.getApplicationContext(),
                permissions[2]) == PackageManager.PERMISSION_GRANTED){
        }else {
            ActivityCompat.requestPermissions(this, permissions, 1);
        }

    }




}
