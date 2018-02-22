package michaelbumes.therapysupportapp.fragments;


import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.MoodDiary;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */

public class NoteFragment extends BaseFragment {
    private static final String TAG = NoteFragment.class.getName();
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int ACTIVITY_START_CAMERA_APP = 1;
    private static final int MIC_FLAG = 10;
    private static final int PHOTO_FLAG = 11;
    private static final int VIDEO_FLAG = 12;





    int flag = 0;
    EditText noteEdit;
    String noteText;
    ImageButton micButton, photoButton, videoButton;
    Button addButton;
    ImageView noteImage;
    VideoView noteVideo;
    String mCurrentPhotoPath;
    String mCurrentVideoPath;
    File image;
    File video;






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
        noteEdit = view.findViewById(R.id.note_text);
        micButton=view.findViewById(R.id.mic_button);
        photoButton=view.findViewById(R.id.photo_button);
        videoButton=view.findViewById(R.id.video_button);
        addButton = view.findViewById(R.id.ad_note);
        noteImage = view.findViewById(R.id.note_image);
        noteVideo = view.findViewById(R.id.note_video);
        noteVideo.setEnabled(false);


        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    image = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "peter.provider",
                        image);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(intent,REQUEST_TAKE_PHOTO);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callVideoIntent = new Intent();
                callVideoIntent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);


                startActivityForResult(callVideoIntent, ACTIVITY_START_CAMERA_APP);
            }
        });
        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                noteVideo.start();
            }
        });


    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view1 = inflater.inflate(R.layout.fragment_note, container, false);
        return view1;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED){
            File deleteFile = new File(mCurrentPhotoPath);
            boolean deleted = deleteFile.delete();
        }
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bitmap myBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            noteImage.setVisibility(View.VISIBLE);
            noteImage.setImageBitmap(myBitmap);
            flag = PHOTO_FLAG;
        }else if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK){
            flag = VIDEO_FLAG;
            Uri videoUri = data.getData();
            noteVideo.setVisibility(View.VISIBLE);
            noteVideo.setAlpha(1);
            noteVideo.setVideoURI(videoUri);

            try {
                String videoFileName = getVideoName();
                File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_MOVIES);
                AssetFileDescriptor videoAsset = getActivity().getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                FileInputStream fis = videoAsset.createInputStream();

                video = new File(storageDir, videoFileName);
                mCurrentVideoPath = video.getAbsolutePath();


                Log.d(TAG, "createt File:"+videoFileName+ " to:" + storageDir );

                FileOutputStream fos = new FileOutputStream(video);
                byte[] buf = new byte[1024];
                int len;
                while ((len = fis.read(buf)) > 0) {
                    fos.write(buf, 0, len);
                }
                fis.close();
                fos.close();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private File createImageFile() throws IOException {
        String imageFileName = getImageName();
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private String getImageName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
        String timestamp = simpleDateFormat.format(new Date());
        return "Note_Image_" + timestamp;
    }

    private String getVideoName(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss");
        String timestamp = simpleDateFormat.format(new Date());
        return "Note_Video_" + timestamp + ".mp4";
    }


    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }

    private void addNote() {
        noteText = noteEdit.getText().toString();
        if(TextUtils.isEmpty(noteText)){
            noteEdit.setError("Sie müssen eine Notiz hinzufügen");
            return;
        }else {
            Calendar calendar =  Calendar.getInstance();
            MoodDiary moodDiary = new MoodDiary();
            moodDiary.setDate(((int) calendar.getTimeInMillis()));
            moodDiary.setInfo1(noteText.toString());
            moodDiary.setArtID(3);
            switch (flag) {
                case 1:
                    //ToDo: Notiz hinzufügen
                    break;
                case 2:
                    //ToDo: Notiz + Sprache
                    break;
                case PHOTO_FLAG:
                    //Notiz + Foto hinzufügen
                    moodDiary.setInfo2(mCurrentPhotoPath);
                    break;

                case VIDEO_FLAG:
                    moodDiary.setInfo2(mCurrentVideoPath);
                    break;

            }
            AppDatabase.getAppDatabase(getContext()).moodDiaryDao().insertAll(moodDiary);
            Toast.makeText(getContext(), "Notiz hinzugefügt", Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();

            return;
        }
    }





}
