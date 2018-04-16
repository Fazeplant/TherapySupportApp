package michaelbumes.therapysupportapp.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import michaelbumes.therapysupportapp.R;

/**
 * Created by Michi on 03.03.2018.
 */

public class CustomListView extends ArrayAdapter<String>{

    private final String[] text1;
    private final String[] text2;
    private final Activity context;


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r = convertView;
        ViewHolder viewHolder;
        if (r==null){
            LayoutInflater layoutInflater = context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listview_layout, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) r.getTag();
        }
        viewHolder.tvw1.setText(text1[position]);
        viewHolder.tvw2.setText(text2[position]);
        return  r;
    }

    public CustomListView(Activity context, String[] text1, String[] text2) {
        super(context, R.layout.listview_layout, text1);

        this.context = context;
        this.text1 = text1;
        this.text2 = text2;

    }

    class ViewHolder{
        final TextView tvw1;
        final TextView tvw2;
        ViewHolder(View v){
            tvw1 = v.findViewById(R.id.text_view_drug_1);
            tvw2 = v.findViewById(R.id.text_view_drug_2);

        }
    }
}
