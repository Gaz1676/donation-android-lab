package app.donation.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import app.donation.R;
import app.donation.main.DonationApp;
import app.donation.model.User;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Signup extends AppCompatActivity {

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mp = MediaPlayer.create(this, R.raw.kinichiwa_music);

    }

    public void signupPressed(View view) {
        TextView firstName = (TextView)  findViewById(R.id.firstName);
        TextView lastName  = (TextView)  findViewById(R.id.lastName);
        TextView email     = (TextView)  findViewById(R.id.Email);
        TextView password  = (TextView)  findViewById(R.id.Password);

        User user = new User (firstName.getText().toString(), lastName.getText().toString(), email.getText().toString(), password.getText().toString());

        DonationApp app = (DonationApp) getApplication();
        app.newUser(user);
        startActivity(new Intent(this, Login.class));
        mp.start();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
}