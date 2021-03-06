package michaelbumes.therapysupportapp.activities;


import android.content.CursorLoader;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.adapter.NoteAdapter;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.MoodDiary;
import michaelbumes.therapysupportapp.utils.MyVideoView;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;


public class NoteActivity extends AppCompatActivity {

    private static final String TAG = NoteActivity.class.getName();
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int ACTIVITY_START_CAMERA_APP = 1;
    private static final int PHOTO_FLAG = 11;
    private static final int VIDEO_FLAG = 12;

    private int flag = 0;
    private EditText noteEdit;
    private String noteText;
    private Button photoButton;
    private Button videoButton;
    private RelativeLayout relativeLayout;
    private Button addButton;
    private ImageView noteImage;
    private MyVideoView noteVideo;
    private String mCurrentPhotoPath;
    private String mCurrentVideoPath;
    private File image;
    private File video;
    private MoodDiary moodDiaryToday;

    private ExifInterface exif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        this.setTitle(R.string.add_note);
        noteEdit = findViewById(R.id.note_text);
        photoButton = findViewById(R.id.photo_button);
        videoButton = findViewById(R.id.video_button);
        addButton = findViewById(R.id.ad_note);
        noteImage = findViewById(R.id.note_image);
        noteVideo = findViewById(R.id.note_video);
        relativeLayout = findViewById(R.id.relative_layout);
        moodDiaryToday =null;


        //ID wird gesetzt falls der Eintrag bearbeitet wird, also nicht neu erstellt wird
        Intent intent = getIntent();
        int id = intent.getIntExtra("noteId", -1);

        //Foto Intent
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                try {
                    image = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                        "file.provider",
                        image);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNote();
            }
        });

        //Video Intent
        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callVideoIntent = new Intent();
                callVideoIntent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);


                startActivityForResult(callVideoIntent, ACTIVITY_START_CAMERA_APP);
            }
        });
        //Falls der Eintrag bearbeitet wird wird das Bild und die Notiz angezeigt
        if (id != -1) {
            moodDiaryToday = AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().findById(id);
            noteEdit.setText(moodDiaryToday.getInfo1());
            if (moodDiaryToday.getInfo2() != null && NoteAdapter.isImageFile(moodDiaryToday.getInfo2())) {
                mCurrentPhotoPath = moodDiaryToday.getInfo2();
                File imgFile = new File(moodDiaryToday.getInfo2());
                if (imgFile.exists()) {
                    //Bild wird korrekt gedreht falls falsch abgespeichert
                    try {
                        exif = new ExifInterface(moodDiaryToday.getInfo2());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
                    int rotationInDegrees = exifToDegrees(rotation);
                    Matrix matrix = new Matrix();
                    if (rotation != 0f) {
                        matrix.preRotate(rotationInDegrees);
                    }

                    Bitmap myBitmap = BitmapFactory.decodeFile(moodDiaryToday.getInfo2());

                    Bitmap adjustedBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
                    noteImage.setImageBitmap(adjustedBitmap);
                    relativeLayout.setVisibility(View.VISIBLE);

                    flag = PHOTO_FLAG;

                }
            //Falls Video vorhanden wird es angezeigt
            }else if (moodDiaryToday.getInfo2() != null && NoteAdapter.isVideoFile(moodDiaryToday.getInfo2())){
                mCurrentVideoPath = moodDiaryToday.getInfo2();
                MediaController mediaController = new MediaController(this);
                mediaController.setAnchorView(noteVideo);
                noteVideo.setMediaController(mediaController);

                Uri videoUri = Uri.parse(moodDiaryToday.getInfo2());
                relativeLayout.setVisibility(View.VISIBLE);
                photoButton.setText("Foto ändern");
                videoButton.setText("Video ändern");
                Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(moodDiaryToday.getInfo2(),MINI_KIND);
                noteVideo.setVideoSize(thumbnail.getWidth() *4, thumbnail.getHeight()*4);
                noteVideo.setVideoURI(videoUri);
                noteVideo.seekTo(1);
            }
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Löscht temporäres Foto
        if (resultCode == RESULT_CANCELED) {
            try {
                File deleteFile = new File(mCurrentPhotoPath);
                boolean deleted = deleteFile.delete();
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
            }
        }
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            //Bild korrekt drehen
            try {
                exif = new ExifInterface(mCurrentPhotoPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            int rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationInDegrees = exifToDegrees(rotation);
            Matrix matrix = new Matrix();
            if (rotation != 0f) {
                matrix.preRotate(rotationInDegrees);
            }

            Bitmap myBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);

            Bitmap adjustedBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
            noteImage.setImageBitmap(adjustedBitmap);
            noteVideo.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
            photoButton.setText("Foto ändern");
            videoButton.setText("Video ändern");
            flag = PHOTO_FLAG;
        } else if (requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
            flag = VIDEO_FLAG;
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(noteVideo);
            noteVideo.setMediaController(mediaController);
            relativeLayout.setVisibility(View.VISIBLE);
            noteImage.setVisibility(View.GONE);
            photoButton.setText("Foto ändern");
            videoButton.setText("Video ändern");


            try {
                //Speichert das Video im externen Speicher und zeigt es an
                String videoFileName = getVideoName();
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
                AssetFileDescriptor videoAsset = getContentResolver().openAssetFileDescriptor(data.getData(), "r");
                FileInputStream fis = videoAsset.createInputStream();


                video = new File(storageDir, videoFileName);
                mCurrentVideoPath = video.getAbsolutePath();



                FileOutputStream fos = new FileOutputStream(video);
                byte[] buf = new byte[1024];
                int len;
                while ((len = fis.read(buf)) > 0) {
                    fos.write(buf, 0, len);
                }
                fis.close();
                fos.close();

                Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(mCurrentVideoPath,MINI_KIND);
                noteVideo.setVideoSize(thumbnail.getWidth() *4, thumbnail.getHeight()*4);
                noteVideo.setVideoURI(Uri.parse(video.getAbsolutePath()));
                noteVideo.seekTo(1);
                File tempVideo = new File(getRealPathFromURI(data.getData()));
                if (tempVideo.exists()){
                    tempVideo.delete();
                    Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    scanIntent.setData(Uri.fromFile(tempVideo));
                    sendBroadcast(scanIntent);                }


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private File createImageFile() throws IOException {
        String imageFileName = getImageName();
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    private String getImageName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss", Locale.getDefault());
        String timestamp = simpleDateFormat.format(new Date());
        return "Note_Image_" + timestamp;
    }

    private String getVideoName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss", Locale.getDefault());
        String timestamp = simpleDateFormat.format(new Date());
        return "Note_Video_" + timestamp + ".mp4";
    }


    private void addNote() {
        noteText = noteEdit.getText().toString();
        if (TextUtils.isEmpty(noteText)) {
            noteEdit.setError("Sie müssen eine Notiz hinzufügen");
            return;
        } else {
            //Falls Eintrag neu erstellt wird
            MoodDiary moodDiary = new MoodDiary();
            if (moodDiaryToday == null){
                Date currentDate = Calendar.getInstance().getTime();
                moodDiary.setDate(currentDate);
                moodDiary.setArtID(3);

                //Falls Eintrag bearbeitet wird
            }else {
                moodDiary = moodDiaryToday;
            }

            moodDiary.setInfo1(noteText.toString());
            switch (flag) {
                case 2:
                    //Notiz + Sprache könnte man auch machen
                    break;
                case PHOTO_FLAG:
                    //Notiz + Foto hinzufügen
                    moodDiary.setInfo2(mCurrentPhotoPath);
                    break;

                case VIDEO_FLAG:
                    //Notiz + Video hinzufügen
                    moodDiary.setInfo2(mCurrentVideoPath);
                    break;

            }
            int result = AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().update(moodDiary);
            if (result <= 0){
                AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().insert(moodDiary);
            }
            Toast.makeText(this, "Notiz hinzugefügt", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }

    //Gibt Grad zurück wie das Bild gedreht werden muss
    public static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

}

