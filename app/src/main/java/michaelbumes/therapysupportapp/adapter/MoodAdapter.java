package michaelbumes.therapysupportapp.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ncapdevi.fragnav.FragNavController;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.MainActivity;
import michaelbumes.therapysupportapp.alarms.AlarmMain;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.DrugEventDb;
import michaelbumes.therapysupportapp.entity.MoodDiary;
import michaelbumes.therapysupportapp.fragments.DrugEvent;
import michaelbumes.therapysupportapp.fragments.DrugFragment;

/**
 * Created by Michi on 07.04.2018.
 */

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.ViewHolder>{
    List<MoodDiary> moodDiaries;
    private Context context;
    int instanceInt = 0;



    public MoodAdapter(List<MoodDiary> moodDiaries) {
        List<MoodDiary> returnList = new ArrayList<>();
        for (int i = 0; i < moodDiaries.size(); i++) {
            if (moodDiaries.get(i).getArtID() == 1){
                returnList.add(moodDiaries.get(i));

            }
        }        this.moodDiaries = returnList;
    }

    @Override
    public MoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_cardview_mood, parent,false );
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoodAdapter.ViewHolder holder, int position) {
        holder.moodButton0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "MoodButton 0", Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return moodDiaries.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public Button moodButton0, moodButton1, moodButton2, moodButton3, moodButtonNormal, moodButton4, moodButton5 ,addMoodButton, expandMoodButton;
        public SeekBar seekBar1, seekBar2, seekBar3, seekBar4, seekBar5, seekBar6, seekBar7, seekBar8;
        CardView cardViewMoodExpand,cardViewMood;





        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            moodButton0 = itemView.findViewById(R.id.today_mood_button_0);
            moodButton1 = itemView.findViewById(R.id.today_mood_button_1);
            moodButton2 = itemView.findViewById(R.id.today_mood_button_2);
            moodButton3 = itemView.findViewById(R.id.today_mood_button_3);
            moodButton4 = itemView.findViewById(R.id.today_mood_button_4);
            moodButton5 = itemView.findViewById(R.id.today_mood_button_5);
            moodButtonNormal = itemView.findViewById(R.id.today_mood_button_nomal);
            expandMoodButton = itemView.findViewById(R.id.today_expand_mood_button);
            cardViewMood = itemView.findViewById(R.id.today_card_view_mood);
            cardViewMoodExpand = itemView.findViewById(R.id.today_mood_expanded);

            seekBar1 = itemView.findViewById(R.id.today_seek_bar_mood_1);
            seekBar2 = itemView.findViewById(R.id.today_seek_bar_mood_2);
            seekBar3 = itemView.findViewById(R.id.today_seek_bar_mood_3);
            seekBar4 = itemView.findViewById(R.id.today_seek_bar_mood_4);
            seekBar5 = itemView.findViewById(R.id.today_seek_bar_mood_5);
            seekBar6 = itemView.findViewById(R.id.today_seek_bar_mood_6);
            seekBar7 = itemView.findViewById(R.id.today_seek_bar_mood_7);
            seekBar8 = itemView.findViewById(R.id.today_seek_bar_mood_8);

            seekBar1.setMax(8);
            seekBar2.setMax(8);
            seekBar3.setMax(8);
            seekBar4.setMax(8);
            seekBar5.setMax(8);
            seekBar6.setMax(8);
            seekBar7.setMax(8);
            seekBar8.setMax(8);

        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();



        }

    }







}
