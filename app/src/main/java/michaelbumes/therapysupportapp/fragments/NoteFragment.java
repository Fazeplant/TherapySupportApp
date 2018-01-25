package michaelbumes.therapysupportapp.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import michaelbumes.therapysupportapp.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class NoteFragment extends BaseFragment {
    private static final String TAG = NoteFragment.class.getName();
    EditText noteText;
    ImageButton micButton, photoButton, videoButton;
    Button addButton;

    public static NoteFragment newInstance(int instance) {
        Bundle args = new Bundle();
        args.putInt(ARGS_INSTANCE, instance);
        NoteFragment fragment = new NoteFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.add_note);
        noteText = view.findViewById(R.id.note_text);
        micButton=view.findViewById(R.id.mic_button);
        photoButton=view.findViewById(R.id.photo_button);
        videoButton=view.findViewById(R.id.video_button);
        addButton = view.findViewById(R.id.ad_note);




    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_note, container, false);
        return view1;
    }
}
