package michaelbumes.therapysupportapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.ncapdevi.fragnav.FragNavController;
import com.ncapdevi.fragnav.FragNavSwitchController;
import com.ncapdevi.fragnav.FragNavTransactionOptions;
import com.ncapdevi.fragnav.tabhistory.FragNavTabHistoryController;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.Calendar;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.alarms.NotificationHelper;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.database.DatabaseDrugList;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.TakenDrug;
import michaelbumes.therapysupportapp.fragments.BaseFragment;
import michaelbumes.therapysupportapp.fragments.CalendarFragment;
import michaelbumes.therapysupportapp.fragments.DrugPlanFragment;
import michaelbumes.therapysupportapp.fragments.SettingsFragment;
import michaelbumes.therapysupportapp.fragments.TodayFragment;


public class MainActivity extends AppCompatActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {
    private final int INDEX_TODAY = FragNavController.TAB1;
    private final int INDEX_DRUGPLAN = FragNavController.TAB2;
    private final int INDEX_BLANK = FragNavController.TAB3;
    private final int INDEX_CALENDAR = FragNavController.TAB4;
    private final int INDEX_SETTINGS = FragNavController.TAB5;
    public static DatabaseDrugList databaseDrugList;


    private static final String OK_ACTION ="michaelbumes.therapysupportapp.OK_ACTION" ;
    private static final String CANCLE_ACTION ="michaelbumes.therapysupportapp.CANCLE_ACTION" ;

    public final int ART_ID_NOTE = 3;
    public final int ART_ID_MOOD = 1;
    public final int ART_ID_FOOD = 2;

    private ImageView dim_layout;

    private FragNavController mNavController;
    private FloatingActionButton floatingActionButton;
    private FloatingActionButton fabNote;
    private FloatingActionButton fabMood;
    private FloatingActionButton fabFood;
    private Animation fabOpen;
    private Animation fabClose;
    private Animation rotateForward;
    private Animation rotateBackwards;
    private Boolean isOpen = false;
    int instanceInt = 0;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final BottomBar bottomBar = findViewById(R.id.bottomBar);


        processIntentAction(getIntent());




        verifyPermissions();

        databaseDrugList = DatabaseDrugList.getAppDatabase(getApplicationContext());


        dim_layout  =findViewById(R.id.dim_layout);
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
                Toast.makeText(MainActivity.this, "Note fab Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                String value = intent.getStringExtra("key");
                Intent myIntent = new Intent(MainActivity.this, michaelbumes.therapysupportapp.activities.NoteActivity.class);
                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
                animateFab();

            }
        });

        fabMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Mood fab Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                String value = intent.getStringExtra("key");
                Intent myIntent = new Intent(MainActivity.this, michaelbumes.therapysupportapp.activities.MoodActivity.class);
                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
                animateFab();

            }
        });

        fabFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Food fab Clicked!", Toast.LENGTH_SHORT).show();
                Intent intent = getIntent();
                String value = intent.getStringExtra("key");
                Intent myIntent = new Intent(MainActivity.this, michaelbumes.therapysupportapp.activities.FoodActivity.class);
                myIntent.putExtra("key", value); //Optional parameters
                MainActivity.this.startActivity(myIntent);
                animateFab();
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
        switch (mNavController.getCurrentFrag().getClass().getName()){
            case "michaelbumes.therapysupportapp.fragments.DrugDetailFragment":
                Toast.makeText(this, "Funzt Lol", Toast.LENGTH_SHORT).show();
                break;
        }
        if(!mNavController.popFragment()) {
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
            case R.id.save_drug:
                return false;
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
            dim_layout.setVisibility(View.GONE);
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
            dim_layout.setVisibility(View.VISIBLE);
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


    public void dim_layout_on_click(View view) {
        animateFab();
    }

    public  FragNavController getmNavController(){

        return mNavController;
    }

    private Intent getNotificationIntent() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return intent;
    }
    private void processIntentAction(Intent intent) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case OK_ACTION:
                    Toast.makeText(this, "Bestätigt", Toast.LENGTH_SHORT).show();
                    int idGenerated = intent.getBundleExtra("notiBundle").getInt("id");
                    int dosage = intent.getBundleExtra("notiBundle").getInt("dosage");
                    int id;
                    if (idGenerated >999){
                        id = Integer.valueOf(String.valueOf(idGenerated).substring(0,3));

                    }
                    else if (idGenerated>99){
                        id = Integer.valueOf(String.valueOf(idGenerated).substring(0,2));
                    }else {
                        id = Integer.valueOf(String.valueOf(idGenerated).substring(0,1));

                    }
                    Drug drug = AppDatabase.getAppDatabase(getApplicationContext()).drugDao().findById(id);
                    TakenDrug takenDrug = drugToTakenDrug(getApplicationContext(), drug, dosage);
                    AppDatabase.getAppDatabase(getApplicationContext()).takenDrugDao().insert(takenDrug);
                    NotificationManagerCompat.from(getApplicationContext()).cancel(idGenerated);
                    try {
                        NotificationHelper.ringtone.stop();
                    }catch (Exception ignored){

                    }
                    break;
                case CANCLE_ACTION:

                    Toast.makeText(this, "Übersprungen", Toast.LENGTH_SHORT).show();
                    NotificationManagerCompat.from(getApplicationContext()).cancel(intent.getBundleExtra("notiBundle").getInt("id"));
                    try {
                        NotificationHelper.ringtone.stop();
                    }catch (Exception ignored){

                    }
                    break;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntentAction(intent);
        super.onNewIntent(intent);
    }

    private static TakenDrug drugToTakenDrug(Context context, Drug drug, int dosage){
        TakenDrug takenDrug = new TakenDrug();
        takenDrug.setDosageForm(databaseDrugList.dosageFormDao().getNameById(drug.getDosageFormId()));
        takenDrug.setDrugName(drug.getDrugName());
        takenDrug.setDosageFormId(drug.getDosageFormId());
        takenDrug.setManufacturer(drug.getManufacturer());
        takenDrug.setSideEffects(drug.getSideEffects());
        takenDrug.setTakingNote(drug.getTakingNote());
        takenDrug.setPzn(drug.getPzn());

        takenDrug.setDosage(dosage);
        takenDrug.setDate(Calendar.getInstance().getTime());
        return takenDrug;

    }


}
