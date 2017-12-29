// reworked the Signup Activity to create and send new user registrations to the remote service

package app.donation.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import app.donation.R;
import app.donation.main.DonationApp;
import app.donation.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup extends AppCompatActivity implements Callback<User> {
    private DonationApp app;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        app = (DonationApp) getApplication();

        mp = MediaPlayer.create(this, R.raw.kinichiwa_music);

    }

    public void signupPressed(View view) {
        TextView firstName = (TextView) findViewById(R.id.firstName);
        TextView lastName = (TextView) findViewById(R.id.lastName);
        TextView email = (TextView) findViewById(R.id.Email);
        TextView password = (TextView) findViewById(R.id.Password);

        User user = new User(firstName.getText().toString(),
                lastName.getText().toString(),
                email.getText().toString(),
                password.getText().toString());

        DonationApp app = (DonationApp) getApplication();

        // We send a new User details to the service here:
        Call<User> call = (Call<User>) app.donationServiceOpen.createUser(user);
        call.enqueue(this);
        mp.start();
    }

    // we deal with the responses here:
    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        Toast toast = Toast.makeText(this, "Signup Successful", Toast.LENGTH_LONG);
        toast.show();
        startActivity(new Intent(this, Welcome.class));
    }


    @Override
    public void onFailure(Call<User> call, Throwable t) {
        app.donationServiceAvailable = false;
        Toast toast = Toast.makeText(this, "Donation Service Unavailable. Try again later", Toast.LENGTH_LONG);
        toast.show();
        startActivity(new Intent(this, Welcome.class));
    }
}