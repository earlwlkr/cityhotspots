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
import me.earlwlkr.cityhotspots.model.Diner;
import me.earlwlkr.cityhotspots.model.DinerOptions;
import me.earlwlkr.cityhotspots.R;
import me.earlwlkr.cityhotspots.service.RestClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DinerOptionsActivity extends Activity implements View.OnClickListener {

    private Spinner spinnerCuisine;
    private Spinner spinnerCategory;
    private Spinner spinnerDistrict;
    private RangeSeekBar<Integer> priceRange;
    CircularProgressButton btnSearch;
    CityHotSpotsService service;

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
        priceRange.setRangeValues(min, max);
        priceRange.setSelectedMinValue(min);
        priceRange.setSelectedMaxValue(max);
    }

    private void fetchOptions(DinerOptions options) {
        setSpinnerData(spinnerCuisine, options.getCuisines());
        setSpinnerData(spinnerCategory, options.getCategories());
        setSpinnerData(spinnerDistrict, options.getDistricts());
        setPriceRangeSliderData(options.getPriceMin(), options.getPriceMax());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diner_options);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        spinnerCuisine = (Spinner) findViewById(R.id.spinner_option_diner_cuisine);
        spinnerCategory = (Spinner) findViewById(R.id.spinner_option_diner_category);
        spinnerDistrict = (Spinner) findViewById(R.id.spinner_option_diner_district);
        priceRange = (RangeSeekBar<Integer>) findViewById(R.id.price_range);
        btnSearch = (CircularProgressButton) findViewById(R.id.btn_search);
        btnSearch.setIndeterminateProgressMode(true);
        btnSearch.setOnClickListener(this);

        Bundle bundle = this.getIntent().getExtras();
        RestClient restClient = RestClient.getInstance();
        service = restClient.getApiService();

        DinerOptions options = Parcels.unwrap(bundle.getParcelable("options"));
        if (options == null) {
            options = service.getDinerOptions();
        }
        fetchOptions(options);
    }

    public void onClick(View v) {
        btnSearch.setProgress(50);
        String
                cuisine = spinnerCuisine.getSelectedItem().toString(),
                category = spinnerCategory.getSelectedItem().toString(),
                district = spinnerDistrict.getSelectedItem().toString();

        if (cuisine.equals("Tất cả")) {
            cuisine = null;
        }

        if (category.equals("Tất cả")) {
            category = null;
        }

        if (district.equals("Tất cả")) {
            district = null;
        }

        String price_min = priceRange.getSelectedMinValue().toString();
        String price_max = priceRange.getSelectedMaxValue().toString();

        service.getDiners(cuisine, district, category,
                price_min, price_max,
                "6",
                new Callback<List<Diner>>() {
                    @Override
                    public void success(List<Diner> diners, Response response) {
                        btnSearch.setProgress(100);
                        if (diners.isEmpty()) {
                            Toast.makeText(getApplicationContext(), "Không tìm thấy địa điểm", Toast.LENGTH_LONG).show();
                            btnSearch.setProgress(0);
                        } else {

                        }
                        System.out.println(diners.size());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        btnSearch.setProgress(-1);
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
