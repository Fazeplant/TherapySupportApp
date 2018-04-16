package michaelbumes.therapysupportapp.activities;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import michaelbumes.therapysupportapp.R;
import michaelbumes.therapysupportapp.database.AppDatabase;
import michaelbumes.therapysupportapp.entity.MoodDiary;

import static michaelbumes.therapysupportapp.activities.NoteActivity.exifToDegrees;

public class FoodActivity extends AppCompatActivity {
    private EditText textEditFood;
    private Button photoButtonFood;
    private Button adFood;
    private RadioGroup radioGroup;
    private ImageView imageViewFood;
    private String mCurrentPhotoPath;
    private File image;
    private boolean photoTaken = false;
    private static final int REQUEST_TAKE_PHOTO = 0;
    private ExifInterface exif;
    private int foodTypeId = -1;
    private MoodDiary moodDiaryToday;







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

        RadioButton radioButtonBreakfast = findViewById(R.id.radio_button_food_breakfast);
        RadioButton radioButtonSnack1 = findViewById(R.id.radio_button_food_snack1);
        RadioButton radioButtonLunch = findViewById(R.id.radio_button_food_lunch);
        RadioButton radioButtonSnack2 = findViewById(R.id.radio_button_food_snack2);
        RadioButton radioButtonDinner = findViewById(R.id.radio_button_food_dinner);
        RadioButton radioButtonSnack3 = findViewById(R.id.radio_button_food_snack3);
        moodDiaryToday = null;

        Intent intent = getIntent();
        int id = intent.getIntExtra("foodId", -1);


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

                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        });
        adFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFood();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio_button_food_snack:
                        foodTypeId = 1;
                        break;
                    case R.id.radio_button_food_breakfast:
                        foodTypeId = 2;
                        break;
                    case R.id.radio_button_food_snack1:
                        foodTypeId = 3;
                        break;
                    case R.id.radio_button_food_lunch:
                        foodTypeId = 4;
                        break;
                    case R.id.radio_button_food_snack2:
                        foodTypeId = 5;
                        break;
                    case R.id.radio_button_food_dinner:
                        foodTypeId = 6;
                        break;
                    case R.id.radio_button_food_snack3:
                        foodTypeId = 7;
                        break;
                }
            }
        });

        if (id != -1) {
            moodDiaryToday = AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().findById(id);
            List<String> arrayList = new ArrayList<String>(Arrays.asList(moodDiaryToday.getInfo1().split(",")));
            if (arrayList.size() > 1) {
                textEditFood.setText(arrayList.get(0));
                switch (Integer.valueOf(arrayList.get(1))) {
                    case 1:
                        radioGroup.check(R.id.radio_button_food_snack);
                        break;
                    case 2:
                        radioGroup.check(R.id.radio_button_food_breakfast);
                        break;
                    case 3:
                        radioGroup.check(R.id.radio_button_food_snack1);
                        break;
                    case 4:
                        radioGroup.check(R.id.radio_button_food_lunch);
                        break;
                    case 5:
                        radioGroup.check(R.id.radio_button_food_snack2);
                        break;
                    case 6:
                        radioGroup.check(R.id.radio_button_food_dinner);
                        break;
                    case 7:
                        radioGroup.check(R.id.radio_button_food_snack3);
                        break;
                }
            } else {
                textEditFood.setText(moodDiaryToday.getInfo1());
            }
            if (moodDiaryToday.getInfo2() != null) {
                mCurrentPhotoPath = moodDiaryToday.getInfo2();
                File imgFile = new File(moodDiaryToday.getInfo2());
                if (imgFile.exists()) {
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
                    imageViewFood.setImageBitmap(adjustedBitmap);
                }


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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss", Locale.getDefault());
        String timestamp = simpleDateFormat.format(new Date());
        return "Food_Image_" + timestamp;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            photoTaken = true;

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

            imageViewFood.setImageBitmap(adjustedBitmap);
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
            MoodDiary moodDiary = new MoodDiary();
            Date currentDate = Calendar.getInstance().getTime();
            if (moodDiaryToday == null){
                moodDiary.setDate(currentDate);
                moodDiary.setArtID(2);

            }else {
                moodDiary = moodDiaryToday;
            }

            if (foodTypeId == -1){
                moodDiary.setInfo1(foodText);
            }else {
                moodDiary.setInfo1(foodText + "," + String.valueOf(foodTypeId));
            }
            if (photoTaken) {
                moodDiary.setInfo2(mCurrentPhotoPath);
            }
            int result = AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().update(moodDiary);
            if (result <= 0){
                AppDatabase.getAppDatabase(getApplicationContext()).moodDiaryDao().insert(moodDiary);
            }
            Toast.makeText(this, "Mahlzeit hinzugefügt", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
}
