package michaelbumes.therapysupportapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

import michaelbumes.therapysupportapp.R;


public class LoginScreen extends AppCompatActivity {
    CardView cardView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        cardView = findViewById(R.id.cardView);

    }

    public void onClick(View view) {
        Intent intent = getIntent();
        String value = intent.getStringExtra("key");
        Intent myIntent = new Intent(LoginScreen.this, michaelbumes.therapysupportapp.activities.MainActivity.class);
        myIntent.putExtra("key", value); //Optional parameters
        LoginScreen.this.startActivity(myIntent);
    }
}
