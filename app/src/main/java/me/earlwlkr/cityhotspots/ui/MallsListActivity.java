package me.earlwlkr.cityhotspots.ui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

import org.parceler.Parcels;

import java.util.List;

import me.earlwlkr.cityhotspots.R;
import me.earlwlkr.cityhotspots.models.Place;

public class MallsListActivity extends FragmentActivity {
    private List<Place> mMallsList;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("malls", Parcels.wrap(mMallsList));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_list);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle == null) {
            bundle = savedInstanceState;
        }
        mMallsList = Parcels.unwrap(bundle.getParcelable("malls"));
        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_results_list, MallsListFragment.createInstance(mMallsList))
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results_list, menu);
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
                Intent i = new Intent(getApplicationContext(), MallsMapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("malls", Parcels.wrap(mMallsList));
                i.putExtras(bundle);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
