package michaelbumes.therapysupportapp.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.MoodDiary;

public class FoodActivity extends AppCompatActivity {
    EditText textEditFood;
    Button photoButtonFood, adFood;
    RadioGroup radioGroup;
    ImageView imageViewFood;
    String mCurrentPhotoPath;
    File image;
    boolean photoTaken = false;
    private static final int REQUEST_TAKE_PHOTO = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        this.setTitle(R.string.title_food);
        textEditFood = findViewById(R.id.text_edit_food);
        adFood = findViewById(R.id.ad_food);
        photoButtonFood = findViewById(R.id.photo_button_food);
        radioGroup = findViewById(R.id.radio_group_food);
        imageViewFood = findViewById(R.id.image_view_food);



        photoButtonFood.setOnClickListener(new View.OnClickListener() {
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
        adFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFood();
            }
        });


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
        return "Food_Image_" + timestamp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            photoTaken = true;
            Bitmap myBitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            imageViewFood.setImageBitmap(myBitmap);
        }else {
            Toast.makeText(this, "Fehler", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void addFood() {
        String foodText = textEditFood.getText().toString();
        if (TextUtils.isEmpty(foodText)) {
            textEditFood.setError("Sie müssen eine Mahlzeit hinzufügen");
            return;
        } else {
            int radioId = radioGroup.getCheckedRadioButtonId();
            RadioButton radioButton = findViewById(radioId);
            Calendar calendar = Calendar.getInstance();
            MoodDiary moodDiary = new MoodDiary();
            Date currentDate = Calendar.getInstance().getTime();
            moodDiary.setDate(currentDate);
            moodDiary.setArtID(2);
            if (radioId == -1){
                moodDiary.setInfo1(foodText);
            }else {
                moodDiary.setInfo1(foodText + "," + radioButton.getText());
            }
            if (photoTaken) {
                moodDiary.setInfo2(mCurrentPhotoPath);
            }
            AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().insertAll(moodDiary);
            Toast.makeText(this, "Mahlzeit hinzugefügt", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
}
