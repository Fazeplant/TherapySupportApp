package michaelbumes.therapysupportapp.activities;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.MoodDiary;


public class MoodActivity extends AppCompatActivity {
    ImageView moodView;
    private static final int MOOD_ERROR = -10;
    private int moodFlag = MOOD_ERROR;
    private boolean isExpanded = false;
    private final int MOOD_0 = -3;
    private final int MOOD_1 = -2;
    private final int MOOD_2 = -1;
    private final int MOOD_3 = 1;
    private final int MOOD_4 = 2;
    private final int MOOD_5 = 3;
    private final int MOOD_NORMAL = 0;
    private MoodDiary moodDiaryToday;


    private Button moodButton0;
    private Button moodButton1;
    private Button moodButton2;
    private Button moodButton3;
    private Button moodButtonNormal;
    private Button moodButton4;
    private Button moodButton5;
    private Button addMoodButton;
    private Button expandMoodButton;
    private LinearLayout cardViewMoodExpand;

    private SeekBar seekBar1;
    private SeekBar seekBar2;
    private SeekBar seekBar3;
    private SeekBar seekBar4;
    private SeekBar seekBar5;
    private SeekBar seekBar6;
    private SeekBar seekBar7;
    private SeekBar seekBar8;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);
        this.setTitle(R.string.title_mood);


        addMoodButton = findViewById(R.id.add_mood_button);
        moodButton0 = findViewById(R.id.mood_button_0);
        moodButton1 = findViewById(R.id.mood_button_1);
        moodButton2 = findViewById(R.id.mood_button_2);
        moodButton3 = findViewById(R.id.mood_button_3);
        moodButton4 = findViewById(R.id.mood_button_4);
        moodButton5 = findViewById(R.id.mood_button_5);
        moodButtonNormal = findViewById(R.id.mood_button_nomal);
        expandMoodButton = findViewById(R.id.expand_mood_button);
        cardViewMoodExpand = findViewById(R.id.card_view_mood_extended);

        seekBar1 = findViewById(R.id.seek_bar_mood_1);
        seekBar2 = findViewById(R.id.seek_bar_mood_2);
        seekBar3 = findViewById(R.id.seek_bar_mood_3);
        seekBar4 = findViewById(R.id.seek_bar_mood_4);
        seekBar5 = findViewById(R.id.seek_bar_mood_5);
        seekBar6 = findViewById(R.id.seek_bar_mood_6);
        seekBar7 = findViewById(R.id.seek_bar_mood_7);
        seekBar8 = findViewById(R.id.seek_bar_mood_8);

        seekBar1.setMax(6);
        seekBar2.setMax(6);
        seekBar3.setMax(6);
        seekBar4.setMax(6);
        seekBar5.setMax(6);
        seekBar6.setMax(6);
        seekBar7.setMax(6);
        seekBar8.setMax(6);



        //ID wird gesetzt falls der Eintrag bearbeitet wird, also nicht neu erstellt wird
        Intent intent = getIntent();
        int id = intent.getIntExtra("moodId", -1);





        moodButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodFlag = MOOD_0;
                moodButton0.setBackground(getResources().getDrawable(R.drawable.border_mood_0));
                moodButton1.setBackground(getResources().getDrawable(R.color.mood_1));
                moodButton2.setBackground(getResources().getDrawable(R.color.mood_2));
                moodButton3.setBackground(getResources().getDrawable(R.color.mood_3));
                moodButton4.setBackground(getResources().getDrawable(R.color.mood_4));
                moodButton5.setBackground(getResources().getDrawable(R.color.mood_5));
                moodButtonNormal.setBackground(getResources().getDrawable(R.color.mood_normal));

            }
        });
        moodButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodFlag = MOOD_1;
                moodButton1.setBackground(getResources().getDrawable(R.drawable.border_mood_1));
                moodButton0.setBackground(getResources().getDrawable(R.color.mood_0));
                moodButton2.setBackground(getResources().getDrawable(R.color.mood_2));
                moodButton3.setBackground(getResources().getDrawable(R.color.mood_3));
                moodButton4.setBackground(getResources().getDrawable(R.color.mood_4));
                moodButton5.setBackground(getResources().getDrawable(R.color.mood_5));
                moodButtonNormal.setBackground(getResources().getDrawable(R.color.mood_normal));

            }
        });
        moodButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodFlag = MOOD_2;
                moodButton2.setBackground(getResources().getDrawable(R.drawable.border_mood_2));
                moodButton1.setBackground(getResources().getDrawable(R.color.mood_1));
                moodButton0.setBackground(getResources().getDrawable(R.color.mood_0));
                moodButton3.setBackground(getResources().getDrawable(R.color.mood_3));
                moodButton4.setBackground(getResources().getDrawable(R.color.mood_4));
                moodButton5.setBackground(getResources().getDrawable(R.color.mood_5));
                moodButtonNormal.setBackground(getResources().getDrawable(R.color.mood_normal));

            }
        });
        moodButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodFlag = MOOD_3;
                moodButton3.setBackground(getResources().getDrawable(R.drawable.border_mood_3));
                moodButton1.setBackground(getResources().getDrawable(R.color.mood_1));
                moodButton2.setBackground(getResources().getDrawable(R.color.mood_2));
                moodButton0.setBackground(getResources().getDrawable(R.color.mood_0));
                moodButton4.setBackground(getResources().getDrawable(R.color.mood_4));
                moodButton5.setBackground(getResources().getDrawable(R.color.mood_5));
                moodButtonNormal.setBackground(getResources().getDrawable(R.color.mood_normal));

            }
        });
        moodButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodFlag = MOOD_4;
                moodButton4.setBackground(getResources().getDrawable(R.drawable.border_mood_4));
                moodButton1.setBackground(getResources().getDrawable(R.color.mood_1));
                moodButton2.setBackground(getResources().getDrawable(R.color.mood_2));
                moodButton3.setBackground(getResources().getDrawable(R.color.mood_3));
                moodButton0.setBackground(getResources().getDrawable(R.color.mood_0));
                moodButton5.setBackground(getResources().getDrawable(R.color.mood_5));
                moodButtonNormal.setBackground(getResources().getDrawable(R.color.mood_normal));

            }
        });
        moodButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodFlag = MOOD_5;
                moodButton5.setBackground(getResources().getDrawable(R.drawable.border_mood_5));
                moodButton1.setBackground(getResources().getDrawable(R.color.mood_1));
                moodButton2.setBackground(getResources().getDrawable(R.color.mood_2));
                moodButton3.setBackground(getResources().getDrawable(R.color.mood_3));
                moodButton4.setBackground(getResources().getDrawable(R.color.mood_4));
                moodButton0.setBackground(getResources().getDrawable(R.color.mood_0));
                moodButtonNormal.setBackground(getResources().getDrawable(R.color.mood_normal));

            }
        });
        moodButtonNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodFlag = MOOD_NORMAL;
                moodButtonNormal.setBackground(getResources().getDrawable(R.drawable.border_mood_normal));
                moodButton1.setBackground(getResources().getDrawable(R.color.mood_1));
                moodButton2.setBackground(getResources().getDrawable(R.color.mood_2));
                moodButton3.setBackground(getResources().getDrawable(R.color.mood_3));
                moodButton4.setBackground(getResources().getDrawable(R.color.mood_4));
                moodButton5.setBackground(getResources().getDrawable(R.color.mood_5));
                moodButton0.setBackground(getResources().getDrawable(R.color.mood_0));

            }
        });
        //Speichert den Eintrag
        addMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moodFlag == MOOD_ERROR){
                    Toast.makeText(MoodActivity.this, "Bitte wählen Sie eine Stimmung", Toast.LENGTH_SHORT).show();
                    return;
                }else  if (isExpanded){
                    Date currentDate = Calendar.getInstance().getTime();
                    MoodDiary moodDiary;
                    //Falls der Eintrag neu erstellt wird
                    if (moodDiaryToday == null){
                        moodDiary = new MoodDiary();
                        moodDiary.setDate(currentDate);
                        moodDiary.setArtID(1);

                    }else {
                        moodDiary = moodDiaryToday;
                    }
                    //Info1 = Stimmungseingabe -3 - 3
                    moodDiary.setInfo1(String.valueOf(moodFlag));
                    //Info2 = Erweiterte Stimmungseingabe = seekbar progress
                    String arrayMood[] = {String.valueOf(seekBar1.getProgress()),String.valueOf(seekBar2.getProgress()),String.valueOf(seekBar3.getProgress()),String.valueOf(seekBar4.getProgress()),String.valueOf(seekBar5.getProgress()),String.valueOf(seekBar6.getProgress()),String.valueOf(seekBar7.getProgress()),String.valueOf(seekBar8.getProgress())};

                    StringBuilder buffer = new StringBuilder();
                    for (String each : arrayMood)
                        buffer.append(",").append(each);
                    String joined = buffer.deleteCharAt(0).toString();

                    moodDiary.setInfo2(joined);

                    int result = AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().update(moodDiary);
                    if (result <= 0){
                        AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().insert(moodDiary);
                    }

                    Toast.makeText(getApplicationContext(), "Stimmung hinzugefügt", Toast.LENGTH_SHORT).show();

                    finish();

                }else{
                    //Keine erweiterte Eingabe
                    Date currentDate = Calendar.getInstance().getTime();
                    MoodDiary moodDiary;

                    //Falls der Eintrag neu erstellt wird
                    if (moodDiaryToday == null){
                        moodDiary = new MoodDiary();
                        moodDiary.setDate(currentDate);
                        moodDiary.setArtID(1);


                        //Falls der Eintrag bearbeitet wird
                    }else {
                        moodDiary = moodDiaryToday;
                    }
                    moodDiary.setInfo1(String.valueOf(moodFlag));
                    moodDiary.setArtID(1);
                    int result = AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().update(moodDiary);
                    if (result <= 0){
                        AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().insert(moodDiary);
                    }
                    Toast.makeText(getApplicationContext(), "Stimmung hinzugefügt", Toast.LENGTH_SHORT).show();

                    finish();
                }
            }
        });
        expandMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded){
                    expandMoodButton.setText(R.string.more_time);
                    isExpanded = false;
                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
                    expandMoodButton.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                    cardViewMoodExpand.setVisibility(View.GONE);
                }else {
                    expandMoodButton.setText(R.string.less_time);
                    isExpanded = true;
                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp);
                    expandMoodButton.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                    cardViewMoodExpand.setVisibility(View.VISIBLE);
                }
            }
        });
        //Falls der Eintrag bearbeitet wird der ensprechende Knopf getriggert
        if (id != -1){
            moodDiaryToday = AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().findById(id);
            switch (Integer.valueOf(moodDiaryToday.getInfo1())){
                case -3:
                    moodButton0.performClick();
                    break;
                case -2:
                    moodButton1.performClick();
                    break;
                case -1:
                    moodButton2.performClick();
                    break;
                case 0:
                    moodButtonNormal.performClick();
                    break;
                case 1:
                    moodButton3.performClick();
                    break;
                case 2:
                    moodButton4.performClick();
                    break;
                case 3:
                    moodButton5.performClick();
                    break;

            }
            //Seekbars werden auf den Stand des Eintrages gesetzt
            if(moodDiaryToday.getInfo2()!= null){
                List<String> seekBarList = new ArrayList<String>(Arrays.asList(moodDiaryToday.getInfo2().split(",")));
                seekBar1.setProgress(Integer.valueOf(seekBarList.get(0)));
                seekBar2.setProgress(Integer.valueOf(seekBarList.get(1)));
                seekBar3.setProgress(Integer.valueOf(seekBarList.get(2)));
                seekBar4.setProgress(Integer.valueOf(seekBarList.get(3)));
                seekBar5.setProgress(Integer.valueOf(seekBarList.get(4)));
                seekBar6.setProgress(Integer.valueOf(seekBarList.get(5)));
                seekBar7.setProgress(Integer.valueOf(seekBarList.get(6)));
                seekBar8.setProgress(Integer.valueOf(seekBarList.get(7)));



            }

        }
        









    }

}
