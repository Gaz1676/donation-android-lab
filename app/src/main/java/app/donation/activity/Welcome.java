package app.donation.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import app.donation.R;
import app.donation.main.DonationApp;
import app.donation.model.Candidate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Welcome extends AppCompatActivity /*implements Callback<List<User>> */{
    private DonationApp app;

    MediaPlayer mp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        app = (DonationApp) getApplication();

        mp = MediaPlayer.create(this, R.raw.theme_music);
        mp.start();
    }

    @Override
    public void onResume() {
        super.onResume();
        app.currentUser = null;

        // requesting a list of candidates from the remote service, and storing them in the app object.
        // using an anonymous inner class instead of the Welcome class as the implementor of the callback interface.
        Call<List<Candidate>> call = app.donationServiceOpen.getAllCandidates();
        call.enqueue(new Callback<List<Candidate>>() {
            @Override
            public void onResponse(Call<List<Candidate>> call, Response<List<Candidate>> response) {
                serviceAvailableMessage();
                app.candidates = response.body();
            }

            @Override
            public void onFailure(Call<List<Candidate>> call, Throwable t) {
                serviceUnavailableMessage();
            }
        });
    }


    public void loginPressed(View view) {
        if (app.donationServiceAvailable) {
            startActivity(new Intent(this, Login.class));
            mp = MediaPlayer.create(this, R.raw.button_music);
            mp.start();
        } else {
            serviceUnavailableMessage();
        }
    }


    public void signupPressed(View view) {
        if (app.donationServiceAvailable) {
            startActivity(new Intent(this, Signup.class));
            mp = MediaPlayer.create(this, R.raw.button_music);
            mp.start();
        } else {
            serviceUnavailableMessage();
        }
    }


    void serviceUnavailableMessage() {
        app.donationServiceAvailable = false;
        Toast toast = Toast.makeText(this, "Well done!! you broke the services again", Toast.LENGTH_LONG);
        toast.show();
    }


    void serviceAvailableMessage() {
        app.donationServiceAvailable = true;
        Toast toast = Toast.makeText(this, "Donation Contacted Successfully", Toast.LENGTH_LONG);
        toast.show();
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
}