package me.earlwlkr.cityhotspots.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import org.parceler.Parcels;

import java.util.List;

import me.earlwlkr.cityhotspots.R;
import me.earlwlkr.cityhotspots.models.Diner;

public class DinersListActivity extends FragmentActivity {
    private List<Diner> mDinersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diners_list);
        Bundle bundle = this.getIntent().getExtras();
        List<Diner> diners = Parcels.unwrap(bundle.getParcelable("diners"));
        mDinersList = diners;
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, DinersListFragment.createInstance(diners))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diners_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.map:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q=" +
                        Uri.encode(mDinersList.get(0).getAddress().getStreetAddress() +
                        mDinersList.get(0).getAddress().getDistrict())));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
