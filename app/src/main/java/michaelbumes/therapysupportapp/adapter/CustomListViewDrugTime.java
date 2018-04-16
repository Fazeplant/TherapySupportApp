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

import java.util.ArrayList;

import michaelbumes.therapysupportapp.R;

/**
 * Created by Michi on 08.03.2018.
 */

public class CustomListViewDrugTime extends ArrayAdapter<String> {

    private final ArrayList<String> textViewDrugTime;
    private final ArrayList<String> textViewDosage;
    private final ArrayList<String> textViewDosageForm;
    private ImageButton imageButtonCancle;
    private final Activity context;
    private View.OnClickListener durgTimeOnclickListener,dosageOnclickListener,dosageFormOnclickListener, imageButtonOnclickListener;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder = null;
        if (r==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listview_layout_drug_time, null, true);
            r.setNestedScrollingEnabled(false);
            r.setOverScrollMode(0);
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
        final TextView tvw1;
        final TextView tvw2;
        final TextView tvw3;
        final ImageButton imb;
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

