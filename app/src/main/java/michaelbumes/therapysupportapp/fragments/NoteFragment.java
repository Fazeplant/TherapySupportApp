package michaelbumes.therapysupportapp.fragments;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import michaelbumes.therapysupportapp.BuildConfig;
import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.activities.MainActivity;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */

public class NoteFragment extends BaseFragment {
    private static final String TAG = NoteFragment.class.getName();
    private static final int REQUEST_CAMERA_IMAGE = 0 ;
    EditText noteText;
    ImageButton micButton, photoButton, videoButton;
    Button addButton;
    ImageView noteImage;
    String mCurrentPhotoPath;
    static final int REQUEST_TAKE_PHOTO = 1;
    File image;






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
        noteImage = view.findViewById(R.id.note_image);


        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String imageName = getImageName();
                File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

                try {
                     image = File.createTempFile(
                            imageName,  /* prefix */
                            ".jpg",         /* suffix */
                            storageDir
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mCurrentPhotoPath = image.getAbsolutePath();

                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "peter.provider",
                        image);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(intent,0);
            }
        });


    }

    private String getImageName() {
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        return "noteImage" + timeStamp;
    }

    private String getPictureName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
        String timestamp = simpleDateFormat.format(new Date());
        return "Note Image" + timestamp;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_note, container, false);
        return view1;

    }


}
