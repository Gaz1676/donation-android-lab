package app.donation.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import app.donation.R;
import app.donation.main.DonationApp;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by gary on 22/09/17.
 */

public class Login extends AppCompatActivity {

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void loginPressed(View view) {
        DonationApp app = (DonationApp) getApplication();

        TextView email     = (TextView)  findViewById(R.id.Email);
        TextView password  = (TextView)  findViewById(R.id.Password);

        if (app.validUser(email.getText().toString(), password.getText().toString())) {
            startActivity (new Intent(this, Donate.class));
            mp = MediaPlayer.create(this, R.raw.woohoo_music);
            mp.start();
        } else {
            Toast toast = Toast.makeText(this, "Doh!!  Who are yooouuuu??", Toast.LENGTH_SHORT);
            toast.show();
            mp = MediaPlayer.create(this, R.raw.doh_music);
            mp.start();
        }
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
}
