package app.donation.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import app.donation.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Welcome extends Activity {

    MediaPlayer mp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mp = MediaPlayer.create(this, R.raw.theme_music);
        mp.start();
    }

    public void loginPressed(View view) {
        startActivity(new Intent(this, Login.class));

        mp = MediaPlayer.create(this, R.raw.button_music);
        mp.start();

    }

    public void signupPressed(View view) {
        startActivity(new Intent(this, Signup.class));

        mp = MediaPlayer.create(this, R.raw.button_music);
        mp.start();

    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

}