package me.earlwlkr.cityhotspots.service;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import me.earlwlkr.cityhotspots.utils.Utils;

/**
 * Created by earl on 4/29/2015.
 */
public class ShortestRouteFinder {

    private PolylineOptions mPolyLineOptions;
    private GoogleMap mMap;
    private List<Polyline> mPolylines;

    public ShortestRouteFinder(GoogleMap map) {
        mPolyLineOptions = null;
        mMap = map;
        mPolylines = new ArrayList<Polyline>();
    }

    public void findShortestRoute(LatLng start, LatLng dest) {
        for (Polyline line : mPolylines) {
            line.remove();
        }

        mPolylines.clear();

        String url = getMapsApiDirectionsUrl(start, dest);
        ReadTask downloadTask = new ReadTask();
        downloadTask.execute(url);
    }

    private String getMapsApiDirectionsUrl(LatLng start, LatLng dest) {
        String waypoints = "origin="
                + start.latitude + "," + start.longitude
                + "&destination=" + dest.latitude + ","
                + dest.longitude;

        String output = "json";
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + output + "?" + waypoints;
        return url;
    }

    private class ReadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... url) {
            String data = "";
            try {
                data = Utils.readUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            new ParserTask().execute(result);
        }
    }
    private class ParserTask extends
            AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                routes = Utils.parseDirections(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            ArrayList<LatLng> points = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                mPolyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                mPolyLineOptions.addAll(points);
                mPolyLineOptions.width(8);
                mPolyLineOptions.color(Color.BLUE);
            }

            mPolylines.add(mMap.addPolyline(mPolyLineOptions));
        }
    }
}
