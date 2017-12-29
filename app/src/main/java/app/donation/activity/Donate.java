package app.donation.activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import app.donation.R;
import app.donation.main.DonationApp;
import app.donation.model.Candidate;
import app.donation.model.Donation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



// implements Callback<Donation> interface in the class:
public class Donate extends AppCompatActivity implements Callback<Donation> {

    private int target = 10000;
    private RadioGroup paymentMethod;
    private ProgressBar progressBar;
    private NumberPicker amountPicker;
    private EditText amountText;
    private TextView amountTotal;
    private Spinner candidateSelection;
    private DonationApp app;

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        mp = MediaPlayer.create(this, R.raw.donate_music);

        app = (DonationApp) getApplication();

        paymentMethod = (RadioGroup) findViewById(R.id.paymentMethod);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        amountPicker = (NumberPicker) findViewById(R.id.amountPicker);
        amountTotal = (TextView) findViewById(R.id.totalText);
        amountText = (EditText) findViewById(R.id.amountText);

        amountPicker.setMinValue(0);
        amountPicker.setMaxValue(1000);
        progressBar.setMax(target);

        // initialises the spinner with the candidates list
        candidateSelection = (Spinner) findViewById(R.id.spinner);
        CandidateAdapter adapter = new CandidateAdapter(app.candidates);
        candidateSelection.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_donate, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // intent starts new activity
            case R.id.menuReport:
                startActivity(new Intent(this, Report.class));
                break;
            case R.id.menuSettings:
                Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
                mp = MediaPlayer.create(this, R.raw.button_music);
                mp.start();
                break;
            case R.id.menuLogout:
                startActivity(new Intent(this, Welcome.class));
                mp = MediaPlayer.create(this, R.raw.button_music);
                mp.start();
                break;
        }
        return true;
    }


    public void donateButtonPressed(View view) {
        mp.start();
        String method = paymentMethod.getCheckedRadioButtonId() == R.id.payBuddy ? "PayBuddy" : "Direct";

        int donatedAmount = amountPicker.getValue();
        if (donatedAmount == 0) {
            String text = amountText.getText().toString();
            if (!text.equals(""))
                donatedAmount = Integer.parseInt(text);
        }

        // makes the call to the service:
        if (donatedAmount > 0) {
            Donation donation = new Donation(donatedAmount, method);
            Candidate candidate = (Candidate) candidateSelection.getSelectedItem();
            Call<Donation> call = app.donationService.createDonation(candidate._id, donation);
            call.enqueue(this);
        }

        // resets values to 0 and original text box state
        amountPicker.setValue(0);
        amountText.setText("");
    }


    @Override
    public void onResponse(Call<Donation> call, Response<Donation> response) {
        Toast toast = Toast.makeText(this, "Donation Accepted", Toast.LENGTH_SHORT);
        toast.show();
        app.newDonation(response.body());
        progressBar.setProgress(app.totalDonated);
        String totalDonatedStr = "Amount so far: €" + app.totalDonated;
        amountTotal.setText(totalDonatedStr);
        amountText.setText("");
        amountPicker.setValue(0);
    }

    @Override
    public void onFailure(Call<Donation> call, Throwable t) {
        Toast toast = Toast.makeText(this, "Error making donation", Toast.LENGTH_LONG);
        toast.show();
    }


    // handles the spinner we have just introduced to present the candidates list
    private class CandidateAdapter extends BaseAdapter implements SpinnerAdapter {
        private final List<Candidate> data;

        public CandidateAdapter(List<Candidate> data) {
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View recycle, ViewGroup parent) {
            TextView text;
            if (recycle != null) {
                text = (TextView) recycle;
            } else {
                text = (TextView) getLayoutInflater().inflate(
                        android.R.layout.simple_dropdown_item_1line, parent, false
                );
            }
            text.setTextColor(Color.BLACK);
            text.setText(data.get(position).firstName);
            return text;
        }
    }
}