package michaelbumes.therapysupportapp.activities;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import michaelbumes.therapysupportapp.R;


public class MoodActivity extends AppCompatActivity {
    ImageView moodView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);
        this.setTitle(R.string.title_mood);
        moodView = findViewById(R.id.mood_view);

    }

}
