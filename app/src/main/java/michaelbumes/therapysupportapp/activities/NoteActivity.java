package michaelbumes.therapysupportapp.activities;


        import android.content.Intent;
        import android.content.res.AssetFileDescriptor;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.Matrix;
        import android.media.ExifInterface;
        import android.net.Uri;
        import android.os.Bundle;
        import android.app.Fragment;
        import android.os.Environment;
        import android.provider.MediaStore;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.content.FileProvider;
        import android.support.v7.app.AppCompatActivity;
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
        import android.widget.MediaController;
        import android.widget.RelativeLayout;
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
        import java.util.concurrent.ExecutionException;

        import michaelbumes.therapysupportapp.R;
        import michaelbumes.therapysupportapp.database.AppDatabase;
        import michaelbumes.therapysupportapp.entity.MoodDiary;

        import static android.app.Activity.RESULT_CANCELED;
        import static android.app.Activity.RESULT_OK;


public class NoteActivity extends AppCompatActivity {

    private static final String TAG = NoteActivity.class.getName();
    private static final int REQUEST_TAKE_PHOTO = 0;
    private static final int ACTIVITY_START_CAMERA_APP = 1;
    private static final int MIC_FLAG = 10;
    private static final int PHOTO_FLAG = 11;
    private static final int VIDEO_FLAG = 12;

    int flag = 0;
    EditText noteEdit;
    String noteText;
    Button photoButton, videoButton;
    RelativeLayout relativeLayout;
    Button addButton;
    ImageView noteImage;
    VideoView noteVideo;
    String mCurrentPhotoPath;
    String mCurrentVideoPath;
    File image;
    File video;

    ExifInterface exif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        this.setTitle(R.string.add_note);
        noteEdit =  findViewById(R.id.note_text);
        photoButton = findViewById(R.id.photo_button);
        videoButton = findViewById(R.id.video_button);
        addButton = findViewById(R.id.ad_note);
        noteImage = findViewById(R.id.note_image);
        noteVideo = findViewById(R.id.note_video);
        relativeLayout = findViewById(R.id.relative_layout);



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
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED){
            try {
                File deleteFile = new File(mCurrentPhotoPath);
                boolean deleted = deleteFile.delete();
            }catch (Exception e){
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
            if (rotation != 0f) {matrix.preRotate(rotationInDegrees);}

            Bitmap myBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);

            Bitmap adjustedBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
            noteImage.setImageBitmap(adjustedBitmap);

            relativeLayout.setVisibility(View.VISIBLE);
            noteImage.setImageBitmap(myBitmap);
            photoButton.setVisibility(View.GONE);
            videoButton.setVisibility(View.GONE);
            flag = PHOTO_FLAG;
        }else if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK){
            flag = VIDEO_FLAG;
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(noteVideo);
            noteVideo.setMediaController(mediaController);
            Uri videoUri = data.getData();
            relativeLayout.setVisibility(View.VISIBLE);
            photoButton.setVisibility(View.GONE);
            videoButton.setVisibility(View.GONE);
            noteVideo.setVideoURI(videoUri);
            noteVideo.seekTo(1);

            try {
                String videoFileName = getVideoName();
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_MOVIES);
                AssetFileDescriptor videoAsset = getContentResolver().openAssetFileDescriptor(data.getData(), "r");
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
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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
        sendBroadcast(mediaScanIntent);
    }

    private void addNote() {
        noteText = noteEdit.getText().toString();
        if(TextUtils.isEmpty(noteText)){
            noteEdit.setError("Sie müssen eine Notiz hinzufügen");
            return;
        }else {
            Calendar calendar =  Calendar.getInstance();
            MoodDiary moodDiary = new MoodDiary();
            Date currentDate = Calendar.getInstance().getTime();
            moodDiary.setDate(currentDate);
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
            AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().insertAll(moodDiary);
            Toast.makeText(this, "Notiz hinzugefügt", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }


    public void add_photo(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        try {
            image = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(),
                "peter.provider",
                image);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);

        startActivityForResult(intent,REQUEST_TAKE_PHOTO);

    }

    private static int exifToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) { return 90; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {  return 180; }
        else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {  return 270; }
        return 0;
    }

}

