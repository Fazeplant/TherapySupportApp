package michaelbumes.therapysupportapp.adapter;

/**
 * Created by Michi on 08.04.2018.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.NoteActivity;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.TakenDrug;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.MainActivity;
import michaelbumes.therapysupportapp.alarms.AlarmMain;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.DrugEventDb;
import michaelbumes.therapysupportapp.entity.TakenDrug;
import michaelbumes.therapysupportapp.fragments.DrugEvent;
import michaelbumes.therapysupportapp.fragments.DrugFragment;

/**
 * Created by Michi on 07.04.2018.
 */

public class TakenDrugAdapter extends RecyclerView.Adapter<TakenDrugAdapter.ViewHolder> {
    List<TakenDrug> takenDrug;
    private Context context;
    int instanceInt = 0;


    public TakenDrugAdapter(List<TakenDrug> takenDrug, Calendar calStartOfDay, Calendar calEndOfDay) {
        List<TakenDrug> returnList = new ArrayList<>();
        for (int i = 0; i < takenDrug.size(); i++) {
            if ( takenDrug.get(i).getDate().getTime() > calStartOfDay.getTime().getTime() && takenDrug.get(i).getDate().getTime() < calEndOfDay.getTime().getTime()) {
                returnList.add(takenDrug.get(i));

            }
        }

        this.takenDrug = returnList;
    }

    @Override
    public TakenDrugAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_taken_drug, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TakenDrugAdapter.ViewHolder holder, final int position) {
        holder.textViewDrugName.setText(takenDrug.get(position).getDrugName());
        holder.textViewDosageForm.setText(takenDrug.get(position).getDosageForm());
        holder.textViewDosage.setText(String.valueOf(takenDrug.get(position).getDosage()));
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        holder.textViewAlarmTime.setText(sdf.format(takenDrug.get(position).getDate()));

    }

    @Override
    public int getItemCount() {
        return takenDrug.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewDrugName, textViewDosage, textViewDosageForm, textViewAlarmTime;
        public ImageView imageView;



        public ViewHolder(View itemView) {
            super(itemView);
            textViewDrugName = itemView.findViewById(R.id.taken_drug_name);
            textViewAlarmTime = itemView.findViewById(R.id.taken_drug_plan_alarm_time);
            textViewDosage = itemView.findViewById(R.id.taken_drug_dosage);
            textViewDosageForm = itemView.findViewById(R.id.taken_drug_dosage_form);
            imageView = itemView.findViewById(R.id.image_taken_drug);


        }





    }


}
