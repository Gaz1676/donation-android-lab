package app.donation;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Signup extends AppCompatActivity {

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mp = MediaPlayer.create(this, R.raw.button_music);

    }

    public void signupPressed (View view)
    {
        startActivity (new Intent(this, Donate.class));
        mp.start();
    }

}