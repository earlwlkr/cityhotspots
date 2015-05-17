package me.earlwlkr.cityhotspots;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.parceler.Parcels;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RestClient restClient = RestClient.getInstance();
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
                                    Intent i = new Intent(getApplicationContext(), DinerOptionsActivity.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putParcelable("options", Parcels.wrap(options));
                                    i.putExtras(bundle);
                                    startActivity(i);
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
