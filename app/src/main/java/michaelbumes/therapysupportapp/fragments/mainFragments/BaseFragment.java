package michaelbumes.therapysupportapp.fragments.mainFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ncapdevi.fragnav.FragNavTransactionOptions;

import michaelbumes.therapysupportapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseFragment extends Fragment {


    public static final String ARGS_INSTANCE = "com.ncapdevi.sample.argsInstance";

    FragmentNavigation fragmentNavigation;
    int instanceInt = 0;
    private View cachedView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            instanceInt = args.getInt(ARGS_INSTANCE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (cachedView == null) {
            cachedView = inflater.inflate(R.layout.fragment_base, container, false);
        }
        return cachedView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentNavigation) {
            fragmentNavigation = (FragmentNavigation) context;
        }
    }

    public interface FragmentNavigation {
        void pushFragment(Fragment fragment);
    }

}
