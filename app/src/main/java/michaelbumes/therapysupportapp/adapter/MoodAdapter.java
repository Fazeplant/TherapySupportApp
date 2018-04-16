package michaelbumes.therapysupportapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.MoodDiary;

/**
 * Created by Michi on 07.04.2018.
 */

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.ViewHolder>{
    private final List<MoodDiary> moodDiaries;
    private Context context;
    int instanceInt = 0;



    public MoodAdapter(List<MoodDiary> moodDiaries, Calendar calStartOfDay, Calendar calEndOfDay) {
        List<MoodDiary> returnList = new ArrayList<>();
        for (int i = 0; i < moodDiaries.size(); i++) {
            if (moodDiaries.get(i).getArtID() == 1 && moodDiaries.get(i).getDate().getTime() > calStartOfDay.getTime().getTime() && moodDiaries.get(i).getDate().getTime() < calEndOfDay.getTime().getTime() ){
                returnList.add(moodDiaries.get(i));

            }
        }
        this.moodDiaries = returnList;
    }

    @Override
    public MoodAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_cardview_mood, parent,false );
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoodAdapter.ViewHolder holder, final int position) {
        switch (Integer.valueOf(moodDiaries.get(position).getInfo1())){
            case -3:
                holder.moodButton0.setBackground(context.getResources().getDrawable(R.drawable.border_mood_0));
                break;
            case -2:
                holder.moodButton1.setBackground(context.getResources().getDrawable(R.drawable.border_mood_1));
                break;
            case -1:
                holder.moodButton2.setBackground(context.getResources().getDrawable(R.drawable.border_mood_2));
                break;
            case 0:
                holder.moodButtonNormal.setBackground(context.getResources().getDrawable(R.drawable.border_mood_normal));
                break;
            case 1:
                holder.moodButton3.setBackground(context.getResources().getDrawable(R.drawable.border_mood_3));
                break;
            case 2:
                holder.moodButton4.setBackground(context.getResources().getDrawable(R.drawable.border_mood_4));
                break;
            case 3:
                holder.moodButton5.setBackground(context.getResources().getDrawable(R.drawable.border_mood_5));
                break;

        }




    }

    @Override
    public int getItemCount() {
        return moodDiaries.size();
    }

    public void deleteItem(int adapterPosition) {
        AppDatabase.getAppDatabase(context).moodDiaryDao().delete(moodDiaries.get(adapterPosition));

        moodDiaries.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        notifyItemRangeChanged(adapterPosition,moodDiaries.size());
        Toast.makeText(context, "Stimmungtagebuch Eintrag wurde gelöscht" , Toast.LENGTH_SHORT).show();

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final Button moodButton0;
        public final Button moodButton1;
        public final Button moodButton2;
        public final Button moodButton3;
        public final Button moodButtonNormal;
        public final Button moodButton4;
        public final Button moodButton5;
        public Button addMoodButton;
        public Button expandMoodButton;
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




        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            Intent myIntent = new Intent(context, michaelbumes.therapysupportapp.activities.MoodActivity.class);
            myIntent.putExtra("moodId", moodDiaries.get(position).getId());
            context.startActivity(myIntent);



        }

    }








}
