package me.earlwlkr.cityhotspots;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.dd.CircularProgressButton;

import org.parceler.Parcels;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class DinerOptionsActivity extends Activity {

    private void setSpinnerData(int spinnerId, List<String> data) {
        Spinner spinner = (Spinner) findViewById(spinnerId);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                getApplicationContext(),
                R.layout.spinner_item,
                data);
        dataAdapter.setDropDownViewResource(
                R.layout.spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
    }

    private void fetchOptions(DinerOptions options) {
        setSpinnerData(R.id.spinner_option_diner_cuisine, options.getCuisines());
        setSpinnerData(R.id.spinner_option_diner_category, options.getCategories());
        setSpinnerData(R.id.spinner_option_diner_district, options.getDistricts());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diner_options);

        Bundle bundle = this.getIntent().getExtras();
        RestClient restClient = RestClient.getInstance();
        final CityHotSpotsService service = restClient.getApiService();

        DinerOptions options = Parcels.unwrap(bundle.getParcelable("options"));
        fetchOptions(options);

        int min = options.getPriceMin(), max = options.getPriceMax();
        final RangeSeekBar<Integer> price_range = (RangeSeekBar<Integer>) findViewById(R.id.price_range);
        price_range.setRangeValues(min, max);
        price_range.setSelectedMinValue(min);
        price_range.setSelectedMaxValue(max);

        final CircularProgressButton btnSearch = (CircularProgressButton) findViewById(R.id.btn_search);
        btnSearch.setIndeterminateProgressMode(true);
        btnSearch.setOnClickListener(new CircularProgressButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSearch.setProgress(50);
                String cuisine = null,
                        category = null,
                        district = null;
                int pos;
                Spinner spinner = (Spinner) findViewById(R.id.spinner_option_diner_cuisine);
                pos = spinner.getSelectedItemPosition();
                if (pos != 0) {
                    cuisine = spinner.getSelectedItem().toString();
                }

                spinner = (Spinner) findViewById(R.id.spinner_option_diner_category);
                pos = spinner.getSelectedItemPosition();
                if (pos != 0) {
                    category = spinner.getSelectedItem().toString();
                }

                spinner = (Spinner) findViewById(R.id.spinner_option_diner_district);
                pos = spinner.getSelectedItemPosition();
                if (pos != 0) {
                    district = spinner.getSelectedItem().toString();
                }


                String price_min = price_range.getSelectedMinValue().toString();
                String price_max = price_range.getSelectedMaxValue().toString();

                service.getDiners(cuisine, district, category,
                        price_min, price_max,
                        "6",
                        new Callback<List<Diner>>() {
                            @Override
                            public void success(List<Diner> diners, Response response) {
                                btnSearch.setProgress(100);
                                System.out.println(diners.size());
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                btnSearch.setProgress(-1);
                            }
                        });
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
