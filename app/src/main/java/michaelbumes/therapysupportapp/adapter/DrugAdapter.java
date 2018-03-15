package michaelbumes.therapysupportapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;

/**
 * Created by Michi on 31.01.2018.
 */

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.ViewHolder>{
    List<Drug> drugs;
    private Context context;


    public DrugAdapter(List<Drug> drugs) {
        this.drugs = drugs;
    }

    @Override
    public DrugAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.drugs_row, parent,false );
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DrugAdapter.ViewHolder holder, int position) {
        holder.drugName.setText(drugs.get(position).getDrugName());
        holder.drugManufacturer.setText(drugs.get(position).getManufacturer());
        //        holder.drugManufacturer.setText((CharSequence) AppDatabase.getAppDatabase(context).manufacturerDao().findById(drugs.get(position).getManufacturer()));


    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView drugName;
        public TextView drugManufacturer;

        public ViewHolder(View itemView) {
            super(itemView);
            drugName = itemView.findViewById(R.id.drug_name);
            drugManufacturer = itemView.findViewById(R.id.drug_manufacturer);
        }
    }
}
