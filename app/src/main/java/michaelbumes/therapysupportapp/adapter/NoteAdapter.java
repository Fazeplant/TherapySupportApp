package michaelbumes.therapysupportapp.adapter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ncapdevi.fragnav.FragNavController;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    List<MoodDiary> notes;
    private Context context;
    int instanceInt = 0;



    public NoteAdapter(List<MoodDiary> notes) {
        Calendar calStartOfDay = Calendar.getInstance(TimeZone.getDefault());
        calStartOfDay.setTime(calStartOfDay.getTime()); // compute start of the day for the timestamp
        calStartOfDay.set(Calendar.HOUR_OF_DAY, 0);
        calStartOfDay.set(Calendar.MINUTE, 0);
        calStartOfDay.set(Calendar.SECOND, 0);
        calStartOfDay.set(Calendar.MILLISECOND, 0);

        Calendar calEndOfDay = Calendar.getInstance(TimeZone.getDefault());
        calEndOfDay.setTime(calEndOfDay.getTime()); // compute start of the day for the timestamp
        calEndOfDay.set(Calendar.HOUR_OF_DAY, 23);
        calEndOfDay.set(Calendar.MINUTE, 59);
        calEndOfDay.set(Calendar.SECOND, 59);
        calEndOfDay.set(Calendar.MILLISECOND, 999);
        List<MoodDiary> returnList = new ArrayList<>();
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).getArtID() == 3 && notes.get(i).getDate().getTime() > calStartOfDay.getTime().getTime() && notes.get(i).getDate().getTime() < calEndOfDay.getTime().getTime() ){
                returnList.add(notes.get(i));

            }
        }
        this.notes = returnList;
    }


    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_cardview_notes, parent,false );
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, final int position) {

        holder.textView.setText(notes.get(position).getInfo1());
        if (notes.get(position).getInfo2() != null){
            File imgFile = new  File(notes.get(position).getInfo2());
            if(imgFile.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.imageView.setImageBitmap(myBitmap);

            }
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void deleteItem(int adapterPosition) {
        AppDatabase.getAppDatabase(context).moodDiaryDao().delete(notes.get(adapterPosition));

        notes.remove(adapterPosition);
        notifyItemRemoved(adapterPosition);
        notifyItemRangeChanged(adapterPosition,notes.size());
        Toast.makeText(context, "Stimmungtagebuch Eintrag wurde gelöscht" , Toast.LENGTH_SHORT).show();

    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public ImageView imageView;






        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            textView = itemView.findViewById(R.id.text_view_food_today);
            imageView = itemView.findViewById(R.id.image_view_food_today);






        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            Intent myIntent = new Intent(context, michaelbumes.therapysupportapp.activities.MoodActivity.class);
            myIntent.putExtra("moodId", notes.get(position).getId());
            context.startActivity(myIntent);



        }

    }








}
