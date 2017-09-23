package app.donation;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

// any CRUD done through this class
public class DonationApp extends Application {
    public final int target = 10000;
    public int totalDonated = 0;
    public List<Donation> donations = new ArrayList<>(); // creates new donations arraylist
    public List<User> users = new ArrayList<>(); // creates new users arraylist

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

    public void newUser(User user) {
        users.add(user);
    }

    public boolean validUser (String email, String password) {
        for (User user : users) {
            if (user.email.equals(email) && user.password.equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder() // adds new simpson font
                .setDefaultFontPath("fonts/simpsons.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        Log.v("Donate", "Donation App Started");
    }
}