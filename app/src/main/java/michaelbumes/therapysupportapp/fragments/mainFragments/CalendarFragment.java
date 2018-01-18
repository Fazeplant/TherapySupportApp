package michaelbumes.therapysupportapp.fragments.mainFragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.dao.DrugDao;
import michaelbumes.therapysupportapp.database.AppDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends BaseFragment {
    Button nukeButton;

    public static CalendarFragment  newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.title_calendar);
        nukeButton = view.findViewById(R.id.nuke_button);
        nukeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppDatabase.destroyInstance();
                AppDatabase.getAppDatabase(getContext()).drugDao().nukeTable();
            }
        });

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }
}
