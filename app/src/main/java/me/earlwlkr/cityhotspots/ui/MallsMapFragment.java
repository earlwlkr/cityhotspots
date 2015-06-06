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
import me.earlwlkr.cityhotspots.models.Place;
import me.earlwlkr.cityhotspots.service.ShortestRouteFinder;
import me.earlwlkr.cityhotspots.utils.Utils;

/**
 * Show list of places on map for user to pick.
 */
public class MallsMapFragment extends Fragment implements GoogleMap.OnMarkerClickListener {

    MapView mapView;
    GoogleMap mMap;
    Location mLastLocation;
    LocationManager mLocationManager;
    ShortestRouteFinder mRouteFinder;

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

    public static MallsMapFragment createInstance(List<Place> malls) {
        MallsMapFragment fragment = new MallsMapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("malls", Parcels.wrap(malls));
        fragment.setArguments(bundle);
        return fragment;
    }

    public MallsMapFragment() {}

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
        mRouteFinder = new ShortestRouteFinder(mMap);
        List<Place> malls = Parcels.unwrap(getArguments().getParcelable("malls"));
        for (Place mall: malls) {
            ShowMarkers task = new ShowMarkers();
            task.execute(mall);
        }
        return v;
    }

    private class ShowMarkers extends AsyncTask<Place, Void, Place> {
        @Override
        protected Place doInBackground(Place... malls) {
            int count = 0;
            ArrayList<LatLng> positions = new ArrayList<LatLng>();
            Place mall = malls[0];
            me.earlwlkr.cityhotspots.models.Address addr = mall.getAddress();
            String address = addr.getStreetAddress() + " " + addr.getDistrict()
                    + " " + addr.getCity();
            LatLng pos = Utils.getLocationFromAddress(getActivity(), address);
            if (pos != null) {
                mall.setPosition(pos);
                positions.add(pos);
                SystemClock.sleep(20);
            } else {
                // Handle Google API rate limit
                SystemClock.sleep(20);
                address = addr.getStreetAddress() + " " + addr.getDistrict();
                pos = Utils.getLocationFromAddress(getActivity(), address);
                if (pos != null) {
                    mall.setPosition(pos);
                    positions.add(pos);
                } else {
                    System.out.println(address);
                    System.out.println(++count);
                }
            }
            return mall;
        }

        @Override
        protected void onPostExecute(Place mall) {
            super.onPostExecute(mall);
            LatLng pos = mall.getPosition();
            if (pos != null) {
                mMap.addMarker(new MarkerOptions().position(pos).title(mall.getName())
                        .snippet(mall.getAddress().getAddressString()));
            }
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (mLastLocation != null) {
            mRouteFinder.findShortestRoute(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()),
                    marker.getPosition());
        }
        return false;
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
