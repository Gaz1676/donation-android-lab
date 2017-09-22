package app.donation;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

// any CRUD done through this class
public class DonationApp extends Application {
    public final int target = 10000;
    public int totalDonated = 0;
    public List<Donation> donations = new ArrayList<>(); // creates new arraylist

    public boolean newDonation(Donation donation) {
        boolean targetAchieved = totalDonated > target;
        if (!targetAchieved) {
            donations.add(donation);
            totalDonated += donation.amount;
        } else {
            Toast toast = Toast.makeText(this, "Target Exceeded!", Toast.LENGTH_SHORT);
            toast.show();
        }
        return targetAchieved;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder() // adds new simpson font
                .setDefaultFontPath("fonts/simpsons.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}