package me.earlwlkr.cityhotspots.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.dd.CircularProgressButton;

import org.parceler.Parcels;

import java.util.List;

import me.earlwlkr.cityhotspots.models.Cinema;
import me.earlwlkr.cityhotspots.models.CinemaOptions;
import me.earlwlkr.cityhotspots.service.CityHotSpotsService;
import me.earlwlkr.cityhotspots.models.Diner;
import me.earlwlkr.cityhotspots.models.DinerOptions;
import me.earlwlkr.cityhotspots.R;
import me.earlwlkr.cityhotspots.service.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class CinemaOptionsActivity extends Activity implements View.OnClickListener {

    private Spinner mSpinnerTrademark;
    private CircularProgressButton mBtnSearch;
    private CityHotSpotsService mService;
    private CinemaOptions mOptions;

    private void setSpinnerData(Spinner spinner, List<String> data) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.spinner_item,
                data);
        dataAdapter.setDropDownViewResource(
                R.layout.spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void fetchOptions(CinemaOptions options) {
        setSpinnerData(mSpinnerTrademark, options.getTrademarks());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("options", Parcels.wrap(mOptions));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mOptions = Parcels.unwrap(savedInstanceState.getParcelable("options"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cinema_options);

        // Back button on ActionBar
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mSpinnerTrademark = (Spinner) findViewById(R.id.spinner_option_cinema_trademark);
        mBtnSearch = (CircularProgressButton) findViewById(R.id.btn_search);
        mBtnSearch.setIndeterminateProgressMode(true);
        mBtnSearch.setOnClickListener(this);

        RestClient restClient = RestClient.getInstance();
        mService = restClient.getApiService();

        // Get wrapped options object from MainActivity
        Bundle bundle = this.getIntent().getExtras();
        if (mOptions == null) {
            // If navigated back from DinersListActivity
            if (savedInstanceState != null) {
                mOptions = Parcels.unwrap(savedInstanceState.getParcelable("options"));
            } else if (bundle != null) {
                mOptions = Parcels.unwrap(bundle.getParcelable("options"));
            }
        }
        fetchOptions(mOptions);
    }

    public void onClick(View v) {
        // Set button loading state
        mBtnSearch.setProgress(50);
        String trademark = mSpinnerTrademark.getSelectedItem().toString();

        mService.getCinemas(trademark,
                new Callback<List<Cinema>>() {
                    @Override
                    public void success(List<Cinema> cinemas, Response response) {
                        mBtnSearch.setProgress(0);
                        if (cinemas.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Không tìm thấy địa điểm", Toast.LENGTH_LONG).show();
                        } else {
                            Intent i = new Intent(getApplicationContext(), CinemasListActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("cinemas", Parcels.wrap(cinemas));
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        mBtnSearch.setProgress(-1);
                    }
                });
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
