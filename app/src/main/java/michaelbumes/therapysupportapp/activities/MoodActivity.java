package michaelbumes.therapysupportapp.activities;


import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

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


    Button moodButton0, moodButton1, moodButton2, moodButton3, moodButtonNormal, moodButton4, moodButton5 ,addMoodButton, expandMoodButton;
    LinearLayout cardViewMoodExpand;

    SeekBar seekBar1, seekBar2, seekBar3, seekBar4, seekBar5, seekBar6, seekBar7, seekBar8;


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

        seekBar1.setMax(8);
        seekBar2.setMax(8);
        seekBar3.setMax(8);
        seekBar4.setMax(8);
        seekBar5.setMax(8);
        seekBar6.setMax(8);
        seekBar7.setMax(8);
        seekBar8.setMax(8);



        moodButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodFlag = MOOD_0;
                moodButton0.setPaintFlags(moodButton0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                moodButton1.setPaintFlags(0);
                moodButton2.setPaintFlags(0);
                moodButton3.setPaintFlags(0);
                moodButton4.setPaintFlags(0);
                moodButton5.setPaintFlags(0);
                moodButtonNormal.setPaintFlags(0);

            }
        });
        moodButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodFlag = MOOD_1;
                moodButton1.setPaintFlags(moodButton0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                moodButton0.setPaintFlags(0);
                moodButton2.setPaintFlags(0);
                moodButton3.setPaintFlags(0);
                moodButton4.setPaintFlags(0);
                moodButton5.setPaintFlags(0);
                moodButtonNormal.setPaintFlags(0);

            }
        });
        moodButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodFlag = MOOD_2;
                moodButton2.setPaintFlags(moodButton0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                moodButton1.setPaintFlags(0);
                moodButton0.setPaintFlags(0);
                moodButton3.setPaintFlags(0);
                moodButton4.setPaintFlags(0);
                moodButton5.setPaintFlags(0);
                moodButtonNormal.setPaintFlags(0);

            }
        });
        moodButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodFlag = MOOD_3;
                moodButton3.setPaintFlags(moodButton0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                moodButton1.setPaintFlags(0);
                moodButton2.setPaintFlags(0);
                moodButton0.setPaintFlags(0);
                moodButton4.setPaintFlags(0);
                moodButton5.setPaintFlags(0);
                moodButtonNormal.setPaintFlags(0);

            }
        });
        moodButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodFlag = MOOD_4;
                moodButton4.setPaintFlags(moodButton0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                moodButton1.setPaintFlags(0);
                moodButton2.setPaintFlags(0);
                moodButton3.setPaintFlags(0);
                moodButton0.setPaintFlags(0);
                moodButton5.setPaintFlags(0);
                moodButtonNormal.setPaintFlags(0);

            }
        });
        moodButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodFlag = MOOD_5;
                moodButton5.setPaintFlags(moodButton0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                moodButton1.setPaintFlags(0);
                moodButton2.setPaintFlags(0);
                moodButton3.setPaintFlags(0);
                moodButton4.setPaintFlags(0);
                moodButton0.setPaintFlags(0);
                moodButtonNormal.setPaintFlags(0);

            }
        });
        moodButtonNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moodFlag = MOOD_NORMAL;
                moodButtonNormal.setPaintFlags(moodButton0.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                moodButton1.setPaintFlags(0);
                moodButton2.setPaintFlags(0);
                moodButton3.setPaintFlags(0);
                moodButton4.setPaintFlags(0);
                moodButton5.setPaintFlags(0);
                moodButton0.setPaintFlags(0);

            }
        });
        addMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (moodFlag == MOOD_ERROR){
                    Toast.makeText(MoodActivity.this, "Bitte wählen Sie eine Stimmung", Toast.LENGTH_SHORT).show();
                    return;
                }else  if (isExpanded){
                    Calendar calendar = Calendar.getInstance();
                    MoodDiary moodDiary = new MoodDiary();
                    Date currentDate = Calendar.getInstance().getTime();
                    moodDiary.setDate(currentDate);
                    moodDiary.setInfo1(String.valueOf(moodFlag));
                    moodDiary.setArtID(1);
                    String arrayMood[] = {String.valueOf(seekBar1.getProgress()),String.valueOf(seekBar2.getProgress()),String.valueOf(seekBar3.getProgress()),String.valueOf(seekBar4.getProgress()),String.valueOf(seekBar5.getProgress()),String.valueOf(seekBar6.getProgress()),String.valueOf(seekBar7.getProgress()),String.valueOf(seekBar8.getProgress())};

                    StringBuilder buffer = new StringBuilder();
                    for (String each : arrayMood)
                        buffer.append(",").append(each);
                    String joined = buffer.deleteCharAt(0).toString();

                    moodDiary.setInfo2(joined);
                    AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().insertAll(moodDiary);
                    Toast.makeText(getApplicationContext(), "Stimmung hinzugefügt", Toast.LENGTH_SHORT).show();
                    onBackPressed();

                }else{
                    Calendar calendar = Calendar.getInstance();
                    MoodDiary moodDiary = new MoodDiary();
                    Date currentDate = Calendar.getInstance().getTime();
                    moodDiary.setDate(currentDate);
                    moodDiary.setInfo1(String.valueOf(moodFlag));
                    moodDiary.setArtID(1);
                    AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().insertAll(moodDiary);
                    Toast.makeText(getApplicationContext(), "Stimmung hinzugefügt", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }
        });
        expandMoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isExpanded){
                    expandMoodButton.setText("Mehr Zeit?");
                    isExpanded = false;
                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp);
                    expandMoodButton.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                    cardViewMoodExpand.setVisibility(View.GONE);
                }else {
                    expandMoodButton.setText("Weniger Zeit?");
                    isExpanded = true;
                    Drawable img = getApplicationContext().getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp);
                    expandMoodButton.setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                    cardViewMoodExpand.setVisibility(View.VISIBLE);
                }
            }
        });
        









    }

}
