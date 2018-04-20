package michaelbumes.therapysupportapp.activities;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import michaelbumes.therapysupportapp.alarms.AlarmMain;
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

import static michaelbumes.therapysupportapp.fragments.SettingsFragment.dailyNotificationTime;


public class MainActivity extends AppCompatActivity implements BaseFragment.FragmentNavigation, FragNavController.TransactionListener, FragNavController.RootFragmentListener {
    private final int INDEX_TODAY = FragNavController.TAB1;
    private final int INDEX_DRUGPLAN = FragNavController.TAB2;
    private final int INDEX_BLANK = FragNavController.TAB3;
    private final int INDEX_CALENDAR = FragNavController.TAB4;
    private final int INDEX_SETTINGS = FragNavController.TAB5;
    public static DatabaseDrugList databaseDrugList;


    private static final String OK_ACTION ="michaelbumes.therapysupportapp.OK_ACTION" ;
    private static final String CANCEL_ACTION ="michaelbumes.therapysupportapp.CANCEL_ACTION" ;
    private static final String CANCEL_ACTION_DAILY = "michaelbumes.therapysupportapp.CANCEL_ACTION_DAILY";
    private static final String OK_ACTION_DAILY ="michaelbumes.therapysupportapp.OK_ACTION_DAILY" ;

    public final int ART_ID_NOTE = 3;
    public final int ART_ID_MOOD = 1;
    public final int ART_ID_FOOD = 2;

    private ImageView dim_layout;

    public FragNavController mNavController;
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


        //Wird bei Interaktion mit der Notiifikation aufgerufen
        processIntentAction(getIntent());



        //Beim ersten Start der App wird nach den Berechtigungen gefragt
        verifyPermissions();

        databaseDrugList = DatabaseDrugList.getAppDatabase(getApplicationContext());

        //Wird nur beim ersten start ausgeführt. Setzt die tägliche Notifikation auf 20:00
        SharedPreferences preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        boolean firstStart = preferences.getBoolean("firstStart", true);
        if (firstStart){
            dailyNotificationTime = "20:00";
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("firstStart", false);
            editor.apply();
            AlarmMain alarm = new AlarmMain(this);

        }




        //dim_layout verdunkelt den Bildschirm, beim drüchen des floatingActionButton
        dim_layout  =findViewById(R.id.dim_layout);
        //floatingActionButton ist der haupt-floatingActionButton in der Mitte, welcher fabNote, fabMood und fabFood anzeigen lässt
        floatingActionButton = findViewById(R.id.floatingActionButton);
        fabNote = findViewById(R.id.fab_note);
        fabMood = findViewById(R.id.fab_mood);
        fabFood = findViewById(R.id.fab_food);

        //Animationen für das Öffnen und Schließen des floatingActionButton
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
                Intent myIntent = new Intent(MainActivity.this, michaelbumes.therapysupportapp.activities.NoteActivity.class);
                MainActivity.this.startActivity(myIntent);
                animateFab();

            }
        });

        fabMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, michaelbumes.therapysupportapp.activities.MoodActivity.class);
                MainActivity.this.startActivity(myIntent);
                animateFab();

            }
        });

        fabFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, michaelbumes.therapysupportapp.activities.FoodActivity.class);
                MainActivity.this.startActivity(myIntent);
                animateFab();
            }
        });

        //Falls keine Instanz gespeichert, wird das Heute Fragment angezeigt
        boolean initial = savedInstanceState == null;
        if (initial) {
            bottomBar.selectTabAtPosition(INDEX_TODAY);
        }
        //TabAtPosition 2 ist ein deaktivierter Tab in der BottomBar, da darüber der floatingActionButton liegt.
        bottomBar.getTabAtPosition(2).setEnabled(false);

        //Hier könnten Animationen für die Fragmente eingefügt werden
        FragNavTransactionOptions fragNavTransactionOptions = FragNavTransactionOptions.newBuilder()
                .build();

        //numberOfTabs = 5 wegen den deaktivierten Tab in der Mitte
        mNavController = FragNavController.newBuilder(savedInstanceState, getSupportFragmentManager(), R.id.container)
                .transactionListener(this)
                .defaultTransactionOptions(fragNavTransactionOptions)
                .rootFragmentListener(this, 5)
                .popStrategy(FragNavTabHistoryController.UNIQUE_TAB_HISTORY)
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
        if (getSupportActionBar() != null && mNavController != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(!mNavController.isRootFragment());
        }
    }


    @Override
    public void onFragmentTransaction(Fragment fragment, FragNavController.TransactionType transactionType) {
        //Falls Fragmente auf dem Backstack liegen, wird der Zurück-Button link oben angezeigt
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
        throw new IllegalStateException("Falscher Index");
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


    //Wird bei Interaktion mit der Notiifikation aufgerufen
    private void processIntentAction(Intent intent) {
        if (intent.getAction() != null) {
            switch (intent.getAction()) {
                case OK_ACTION:
                    Toast.makeText(this, R.string.confirmed, Toast.LENGTH_SHORT).show();
                    //idGenerated ist die ID des Medikaments mit der angehängten Nummer der Alarmzeit (0 = erste Alarmzeit usw.)
                    int idGenerated = intent.getBundleExtra("notiBundle").getInt("id");
                    int dosage = intent.getBundleExtra("notiBundle").getInt("dosage");
                    int id;
                    //Falls idGenerated viersellig ist, ist id die ersten drei
                    if (idGenerated >999){
                        id = Integer.valueOf(String.valueOf(idGenerated).substring(0,3));

                    }
                    else if (idGenerated>99){
                        id = Integer.valueOf(String.valueOf(idGenerated).substring(0,2));
                    }else {
                        id = Integer.valueOf(String.valueOf(idGenerated).substring(0,1));

                    }
                    //Umwandlung von drug -> takendrug und Speicherung in der Datenbank
                    Drug drug = AppDatabase.getAppDatabase(getApplicationContext()).drugDao().findById(id);
                    TakenDrug takenDrug = drugToTakenDrug(drug, dosage);
                    AppDatabase.getAppDatabase(getApplicationContext()).takenDrugDao().insert(takenDrug);
                    NotificationManagerCompat.from(getApplicationContext()).cancel(idGenerated);
                    //Alarm wird deaktiviert, falls vorhanden
                    try {
                        NotificationHelper.ringtone.stop();
                    }catch (Exception ignored){

                    }
                    break;
                case CANCEL_ACTION:

                    Toast.makeText(this, R.string.skipped, Toast.LENGTH_SHORT).show();
                    NotificationManagerCompat.from(getApplicationContext()).cancel(intent.getBundleExtra("notiBundle").getInt("id"));
                    //Alarm wird deaktiviert, falls vorhanden
                    try {
                        NotificationHelper.ringtone.stop();
                    }catch (Exception ignored){

                    }
                    break;
                case OK_ACTION_DAILY:
                        Intent myIntent = new Intent(MainActivity.this, michaelbumes.therapysupportapp.activities.MoodActivity.class);
                        MainActivity.this.startActivity(myIntent);
                        NotificationManagerCompat.from(getApplicationContext()).cancel(111111);
                        break;
                case CANCEL_ACTION_DAILY:
                    Toast.makeText(this, R.string.skipped, Toast.LENGTH_SHORT).show();
                    NotificationManagerCompat.from(getApplicationContext()).cancel(111111);
                    break;
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processIntentAction(intent);
        super.onNewIntent(intent);
    }


    private static TakenDrug drugToTakenDrug(Drug drug, int dosage){
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
