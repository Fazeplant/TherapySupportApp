package michaelbumes.therapysupportapp.fragments;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.alarms.AlarmMain;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.Drug;
import michaelbumes.therapysupportapp.entity.DrugList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends BaseFragment {

    public static TodayFragment  newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        TodayFragment fragment = new TodayFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.title_today);

        Button cancelButton = view.findViewById(R.id.cancleIntentButton);
        final DrugList drug = AppDatabase.getAppDatabase(getContext()).drugListDao().findByName("Isla Cassis");

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //AlarmMain.cancelAlarm(getContext(), drug.getId());
                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                Intent updateServiceIntent = new Intent(getContext(), AlarmMain.class);
                PendingIntent pendingUpdateIntent = PendingIntent.getService(getContext(), drug.getId(), updateServiceIntent, 0);



                Toast.makeText(getContext(), "lol", Toast.LENGTH_SHORT).show();

            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today, container, false);
    }
}

