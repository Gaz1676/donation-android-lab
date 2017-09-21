package app.donation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Report extends AppCompatActivity {
    private ListView listView;
    private DonationApp app;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        app = (DonationApp) getApplication();

        listView = (ListView) findViewById(R.id.reportList);
        DonationAdapter adapter = new DonationAdapter(this, app.donations);
        listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSettings:
                Toast.makeText(this, "Settings Selected - Not Set Up Yet", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuDonate:
                startActivity(new Intent(this, Donate.class));
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_report, menu);
        return super.onCreateOptionsMenu(menu);
    }

    class DonationAdapter extends ArrayAdapter<Donation> {
        private Context context;
        public List<Donation> donations;

        public DonationAdapter(Context context, List<Donation> donations) {
            super(context, R.layout.row_layout, donations);
            this.context = context;
            this.donations = donations;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.row_layout, parent, false);
            Donation donation = donations.get(position);
            TextView amountView = view.findViewById(R.id.row_amount);
            TextView methodView = view.findViewById(R.id.row_method);

            amountView.setText("" + donation.amount);
            methodView.setText(donation.method);

            return view;
        }

        @Override
        public int getCount() {
            return donations.size();
        }
    }
}