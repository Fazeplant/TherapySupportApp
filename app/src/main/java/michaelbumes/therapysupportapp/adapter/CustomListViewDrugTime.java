package michaelbumes.therapysupportapp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import michaelbumes.therapysupportapp.R;

/**
 * Created by Michi on 08.03.2018.
 */

public class CustomListViewDrugTime extends ArrayAdapter<String> {

    private ArrayList<String> textViewDrugTime;
    private ArrayList<String> textViewDosage;
    private ArrayList<String> textViewDosageForm;
    private ImageButton imageButtonCancle;
    private Activity context;
    private View.OnClickListener durgTimeOnclickListener,dosageOnclickListener,dosageFormOnclickListener, imageButtonOnclickListener;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listview_layout_drug_time, null, true);
            viewHolder = new ViewHolder(r);
            viewHolder.tvw1.setText(textViewDrugTime.get(position));
            viewHolder.tvw2.setText(textViewDosage.get(position));
            viewHolder.tvw3.setText(textViewDosageForm.get(position));

            viewHolder.tvw1.setOnClickListener(this.durgTimeOnclickListener);
            viewHolder.tvw2.setOnClickListener(this.dosageOnclickListener);
            viewHolder.tvw3.setOnClickListener(this.dosageFormOnclickListener);
            viewHolder.imb.setOnClickListener(this.imageButtonOnclickListener);



            r.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) r.getTag();
        }





        return  r;
    }

    public CustomListViewDrugTime(Activity context, ArrayList<String> text1, ArrayList<String> text2, ArrayList<String> text3) {
        super(context, R.layout.listview_layout, text1);

        this.context = context;
        this.textViewDrugTime = text1;
        this.textViewDosage = text2;
        this.textViewDosageForm = text3;


    }

    class ViewHolder{
        TextView tvw1;
        TextView tvw2;
        TextView tvw3;
        ImageButton imb;
        ViewHolder(View v){
            tvw1 = v.findViewById(R.id.text_view_drug_time);
            tvw2 = v.findViewById(R.id.text_view_drug_time_dosage);
            tvw3 = v.findViewById(R.id.text_view_drug_time_dosage_form);
            imb = v.findViewById(R.id.image_button_drug_time);

        }
    }
    public void setDurgTimeOnclickListener(final View.OnClickListener onClickListener){
        this.durgTimeOnclickListener = onClickListener;
    }
    public void setDosageOnclickListener(final View.OnClickListener onClickListener){
        this.dosageOnclickListener = onClickListener;
    }
    public void setDosageFormeOnclickListener(final View.OnClickListener onClickListener){
        this.dosageFormOnclickListener = onClickListener;
    }
    public void setImageButtonOnclickListener(final View.OnClickListener onClickListener){
        this.imageButtonOnclickListener = onClickListener;
    }

} 

