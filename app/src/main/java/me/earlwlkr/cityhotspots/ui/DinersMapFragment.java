package me.earlwlkr.cityhotspots.ui;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.SystemClock;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import me.earlwlkr.cityhotspots.R;
import me.earlwlkr.cityhotspots.models.Diner;
import me.earlwlkr.cityhotspots.service.ShortestRouteFinder;

/**
 * Show list of places on map for user to pick.
 */
public class DinersMapFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

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

    public static DinersMapFragment createInstance(List<Diner> diners) {
        DinersMapFragment fragment = new DinersMapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("diners", Parcels.wrap(diners));
        fragment.setArguments(bundle);
        return fragment;
    }

    public DinersMapFragment() {}

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
        mMap.setOnMarkerClickListener(this);

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
                me.earlwlkr.cityhotspots.models.Address addr = diner.getAddress();
                String address = addr.getStreetAddress() + " " + addr.getDistrict()
                        + " " + addr.getCity();
                LatLng pos = getLocationFromAddress(address);
                if (pos != null) {
                    diner.setPosition(pos);
                    positions.add(pos);
                    SystemClock.sleep(100);
                } else {
                    // Handle Google API rate limit
                    SystemClock.sleep(100);
                    address = addr.getStreetAddress() + " " + addr.getDistrict();
                    pos = getLocationFromAddress(address);
                    if (pos != null) {
                        diner.setPosition(pos);
                        positions.add(pos);
                    } else {
                        System.out.println(address);
                        System.out.println(++count);
                    }
                }
                // Handle Google API rate limit
                SystemClock.sleep(200);
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (mLastLocation != null) {
            new ShortestRouteFinder(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()),
                    marker.getPosition(), mMap).findShortestRoute();
        }
        return false;
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
