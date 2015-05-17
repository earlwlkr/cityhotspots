package me.earlwlkr.cityhotspots;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RestClient restClient = new RestClient();
        final CityHotSpotsService service = restClient.getApiService();

        final ListView listView = (ListView) findViewById(R.id.main_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.main_menu, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                switch (position) {
                    case 1:
                        try {
                            service.getDinerOptions(new Callback<DinerOptions>() {
                                @Override
                                public void success(DinerOptions options, Response response) {
                                    // got the list of contributors
                                    setContentView(R.layout.layout_diner_options);
                                    Spinner spinner = (Spinner) findViewById(R.id.spinner);
                                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
                                            getApplicationContext(),
                                            R.layout.spinner_item,
                                            options.getCuisines());
                                    dataAdapter.setDropDownViewResource(
                                            R.layout.spinner_dropdown_item);
                                    spinner.setAdapter(dataAdapter);
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    // Code for when something went wrong
                                }
                            });
                        } catch (RetrofitError e) {
                            System.out.println(e.getResponse().getStatus());
                        }
                        break;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
