package me.earlwlkr.cityhotspots.ui;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.earlwlkr.cityhotspots.R;
import me.earlwlkr.cityhotspots.models.Diner;

/**
 * Show list of places on map for user to pick.
 */
public class MapViewFragment extends Fragment {

    MapView mapView;
    GoogleMap mMap;
    Location mLastLocation;
    LocationManager mLocationManager;

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            mLastLocation = location;
            if (mMap != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(),
                        mLastLocation.getLongitude()), 17));
            }
        }
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    };

    public static MapViewFragment createInstance(List<Diner> diners) {
        MapViewFragment fragment = new MapViewFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("diners", Parcels.wrap(diners));
        fragment.setArguments(bundle);
        return fragment;
    }

    public MapViewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map_view, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        mMap = mapView.getMap();
        mMap.setMyLocationEnabled(true);

        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 200l, 500.0f, mLocationListener);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        MapsInitializer.initialize(this.getActivity());

        // Updates the location and zoom of the MapView
        if (mLastLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastLocation.getLatitude(),
                    mLastLocation.getLongitude()), 17));
        }
        List<Diner> diners = Parcels.unwrap(getArguments().getParcelable("diners"));
        ShowMarkers task = new ShowMarkers();
        task.execute(diners);

        return v;
    }

    private class ShowMarkers extends AsyncTask<List<Diner>, Void, List<Diner>> {
        @Override
        protected List<Diner> doInBackground(List<Diner>... diners) {
            int count = 0;
            ArrayList<LatLng> positions = new ArrayList<LatLng>();
            for (Diner diner: diners[0])
            {
                String address = diner.getAddress().getStreetAddress() + " " + diner.getAddress().getDistrict()
                        + " " + diner.getAddress().getCity();
                LatLng dinerLatlng = getLocationFromAddress(address);
                if (dinerLatlng != null) {
                    diner.setPosition(dinerLatlng);
                    positions.add(dinerLatlng);
                } else {
                    System.out.println(address);
                    System.out.println(++count);
                }
            }
            return diners[0];
        }

        @Override
        protected void onPostExecute(List<Diner> diners) {
            super.onPostExecute(diners);
            for (Diner diner: diners) {
                LatLng pos = diner.getPosition();
                if (pos != null) {
                    mMap.addMarker(new MarkerOptions().position(pos).title(diner.getName()).snippet(diner.getCuisine()));
                }
            }
        }
    }

    public LatLng getLocationFromAddress(String strAddress) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> address = geocoder.getFromLocationName(strAddress, 1);

            if (address.size() > 0) {
                Address location = address.get(0);
                return new LatLng(location.getLatitude(), location.getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
