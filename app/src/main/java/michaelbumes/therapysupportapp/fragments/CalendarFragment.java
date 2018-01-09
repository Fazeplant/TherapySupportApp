package michaelbumes.therapysupportapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import michaelbumes.therapysupportapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends BaseFragment {

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
        if (btn != null) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mFragmentNavigation != null) {
                        mFragmentNavigation.pushFragment(CalendarFragment.newInstance(mInt+1));
                    }
                }
            });
            btn.setText(getClass().getSimpleName() + " " + mInt);
        }
    }
}
