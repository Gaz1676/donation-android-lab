package app.donation;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Donate extends AppCompatActivity {

    private int target = 10000;
    private RadioGroup paymentMethod;
    private ProgressBar progressBar;
    private NumberPicker amountPicker;
    private EditText amountText;
    private TextView amountTotal;
    private DonationApp app;

    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);

        //audio initialized
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        // Inflating the sub_menu menu will add its menu items to the empty SubMenu created
        inflater.inflate(R.menu.menu_donate, menu);
        return super.onCreateOptionsMenu(menu);
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

        if (donatedAmount > 0) {
            app.newDonation(new Donation(donatedAmount, method));
            progressBar.setProgress(app.totalDonated);
            String totalDonatedStr = "Donuts: " + app.totalDonated;
            amountTotal.setText(totalDonatedStr);

            // resets values to 0 and original text box state
            amountPicker.setValue(0);
            amountText.setText("");
        }
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
}