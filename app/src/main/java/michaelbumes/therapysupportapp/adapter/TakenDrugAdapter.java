package michaelbumes.therapysupportapp.adapter;

/**
 * Created by Michi on 08.04.2018.
 */

import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.entity.TakenDrug;

/**
 * Created by Michi on 07.04.2018.
 */

public class TakenDrugAdapter extends RecyclerView.Adapter<TakenDrugAdapter.ViewHolder> {
    private final List<TakenDrug> takenDrug;
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
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm" , Locale.getDefault());
        holder.textViewAlarmTime.setText(sdf.format(takenDrug.get(position).getDate()));
        switch (takenDrug.get(position).getDosageFormId()){
            case 1:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ampoules));
                break;
            case 2:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hashtag));
                break;
            case 3:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hashtag));
                break;
            case 4:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_weight));
                break;
            case 5:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_inhalator));
                break;
            case 6:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_syringe));
                break;
            case 7:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pills));
                break;
            case 8:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_weight));
                break;
            case 9:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 10:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hashtag));
                break;
            case 11:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 12:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 13:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_suppositories));
                break;
            case 14:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ampoules));
                break;
            case 15:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hashtag));
                break;
            case 16:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hashtag));
                break;
            case 17:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_weight));
                break;
            case 18:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_inhalator));
                break;
            case 19:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_syringe));
                break;
            case 20:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pills));
                break;
            case 21:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_weight));
                break;
            case 22:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 23:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_hashtag));
                break;
            case 24:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 25:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 26:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_suppositories));
                break;
            case 27:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ampoules));
                break;
            case 28:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 29:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pills));
                break;
            case 30:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 31:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 32:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 33:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 34:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 35:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pills));
                break;
            case 36:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 37:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_suppositories));
                break;
            case 38:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 39:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_drop));
                break;
            case 40:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 41:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_medical_pills_couple));
                break;
            case 42:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_pills));
                break;
            case 43:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ampoules));
                break;
            case 44:
                holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_ampoules));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return takenDrug.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public final TextView textViewDrugName;
        public final TextView textViewDosage;
        public final TextView textViewDosageForm;
        public final TextView textViewAlarmTime;
        public final ImageView imageView;



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
