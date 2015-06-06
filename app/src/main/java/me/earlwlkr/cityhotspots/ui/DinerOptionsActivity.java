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

import java.util.ArrayList;
import java.util.List;

import me.earlwlkr.cityhotspots.service.CityHotSpotsService;
import me.earlwlkr.cityhotspots.models.Diner;
import me.earlwlkr.cityhotspots.models.DinerOptions;
import me.earlwlkr.cityhotspots.R;
import me.earlwlkr.cityhotspots.service.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DinerOptionsActivity extends Activity implements View.OnClickListener {

    private Spinner mSpinnerCuisine;
    private Spinner mSpinnerCategory;
    private Spinner mSpinnerDistrict;
    private Spinner mSpinnerTime;
    private RangeSeekBar<Integer> mPriceRange;
    private CircularProgressButton mBtnSearch;
    private CityHotSpotsService mService;
    private DinerOptions mOptions;

    private void setSpinnerData(Spinner spinner, List<String> data) {
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.spinner_item,
                data);
        dataAdapter.setDropDownViewResource(
                R.layout.spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void setPriceRangeSliderData(int min, int max) {
        mPriceRange.setRangeValues(min, max);
        mPriceRange.setSelectedMinValue(min);
        mPriceRange.setSelectedMaxValue(max);
    }

    private void fetchOptions(DinerOptions options) {
        setSpinnerData(mSpinnerCuisine, options.getCuisines());
        setSpinnerData(mSpinnerCategory, options.getCategories());
        setSpinnerData(mSpinnerDistrict, options.getDistricts());
        List<String> data = new ArrayList<String>();
        data.add("Tất cả");
        for (int i = 0; i < 24; i++) {
            data.add(Integer.toString(i));
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.spinner_item,
                data);
        dataAdapter.setDropDownViewResource(
                R.layout.spinner_dropdown_item);
        mSpinnerTime.setAdapter(dataAdapter);
        setPriceRangeSliderData(options.getPriceMin(), options.getPriceMax());
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
        setContentView(R.layout.activity_diner_options);

        // Back button on ActionBar
        getActionBar().setDisplayHomeAsUpEnabled(true);

        mSpinnerCuisine = (Spinner) findViewById(R.id.spinner_option_diner_cuisine);
        mSpinnerCategory = (Spinner) findViewById(R.id.spinner_option_diner_category);
        mSpinnerDistrict = (Spinner) findViewById(R.id.spinner_option_diner_district);
        mSpinnerTime = (Spinner) findViewById(R.id.spinner_option_diner_time);
        mPriceRange = (RangeSeekBar<Integer>) findViewById(R.id.price_range);
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
        String
                cuisine = mSpinnerCuisine.getSelectedItem().toString(),
                category = mSpinnerCategory.getSelectedItem().toString(),
                district = mSpinnerDistrict.getSelectedItem().toString(),
                time = mSpinnerTime.getSelectedItem().toString();

        String price_min = mPriceRange.getSelectedMinValue().toString();
        String price_max = mPriceRange.getSelectedMaxValue().toString();

        if (time == "Tất cả") {
            time = null;
        }

        mService.getDiners(cuisine, district, category,
                price_min, price_max, time,
                new Callback<List<Diner>>() {
                    @Override
                    public void success(List<Diner> diners, Response response) {
                        mBtnSearch.setProgress(0);
                        if (diners.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Không tìm thấy địa điểm", Toast.LENGTH_LONG).show();
                        } else {
                            Intent i = new Intent(getApplicationContext(), DinersListActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("diners", Parcels.wrap(diners));
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
