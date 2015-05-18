package me.earlwlkr.cityhotspots.ui;

import android.app.Activity;
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
    private RangeSeekBar<Integer> mPriceRange;
    CircularProgressButton mBtnSearch;
    CityHotSpotsService mService;

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
        setPriceRangeSliderData(options.getPriceMin(), options.getPriceMax());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diner_options);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        mSpinnerCuisine = (Spinner) findViewById(R.id.spinner_option_diner_cuisine);
        mSpinnerCategory = (Spinner) findViewById(R.id.spinner_option_diner_category);
        mSpinnerDistrict = (Spinner) findViewById(R.id.spinner_option_diner_district);
        mPriceRange = (RangeSeekBar<Integer>) findViewById(R.id.price_range);
        mBtnSearch = (CircularProgressButton) findViewById(R.id.btn_search);
        mBtnSearch.setIndeterminateProgressMode(true);
        mBtnSearch.setOnClickListener(this);

        Bundle bundle = this.getIntent().getExtras();
        RestClient restClient = RestClient.getInstance();
        mService = restClient.getApiService();

        DinerOptions options = Parcels.unwrap(bundle.getParcelable("options"));
        if (options == null) {
            options = mService.getDinerOptions();
        }
        fetchOptions(options);
    }

    public void onClick(View v) {
        mBtnSearch.setProgress(50);
        String
                cuisine = mSpinnerCuisine.getSelectedItem().toString(),
                category = mSpinnerCategory.getSelectedItem().toString(),
                district = mSpinnerDistrict.getSelectedItem().toString();

        if (cuisine.equals("Tất cả")) {
            cuisine = null;
        }

        if (category.equals("Tất cả")) {
            category = null;
        }

        if (district.equals("Tất cả")) {
            district = null;
        }

        String price_min = mPriceRange.getSelectedMinValue().toString();
        String price_max = mPriceRange.getSelectedMaxValue().toString();

        mService.getDiners(cuisine, district, category,
                price_min, price_max,
                "6",
                new Callback<List<Diner>>() {
                    @Override
                    public void success(List<Diner> diners, Response response) {
                        mBtnSearch.setProgress(100);
                        if (diners.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Không tìm thấy địa điểm", Toast.LENGTH_LONG).show();
                            mBtnSearch.setProgress(0);
                        } else {

                        }
                        System.out.println(diners.size());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        mBtnSearch.setProgress(-1);
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_diner_options, menu);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
